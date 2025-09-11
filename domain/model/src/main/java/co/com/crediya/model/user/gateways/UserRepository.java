package co.com.crediya.model.user.gateways;

import co.com.crediya.model.user.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface UserRepository {

    Mono<User> findById(String id);
    Mono<User> findByEmail(String email);
    Mono<User> save(User user);
    Mono<Void> deleteById(UUID id);
    Mono<Boolean> existsByEmail(String email);
    Mono<Boolean> existsByDniNumber(String dniNumber);
    Flux<User> findAll();

}
