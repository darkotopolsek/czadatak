package com.omicron.ctest;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.*;
import javax.imageio.ImageIO;

import org.springframework.web.multipart.MultipartFile;

public class FileUploadUtils {

    public static void saveFile(String uploadDir, String fileName,
            MultipartFile multipartFile) throws IOException {
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        //System.out.println("UploadPath:" + uploadPath);
        try (InputStream inputStream = multipartFile.getInputStream()) {

            Path filePath = null;
            if (uploadPath != null && fileName != null) {
                filePath = uploadPath.resolve(fileName);

                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);

                //System.out.println("Original: " + filePath + "\n" + "Thumbnail: " + filePath.getParent() + "\\thumbnail.jpg");
                resize(filePath.toString(), filePath.getParent() + "\\thumbnail.jpg", 60, 60);
            }
        } catch (IOException ioe) {
            //throw new IOException("Could not save image file: " + fileName, ioe);
            System.out.println("File already exists.");
        }
    }

    public static void resize(String inputImagePath,
            String outputImagePath, int scaledWidth, int scaledHeight)
            throws IOException {
        // reads input image
        if (inputImagePath != null) {
            File inputFile = new File(inputImagePath);
            BufferedImage inputImage = ImageIO.read(inputFile);

            if (inputImage == null) {
                return;
            }
            // creates output image
            BufferedImage outputImage = new BufferedImage(scaledWidth,
                    scaledHeight, inputImage.getType());

            // scales the input image to the output image
            Graphics2D g2d = outputImage.createGraphics();
            g2d.drawImage(inputImage, 0, 0, scaledWidth, scaledHeight, null);
            g2d.dispose();

            // extracts extension of output file
            String formatName = outputImagePath.substring(outputImagePath
                    .lastIndexOf(".") + 1);

            // writes to output file
            ImageIO.write(outputImage, formatName, new File(outputImagePath));

            //System.out.println("Resized and saved.");
        } else {
            return;
        }
    }
}
