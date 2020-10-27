package cm.belrose.jwtspringsecurity.servicesImpl;

import cm.belrose.jwtspringsecurity.utils.ExcelUtils;
import cm.belrose.jwtspringsecurity.dao.TutorialRepository;
import cm.belrose.jwtspringsecurity.entities.Tutorial;
import cm.belrose.jwtspringsecurity.services.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ExcelServiceImpl implements ExcelService {
    @Autowired
    TutorialRepository repository;

    /**
     * Cette méthode stocke les données d'excel dans la base de données
     * @param file
     */
    @Override
    public void save(MultipartFile file) {
        try {
            List<Tutorial> tutorials = ExcelUtils.excelToTutorials(file.getInputStream());
            //repository.saveAll(tutorials);
            tutorials.forEach(t-> {
                System.out.println(t);
                Optional<Tutorial> tutorial=repository.findById(t.getId());
                System.out.println(tutorial);
                if (tutorial.isEmpty()){
                    repository.save(t);
                }else{
                    //Update the existing registration
                    tutorial.get().setDescription(t.getDescription());
                    tutorial.get().setTitle(t.getTitle());
                    tutorial.get().setPublished(t.isPublished());
                    repository.save(tutorial.get());
                }
            });
        } catch (IOException e) {
            throw new RuntimeException("Fail to store excel data: " + e.getMessage());
        }
    }

    /**
     * Cette methode lit les données dans la base de données et retourne une List<Tutorial>
     * @return
     */
    @Override
    public List<Tutorial> getAllTutorials() {
        return repository.findAll();
    }

    /**
     * This method which read data from database and return ByteArrayInputStream
     * @return
     */
    @Override
    public ByteArrayInputStream load() {
        List<Tutorial> tutorials = repository.findAll();

        ByteArrayInputStream in = ExcelUtils.tutorialsToExcel(tutorials);
        return in;
    }
}
