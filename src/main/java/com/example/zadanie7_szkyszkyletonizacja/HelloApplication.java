package com.example.zadanie7_szkyszkyletonizacja;

import javafx.application.Application;
import javafx.stage.Stage;
import lombok.SneakyThrows;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Scanner;


public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), 1000, 800);
//        stage.setTitle("Hello!");
//        stage.setScene(scene);
//        stage.show();


        // TESTING MINUTIAE

//        testMinutiaeFromTXT("test1.txt");
//        testMinutiaeFromTXT("test2.txt");
//        testMinutiaeFromTXT("test3.txt");
//        testMinutiaeFromTXT("test4.txt");

//        testMinutiaeFromImg("imgtest1.jpg");
//        testMinutiaeFromImg("imgtest2.jpg");
        testMinutiaeFromImg("imgtest3.jpg");

        System.out.print("--------------ZANG SEUN--------------");
        testMinutiaeFromImgZang("imgtest3.jpg");




    }

    public static void main(String[] args) {
        launch();


    }

    public void print2Darray(int [][] array)
    {
        int numberOfRows = array.length;
        int numberOfColumns = array[0].length;

        for(int row = 0; row < numberOfRows - 1; row++)
        {
            for(int col = 0; col < numberOfColumns - 1; col++)
            {

                if (array[row][col] != 0) {
                    System.out.print(array[row][col]);
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }

    public void printArray(int [] array) {
        System.out.print('[');
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + ", ");
        }
        System.out.print("]\n");
    }

    @SneakyThrows
    public void save2DArrayToTXT(int [][]array, String path) {

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

    public int [][] loadFrom2DArray(String path) throws FileNotFoundException{

        int rows = 0;
        int cols = 0;
        Scanner file = null;


        try {
            file = new Scanner(new File(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Getting number of rows and coums of array
        while (file.hasNextLine()) {
            String line = file.nextLine();
            cols = Math.max(cols, line.length());
            rows++;
        }

        int[][] resArr = new int[rows][cols];

        // Reseting scanner
        try {
            file = new Scanner(new File(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Fillig up the array
        for(int row = 0; row < rows; row++) {
            String line = file.nextLine();
            for (int col = 0; col < cols; col++) {
                resArr[row][col] = Integer.parseInt(String.valueOf(line.charAt(col)));
            }
        }

        return resArr;

    }

    public void testMinutiaeFromTXT(String testFilePath) throws FileNotFoundException {

        int[][] test = loadFrom2DArray(testFilePath);
        ImageMinutiae imageMinutiae1 = MinutiaeExtraction.calculateAllCN(test);
        int[][] cnArray = imageMinutiae1.getCNArray();
        print2Darray(cnArray);
        System.out.print("CN array: ");
        printArray(imageMinutiae1.getNumbersOfCN());
        System.out.println();

    }

    @SneakyThrows
    public void testMinutiaeFromImg(String testFilePath) throws FileNotFoundException {

        BufferedImage img = null;

        try {
            img = ImageIO.read(new File(testFilePath));
        } catch (IOException e) {
            System.out.println("wrong image path");
        }


        BufferedImage simpleBinarization = Binarization.simpleBinarization(img, 120);
        int[][] imgArray = K3M.convertBinarizatedImgToArray2D(simpleBinarization);

        int[][] skeletonImgArray;
        skeletonImgArray = K3M.k3m(imgArray);



        ImageMinutiae imageMinutiae = MinutiaeExtraction.calculateAllCN(skeletonImgArray);
        int[][] cnArray = imageMinutiae.getCNArray();

        print2Darray(cnArray);
        System.out.print("CN array: ");
        printArray(imageMinutiae.getNumbersOfCN());
        System.out.println("********************");

    }

    public void testMinutiaeFromImgZang(String testFilePath) throws FileNotFoundException {

        BufferedImage img = null;

        try {
            img = ImageIO.read(new File(testFilePath));
        } catch (IOException e) {
            System.out.println("wrong image path");
        }


        BufferedImage simpleBinarization = Binarization.simpleBinarization(img, 120);
        int[][] imgArray = K3M.convertBinarizatedImgToArray2D(simpleBinarization);

        int[][] skeletonImgArray;
        skeletonImgArray = ZangSuen.thinImage(imgArray);



        ImageMinutiae imageMinutiae = MinutiaeExtraction.calculateAllCN(skeletonImgArray);
        int[][] cnArray = imageMinutiae.getCNArray();

        print2Darray(cnArray);
        System.out.print("CN array: ");
        printArray(imageMinutiae.getNumbersOfCN());
        System.out.println("********************");

    }






    /*

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
        String path3 = "test1.txt";

        save2DarrayToTXT(cnArray, path1);
        save2DarrayToTXT(cnArray2, path2);


        BufferedImage skeletonImg = K3M.convertArraytoBinarizatedImg(skeletonImgArray);


    */
}