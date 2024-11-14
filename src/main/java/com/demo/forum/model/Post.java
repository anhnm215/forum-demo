package com.demo.forum.model;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "post")
public class Post implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
     
    @Column(nullable = false)
    private Long user_id;
     
    @Column(nullable = false)
    private String title;
     
    @Column(nullable = false)
    private String content;

    public Long getId() {
        return this.id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return this.user_id;
    }

    public void setUserId(final Long user_id) {
        this.user_id = user_id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(final String content) {
        this.content = content;
    }
}