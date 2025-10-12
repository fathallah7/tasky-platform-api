package com.crud.demo.repository;

import com.crud.demo.models.Todo;
import com.crud.demo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findByUser(User user);
}
