package com.crud.demo.controller;

import com.crud.demo.dto.ApiResponse;
import com.crud.demo.models.Goal;
import com.crud.demo.models.User;
import com.crud.demo.repository.GoalRepository;
import com.crud.demo.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/goal")
public class GoalController {

    private final GoalRepository goalRepository;
    private final UserRepository userRepository;

    public GoalController(GoalRepository goalRepository, UserRepository userRepository) {
        this.goalRepository = goalRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public ApiResponse<List<Goal>> getUserGoals(Principal principal) {
        User user = userRepository.findByEmail(principal.getName()).orElseThrow();
        List<Goal> goals = goalRepository.findByUser(user);
        return ApiResponse.success("Fetched goals successfully", goals);
    }

    @PostMapping
    public ApiResponse<Goal> createGoal(@RequestBody @Valid Goal goal, Principal principal) {
        User user = userRepository.findByEmail(principal.getName()).orElseThrow();
        goal.setUser(user);
        Goal savedGoal = goalRepository.save(goal);
        return ApiResponse.success("Goal created successfully", savedGoal);
    }

    @PutMapping("/{id}")
    public ApiResponse<Goal> updateGoal(@PathVariable Long id, @RequestBody @Valid Goal newGoal, Principal principal) {
        User user = userRepository.findByEmail(principal.getName()).orElseThrow();

        Goal existingGoal = goalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Goal Not Found"));

        if (!existingGoal.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You are not authorized to update this goal");
        }

        existingGoal.setTitle(newGoal.getTitle());
        existingGoal.setDescription(newGoal.getDescription());
        existingGoal.setCompleted(newGoal.getCompleted());

        Goal updatedGoal = goalRepository.save(existingGoal);
        return ApiResponse.success("Goal updated successfully", updatedGoal);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteGoal(@PathVariable Long id, Principal principal) {
        User user = userRepository.findByEmail(principal.getName()).orElseThrow();

        Goal existingGoal = goalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Goal Not Found"));

        if (!existingGoal.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You are not authorized to update this goal");
        }

        goalRepository.deleteById(id);
        return ApiResponse.success("Goal deleted successfully", null);
    }

}
