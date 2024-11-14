package com.demo.forum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.demo.forum.model.*;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.email = ?1")
    public User findByEmail(String email);

    @Query("SELECT count(*) from User u WHERE u.username = ?1")
    public int findExistByUsername(String username);

    @Query("SELECT count(*) from User u WHERE u.email = ?1")
    public int findExistByEmail(String email);
}