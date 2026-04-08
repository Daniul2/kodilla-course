package com.kodilla;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.sql.*;

public class DbManagerTestSuite {

    @Test
    public void testSelectUsersWithAtLeastTwoItemsFixed() throws SQLException {
        try (Connection conn = DbManager.getConnection()) {

            // 1) Ensure users table exists and has ids
            try (ResultSet rs = conn.getMetaData().getTables(null, null, "users", new String[] {"TABLE"})) {
                Assertions.assertTrue(rs.next(), "Table 'users' not found");
            }

            // 2) Prefer the most likely join column in task table
            String chosenTable = "task";
            String chosenJoinCol = null;

            // check common candidate explicitly (case-insensitive)
            String[] candidates = {"USER_ID_ASSIGNEDTO", "user_id_assignedto", "user_id", "assigned_to", "userid", "userId"};
            for (String c : candidates) {
                if (columnExists(conn, chosenTable, c)) {
                    chosenJoinCol = getActualColumnName(conn, chosenTable, c);
                    break;
                }
            }

            // fallback: try to find any numeric column in task that matches users.id values
            if (chosenJoinCol == null) {
                chosenJoinCol = detectJoinColumnForTable(conn, "task", "users");
            }

            Assertions.assertNotNull(chosenJoinCol, "No join column found in table 'task' linking to users.id");

            // 3) Detect which user display column exists (prefer FULLNAME, then USERNAME, then name)
            String userDisplayCol = null;
            if (columnExists(conn, "users", "FULLNAME")) userDisplayCol = getActualColumnName(conn, "users", "FULLNAME");
            else if (columnExists(conn, "users", "USERNAME")) userDisplayCol = getActualColumnName(conn, "users", "USERNAME");
            else if (columnExists(conn, "users", "name")) userDisplayCol = getActualColumnName(conn, "users", "name");

            Assertions.assertNotNull(userDisplayCol, "No suitable user display column found (FULLNAME/USERNAME/name)");

            System.out.println("Using join: " + chosenTable + "." + chosenJoinCol + "  and users." + userDisplayCol);

            // 4) Build queries using exact column names (preserve casing)
            String selectQuery = String.format(
                    "SELECT u.%s AS user_name, COUNT(t.id) AS items_count " +
                            "FROM users u JOIN %s t ON u.id = t.%s " +
                            "GROUP BY u.id, u.%s " +
                            "HAVING COUNT(t.id) >= 2",
                    userDisplayCol, chosenTable, chosenJoinCol, userDisplayCol
            );

            String countQuery = String.format(
                    "SELECT COUNT(*) AS cnt FROM (" +
                            "  SELECT u.id FROM users u JOIN %s t ON u.id = t.%s " +
                            "  GROUP BY u.id HAVING COUNT(t.id) >= 2" +
                            ") sub",
                    chosenTable, chosenJoinCol
            );

            // 5) Execute select and print results
            int rowCount = 0;
            try (PreparedStatement ps = conn.prepareStatement(selectQuery);
                 ResultSet rs = ps.executeQuery()) {
                System.out.println("Users with >= 2 items:");
                while (rs.next()) {
                    String name = rs.getString("user_name");
                    int items = rs.getInt("items_count");
                    System.out.printf("- %s (items: %d)%n", name, items);
                    rowCount++;
                }
            }

            // 6) Get expected count and assert
            int expectedCount = 0;
            try (PreparedStatement ps = conn.prepareStatement(countQuery);
                 ResultSet rs = ps.executeQuery()) {
                if (rs.next()) expectedCount = rs.getInt("cnt");
            }

            Assertions.assertEquals(expectedCount, rowCount, "Number of users with at least 2 items should match");
        }
    }

    private boolean columnExists(Connection conn, String tableName, String columnName) throws SQLException {
        try (ResultSet rs = conn.getMetaData().getColumns(null, null, tableName, null)) {
            while (rs.next()) {
                String col = rs.getString("COLUMN_NAME");
                if (col.equalsIgnoreCase(columnName)) return true;
            }
        }
        return false;
    }

    private String getActualColumnName(Connection conn, String tableName, String columnName) throws SQLException {
        try (ResultSet rs = conn.getMetaData().getColumns(null, null, tableName, null)) {
            while (rs.next()) {
                String col = rs.getString("COLUMN_NAME");
                if (col.equalsIgnoreCase(columnName)) return col;
            }
        }
        return columnName; // fallback
    }

    // Try to detect a numeric column in 'tableName' that references users.id by checking matches > 0
    private String detectJoinColumnForTable(Connection conn, String tableName, String usersTable) throws SQLException {
        DatabaseMetaData md = conn.getMetaData();
        try (ResultSet cols = md.getColumns(null, null, tableName, "%")) {
            while (cols.next()) {
                String colName = cols.getString("COLUMN_NAME");
                String typeName = cols.getString("TYPE_NAME").toLowerCase();
                if (typeName.contains("int") || typeName.contains("bigint") || typeName.contains("numeric")) {
                    String sql = String.format("SELECT COUNT(*) AS cnt FROM %s WHERE %s IN (SELECT id FROM %s)", tableName, colName, usersTable);
                    try (Statement s = conn.createStatement();
                         ResultSet rs = s.executeQuery(sql)) {
                        if (rs.next() && rs.getInt("cnt") > 0) {
                            return colName;
                        }
                    } catch (SQLException ignored) {
                        // ignore columns that can't be compared
                    }
                }
            }
        }
        return null;
    }
}
