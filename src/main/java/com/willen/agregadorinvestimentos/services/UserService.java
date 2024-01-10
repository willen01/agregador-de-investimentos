package com.willen.agregadorinvestimentos.services;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.willen.agregadorinvestimentos.controllers.dto.AccountResponseDTO;
import com.willen.agregadorinvestimentos.controllers.dto.CreateAccountDTO;
import com.willen.agregadorinvestimentos.controllers.dto.CreateUserDTO;
import com.willen.agregadorinvestimentos.controllers.dto.UpdateUserDTO;
import com.willen.agregadorinvestimentos.entities.Account;
import com.willen.agregadorinvestimentos.entities.BillingAddress;
import com.willen.agregadorinvestimentos.entities.User;
import com.willen.agregadorinvestimentos.repositories.AccountRepository;
import com.willen.agregadorinvestimentos.repositories.BillingAddressRepository;
import com.willen.agregadorinvestimentos.repositories.UserRepository;

@Service
public class UserService {

    private UserRepository userRepository;

    private AccountRepository accountRepository;

    private BillingAddressRepository billingAddressRepository;

    public UserService(UserRepository userRepository, AccountRepository accountRepository,
            BillingAddressRepository billingAddressRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.billingAddressRepository = billingAddressRepository;
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

    public void updateUserById(String userId, UpdateUserDTO updateUserDTO) {
        var id = UUID.fromString(userId);

        var userEntity = userRepository.findById(id);

        if (userEntity.isPresent()) {
            var user = userEntity.get();

            if (updateUserDTO.username() != null) {
                user.setUsername(updateUserDTO.username());
            }

            if (updateUserDTO.password() != null) {
                user.setPassword(updateUserDTO.password());
            }

            user.setUpdatedTimestamp(Instant.now());

            userRepository.save(user);
        }
    }

    public void deleteById(String userId) {
        var id = UUID.fromString(userId);
        var userExists = userRepository.existsById(id);

        if (userExists) {
            userRepository.deleteById(id);
        }
    }

    public void createAccount(String userId, CreateAccountDTO createAccountDTO) {
        var user = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        // dto -> entity
        var account = new Account(
                UUID.randomUUID(),
                user,
                createAccountDTO.description(),
                null,
                new ArrayList<>());

        var accountCreated = accountRepository.save(account);

        var billingAddress = new BillingAddress(
                accountCreated.getAccountId(),
                account,
                createAccountDTO.street(),
                createAccountDTO.number());

        billingAddressRepository.save(billingAddress);
    }

    public List<AccountResponseDTO> listAccounts(String userId) {
        var user = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        // retorna como AccountResponseDTO
        return user.getAccounts()
                .stream()
                .map(ac -> new AccountResponseDTO(ac.getAccountId().toString(), ac.getDescriprion()))
                .toList();
    }
}
