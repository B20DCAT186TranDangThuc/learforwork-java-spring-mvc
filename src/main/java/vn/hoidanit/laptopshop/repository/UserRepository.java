package vn.hoidanit.laptopshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.hoidanit.laptopshop.domain.User;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    User save(User user);

    List<User> findByEmail(String email);

    List<User> findAll();

    User findById(long id);

    void deleteById(long id);

}
