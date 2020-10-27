package cm.belrose.jwtspringsecurity.services;

import cm.belrose.jwtspringsecurity.entities.Tutorial;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.List;

public interface ExcelService {
    public void save(MultipartFile file);
    public List<Tutorial> getAllTutorials();
    public ByteArrayInputStream load();
}
