package com.team.bookstore.Utilities;

import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Iterator;

public class ImageUtils {
    public static byte[] compressImage(byte[] originalImageBytes, float compressionQuality) throws IOException, IOException {
        ByteArrayInputStream bis = new ByteArrayInputStream(originalImageBytes);
        BufferedImage originalImage = ImageIO.read(bis);
        bis.close();

        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        // Find a jpeg writer
        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");
        if (!writers.hasNext()) throw new IllegalStateException("No writers found");
        ImageWriter writer = writers.next();

        // Set the compression quality
        ImageWriteParam param = writer.getDefaultWriteParam();
        if (param.canWriteCompressed()) {
            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            param.setCompressionQuality(compressionQuality);
        }

        ImageOutputStream ios = ImageIO.createImageOutputStream(bos);
        writer.setOutput(ios);
        writer.write(null, new IIOImage(originalImage, null, null), param);

        ios.close();
        writer.dispose();

        return bos.toByteArray();
    }
}
