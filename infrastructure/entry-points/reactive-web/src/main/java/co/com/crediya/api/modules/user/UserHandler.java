package co.com.crediya.api.modules.user;

import co.com.crediya.api.modules.user.dto.NewUserRequestDto;
import co.com.crediya.api.modules.user.mapper.UserMapper;
import co.com.crediya.api.reponse.ResponseApiFactory;
import co.com.crediya.usecase.user.UserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
@RequiredArgsConstructor
public class UserHandler {

    private static final Logger logger = LoggerFactory.getLogger(UserHandler.class);
    private final UserUseCase userUseCase;

    public Mono<ServerResponse> listenSaveUser(ServerRequest serverRequest) {

        logger.info("Received request to create user");

        return serverRequest.bodyToMono(NewUserRequestDto.class)
                .doOnNext(dto -> logger.debug("Request body: {}", dto))
                .map(UserMapper::toDomain)
                .flatMap(userUseCase::save)
                .doOnNext(user -> logger.info("User created successfully: {}", user))
                .map(UserMapper::toUserResponseDto)
                .map(responseDto -> ResponseApiFactory.responseApi(
                        null,
                        null,
                        null,
                        null,
                        true,
                        "Usuario creado exitosamente",
                        responseDto))
                .flatMap(responseApi -> ServerResponse.status(HttpStatus.CREATED)
                        .bodyValue(responseApi))
                .doOnError(error -> logger.error("Error creating user", error));

    }

    public Mono<ServerResponse> listenExistsByIdUser (ServerRequest serverRequest) {

        String dni = serverRequest.queryParam("dni").orElse(null);

        logger.info("Received request to check existence of user by ID: {}", dni);

        return userUseCase.existsByDniNumber(dni)
                .doOnNext(exists -> logger.info("Existence check result for ID {}: {}", dni, exists))
                .map(exists -> ResponseApiFactory.responseApi(
                        null,
                        null,
                        null,
                        null,
                        true,
                        "Usuario existe en el sistema",
                        null))
                .flatMap(responseApi -> ServerResponse.status(HttpStatus.OK)
                        .bodyValue(responseApi))
                .doOnError(error -> logger.error("Error checking existence by ID", error));

    }

}
