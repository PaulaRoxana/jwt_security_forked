package ro.itschool.controller;


import ro.itschool.entity.MyRole;
import ro.itschool.entity.MyUser;
import ro.itschool.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping(value = "/user")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity getAllUsers() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    //TODO Add user
    //TODO Delete user
    //TODO Update user
    //TODO getUsersByRole

    @PutMapping(value = "/update-role/{id}")
    public ResponseEntity updateRole(@PathVariable Integer id, @RequestBody Set<MyRole> roles) {
        Optional<MyUser> optionalUser = userService.findById(id);
        if (optionalUser.isPresent()) {
            optionalUser.ifPresent(user -> {
                user.setRoles(roles);
                userService.saveUser(user);
            });
            return ResponseEntity.ok().build();
        } else
            return ResponseEntity.badRequest().build();
    }
}
