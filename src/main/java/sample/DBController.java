package sample;

import java.sql.*;
import java.util.ArrayList;

public class DBController {

    private static final int DEFAULT_QUANTITY = 10;

    // Note: DB
    public static DBController myDBC;

    private Connection connection;
    private String name;

    private DBController(Connection connection, String name) throws SQLException {
        this.connection = connection;

        initializeTables();

        this.name = name;
    }

    private void initializeTables() throws SQLException {
        if (!this.tableExists("Entry")) {
            Statement statement = connection.createStatement();

            statement.execute("CREATE TABLE ENTRIES (ID INT PRIMARY KEY, USER_INPUT VARCHAR(50), USER_OUTPUT VARCHAR(50))");
        }
    }

    public static void init(String name) throws SQLException {
        DriverManager.registerDriver(new org.apache.derby.jdbc.EmbeddedDriver());
        Connection connection = DriverManager.getConnection("jdbc:derby:" + name + ";create=true");

        myDBC = new DBController(connection, name);
    }

    public static void init() throws SQLException {
        init("calc-db");
    }

    public static void close() throws SQLException {
        myDBC.connection.close();
        myDBC = null;
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

    public String getName() throws SQLException {
        return name;
    }

    private boolean tableExists(String table) throws SQLException {
        DatabaseMetaData dbm = connection.getMetaData();
        ResultSet rs = dbm.getTables(null, null, "ENTRIES", null);
        return rs.next();
    }
}