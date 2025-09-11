package co.com.crediya.r2dbc.modules.user;

import co.com.crediya.model.user.User;
import co.com.crediya.model.user.gateways.UserRepository;
import co.com.crediya.r2dbc.modules.user.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

@Repository
@AllArgsConstructor
public class UserRepositoryAdapter implements UserRepository {
    private static final Logger logger = LoggerFactory.getLogger(UserRepositoryAdapter.class);

    private final TransactionalOperator transactionalOperator;
    private final UserReactiveRepository userReactiveRepository;

    @Override
    public Mono<User> findByEmail(String email) {
        logger.info("Finding user by email: {}", email);
        return userReactiveRepository.findByEmail(email)
                .map(UserMapper::toDomain)
                .doOnNext(user -> logger.debug("Found user: {}", user));
    }

    @Override
    public Mono<User> findById(String id) {
        logger.info("Finding user by id: {}", id);
        return userReactiveRepository.findById(java.util.UUID.fromString(id))
                .map(UserMapper::toDomain)
                .doOnNext(user -> logger.debug("Found user: {}", user));
    }

    @Override
    public Mono<User> save(User user) {
        logger.info("Saving user: {}", user);
        return userReactiveRepository.save(UserMapper.toEntity(user))
                .map(UserMapper::toDomain)
                .doOnNext(savedUser -> logger.info("User saved: {}", savedUser))
                .as(transactionalOperator::transactional);
    }

    @Override
    public Mono<Void> deleteById(UUID id) {
        logger.info("Deleting user by id: {}", id);
        return userReactiveRepository.deleteById(id)
                .doOnSuccess(v -> logger.info("User deleted: {}", id));
    }

    @Override
    public Mono<Boolean> existsByEmail(String email) {
        logger.info("Checking existence by email: {}", email);
        return userReactiveRepository.existsByEmail(email)
                .doOnNext(exists -> logger.debug("Exists by email {}: {}", email, exists));
    }

    @Override
    public Mono<Boolean> existsByDniNumber(String dniNumber) {
        logger.info("Checking existence by dniNumber: {}", dniNumber);
        return userReactiveRepository.findByDniNumber(dniNumber)
                .hasElement()
                .doOnNext(exists -> logger.debug("Exists by dniNumber {}: {}", dniNumber, exists));
    }

    @Override
    public Flux<User> findAll() {
        logger.info("Finding all users");
        return userReactiveRepository.findAll()
                .map(UserMapper::toDomain)
                .doOnNext(user -> logger.debug("Found user: {}", user));
    }
}