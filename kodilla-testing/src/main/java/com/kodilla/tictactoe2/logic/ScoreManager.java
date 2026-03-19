package com.kodilla.tictactoe2.logic;


import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ScoreManager {
    private static final String FILE_NAME = "scores.dat";

    public static void saveWin(String playerName) {
        Map<String, Integer> scores = loadScores();
        scores.put(playerName, scores.getOrDefault(playerName, 0) + 1);

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(scores);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Integer> loadScores() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return new HashMap<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (Map<String, Integer>) ois.readObject();
        } catch (Exception e) {
            return new HashMap<>();
        }
    }
}
