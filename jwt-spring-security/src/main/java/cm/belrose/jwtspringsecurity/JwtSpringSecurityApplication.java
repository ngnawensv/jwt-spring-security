package cm.belrose.jwtspringsecurity;

import cm.belrose.jwtspringsecurity.dao.TasksDao;
import cm.belrose.jwtspringsecurity.entities.AppRole;
import cm.belrose.jwtspringsecurity.entities.AppUser;
import cm.belrose.jwtspringsecurity.entities.Tasks;
import cm.belrose.jwtspringsecurity.services.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.stream.Stream;

//@SpringBootApplication
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class JwtSpringSecurityApplication  implements CommandLineRunner {

	private  static final Logger log= LoggerFactory.getLogger(JwtSpringSecurityApplication.class);

	@Autowired
	private TasksDao tasksDao;
	@Autowired
	private AccountService accountService;

	public static void main(String[] args) {
		SpringApplication.run(JwtSpringSecurityApplication.class, args);
	}

	/**
	 * Cette methode est executée au demarrage de l'application grace à l'annotation @Bean. Le resultat retourné est donc
	 * un bean spring et injectable partout c-à-d dans n'importe quelle classe de votre application
	 * @return
	 */
	@Bean
	public BCryptPasswordEncoder getBCryptPasswordEncoder(){
		return new  BCryptPasswordEncoder();
	}


	@Override
	public void run(String... args) throws Exception {

		accountService.saveUser(new AppUser("admin","123"));
		accountService.saveUser(new AppUser("user","123"));

		accountService.saveRole(new AppRole("ADMIN"));
		accountService.saveRole(new AppRole("USER"));

		accountService.addRoleToUser("admin","ADMIN");
		accountService.addRoleToUser("user","USER");

		Stream.of("T1","T2","T3").forEach(t->{
			tasksDao.save(new Tasks(t));
		});
		tasksDao.findAll().forEach(tasks -> {
			log.info(String.valueOf(tasks));
		});
	}
}
