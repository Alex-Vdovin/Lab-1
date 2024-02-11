package com.lab1.lab1;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class Controller {
    @FXML
    private Label label;
    @FXML
    private Label statistic;
    @FXML
    private AnchorPane pane;
    @FXML
    private Pane modelPane;

    public Pane getPane() {
        return modelPane;
    }

    public Label getStatistic() {
        return statistic;
    }

    public Label getTime() { return label; }

    public void printLabel(String str) {
        label.setText(str);
    }

    public void printStatistic(String str) {
        statistic.setText(str);
    }

    @FXML
    private void keyPressed(KeyEvent keyEvent)throws IOException {
        keyEvent.consume();
        if(keyEvent.getCode().equals(KeyCode.T)){
            Habitat.getInstance().timeFlag = !Habitat.getInstance().timeFlag;
            Habitat.getInstance().showTimeLabel();

        } else if (keyEvent.getCode().equals(KeyCode.B)) {
            if (!Habitat.startFlag) {
                Habitat.getInstance().startAction();
            }

        } else if (keyEvent.getCode().equals(KeyCode.E)) {
            if (Habitat.startFlag) {
                Habitat.getInstance().stopAction();
            }

        }
    }
    @FXML
    void initialize() {
    }
}