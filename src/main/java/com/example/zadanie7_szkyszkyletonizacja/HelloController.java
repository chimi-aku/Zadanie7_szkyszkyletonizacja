package com.example.zadanie7_szkyszkyletonizacja;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.awt.image.BufferedImage;


public class HelloController {


    private BufferedImage originalImage;
    @FXML private Button loadImageBtn;
    @FXML private ImageView originalImageView;
    @FXML private ImageView skeletonImageView;
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML protected void onLoadImageBtnClick()
    {
        Stage thisStage = (Stage) loadImageBtn.getScene().getWindow();
        originalImage = com.example.zadanie7_szkyszkyletonizacja.FileHandler.LoadImage(thisStage);
        originalImageView.setImage(com.example.zadanie7_szkyszkyletonizacja.FileHandler.convertToFxImage(originalImage));

    }
}