package cm.belrose.jwtspringsecurity.services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

public interface FileUploadDownloadService {
    public String uploadFile(MultipartFile file);
    public List<String> getFiles() throws IOException;
    public Resource loadFileAsResource(String fileName) throws MalformedURLException;

}
