package com.crud.demo.controller;

import com.crud.demo.dto.ApiResponse;
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
    public ApiResponse<List<Todo>> getUserTodos(Principal principal) {
        User user = userRepository.findByEmail(principal.getName()).orElseThrow();
        List<Todo> todos = todoRepository.findByUser(user);
        return ApiResponse.success("Fetched todos successfully",todos);
    }

    @PostMapping
    public ApiResponse<Todo> createTodo(@RequestBody @Valid Todo todo, Principal principal) {
        User user = userRepository.findByEmail(principal.getName()).orElseThrow();
        todo.setUser(user);
        Todo savedTodo = todoRepository.save(todo);
        return ApiResponse.success("Todo created successfully",savedTodo);
    }
    
    @PutMapping("/{id}")
    public ApiResponse<Todo> updateTodo(@PathVariable Long id, @RequestBody @Valid Todo newTodoData) {

        Todo existingTodo = todoRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Todo not found"));

        existingTodo.setTitle(newTodoData.getTitle());
        existingTodo.setDescription(newTodoData.getDescription());
        existingTodo.setCompleted(newTodoData.isCompleted());

        Todo updatedTodo = todoRepository.save(existingTodo);
        return ApiResponse.success("Todo updated successfully", updatedTodo);
    }
    
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteTodo(@PathVariable Long id) {
        todoRepository.deleteById(id);
        return ApiResponse.success("Todo deleted successfully", null);
    }
}
