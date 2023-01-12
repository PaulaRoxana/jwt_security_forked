package ro.itschool.service;

import ro.itschool.entity.MyRole;
import ro.itschool.entity.MyUser;
import ro.itschool.repository.RoleRepository;
import ro.itschool.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    public void saveUser(MyUser user) {
        user.getRoles()
                .forEach(role -> {
                            MyRole roleByName = roleRepository.findByName(role.getName());
                            role.setId(roleByName.getId());
                        }
                );
        userRepository.save(user);
    }

    public List<MyUser> findAll() {
        return userRepository.findAll();
    }

    public Optional<MyUser> findById(Integer id) {
        return userRepository.findById(id);
    }
}
