import javafx.scene.control.Label;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.testfx.framework.junit.ApplicationTest;
import javafx.scene.*;
import javafx.scene.control.Button;
import org.junit.experimental.categories.Category;
import testclassifications.SlowTest;
import testclassifications.UiTest;
import org.loadui.testfx.GuiTest;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
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

    // Example of UI test
    @Category(UiTest.class)
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
        verifyThat(display.getText(), is("-4"));
    }

}
