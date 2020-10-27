package cm.belrose.jwtspringsecurity.servicesImpl;

import cm.belrose.jwtspringsecurity.entities.AppUser;
import cm.belrose.jwtspringsecurity.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Spring security ne connait pas l'interface AccountService mais plutot son interface UserDetailsService
 * Cette classe permet d'implementer l'interface UserDetailsService qui à une seule méthode loadUserByUsername()
 *
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private AccountService accountService;

    /**
     * Cette methode prend en paramètre un username et reourne un objet de type UserDetails de spring
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user=accountService.findUserByUsername(username);
        if(user==null) throw new UsernameNotFoundException(username);
        //Creation d'une collection de role du type  GrantedAuthority car ce que spring connait
        Collection<GrantedAuthority> authorities=new ArrayList<>();
        user.getRoles().forEach(r->{
            authorities.add(new SimpleGrantedAuthority(r.getRoleName()));
        });
        return new User(user.getUsername(),user.getPassword(),authorities);
    }
}
