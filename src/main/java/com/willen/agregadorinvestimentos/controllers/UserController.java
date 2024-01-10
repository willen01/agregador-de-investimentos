package com.willen.agregadorinvestimentos.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.willen.agregadorinvestimentos.controllers.dto.AccountResponseDTO;
import com.willen.agregadorinvestimentos.controllers.dto.CreateAccountDTO;
import com.willen.agregadorinvestimentos.controllers.dto.CreateUserDTO;
import com.willen.agregadorinvestimentos.controllers.dto.UpdateUserDTO;
import com.willen.agregadorinvestimentos.entities.User;
import com.willen.agregadorinvestimentos.services.UserService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody CreateUserDTO createUserDTO) {
        var userId = userService.createUser(createUserDTO);

        return ResponseEntity.created(URI.create("/v1/users/" + userId.toString())).build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable("userId") String userId) {

        var user = userService.getUserById(userId);

        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<User>> listUsers() {
        var users = userService.listUsers();

        return ResponseEntity.ok(users);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Void> updateUserById(@PathVariable String userId,
            @RequestBody UpdateUserDTO userDTO) {

        userService.updateUserById(userId, userDTO);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteById(@PathVariable String userId) {

        userService.deleteById(userId);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{userId}/accounts")
    public ResponseEntity<Void> createAccount(
            @PathVariable("userId") String userId,
            @RequestBody CreateAccountDTO createAccountDTO) {

        userService.createAccount(userId, createAccountDTO);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{userId}/accounts")
    public ResponseEntity<List<AccountResponseDTO>> listAccounts(
            @PathVariable("userId") String userId) {

        var accounts = userService.listAccounts(userId);

        return ResponseEntity.ok(accounts);
    }

}
