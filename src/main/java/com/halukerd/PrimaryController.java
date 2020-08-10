package com.halukerd;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.halukerd.db.Datasource;
import com.halukerd.subtitle.SubDicMergeAPI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.FileChooser;

public class PrimaryController {
    @FXML
    private Label pathLabel;
    @FXML
    Label nameLabel;

    @FXML
    private Button convertButton;
    @FXML
    private ProgressBar progressBar;

    @FXML
    public void singleFileSelection(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Subtitle Files", "*.srt", "*.sub", "*.ass"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("All", "*"));
        fileChooser.setTitle("Open File Dialog");
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {


            SubDicMergeAPI.getInstance().setSubtitleFile(file);
            System.out.println(file.getParent() + "\\" + file.getName());
            pathLabel.setVisible(true);
            nameLabel.setVisible(true);
            pathLabel.setText(file.getParent() + "\\");
            nameLabel.setText(file.getName());
            convertButton.setDisable(false);
        }
    }

    @FXML
    public void handleConvertAction(ActionEvent actionEvent) {
        Datasource.getInstance().open();
        SubDicMergeAPI.getInstance().createNewSubtitle();
        convertButton.setDisable(true);
        Datasource.getInstance().close();
    }
}
