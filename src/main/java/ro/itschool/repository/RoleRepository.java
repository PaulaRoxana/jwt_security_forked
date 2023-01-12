package ro.itschool.repository;

import ro.itschool.entity.MyRole;
import ro.itschool.entity.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<MyRole, Integer> {

    MyRole findByName(RoleName name);
}
