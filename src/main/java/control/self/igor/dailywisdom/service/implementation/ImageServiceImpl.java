package control.self.igor.dailywisdom.service.implementation;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.web.multipart.MultipartFile;

import control.self.igor.dailywisdom.service.abstraction.ImageService;

public class ImageServiceImpl implements ImageService {

    private static final Logger LOGGER = Logger.getLogger(ImageServiceImpl.class.getSimpleName());

    @Override
    public byte[] getImageBytes(InputStream inputStream) {
	byte[] authorPlaceHolderBytes = null;
	try (BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream)) {
	    authorPlaceHolderBytes = new byte[bufferedInputStream.available()];
	    inputStream.read(authorPlaceHolderBytes, 0, authorPlaceHolderBytes.length);
	} catch (IOException exception) {
	    LOGGER.log(Level.WARNING, exception.getMessage(), exception);
	}
	return authorPlaceHolderBytes;
    }

    @Override
    public byte[] getImageBytes(BufferedImage image) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public BufferedImage resizeImage(BufferedImage image, int height, int width) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public byte[] getImageBytes(MultipartFile file) {
	// TODO Auto-generated method stub
	return null;
    }

}
