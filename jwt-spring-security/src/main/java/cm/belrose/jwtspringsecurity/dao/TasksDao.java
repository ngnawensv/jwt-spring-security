package cm.belrose.jwtspringsecurity.dao;

import cm.belrose.jwtspringsecurity.entities.Tasks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TasksDao extends JpaRepository<Tasks, Long> {

}
