package control;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import testclassifications.FastTest;

import java.sql.SQLException;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.junit.MatcherAssert.assertThat;

public class DBControllerInitializationTest {

    @Category(FastTest.class)
    @Test
    public void init() throws SQLException {
        // This test wouldn't be valid if myDBC already had a value, so check this first
        assertThat(DBController.myDBC, is(nullValue()));

        // Attempt to initialize
        DBController.init();

        // Verify that myDBC now has a value and has the correct name
        assertThat(DBController.myDBC, is(notNullValue()));
        assertThat(DBController.myDBC.getName(), is("calc-db-test"));

        // Attempt to close
        DBController.close();

        // myDBC should once again be null
        assertThat(DBController.myDBC, is(nullValue()));
    }

    @Category(FastTest.class)
    @Test
    public void named_init() throws SQLException {
        // This test wouldn't be valid if myDBC already had a value, so check this first
        assertThat(DBController.myDBC, is(nullValue()));

        String dbName = "calc-db-test-2";

        // Attempt to initialize
        DBController.init(dbName);

        // Verify that myDBC now has a value and has the correct name
        assertThat(DBController.myDBC, is(notNullValue()));
        assertThat(DBController.myDBC.getName(), is(dbName));

        // Attempt to close
        DBController.close();

        // myDBC should once again be null
        assertThat(DBController.myDBC, is(nullValue()));
    }
}