package control;

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
        if (!this.tableExists("ENTRIES")) {
            Statement statement = connection.createStatement();

            statement.execute("CREATE TABLE ENTRIES (ID INT NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), USER_INPUT VARCHAR(50), USER_OUTPUT VARCHAR(50))");
        }
    }

    public static void init(String name) throws SQLException {
        DriverManager.registerDriver(new org.apache.derby.jdbc.EmbeddedDriver());
        Connection connection = DriverManager.getConnection("jdbc:derby:" + name + ";create=true");

        myDBC = new DBController(connection, name);
    }

    public static void init() throws SQLException {
        init("calc-db-test");
    }

    public static void close() throws SQLException {
        myDBC.connection.close();
        myDBC = null;
    }

    public void dropAll() throws SQLException {
        Statement statement = myDBC.connection.createStatement();

        if (tableExists("ENTRIES")) {
            statement.execute("DROP TABLE ENTRIES");
        }
    }

    public void insertHistory(String input, String output) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO ENTRIES (USER_INPUT, USER_OUTPUT) VALUES (?, ?)");

        statement.setString(1, input);
        statement.setString(2, output);

        statement.execute();
    }

    public ArrayList<String> getRecentHistory(int quantity) throws SQLException {
        PreparedStatement statement;
        if (quantity > 0) {
            statement = connection.prepareStatement("SELECT * FROM ENTRIES ORDER BY ID DESC FETCH NEXT ? ROWS ONLY");

            statement.setInt(1, quantity);
        } else {
            statement = connection.prepareStatement("SELECT * FROM ENTRIES ORDER BY ID DESC");
        }

        ResultSet results = statement.executeQuery();
        ArrayList<String> history = new ArrayList<>();

        while (results.next()) {
            history.add(results.getString(2) + results.getString(3));
        }

        return history;
    }

    public ArrayList<String> getRecentHistory() throws SQLException {
        return getRecentHistory(0);
    }

    public String getName() {
        return name;
    }

    public boolean tableExists(String table) throws SQLException {
        DatabaseMetaData dbm = connection.getMetaData();
        ResultSet rs = dbm.getTables(null, null, table, null);
        return rs.next();
    }
}