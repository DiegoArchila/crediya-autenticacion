package co.com.crediya.usecase.user;

import co.com.crediya.model.exception.ExceptionHandler;
import co.com.crediya.model.user.User;
import co.com.crediya.model.user.exception.UserErrorCode;
import co.com.crediya.model.user.gateways.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserUseCase userUseCase;

    private User validUser;

    @BeforeEach
    void setUp() {
        validUser = new User(
                UUID.randomUUID(),
                "Jose",
                "Navarro",
                "jose.navarro@example.com",
                "123456789012",
                LocalDate.of(1990, 1, 1),
                "+1234567890",
                1,
                BigDecimal.valueOf(3000000.00)
        );
    }

    @Test
    void shouldSaveUserSuccessfully() {
        when(userRepository.existsByEmail(validUser.getEmail())).thenReturn(Mono.just(false));
        when(userRepository.existsByDniNumber(validUser.getDniNumber())).thenReturn(Mono.just(false));
        when(userRepository.save(any(User.class))).thenReturn(Mono.just(validUser));

        Mono<User> result = userUseCase.save(validUser);

        StepVerifier.create(result)
                .expectNextMatches(user -> user.getEmail().equals(validUser.getEmail()) && user.getId() != null)
                .verifyComplete();
    }

    @Test
    void shouldReturnErrorWhenEmailAlreadyExists() {
        when(userRepository.existsByEmail(validUser.getEmail())).thenReturn(Mono.just(true));

        Mono<User> result = userUseCase.save(validUser);

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof ExceptionHandler
                        && ((ExceptionHandler) throwable).getEstadoError() == UserErrorCode.email_AlreadyExists)
                .verify();
    }

    @Test
    void shouldReturnErrorWhenDniAlreadyExists() {
        when(userRepository.existsByEmail(validUser.getEmail())).thenReturn(Mono.just(false));
        when(userRepository.existsByDniNumber(validUser.getDniNumber())).thenReturn(Mono.just(true));

        Mono<User> result = userUseCase.save(validUser);

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof ExceptionHandler
                        && ((ExceptionHandler) throwable).getEstadoError() == UserErrorCode.documentIdentity_AlreadyExists)
                .verify();
    }

    @Test
    void shouldFindUserByEmail() {
        when(userRepository.findByEmail(validUser.getEmail())).thenReturn(Mono.just(validUser));

        Mono<User> result = userUseCase.findByEmail(validUser.getEmail());

        StepVerifier.create(result)
                .expectNextMatches(user -> user.getEmail().equals(validUser.getEmail()))
                .verifyComplete();
    }

    @Test
    void shouldFindUserById() {
        when(userRepository.findById(validUser.getId().toString())).thenReturn(Mono.just(validUser));

        Mono<User> result = userUseCase.findById(validUser.getId().toString());

        StepVerifier.create(result)
                .expectNextMatches(user -> user.getId().equals(validUser.getId()))
                .verifyComplete();
    }

    @Test
    void shouldThrowValidationErrorWhenEmailIsInvalid() {
        User invalidUser = new User(
                UUID.randomUUID(),
                "CArlos",
                "Anastasio",
                "cracoles.colores.com",
                "",
                LocalDate.of(2025, 1, 1),
                "3125652452",
                6,
                BigDecimal.valueOf(350000.00)
        );

        Mono<User> result = userUseCase.save(invalidUser);

        StepVerifier.create(result)
                .expectError(ExceptionHandler.class)
                .verify();
    }

    @Test
    void shouldReturnErrorWhenSaveIsCalledWithNull() {
        Mono<User> result = userUseCase.save(null);
        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof IllegalArgumentException
                        && throwable.getMessage().contains("User cannot be null"))
                .verify();
    }

    @Test
    void shouldReturnErrorWhenFindByEmailIsCalledWithNull() {
        Mono<User> result = userUseCase.findByEmail(null);
        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof IllegalArgumentException
                        && throwable.getMessage().contains("email cannot be null"))
                .verify();
    }

    @Test
    void shouldReturnErrorWhenFindByIdIsCalledWithNull() {
        Mono<User> result = userUseCase.findById(null);
        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof IllegalArgumentException
                        && throwable.getMessage().contains("id cannot be null"))
                .verify();
    }

    @Test
    void shouldReturnErrorWhenRepositoryThrowsOnSave() {
        when(userRepository.existsByEmail(validUser.getEmail())).thenReturn(Mono.just(false));
        when(userRepository.existsByDniNumber(validUser.getDniNumber())).thenReturn(Mono.just(false));
        when(userRepository.save(any(User.class))).thenReturn(Mono.error(new RuntimeException("DB error")));
        Mono<User> result = userUseCase.save(validUser);
        StepVerifier.create(result)
                .expectError(RuntimeException.class)
                .verify();
    }

    @Test
    void shouldReturnErrorWhenRepositoryThrowsOnFindByEmail() {
        when(userRepository.findByEmail(validUser.getEmail())).thenReturn(Mono.error(new RuntimeException("DB error")));
        Mono<User> result = userUseCase.findByEmail(validUser.getEmail());
        StepVerifier.create(result)
                .expectError(RuntimeException.class)
                .verify();
    }

    @Test
    void shouldReturnErrorWhenRepositoryThrowsOnFindById() {
        when(userRepository.findById(validUser.getId().toString())).thenReturn(Mono.error(new RuntimeException("DB error")));
        Mono<User> result = userUseCase.findById(validUser.getId().toString());
        StepVerifier.create(result)
                .expectError(RuntimeException.class)
                .verify();
    }

    @Test
    void shouldDeleteUserByIdSuccessfully() {
        UUID userId = validUser.getId();
        when(userRepository.deleteById(userId)).thenReturn(Mono.empty());
        Mono<Void> result = userUseCase.deleteById(userId);
        StepVerifier.create(result)
                .verifyComplete();
    }

    @Test
    void shouldFindAllUsersSuccessfully() {
        when(userRepository.findAll()).thenReturn(Flux.just(validUser));
        StepVerifier.create(userUseCase.findAll())
                .expectNext(validUser)
                .verifyComplete();
    }
}
