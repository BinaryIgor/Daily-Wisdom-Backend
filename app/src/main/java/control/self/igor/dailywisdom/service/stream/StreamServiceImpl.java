package control.self.igor.dailywisdom.service.stream;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

@Service
public class StreamServiceImpl implements StreamService {

    public static final Logger LOGGER = Logger.getLogger(StreamServiceImpl.class.getSimpleName());

    @Override
    public void writeBytesToOutputStream(OutputStream outputStream, byte[] bytes) {
	try (BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream)) {
	    outputStream.write(bytes);
	} catch (IOException exception) {
	    LOGGER.log(Level.WARNING, exception.toString(), exception);
	}

    }

}
