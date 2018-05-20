package control.self.igor.dailywisdom.service.abstraction;

import java.io.OutputStream;

public interface StreamService {
    void writeBytesToOutputStream(OutputStream outputStream, byte[] bytes);
}
