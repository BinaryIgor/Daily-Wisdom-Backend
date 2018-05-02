package control.self.igor.dailywisdom.service.implementation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import control.self.igor.dailywisdom.service.abstraction.ImageService;
import control.self.igor.dailywisdom.util.MockUtil;
import control.self.igor.dailywisdom.util.MultipartFileMock;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ImageServiceTest {

    // @TestConfiguration
    // static class ImageServiceTestConfiguration {
    //
    // @Bean
    // public ImageService service() {
    // return new ImageServiceImpl();
    // }
    // }

    private static final int IMAGE_WIDTH = 512;
    private static final int IMAGE_HEIGHT = 256;
    @Value("${storageAuthorsImagesPath}")
    private String imagesPath;

    @Autowired
    private ImageService service;

    @Test
    public void getImageFileNameWithFormatTest() {
	String imageFileNameWithFormat = service.getImageFileNameWithFormat("tmp");
	assertTrue(imageFileNameWithFormat != null && !imageFileNameWithFormat.isEmpty());
    }

    @Test
    public void getImageBytesFromProperMultipartFileTest() {
	byte[] image = service.getImageBytes(new MultipartFileMock(false), IMAGE_WIDTH, IMAGE_HEIGHT);
	assertNotNull(image);
	boolean exceptionThrown = false;
	try {
	    BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(image));
	    assertTrue(IMAGE_WIDTH == bufferedImage.getWidth() && IMAGE_HEIGHT == bufferedImage.getHeight());
	} catch (IOException exception) {
	    exceptionThrown = true;
	}
	assertFalse(exceptionThrown);
    }

    @Test
    public void getImageBytesFromEmptyMultipartFileTest() {
	byte[] image = service.getImageBytes(new MultipartFileMock(true), IMAGE_WIDTH, IMAGE_HEIGHT);
	assertNull(image);
	image = service.getImageBytes(null, IMAGE_WIDTH, IMAGE_HEIGHT);
	assertNull(image);
    }

    @Test
    public void createProperImageFileFromBytesTest() {
	File imageFile = createImageFileFromBytes(true);
	assertNotNull(imageFile);
	assertTrue(imageFile.exists());
    }

    @Test
    public void createImproperImageFileFromBytesTest() {
	File imageFile = createImageFileFromBytes(false);
	assertNull(imageFile);
    }

    @Test
    public void getBytesFromeExistingFileTest() {
	File imageFile = createImageFileFromBytes(true);
	byte[] imageBytes = service.getImageBytes(imagesPath + "/" + imageFile.getName());
	assertTrue(imageBytes != null && imageBytes.length > 0);
    }

    @Test
    public void getBytesFromNonExistingFileTest() {

    }

    private File createImageFileFromBytes(boolean proper) {
	byte[] image = null;
	if (proper) {
	    image = MockUtil.createImage();
	}
	String imageFileName = service.getImageFileNameWithFormat("tmp");
	return service.createImageFileFromBytes(imagesPath + "/" + imageFileName, image);
    }

}