package control;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;
import javax.script.ScriptException;
import java.io.IOException;
import java.io.InvalidObjectException;

public class Controller {

    static private String mathString;

    @FXML
    Label inputDisplay;

    public void Controller() {}

    @FXML
    private void initialize() {
        this.mathString = "";
        inputDisplay.setText("");
        System.out.println("init Controller happened");
    }


    @FXML
    private void btnPressed(ActionEvent event) {
        Button btn = (Button) event.getSource();
        String id = btn.getText();
        switch (id) {
            case "=":
                try {
                    // Try to compute answer
                    Double displayNum = computeEngine(this.mathString);
                    if (isInt(displayNum)) {
                        // Forget that .0 !!
                        Integer smallNum = displayNum.intValue();
                        this.mathString = smallNum.toString();
                    } else {
                        this.mathString = String.format("%.2f", displayNum);
                    }
                } catch(ScriptException e) {
                    // Couldn't create result
                    System.err.println("Could not compute expression!");
                    // Show error
                    this.mathString = "That not math tho...";
                }

                break;
            case "C":
                // Clear value
                this.mathString = "";
                break;
            default:
                // Add any other button to the string
                this.mathString += id;
                break;
        }
        // Display new value to Label
        inputDisplay.setText(this.mathString);
    }

    private boolean isInt(Double num) {
        return (num == Math.floor(num) && !Double.isInfinite(num));
    }

    double computeEngine(String operation) throws ScriptException {
        ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine engine = mgr.getEngineByName("JavaScript");
        return new Double(engine.eval(operation).toString());
    }

}
