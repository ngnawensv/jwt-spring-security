package cm.belrose.jwtspringsecurity.services;

import cm.belrose.jwtspringsecurity.entities.AppRole;
import cm.belrose.jwtspringsecurity.entities.AppUser;

/**
 * Cette interface permet de centraliser la gestion des utilisateurs et leurs roles
 */
public interface AccountService {
    public AppUser saveUser(AppUser user);
    public AppRole saveRole(AppRole role);
    public void addRoleToUser(String username, String roleName);
    public AppUser findUserByUsername(String username);
}
