package ro.itschool.controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import ro.itschool.controller.model.AuthenticationRequest;
import ro.itschool.controller.model.AuthenticationResponse;
import ro.itschool.entity.MyUser;
import ro.itschool.service.AuthenticationService;
import ro.itschool.controller.model.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

//    @PostMapping("/register")
//    public ResponseEntity<AuthenticationResponse> register(
//            @RequestBody RegisterRequest request) {
//        return ResponseEntity.ok(service.register(request));
//    }

    //----------------------------REGISTER----------------------------
    @GetMapping(value = "/register")
    public String registerForm(Model model) {
        model.addAttribute("regReq", new RegisterRequest());
        return "register";
    }

    @PostMapping(value = "/register")
    public String registerUser(@ModelAttribute("regReq") @RequestBody RegisterRequest regReq) {
        service.register(regReq);
        return "redirect:/register-successful";
    }
    //------------------------------------------------------------

//    @PostMapping("/authenticate")
//    public ResponseEntity authenticate(
//            @RequestBody AuthenticationRequest request) {
//        AuthenticationResponse authenticate = service.authenticate(request);
//        return ResponseEntity.ok(authenticate.getToken());
//    }

    //----------------------------LOGIN----------------------------
    //----------------------------LOGIN----------------------------
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginForm(Model model) {
        model.addAttribute("authenticationRequest", new AuthenticationRequest());
        return "login";
    }

    //
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginForm(@ModelAttribute("authenticationRequest") @RequestBody AuthenticationRequest authenticationRequest) {
        AuthenticationResponse authenticate = service.authenticate(authenticationRequest);

//        if (myUser.isPresent()) {
//            if (myUser.get().getPassword().equals(user.getPassword()))
        return "redirect:/demo";
//            else
//                return "wrong-password";
//        } else {
//            return "user-not-found";
//        }
    }

    // Login form
//    @RequestMapping(value = {"/login", "/"})
//    public String login(@ModelAttribute(value = "authenticationRequest") AuthenticationRequest authenticationRequest) {
//        AuthenticationResponse authenticate = null;
//        if (authenticationRequest.getPassword() != null && authenticationRequest.getEmail() != null)
//            authenticate = service.authenticate(authenticationRequest);
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
//            return "login";
//        }
//        return "redirect:/index";
//    }


    //------------------------------------------------------------

    //------------------------------------------------------------

    @RequestMapping(value = {"/index"})
    public String index() {
        return "index";
    }

}
