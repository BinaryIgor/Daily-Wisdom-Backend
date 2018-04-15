package control.self.igor.dailywisdom.service.implementation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import control.self.igor.dailywisdom.service.abstraction.ImageService;
import control.self.igor.dailywisdom.util.MultipartFileMock;

@RunWith(SpringRunner.class)
public class ImageServiceTest {

    @TestConfiguration
    static class ImageServiceTestConfiguration {

	@Bean
	public ImageService service() {
	    return new ImageServiceImpl();
	}
    }

    private static final int IMAGE_WIDTH = 512;
    private static final int IMAGE_HEIGHT = 256;

    @Autowired
    private ImageService service;

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
}
