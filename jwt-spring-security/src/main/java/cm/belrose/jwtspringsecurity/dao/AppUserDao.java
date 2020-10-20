package cm.belrose.jwtspringsecurity.dao;

import cm.belrose.jwtspringsecurity.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserDao extends JpaRepository<AppUser,Long> {
    public AppUser findByUsername(String username);
}
