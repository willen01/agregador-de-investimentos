package com.willen.agregadorinvestimentos.services;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.willen.agregadorinvestimentos.controllers.CreateUserDTO;
import com.willen.agregadorinvestimentos.entities.User;
import com.willen.agregadorinvestimentos.repositories.UserRepository;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UUID createUser(CreateUserDTO createUserDTO) {

        var entity = new User(UUID.randomUUID(),
                createUserDTO.username(),
                createUserDTO.email(),
                createUserDTO.password(), Instant.now(),
                null);

        var userSaved = userRepository.save(entity);
        return userSaved.getUserId();
    }

    public Optional<User> getUserById(String userId) {
        var user = userRepository.findById(UUID.fromString(userId));

        return user;
    }

    public List<User> listUsers() {
        return userRepository.findAll();
    }
}
