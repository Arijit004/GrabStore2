package com.authentication.repository;

import com.authentication.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

//user repo...
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    //custom query methods...
    //1. find by email...
    public Optional<User> findByEmail(String email);

    //2. find by email and password...
    public Optional<User> findByEmailAndPassword(String email, String password);

}
