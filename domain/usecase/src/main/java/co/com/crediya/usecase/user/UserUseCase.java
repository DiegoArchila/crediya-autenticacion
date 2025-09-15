package co.com.crediya.usecase.user;

import co.com.crediya.model.exception.ExceptionHandler;
import co.com.crediya.model.user.User;
import co.com.crediya.model.user.exception.UserErrorCode;
import co.com.crediya.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

import java.util.UUID;

@RequiredArgsConstructor
public class UserUseCase {

    public final UserRepository userRepository;

    public Mono<User> findByEmail(String email) {
        if (email == null) {
            return Mono.error(new IllegalArgumentException("email cannot be null"));
        }
        return userRepository.findByEmail(email);
    }

    public Mono<User> findById(String id) {
        if (id == null) {
            return Mono.error(new IllegalArgumentException("id cannot be null"));
        }
        return userRepository.findById(id);
    }

    /**
     * Save a user after validating it and checking for unique email and DNI number.
     *
     * @param user The user to be saved.
     * @return A Mono emitting the saved user or an error if validation fails or
     *         if the email or DNI number already exists.
     */
    public Mono<User> save(User user) {

        return Mono.fromCallable(() -> {

            user.validate();
            return user;

        })
        .flatMap(u -> existsByEmail(u.getEmail())
                .flatMap(emailExists -> {

                    if (emailExists) {
                        return Mono.error(new ExceptionHandler(UserErrorCode.email_AlreadyExists));
                    }

                    return existsByDniNumber(u.getDniNumber());

                })
                .flatMap(dniExists -> {

                    if (dniExists) {
                        return Mono.error(new ExceptionHandler(UserErrorCode.documentIdentity_AlreadyExists));
                    }

                    return userRepository.save(u);
                })
        );

    }

    public Mono<Void> deleteById(UUID id) {
        return userRepository.deleteById(id);
    }

    public Mono<Boolean> existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public Mono<Boolean> existsByDniNumber(String dniNumber) {

        return userRepository.existsByDniNumber(dniNumber)
                .doOnNext(exists -> {
                    if (!exists) {
                        throw new ExceptionHandler(UserErrorCode.USER_NOT_FOUND);
                    }
                })
                .thenReturn(true);

    }

    public Flux<User> findAll() {
        return userRepository.findAll();
    }

}
