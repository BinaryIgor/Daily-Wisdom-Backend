package control.self.igor.dailywisdom.service.abstraction;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    byte[] getImageBytes(MultipartFile file, int width, int height);

}
