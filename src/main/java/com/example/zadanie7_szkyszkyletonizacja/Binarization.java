package com.example.zadanie7_szkyszkyletonizacja;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.IOException;

public class Binarization {

    private static int width;
    private static int height;
    public final int pixel_ob = 0xffffffff; //object pixel
    public final int pixel_bg = 0xff000000; //background pixel

    public static BufferedImage NiblackBinarization (BufferedImage imgSrc, int window, double k ) throws IOException {

        BufferedImage img = deepCopy(imgSrc);

        width = img.getWidth();
        height = img.getHeight();

        int iB, iG, iR, rgb, sum, num;
        double area, mean, standardDeviation, average;

        int pixelRgb, pixelR, pixelG, pixelB, pixelA;
        double NiBlack;


        for (int column = 0; column < width; column++) {
            for (int row = 0; row < height; row++) {

                iR = iG = iB = 0;
                for(int ji = -window; ji < window; ji++){
                    for(int jj = -window; jj < window; jj++){
                        if((column + ji >= 0 && column + ji < width)&&(row + jj >= 0 && row + jj < height)){
                            rgb = img.getRGB(column + ji, row + jj);
                            iR += rgb & 0xff0000 >> 16;
                            iG += rgb & 0x00ff00 >> 8;
                            iB += rgb & 0xff;
                        }
                    }
                }

                sum = (iR + iG + iB) / 3;
                area = (window)*(window)*4;
                standardDeviation = 0.0;
                mean = sum/area;

                for(int ji = -window; ji < window; ji++){
                    for(int jj = -window; jj < window; jj++){
                        if(column + ji >= 0 && column + ji < width){
                            if(row + jj >= 0 && row + jj < height){
                                rgb = img.getRGB(column + ji, row + jj);
                                iR = rgb & 0xff0000 >> 16;
                                iG = rgb & 0x00ff00 >> 8;
                                iB = rgb & 0xff;
                                num = (iR + iG + iB) / 3;
                                standardDeviation += Math.pow(num - mean, 2);
                            }
                        }
                    }
                }
                standardDeviation = Math.sqrt(standardDeviation/area);
                pixelRgb = img.getRGB(column, row);
                pixelR = pixelRgb & 0xff0000 >> 16;
                pixelG = pixelRgb & 0x00ff00 >> 8;
                pixelB = pixelRgb & 0xff;
                pixelA = (pixelR + pixelG + pixelB) / 3;
                average = sum / area;

                NiBlack = average + k * standardDeviation;

                if(pixelA > NiBlack)
                    img.setRGB(column, row, 0xffffffff);
                else
                    img.setRGB(column, row, 0xff000000);
            }
        }

        return img;
    }


    public static BufferedImage PhansalkarBinarization(BufferedImage imgBuff, int window, double k)
    {
        //Window size (for each side)

        width = imgBuff.getWidth();
        height = imgBuff.getHeight();
        double R = 0.0;

        BufferedImage img = deepCopy(imgBuff);

        //Scanning the entire image
        for (int column = 0; column < width; column++)
        {
            for (int row = 0; row < height; row++)
            {
                int iAverage = 0;
                int iRed=0, iGreen=0, iBlue=0;

                //Sweeping the window to get the average
                for(int ji = -window ; ji < window ; ji++)
                {
                    for(int jj = -window ; jj < window ; jj++)
                    {
                        //Accumulating the values and then taking the average - do if serves to protect the window from not walking out of the image
                        if(column+ji >= 0 && column+ji < width)
                            if(row+jj >= 0 && row+jj < height)
                            {
                                iRed += imgBuff.getRGB(column+ji, row+jj) & 0xff0000 >> 16;
                                iGreen += imgBuff.getRGB(column+ji, row+jj) & 0x00ff00 >> 8;
                                iBlue += imgBuff.getRGB(column+ji, row+jj) & 0xff;
                                //iAverage += imgBuff.getRGB(column+ji, row+jj) & 0x00ff0000 >> 16;
                            }
                    }
                }
                iAverage = (iRed + iGreen + iBlue ) / 3;

                double area = (window*2) * (window*2);
                double standardDeviation = 0.0;
                double mean = iAverage/area;
                int num;


                for(int ji = -window ; ji < window ; ji++)
                {
                    for(int jj = -window ; jj < window ; jj++)
                    {
                        //Accumulating the values and then taking the average - do if serves to protect the window from not walking out of the image
                        if(column+ji >= 0 && column+ji < width)
                            if(row+jj >= 0 && row+jj < height)
                            {
                                iRed = imgBuff.getRGB(column+ji, row+jj) & 0xff0000 >> 16;
                                iGreen = imgBuff.getRGB(column+ji, row+jj) & 0x00ff00 >> 8;
                                iBlue = imgBuff.getRGB(column+ji, row+jj) & 0xff;
                                num = (iRed + iGreen + iBlue) / 3;

                                //R = Math.max(R, Math.pow(num - mean, 2));
                                standardDeviation += Math.pow(num - mean, 2);

                            }
                    }
                }

                double SD = Math.sqrt(standardDeviation/area);
                int pixelRed = img.getRGB(column, row) & 0xff0000 >> 16;
                int pixelGreen = img.getRGB(column, row) & 0x00ff00 >> 8;
                int pixelBlue = img.getRGB(column, row) & 0xff;
                int pixelAverage = (pixelRed + pixelGreen + pixelBlue) /3;
                double average = iAverage / area;
                R = Math.max(R, standardDeviation);
                //***********************************//

                //* constanst to implements fielsds *//
                double p = 3;
                double q = 10;

                //Phansalkar Pattern
                //double Phansalkar = average * (1 + k*(SD / R - 1));
                double Phansalkar = average *  (1 + p * Math.exp(-q * average) + (k * (SD / R - 1) ));


                //is higher than average
                //int pixel = img.getRGB(column, row) & 0x00ff0000 >> 16;
                if (pixelAverage > Phansalkar)
                    img.setRGB(column, row, 0x00FFFFFF );
                else
                    img.setRGB(column, row, 0x00000000 );
            }
        }

        return img;
    }

    public static BufferedImage SauvolaBinarization(BufferedImage imgBuff, int window, double k)
    {
        //Window size (for each side)

        width = imgBuff.getWidth();
        height = imgBuff.getHeight();
        double R = 0.0;

        BufferedImage img = deepCopy(imgBuff);

        //Scanning the entire image
        for (int column = 0; column < width; column++)
        {
            for (int row = 0; row < height; row++)
            {
                int iAverage = 0;
                int iRed=0, iGreen=0, iBlue=0;

                //Sweeping the window to get the average
                for(int ji = -window ; ji < window ; ji++)
                {
                    for(int jj = -window ; jj < window ; jj++)
                    {
                        //Accumulating the values and then taking the average - do if serves to protect the window from not walking out of the image
                        if(column+ji >= 0 && column+ji < width)
                            if(row+jj >= 0 && row+jj < height)
                            {
                                iRed += imgBuff.getRGB(column+ji, row+jj) & 0xff0000 >> 16;
                                iGreen += imgBuff.getRGB(column+ji, row+jj) & 0x00ff00 >> 8;
                                iBlue += imgBuff.getRGB(column+ji, row+jj) & 0xff;
                                //iAverage += imgBuff.getRGB(column+ji, row+jj) & 0x00ff0000 >> 16;
                            }
                    }
                }
                iAverage = (iRed + iGreen + iBlue ) / 3;

                double area = (window*2) * (window*2);
                double standardDeviation = 0.0;
                double mean = iAverage/area;
                int num;


                for(int ji = -window ; ji < window ; ji++)
                {
                    for(int jj = -window ; jj < window ; jj++)
                    {
                        //Accumulating the values and then taking the average - do if serves to protect the window from not walking out of the image
                        if(column+ji >= 0 && column+ji < width)
                            if(row+jj >= 0 && row+jj < height)
                            {
                                iRed = imgBuff.getRGB(column+ji, row+jj) & 0xff0000 >> 16;
                                iGreen = imgBuff.getRGB(column+ji, row+jj) & 0x00ff00 >> 8;
                                iBlue = imgBuff.getRGB(column+ji, row+jj) & 0xff;
                                num = (iRed + iGreen + iBlue) / 3;

                                //R = Math.max(R, Math.pow(num - mean, 2));
                                standardDeviation += Math.pow(num - mean, 2);

                            }
                    }
                }

                double SD = Math.sqrt(standardDeviation/area);
                int pixelRed = img.getRGB(column, row) & 0xff0000 >> 16;
                int pixelGreen = img.getRGB(column, row) & 0x00ff00 >> 8;
                int pixelBlue = img.getRGB(column, row) & 0xff;
                int pixelAverage = (pixelRed + pixelGreen + pixelBlue) /3;
                double average = iAverage / area;
                R = Math.max(R, standardDeviation);
                //***********************************//

                //Sauvola Pattern
                double Sauvola = average * (1 + k*(SD / R - 1));


                //is higher than average
                //int pixel = img.getRGB(column, row) & 0x00ff0000 >> 16;
                if (pixelAverage > Sauvola)
                    img.setRGB(column, row, 0x00FFFFFF );
                else
                    img.setRGB(column, row, 0x00000000 );
            }
        }

        return img;
    }

    public static BufferedImage simpleBinarization(BufferedImage imgSrc, double threshold) {

        int width = imgSrc.getWidth();
        int height = imgSrc.getHeight();
        int[][] result = new int[width][height];

        for (int row = 0; row < width; row++) {
            for (int col = 0; col < height; col++) {
                result[row][col] = imgSrc.getRGB(row, col);

                int iRet = result[row][col];
                int iB = (iRet & 0xff);
                int iG = (( iRet & 0x00ff00) >> 8);
                int iR = (( iRet & 0xff0000) >> 16);
                int iAve = ( iR + iG + iB ) / 3;

                 imgSrc.setRGB(row, col, (iR > threshold)?0:0xffffff);
            }
        }

        return imgSrc;
    }

    static BufferedImage deepCopy(BufferedImage bi)
    {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);

        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }
}
