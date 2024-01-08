package com.willen.agregadorinvestimentos.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.willen.agregadorinvestimentos.controllers.CreateUserDTO;
import com.willen.agregadorinvestimentos.entities.User;
import com.willen.agregadorinvestimentos.repositories.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    // captura argumento passado para um parâmetro
    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;

    // nested class - subclasse para organizar melhor os testes
    @Nested
    class createUser {

        @Test
        @DisplayName("Should create a user with success")
        void shouldCreateAUserWithSuccess() {
            // Arrange - organiza cenário de teste
            var user = new User(
                    UUID.randomUUID(),
                    "username",
                    "email@server.com",
                    "123",
                    Instant.now(), null);

            doReturn(user).when(userRepository).save(userArgumentCaptor.capture());

            var input = new CreateUserDTO(
                    "username",
                    "email@server.com",
                    "123");

            // Act - chama unidade a ser testada
            var output = userService.createUser(input);

            // Assert - verificações para testar
            assertNotNull(output);

            var userCaptured = userArgumentCaptor.getValue();
            assertEquals(input.username(), userCaptured.getUsername());
            assertEquals(input.email(), userCaptured.getEmail());
            assertEquals(input.password(), userCaptured.getPassword());

        }

        @Test
        @DisplayName("Should throw exception when error occurs")
        void shouldThrowExceptionWhenErrorOccurs() {

            doThrow(new RuntimeException()).when(userRepository).save(any());
            var input = new CreateUserDTO(
                    "username",
                    "email@server.com",
                    "123");

            // Act - Assert
            assertThrows(RuntimeException.class, () -> userService.createUser(input));
        }
    }

}
