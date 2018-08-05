package control.self.igor.dailywisdom.service.stream;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.springframework.stereotype.Service;

@Service
public class StreamServiceImpl implements StreamService {

    @Override
    public void writeBytesToOutputStream(OutputStream outputStream, byte[] bytes) {
	try (BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream)) {
	    outputStream.write(bytes);
	} catch (IOException exception) {
	    exception.printStackTrace();
	}

    }

}
