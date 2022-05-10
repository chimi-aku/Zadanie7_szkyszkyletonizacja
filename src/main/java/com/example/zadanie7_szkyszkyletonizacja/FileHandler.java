package com.example.zadanie7_szkyszkyletonizacja;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class FileHandler {

    private static String fileURL;
    private static BufferedImage img;

    public FileHandler() {

    }

    public static BufferedImage LoadImage(Stage stage){
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png"));

        File file = fileChooser.showOpenDialog(stage);

        try {
            img = ImageIO.read(file);
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }

        return img;
    }

    public static void saveImage(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName("out.jpg");

        File outDir = new File(System.getProperty("user.home"));
        fileChooser.setInitialDirectory(outDir);

        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JPG", "jpg"));

        File out = fileChooser.showSaveDialog(stage);

        try {
            ImageIO.write(img, "jpg", out);
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static Image convertToFxImage(BufferedImage image) {
        // convert Buffered Img to Image

        WritableImage wr = null;
        if (image != null) {
            wr = new WritableImage(image.getWidth(), image.getHeight());
            PixelWriter pw = wr.getPixelWriter();
            for (int x = 0; x < image.getWidth(); x++) {
                for (int y = 0; y < image.getHeight(); y++) {
                    pw.setArgb(x, y, image.getRGB(x, y));
                }
            }
        }

        return new ImageView(wr).getImage();
    }
}
