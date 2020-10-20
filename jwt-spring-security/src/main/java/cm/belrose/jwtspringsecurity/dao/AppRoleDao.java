package cm.belrose.jwtspringsecurity.dao;

import cm.belrose.jwtspringsecurity.entities.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppRoleDao extends JpaRepository<AppRole,Long> {
    public AppRole findByRoleName(String roleName);
}
