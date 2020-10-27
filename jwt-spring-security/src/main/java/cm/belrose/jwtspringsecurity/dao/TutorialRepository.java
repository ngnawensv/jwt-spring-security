package cm.belrose.jwtspringsecurity.dao;

import cm.belrose.jwtspringsecurity.entities.Tutorial;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TutorialRepository extends JpaRepository<Tutorial,Long> {
}
