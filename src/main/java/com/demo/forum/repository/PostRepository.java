package com.demo.forum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import com.demo.forum.model.*;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("SELECT p FROM Post p WHERE p.user_id = ?1")
    public User findByUserId(String user_id);

    @Query("FROM Post")
    public List<Post> findAll();
}