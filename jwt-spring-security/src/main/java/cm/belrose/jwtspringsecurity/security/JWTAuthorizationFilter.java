package cm.belrose.jwtspringsecurity.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * Cette classe permet d'authoriser un utilisateur d'acceder à une ressource
 */
public class JWTAuthorizationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        //Authirisation des domains à envoyer des requetes. * signifie tous les domains
        httpServletResponse.addHeader("Access-Control-Allow-Origin","*");

        //authorisation des clients a envoyer les entetes
        httpServletResponse.addHeader("Access-Control-Allow-Headers",
                "Origin,Accept,X-Requested-With,Content-Type," +
                        "Access-Control-Request-Method, " +
                        "Access-Control-Request-Headers," +
                        "Authorization");

        //Authorisation d'exposition des entetes aux applications
        httpServletResponse.addHeader("Access-Control-Expose-Headers",
                "Access-Control-Allow-Origin" +
                        "Access-Control-Allow-Credentials," +
                        "Authorization");

        //Toujours laisser la méthode option ok. Ceci me permet d'eviter les problèmes dans les autres taches
        if(httpServletRequest.getMethod().equals("OPTIONS")){
            httpServletResponse.setStatus(httpServletResponse.SC_OK);
        }else{
            //Recupération du jeton
            String jwt= httpServletRequest.getHeader(SecurityConstants.HEADER_STRING);
            System.out.println("Jeton: "+jwt);
            if(jwt==null||!jwt.startsWith(SecurityConstants.TOKEN_PREFIX)){
                filterChain.doFilter(httpServletRequest,httpServletResponse);
                return;
            }
            Claims claims= Jwts.parser()
                    .setSigningKey(SecurityConstants.SECRET)
                    .parseClaimsJws(jwt.replace(SecurityConstants.TOKEN_PREFIX,""))
                    .getBody();

            //Extraction du username
            String username=claims.getSubject();
            System.out.println("username "+username);
            ArrayList<Map<String,String>> roles= (ArrayList<Map<String, String>>) claims.get("roles");
            //Extraction des roles du username
            Collection<GrantedAuthority> authorities=new ArrayList<>();
            roles.forEach(r->{
                authorities.add(new SimpleGrantedAuthority(r.get("authority")));
            });
            //Mise dans le contexte de spring l'utilisateur authentifié
            UsernamePasswordAuthenticationToken authenticateUser=new UsernamePasswordAuthenticationToken(username,null,authorities);
            SecurityContextHolder.getContext().setAuthentication(authenticateUser);
            filterChain.doFilter(httpServletRequest,httpServletResponse);

        }


    }
}
