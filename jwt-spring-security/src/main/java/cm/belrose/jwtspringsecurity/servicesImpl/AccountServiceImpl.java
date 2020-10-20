package cm.belrose.jwtspringsecurity.servicesImpl;

import cm.belrose.jwtspringsecurity.dao.AppRoleDao;
import cm.belrose.jwtspringsecurity.dao.AppUserDao;
import cm.belrose.jwtspringsecurity.entities.AppRole;
import cm.belrose.jwtspringsecurity.entities.AppUser;
import cm.belrose.jwtspringsecurity.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Cette classe implemente l'interface AccountService
 */
@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    AppUserDao userDao;

    @Autowired
    AppRoleDao roleDao;

    @Override
    public AppUser saveUser(AppUser user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userDao.save(user);
    }

    @Override
    public AppRole saveRole(AppRole role) {
        return roleDao.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        AppRole role=roleDao.findByRoleName(roleName);
        AppUser user=userDao.findByUsername(username);
        user.getRoles().add(role);

    }

    @Override
    public AppUser findUserByUsername(String username) {
        return userDao.findByUsername(username);
    }
}
