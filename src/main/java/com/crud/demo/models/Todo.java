package com.crud.demo.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is Required")
    private String title;

    @Size(max = 400, message = "Description cannot exceed 400 characters")
    private String description;
    
    private boolean completed;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Todo() {
    }

    public Todo(final Long id, final String title, final String description, final boolean completed) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.completed = completed;
    }

    public Long getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public User getUser() {
        return this.user;
    }
    public void setUser(final User user) {
        this.user = user;
    }

    public String getDescription() {
        return this.description;
    }

    public boolean isCompleted() {
        return this.completed;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setCompleted(final boolean completed) {
        this.completed = completed;
    }

}
