package com.crud.demo.repository;

import com.crud.demo.models.Goal;
import com.crud.demo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GoalRepository extends JpaRepository<Goal, Long> {
    List<Goal> findByUser(User user);
}
