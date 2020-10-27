package cm.belrose.jwtspringsecurity.servicesImpl;

import cm.belrose.jwtspringsecurity.services.FileUploadDownloadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileUploadDownloadServiceImpl implements FileUploadDownloadService {

    private static final Logger log= LoggerFactory.getLogger(FileUploadDownloadServiceImpl.class);
    @Autowired
    private Environment environment;

    @Override
    public String uploadFile(MultipartFile file) {
        String fileName= StringUtils.cleanPath(file.getOriginalFilename());
        try{
            Path fileStorageLocation= Paths.get(environment.getProperty("file.upload-dir")).toAbsolutePath().normalize();
            Path targetLocation=fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(),targetLocation, StandardCopyOption.REPLACE_EXISTING);
        }catch (Exception ex){
            log.error("exceptions=====> "+ex.getMessage());
        }
        return fileName;
    }

    @Override
    public List<String> getFiles() throws IOException {
        return Files.walk(Paths.get(environment.getProperty("file.upload-dir")))
                .filter(Files::isRegularFile)
                .map(files-> files.getFileName().toString())
                .collect(Collectors.toList());
    }

    @Override
    public Resource loadFileAsResource(String fileName) throws MalformedURLException {
        Path fileStorageLocation= Paths.get(environment.getProperty("file.upload-dir"))
                .toAbsolutePath().normalize();
        Path filePath = fileStorageLocation.resolve(fileName).normalize();
        Resource resource = new UrlResource(filePath.toUri());
        if (resource.exists()) {
            return resource;
        }
        return null;
    }


}
