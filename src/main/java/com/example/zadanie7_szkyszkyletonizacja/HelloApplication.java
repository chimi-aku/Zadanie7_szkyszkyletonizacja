package com.example.zadanie7_szkyszkyletonizacja;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.SneakyThrows;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
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
        String path = System.getProperty("user.dir") + "\\samples\\sample.jpg";

        // #1 image
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

        ImageMinutiae imageMinutiae1 = MinutiaeExtraction.calculateAllCN(skeletonImgArray);
        int[][] cnArray = imageMinutiae1.getCNArray();



        // #2 image

        path = System.getProperty("user.dir") + "\\samples\\sample_ob.jpg";

        try {
            img = ImageIO.read(new File(path));
        } catch (IOException e) {
            System.out.println("wrong image path");
        }

        BufferedImage simpleBinarization2 = Binarization.simpleBinarization(img, 120);

        int[][] imgArray2 = K3M.convertBinarizatedImgToArray2D(simpleBinarization2);
        //print2Darray(imgArray);

        int[][] skeletonImgArray2 = K3M.k3m(imgArray2);
        //print2Darray(skeletonImgArray);

        ImageMinutiae imageMinutiae2 = MinutiaeExtraction.calculateAllCN(skeletonImgArray2);
        int[][] cnArray2 = imageMinutiae1.getCNArray();



        System.out.println(MinutiaeExtraction.compare(imageMinutiae1, imageMinutiae2));;


        String path1 = System.getProperty("user.dir") + "palec.txt";
        String path2 = System.getProperty("user.dir") + "palec_ob.txt";

        save2DarrayToTXT(imgArray, path1);
        save2DarrayToTXT(imgArray2, path2);

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

    @SneakyThrows
    public void save2DarrayToTXT(int [][]array, String path) {

        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < array.length; i++)//for each row
        {
            for(int j = 0; j < array[0].length; j++)//for each column
            {
                builder.append(array[i][j]+"");//append to the output string
            }
            builder.append("\n");//append new line at the end of the row
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter(path));
        writer.write(builder.toString());//save the string representation of the board
        writer.close();

    }
}