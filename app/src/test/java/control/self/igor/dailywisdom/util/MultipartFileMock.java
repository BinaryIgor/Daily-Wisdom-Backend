package control.self.igor.dailywisdom.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

public class MultipartFileMock implements MultipartFile {

    private byte[] content;

    public MultipartFileMock(boolean empty) {
	if (!empty) {
	    this.content = MockUtil.createImage();
	}
    }

    @Override
    public String getName() {
	return "mock";
    }

    @Override
    public String getOriginalFilename() {
	return "root/mock";
    }

    @Override
    public String getContentType() {
	return "jpg";
    }

    @Override
    public boolean isEmpty() {
	return content != null && content.length > 0;
    }

    @Override
    public long getSize() {
	if (content == null || content.length < 1) {
	    return 0;
	}
	return content.length;
    }

    @Override
    public byte[] getBytes() throws IOException {
	return content;
    }

    @Override
    public InputStream getInputStream() throws IOException {
	return new ByteArrayInputStream(content);
    }

    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {
	// TODO Auto-generated method stub

    }

}
