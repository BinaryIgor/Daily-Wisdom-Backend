package control.self.igor.dailywisdom.service.implementation;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import control.self.igor.dailywisdom.service.abstraction.ImageService;

@Service
public class ImageServiceImpl implements ImageService {

    private static final Logger LOGGER = Logger.getLogger(ImageServiceImpl.class.getSimpleName());
    private static final String IMAGES_FORMAT = "jpg";

    @Value("${storageRootPath}")
    private String storageRootPath;

    private BufferedImage resizeImage(InputStream inputStream, int width, int height) throws IOException {
	BufferedImage image = ImageIO.read(inputStream);
	Image tmpImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
	BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	Graphics2D graphics = resized.createGraphics();
	graphics.drawImage(tmpImage, 0, 0, null);
	graphics.dispose();
	return resized;
    }

    public byte[] getImageBytes(BufferedImage image) {
	try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
	    ImageIO.write(image, IMAGES_FORMAT, baos);
	    baos.flush();
	    byte[] imageBytes = baos.toByteArray();
	    baos.close();
	    return imageBytes;
	} catch (IOException exception) {
	    LOGGER.log(Level.WARNING, exception.toString(), exception);
	    return null;
	}
    }

    @Override
    public byte[] getImageBytes(MultipartFile file, int width, int height) {
	if (file == null) {
	    return null;
	}
	try {
	    byte[] image = file.getBytes();
	    if (image == null || image.length < 1) {
		return null;
	    }
	    return getImageBytes(resizeImage(file.getInputStream(), width, height));
	} catch (IOException exception) {
	    LOGGER.log(Level.WARNING, exception.toString(), exception);
	    return null;
	}
    }

    @Override
    public File createImageFileFromBytes(String relativeFilePath, byte[] image) {
	if (image == null || image.length < 1) {
	    return null;
	}
	try {
	    File imageFile = new File(storageRootPath + "/" + relativeFilePath);
	    BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(image));
	    if (!ImageIO.write(bufferedImage, IMAGES_FORMAT, imageFile)) {
		return null;
	    }
	    return imageFile;
	} catch (IOException exception) {
	    LOGGER.log(Level.WARNING, exception.toString(), exception);
	    return null;
	}
    }

    @Override
    public byte[] getImageBytes(String relativeFilePath) {
	try {
	    File imageFile = new File(storageRootPath + "/" + relativeFilePath);
	    if (!imageFile.exists()) {
		return null;
	    }
	    BufferedImage bufferedImage = ImageIO.read(imageFile);
	    return getImageBytes(bufferedImage);
	} catch (IOException exception) {
	    LOGGER.log(Level.WARNING, exception.toString(), exception);
	    return null;
	}
    }

    @Override
    public String getImageFileNameWithFormat(String imageFileName) {
	return imageFileName + "." + IMAGES_FORMAT;
    }

}
