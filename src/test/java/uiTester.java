import javafx.scene.control.Label;
import javafx.geometry.Point2D;
import org.junit.Test;
import org.junit.Before;
import org.junit.experimental.categories.Category;
import org.testfx.framework.junit.ApplicationTest;
import javafx.scene.*;
import javafx.scene.control.Button;
import org.junit.experimental.categories.Category;
import testclassifications.SlowTest;
import testclassifications.FastTest;
import testclassifications.UiTest;
import org.loadui.testfx.GuiTest;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import java.util.List;
import javafx.stage.Window;
import java.sql.*;
import java.util.Random;
import control.DBController;
import static org.testfx.util.DebugUtils.informedErrorMessage;
import static org.testfx.api.FxAssert.verifyThat;
import static org.hamcrest.Matchers.is;

@Category(UiTest.class)
public class uiTester extends ApplicationTest {

    // THE SAMPLE FXML file can't be found (probebly a package issue)
    @Override
    public void start(Stage stage) throws Exception {
        Parent mainNode = FXMLLoader.load(getClass().getResource("sample.fxml"));
        stage.setScene(new Scene(mainNode));
        stage.show();
        stage.toFront();
    }

    @Before
    public void setup_class() throws SQLException {
        DBController.init();
    }

    @Category(FastTest.class)
    @Test
    public void justRandomStuff() {
        Button btn1 = GuiTest.find("#btn1");
        Button btn3 = GuiTest.find("#btn3");
        Button minus = GuiTest.find("#minus");
        Button equal = GuiTest.find("#equal");
        Label display = (Label) GuiTest.find("#inputDisplay");
        clickOn("#btn3");
        clickOn("#btn1");
        clickOn("#minus");
        clickOn("#btn3");
        clickOn("#btn5");
        clickOn("#equal");
        verifyThat(display.getText(), is("-4"), informedErrorMessage(this));
    }

    @Category(SlowTest.class)
    @Test
    // TEST DESCRIPTION: Randomly click within the window for 30 seconds
    // If there any exception is thrown, force a failure and log it
    public void randomTest () {
        List<Window> windows = this.listWindows();

        // There should only be one window
        verifyThat(windows.size(), is(1), informedErrorMessage(this));

        Window window = windows.get(0);

        // Window information
        double width = window.widthProperty().doubleValue();
        double height = window.heightProperty().doubleValue();
        double x = window.getX();
        double y = window.getY();

        Random random = new Random();

        // Test is 30 seconds long
        long randomTestLength = 1_000_000_000L * 30; // Seconds

        long start = System.nanoTime();
        while (System.nanoTime() - start < randomTestLength) {
            // Choose a random point within the window
            Point2D point = new Point2D((float) random.nextInt((int) width) + x, (float) random.nextInt((int) height) + y);

            // Try clicking there
            try {
                this.clickOn(point);
            } catch (Exception e) {
                // Force a failure to log the exception
                // The test will now fail
                verifyThat(true, is(false), informedErrorMessage(this));
            }
        }
    }
}
