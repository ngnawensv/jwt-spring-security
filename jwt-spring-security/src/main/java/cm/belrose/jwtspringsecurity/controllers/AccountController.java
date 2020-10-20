package cm.belrose.jwtspringsecurity.controllers;

import cm.belrose.jwtspringsecurity.entities.AppUser;
import cm.belrose.jwtspringsecurity.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/register")
    public AppUser register(@RequestBody RegisterForm registerForm){
        if(!registerForm.getPassword().equals(registerForm.getRepassword())){

            throw  new RuntimeException("You must confirme your password");
        }
        AppUser userFromDB=accountService.findUserByUsername(registerForm.getUsername());
        if(userFromDB!=null){
            throw  new  RuntimeException("This user already exists");
        }
        AppUser appUser=new AppUser();
        appUser.setUsername(registerForm.getUsername());
        appUser.setPassword(registerForm.getRepassword());

       accountService.saveUser(appUser);
       accountService.addRoleToUser(registerForm.getUsername(),"USER");
       return appUser;

    }
}
