package control;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class Controller {

    @FXML
    Label inputDisplay;

    public void Controller() {}

    @FXML
    private void initialize() {
    }


    @FXML
    private void btnPressed(ActionEvent event) {
        System.out.println("hi there!!!");
        Button btn = (Button) event.getSource();
        System.out.println(btn.getText());
    }

}
