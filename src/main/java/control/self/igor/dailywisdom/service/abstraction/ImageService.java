package control.self.igor.dailywisdom.service.abstraction;

import java.awt.image.BufferedImage;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    byte[] getImageBytes(InputStream inputStream);

    byte[] getImageBytes(BufferedImage image);

    BufferedImage resizeImage(BufferedImage image, int height, int width);

    byte[] getImageBytes(MultipartFile file);

}
