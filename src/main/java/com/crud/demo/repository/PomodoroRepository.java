package com.crud.demo.repository;

import com.crud.demo.models.Pomodoro;
import com.crud.demo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PomodoroRepository extends JpaRepository<Pomodoro, Long> {
    List<Pomodoro> findByUser(User user);
}
