package com.kodilla.jdbc;

import com.kodilla.DbManager;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.assertEquals;

public class StoredProcTestSuite {

    @Test
    public void testUpdateVipLevels() throws SQLException {
        // Given
        DbManager dbManager = DbManager.getInstance();
        String sqlUpdate = "UPDATE READERS SET VIP_LEVEL=\"Not set\"";
        Statement statement = dbManager.getConnection().createStatement();
        statement.executeUpdate(sqlUpdate);
        String sqlCheckTable = "SELECT COUNT(*) AS HOW_MANY FROM READERS WHERE VIP_LEVEL=\"Not set\"";

        // When
        Statement statement2 = dbManager.getConnection().createStatement();
        String sqlProcedureCall = "CALL UpdateVipLevels()";
        statement2.execute(sqlProcedureCall);
        ResultSet rs = statement.executeQuery(sqlCheckTable);

        // Then
        int howMany = -1;
        if (rs.next()) {
            howMany = rs.getInt("HOW_MANY");
        }
        assertEquals(0, howMany);
        rs.close();
        statement.close();
        statement2.close();
    }
    @Test
    public void testUpdateBestsellers() throws SQLException {
        // Given
        DbManager dbManager = DbManager.getInstance();
        String sqlUpdate = "UPDATE BOOKS SET BESTSELLER = 0";
        Statement statement = dbManager.getConnection().createStatement();
        statement.executeUpdate(sqlUpdate);

        // When
        Statement statement2 = dbManager.getConnection().createStatement();
        statement2.execute("CALL UpdateBestsellers()");

        // Then
        ResultSet rs1 = statement.executeQuery(
                "SELECT BESTSELLER FROM BOOKS WHERE BOOK_ID = 1");
        if (rs1.next()) {
            assertEquals(0, rs1.getInt("BESTSELLER"));
        }
        rs1.close();

        ResultSet rs2 = statement.executeQuery(
                "SELECT BESTSELLER FROM BOOKS WHERE BOOK_ID = 5");
        if (rs2.next()) {
            assertEquals(1, rs2.getInt("BESTSELLER"));
        }
        rs2.close();

        ResultSet rs3 = statement.executeQuery(
                "SELECT BESTSELLER FROM BOOKS WHERE BOOK_ID = 3");
        if (rs3.next()) {
            assertEquals(0, rs3.getInt("BESTSELLER"));
        }
        rs3.close();

        statement.close();
        statement2.close();
    }
}
