package control.self.igor.dailywisdom.service.image;

import java.io.File;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    byte[] getImageBytes(MultipartFile file, int width, int height);

    File createImageFileFromBytes(String relativeFilePath, byte[] image);

    byte[] getImageBytes(String relativeFilePath);

    String getImageFileNameWithFormat(String imageFileName);
}
