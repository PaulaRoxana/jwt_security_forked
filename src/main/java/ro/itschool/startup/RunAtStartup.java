package ro.itschool.startup;

import ro.itschool.controller.model.AuthenticationResponse;
import com.alibou.security.entity.*;
import ro.itschool.entity.MyRole;
import ro.itschool.entity.RoleName;
import ro.itschool.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ro.itschool.entity.MyUser;

import java.util.List;
import java.util.Set;

@Component
public class RunAtStartup {

    @Autowired
    private RoleRepository roleRepository;


    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() {

        MyRole userRole = roleRepository.save(new MyRole(RoleName.ROLE_USER));
        MyRole adminRole = roleRepository.save(new MyRole(RoleName.ROLE_ADMIN));

        //CREATE USER WITH ROLES USER AND ADMIN

        RestTemplate restTemplate = new RestTemplate();

        MyUser user = new MyUser();
        user.setLastname("Doe");
        user.setFirstname("John");
        user.setEmail("johndoe@email.com");
        user.setPassword("password");
        user.setRoles(Set.of(userRole));


        //Create user with ROLE_USER
        HttpEntity<MyUser> request = new HttpEntity<>(user);
        AuthenticationResponse authenticationResponse = restTemplate.postForObject("http://localhost:8080/auth/register", request, AuthenticationResponse.class);
        System.out.println(authenticationResponse.getToken());

        //Add ROLE_ADMIN to newly created user
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authenticationResponse.getToken());

        HttpEntity<Set<MyRole>> requestEntity =
                new HttpEntity<>(Set.of(userRole, adminRole), headers);

        restTemplate.put("http://localhost:8080/user/update-role/1", requestEntity);


    }
}
