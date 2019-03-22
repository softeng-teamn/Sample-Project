package control;

import java.sql.*;
import java.util.ArrayList;

public class DBController {
    static DBController myDBC;

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

            try {
                statement.execute("CREATE TABLE ENTRIES (ID INT NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), USER_INPUT VARCHAR(50), USER_OUTPUT VARCHAR(50))");
            } catch (SQLException e) {
                e.printStackTrace();
            }

            statement.close();
        }
    }

    static void init(String name) throws SQLException {
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
        if (tableExists("ENTRIES")) {
            Statement statement = myDBC.connection.createStatement();
            try {
                statement.execute("DROP TABLE ENTRIES");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            statement.close();
        }
    }

    void insertHistory(String input, String output) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO ENTRIES (USER_INPUT, USER_OUTPUT) VALUES (?, ?)");

        try {
            statement.setString(1, input);
            statement.setString(2, output);

            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        statement.close();
    }

    ArrayList<String> getRecentHistory(int quantity) throws SQLException {
        ArrayList<String> history = new ArrayList<>();

        PreparedStatement statement;

        // If quantity is >0, attempt to return that many entries
        // Otherwise, return all entries
        if (quantity > 0) {
            statement = connection.prepareStatement("SELECT * FROM ENTRIES ORDER BY ID DESC FETCH NEXT ? ROWS ONLY");

            try {
                statement.setInt(1, quantity);
            } catch (SQLException e) {
                e.printStackTrace();
                statement.close();
                return history;
            }
        } else {
            statement = connection.prepareStatement("SELECT * FROM ENTRIES ORDER BY ID DESC");
        }

        try {
            ResultSet results = statement.executeQuery();

            while (results.next()) {
                history.add(results.getString(2) + results.getString(3));
            }

            results.close();

        } catch(SQLException e) {
            e.printStackTrace();
        }
        statement.close();

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