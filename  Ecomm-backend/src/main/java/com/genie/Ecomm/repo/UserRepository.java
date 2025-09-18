package com.genie.Ecomm.repo;

import com.genie.Ecomm.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User,Long> {


    User findByEmail(String username);

   Optional< User> findByUsername(String username);
}
