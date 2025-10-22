package com.crud.demo.controller;

import com.crud.demo.dto.ApiResponse;
import com.crud.demo.models.*;
import com.crud.demo.repository.*;

import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/pomodoro")
public class PomodoroController {

    private final PomodoroRepository pomodoroRepository;
    private final UserRepository userRepository;
    private final TodoRepository todoRepository;

    public PomodoroController(PomodoroRepository pomodoroRepository, UserRepository userRepository, TodoRepository todoRepository) {
        this.pomodoroRepository = pomodoroRepository;
        this.userRepository = userRepository;
        this.todoRepository = todoRepository;
    }

    @PostMapping
    public ApiResponse<Pomodoro> startPomodoro(@RequestBody Map<String, Long> request, Principal principal) {
        Long todoId = request.get("todo_id");

        User user = userRepository.findByEmail(principal.getName()).orElseThrow();
        Todo todo = todoRepository.findById(todoId).orElseThrow(() -> new RuntimeException("Todo not found"));

        Pomodoro pomodoro = new Pomodoro();
        pomodoro.setUser(user);
        pomodoro.setTodo(todo);
        pomodoro.setStartTime(LocalDateTime.now());

        Pomodoro savedPomodoro = pomodoroRepository.save(pomodoro);
        return ApiResponse.success("Pomodoro started successfully", savedPomodoro);
    }

}
