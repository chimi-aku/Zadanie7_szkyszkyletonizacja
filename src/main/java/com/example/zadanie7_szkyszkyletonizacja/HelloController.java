package com.example.zadanie7_szkyszkyletonizacja;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.awt.image.BufferedImage;


public class HelloController {

    @FXML private Button loadImageBtn;
    @FXML private Button k3mImageBtn;

    private BufferedImage originalImage;

    @FXML private ImageView originalImageView;
    @FXML private ImageView skeletonImageView;

    @FXML protected void onLoadImageBtnClick()
    {
        Stage thisStage = (Stage) loadImageBtn.getScene().getWindow();
        originalImage = com.example.zadanie7_szkyszkyletonizacja.FileHandler.LoadImage(thisStage);
        originalImageView.setImage(com.example.zadanie7_szkyszkyletonizacja.FileHandler.convertToFxImage(originalImage));

    }

    @FXML protected void onk3mImageBtnClick() {

    }
}