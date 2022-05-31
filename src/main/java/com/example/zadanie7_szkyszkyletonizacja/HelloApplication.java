package com.example.zadanie7_szkyszkyletonizacja;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;



public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 800);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();


        // TESTING MINUTIAE

        BufferedImage img = null;
        String path = System.getProperty("user.dir") + "\\sample.jpg";

        try {
            img = ImageIO.read(new File(path));
        } catch (IOException e) {
            System.out.println("wrong image path");
        }

        BufferedImage simpleBinarization = Binarization.simpleBinarization(img, 140);

        int[][] imgArray = K3M.convertBinarizatedImgToArray2D(simpleBinarization);
        //print2Darray(imgArray);

        int[][] skeletonImgArray = K3M.k3m(imgArray);
        //print2Darray(skeletonImgArray);

        ImageMinutiae ImageMinutiae = MinutiaeExtraction.calculateAllCN(skeletonImgArray);

        BufferedImage skeletonImg = K3M.convertArraytoBinarizatedImg(skeletonImgArray);




    }

    public static void main(String[] args) {
        launch();


    }

    public void print2Darray(int [][]array)
    {
        int numberOfRows = array.length;
        int numberOfColumns = array[0].length;

        for(int row = 0; row < numberOfRows - 1; row++)
        {
            for(int col = 0; col < numberOfColumns - 1; col++)
            {
                System.out.print(array[row][col]);
            }
            System.out.println();
        }
    }
}