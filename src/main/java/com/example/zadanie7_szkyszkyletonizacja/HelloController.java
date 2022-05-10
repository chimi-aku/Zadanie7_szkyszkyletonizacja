package com.example.zadanie7_szkyszkyletonizacja;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.awt.image.BufferedImage;
import java.io.IOException;


public class HelloController {

    @FXML private Button loadImageBtn;
    @FXML private Button k3mImageBtn;

    private BufferedImage originalImage;

    @FXML private ImageView originalImageView;
    @FXML private ImageView skeletonImageView;

    @FXML protected void onLoadImageBtnClick()
    {
        Stage thisStage = (Stage) loadImageBtn.getScene().getWindow();
        originalImage = FileHandler.LoadImage(thisStage);
        originalImageView.setImage(FileHandler.convertToFxImage(originalImage));

    }

    @FXML protected void onk3mImageBtnClick() throws IOException {

        // Binarization Testing
        //BufferedImage binarizedImg = Binarization.NiblackBinarization(originalImage, 30, -.5);
        BufferedImage binarizedImg = Binarization.PhansalkarBinarization(originalImage, 30, 0.3);
        BufferedImage sauvolaBinarizedImg = Binarization.SauvolaBinarization(originalImage, 5, 0.5);

        skeletonImageView.setImage(FileHandler.convertToFxImage(sauvolaBinarizedImg));
    }
}