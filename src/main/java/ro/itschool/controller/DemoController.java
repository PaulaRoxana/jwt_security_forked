package ro.itschool.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class DemoController {

    @GetMapping(value = "/demo")
    public String sayHello(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
//        return ResponseEntity.ok("Hello from secured endpoint: " + currentPrincipalName);

        model.addAttribute("userName", currentPrincipalName);
        return "demo";
    }


    @GetMapping(value = "/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> sayHelloFromAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        System.out.println(authentication.getAuthorities());

        return ResponseEntity.ok("Hello from ADMIN secured endpoint: " + currentPrincipalName);
    }

}
