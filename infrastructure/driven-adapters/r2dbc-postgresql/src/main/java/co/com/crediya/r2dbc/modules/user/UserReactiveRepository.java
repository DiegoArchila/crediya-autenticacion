package co.com.crediya.r2dbc.modules.user;

import co.com.crediya.r2dbc.modules.user.entity.UserEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;
import java.util.UUID;

public interface UserReactiveRepository extends
        ReactiveCrudRepository<UserEntity, UUID>, ReactiveQueryByExampleExecutor<UserEntity> {

    @Query("SELECT u FROM users u WHERE u.email = $1")
    Mono<UserEntity> findByEmail(String email);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM users u WHERE TRIM(LOWER(u.email)) = TRIM(LOWER(:email))")
    Mono<Boolean> existsByEmail(@Param("email") String email);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM users u WHERE TRIM(u.dni_number) = TRIM(:dniNumber)")
    Mono<Boolean> existsByDniNumber(@Param("dniNumber") String dniNumber);

    @Query("SELECT u FROM users u WHERE TRIM(u.dni_number) = TRIM(:dniNumber)")
    Mono<UserEntity> findByDniNumber(@Param("dniNumber") String dniNumber);

}
