package sample;

import java.sql.*;
import java.util.ArrayList;

public class DBController {

    private static final int DEFAULT_QUANTITY = 10;

    // Note: DB
    public static DBController myDBC;

    private Connection connection;

    private DBController(Connection connection) {
        this.connection = connection;
    }

    public static void init(String name) {
        // TODO implement
    }

    public static void init() {
        // TODO implement
    }

    public void insertHistory(String input, String output) {
        // TODO implement
    }

    public ArrayList<String> getRecentHistory(int quantity) {
        // TODO implement
        return null;
    }

    public ArrayList<String> getRecentHistory() {
        // TODO implement
        return null;
    }
}