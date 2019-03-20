package sample;

import org.apache.derby.client.am.SqlException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import testclassifications.FastTest;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static sample.DBController.*;

public class DBControllerTest {

    @Before
    public void setUp() throws Exception {
        init("calc-db-test");
    }

    @After
    public void tearDown() throws Exception {
        myDBC.dropAll();
        close();
    }

    @Category(FastTest.class)
    @Test
    public void insertHistory() throws SQLException {
        String[] inputs = {"5+2"};
        String[] outputs = {"=7"};

        myDBC.insertHistory(inputs[0], outputs[0]);

        ArrayList<String> results = myDBC.getRecentHistory(1);

        assertThat(results.size(), is(1));
        assertThat(results.get(0), is(inputs[0] + outputs[0]));
    }

    @Category(FastTest.class)
    @Test
    public void getRecentHistory() throws SQLException {
        String[] inputs = {"5+2", "42+42", "999 + 1"};
        String[] outputs = {"=7", "=84", "=1000"};
        ArrayList<String> results;

        myDBC.insertHistory(inputs[0], outputs[0]);

        // Get 1 result
        results = myDBC.getRecentHistory(1);
        assertThat(results.size(), is(1));
        assertThat(results.get(0), is(inputs[0] + outputs[0]));

        // Getting without a quantity should return a result size of 1
        results = myDBC.getRecentHistory();
        assertThat(results.size(), is(1));
        assertThat(results.get(0), is(inputs[0] + outputs[0]));

        // Getting with a quantity larger than is available should return a result of the number available
        results = myDBC.getRecentHistory(42);
        assertThat(results.size(), is(1));
        assertThat(results.get(0), is(inputs[0] + outputs[0]));

        // Now add a second entry
        myDBC.insertHistory(inputs[1], outputs[1]);

        // Test again with explicit quantity
        // Results should be in descending order
        results = myDBC.getRecentHistory(2);
        assertThat(results.size(), is(2));
        assertThat(results.get(0), is(inputs[1] + outputs[1]));
        assertThat(results.get(1), is(inputs[0] + outputs[0]));

        // Test again with no quantity
        results = myDBC.getRecentHistory();
        assertThat(results.size(), is(2));
        assertThat(results.get(0), is(inputs[1] + outputs[1]));
        assertThat(results.get(1), is(inputs[0] + outputs[0]));

        // Test getting only one result
        results = myDBC.getRecentHistory(1);
        assertThat(results.size(), is(1));
        assertThat(results.get(0), is(inputs[1] + outputs[1]));

        // Finally, add a third entry
        myDBC.insertHistory(inputs[2], outputs[2]);

        // Just do a basic check on this one
        results = myDBC.getRecentHistory(3);
        assertThat(results.size(), is(3));
        assertThat(results.get(0), is(inputs[2] + outputs[2]));
        assertThat(results.get(1), is(inputs[1] + outputs[1]));
        assertThat(results.get(2), is(inputs[0] + outputs[0]));
    }

    @Category(FastTest.class)
    @Test
    public void getName() {
        assertThat(myDBC.getName(), is("calc-db-test"));
    }

    @Category(FastTest.class)
    @Test
    public void dropAll() throws SQLException {
        // A table must exist first before we can test dropping it
        assertThat(myDBC.tableExists("ENTRIES"), is(true));

        myDBC.dropAll();

        assertThat(myDBC.tableExists("ENTRIES"), is(false));
    }

    @Category(FastTest.class)
    @Test
    public void tableExists() throws SQLException {
        assertThat(myDBC.tableExists("ENTRIES"), is(true));
    }
}