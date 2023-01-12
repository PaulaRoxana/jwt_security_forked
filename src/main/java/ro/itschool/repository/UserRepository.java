package ro.itschool.repository;

import java.util.Optional;

import ro.itschool.entity.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<MyUser, Integer> {

  Optional<MyUser> findByEmail(String email);

}
