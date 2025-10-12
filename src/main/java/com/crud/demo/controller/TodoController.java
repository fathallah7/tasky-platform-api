package com.crud.demo.controller;

import com.crud.demo.models.Todo;
import com.crud.demo.models.User;
import com.crud.demo.repository.TodoRepository;
import com.crud.demo.repository.UserRepository;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/todo")
public class TodoController {

    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

    public TodoController(TodoRepository todoRepository, UserRepository userRepository) {
        this.todoRepository = todoRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<Todo> getUserTodos(Principal principal) {
        User user = userRepository.findByEmail(principal.getName()).orElseThrow();
        return todoRepository.findByUser(user);
    }

    @PostMapping
    public Todo createTodo(@RequestBody @Valid Todo todo, Principal principal) {
        User user = userRepository.findByEmail(principal.getName()).orElseThrow();
        todo.setUser(user);
        return todoRepository.save(todo);
    }
    
    @PutMapping("/{id}")
    public Todo updateTodo(@PathVariable Long id, @RequestBody @Valid Todo newTodoData) {

        Todo existingTodo = todoRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Todo not found"));

        existingTodo.setTitle(newTodoData.getTitle());
        existingTodo.setDescription(newTodoData.getDescription());
        existingTodo.setCompleted(newTodoData.isCompleted());
        
        return todoRepository.save(existingTodo);
    }
    
    @DeleteMapping("/{id}")
    public void deleteTodo(@PathVariable Long id) {
        todoRepository.deleteById(id);
    }
}
