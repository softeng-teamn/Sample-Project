package control;

import control.Controller;
import javafx.scene.control.Button;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import sun.jvm.hotspot.utilities.Assert;
import testclassifications.FastTest;
import javax.script.ScriptException;

import static org.junit.Assert.*;

public class ControllerTest {

    @Category(FastTest.class)
    @Test
    public void computeEngine() throws ScriptException {
        Double errorDelta = 0.0;
        String test1 = "1+1";
        String test2 = "4-2";
        String test3 = "0.1+5";

        Controller control = new Controller();
        assertEquals(control.computeEngine(test1), 2.0, errorDelta);
        assertEquals(control.computeEngine(test2), 2.0, errorDelta);
        assertEquals(control.computeEngine(test3), 5.1, errorDelta);
    }

}
