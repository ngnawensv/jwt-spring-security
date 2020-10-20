package cm.belrose.jwtspringsecurity.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author Ngnawen Samuel
 * Cette classe nous permet de personnaliser la security de l'application
 */

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // Injection de la couche service de gestion d'authentification
    @Autowired
    private UserDetailsService userDetailsService;


    /*Injection de la fonction d'encodage(d'hachage) pour le mot de passe.
    On pourrait aussi utiliser MD5 mais sauf qu'il est facilemnt dechifrable.
    Il faut d'abord créer ce bean avant son injection car Spring Boot ne l'instancie pas par defaut.
    Il faut le faire dans une classe de configuration ou dans la classe principale
     */
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * Cette methode permet de montrer à Spring Security comment il va chercher les utilisateurs et les roles
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 1ere méthode: Cas ou les utilisateurs sont dejà connus et stockés en memoire (Solution basic)
        /*
        auth.inMemoryAuthentication()
                .withUser("admin").password("{noop}12345").roles("ADMIN", "USER")
                .and()
                .withUser("user").password("1234").roles("USER");

         */

        // 2e Métode: Cas de jdbc authentication (Solution aussi basic)
       /*
        auth.jdbcAuthentication().usersByUsernameQuery("").authoritiesByUsernameQuery("");

        */

       // 3e Méthode: Utilisation du systeme d'authentification basé sur la couche service
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    /**
     * Cette methode permet de definir les droits d'accès
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
       /*
       //Ce block est uniquement du spring security
        http.csrf().disable(); // Desactivation du synchronize token
        http.formLogin(); //utilisation du formulaire d'authentification fournit par spring
        //http.formLogin().loginPage("/login"); //utilisation personnlisation de la page d'authentification en cas d'utilisation de JSP, ...
        http.authorizeRequests().antMatchers("/login/**","/register/**").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.POST,"/tasks/**").hasAnyAuthority("ADMIN");
        http.authorizeRequests().anyRequest().authenticated();//indique que toutes les ressources de l'application nécessite une authentification
*/
       //jwt
        http.csrf().disable();
        //Desactivation de l'authentification par des session
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers("/login/**","/register/**").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.POST,"/tasks/**").hasAnyAuthority("ADMIN");
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(new JWTAuthenticationFilter(authenticationManager()));
        http.addFilterBefore(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
