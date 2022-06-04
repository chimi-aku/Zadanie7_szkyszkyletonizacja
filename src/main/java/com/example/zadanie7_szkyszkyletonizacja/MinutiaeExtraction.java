package com.example.zadanie7_szkyszkyletonizacja;

import java.util.ArrayList;

public class MinutiaeExtraction {

    public static ImageMinutiae calculateAllCN(int[][] imgArray) {

        int numberOfRows = imgArray.length;
        int numberOfColumns = imgArray[0].length;
        int[][] CNArray = new int[numberOfRows][numberOfColumns];
        int[] numbersOfCN = new int[5];

        for(int row = 1; row < numberOfRows - 1; row++)
        {
            for(int col = 1; col < numberOfColumns - 1; col++)
            {
                if(imgArray[row][col] != 0) {

                    //Fillig up window to anylize
                    int[] pArr = new int[10];
                    pArr[0] = imgArray[row][col];
                    pArr[1] = imgArray[row][col + 1];
                    pArr[2] = imgArray[row - 1][col + 1];
                    pArr[3] = imgArray[row - 1][col];
                    pArr[4] = imgArray[row - 1][col - 1];
                    pArr[5] = imgArray[row][col - 1];
                    pArr[6] = imgArray[row + 1][col - 1];
                    pArr[7] = imgArray[row + 1][col];
                    pArr[8] = imgArray[row + 1][col + 1];
                    pArr[9] = pArr[1];

                    int CN = calculateCN(pArr);
                    CNArray[row][col] = CN;
                    numbersOfCN[CN]++;

                }


                //System.out.print(CNArray[row][col]);
            }
            //System.out.println();
        }

        return new ImageMinutiae(CNArray, numbersOfCN);

    }

    public static boolean compare(ImageMinutiae original, ImageMinutiae passed) {

        for (int i = 0; i < original.getNumbersOfCN().length; i++) {
            if( Math.abs(original.getNumbersOfCN()[i] - passed.getNumbersOfCN()[i]) == 0){
                return false;
            }
        }

        return true;

    }

    private static int calculateCN(int [] pArr) {

        int CN = 0;

        for(int i = 1; i <= 8; i++){
            CN += Math.abs(pArr[i] - pArr[i + 1]);
        }

        return CN / 2;

    }

}
