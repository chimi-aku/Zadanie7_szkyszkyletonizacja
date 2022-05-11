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
//        BufferedImage niblackBinarizedImg = Binarization.NiblackBinarization(originalImage, 10, -.3);
//        BufferedImage binarizedImg = Binarization.PhansalkarBinarization(originalImage, 30, 0.3);
//        BufferedImage sauvolaBinarizedImg = Binarization.SauvolaBinarization(originalImage, 5, 0.5);

        BufferedImage simpleBinarization = Binarization.simpleBinarization(originalImage, 140);
        int[][] imgArray = K3M.convertBinarizatedImgToArray2D(simpleBinarization);
        K3M.k3m(imgArray);


        skeletonImageView.setImage(FileHandler.convertToFxImage(simpleBinarization));
    }
}