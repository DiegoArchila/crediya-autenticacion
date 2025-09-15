package co.com.crediya.api.modules.user;

import co.com.crediya.api.modules.user.path.UserPath;
import co.com.crediya.api.modules.user.dto.NewUserRequestDto;
import co.com.crediya.api.reponse.ResponseApi;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.RouterOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import org.springframework.web.bind.annotation.RequestMethod;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RequiredArgsConstructor
@Configuration
public class UserRouterRest {

    private final UserPath userPath;

    @Bean
    @RouterOperation(
            beanClass = UserHandler.class,
            beanMethod = "listenSaveUser",
            path = "/api/v1/usuarios",
            method = RequestMethod.POST,
            operation = @Operation(
                    operationId = "listenSaveUser",
                    tags = {"Usuarios"},
                    summary = "Crear un nuevo usuario",
                    description = "Este endpoint permite crear un nuevo usuario en el sistema.",
                    requestBody = @RequestBody(
                            description = "Datos del nuevo usuario",
                            required = true,
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = NewUserRequestDto.class)
                            )
                    ),
                    responses = {
                            @ApiResponse(
                                    responseCode = "201",
                                    description = "Usuario creado exitosamente",
                                    content = @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ResponseApi.class)
                                    )
                            ),
                            @ApiResponse(
                                    responseCode = "400",
                                    description = "Error de validación (por ejemplo, email vacío, documento vacío, etc.)",
                                    content = @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ResponseApi.class)
                                    )
                            ),
                            @ApiResponse(
                                    responseCode = "409",
                                    description = "Error de negocio (por ejemplo, email ya registrado, documento ya registrado)",
                                    content = @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ResponseApi.class)
                                    )
                            )
                    }
            )

    )
    public RouterFunction<ServerResponse> routerFunction(UserHandler handler) {

        return route(POST(userPath.getUser()), handler::listenSaveUser);

    }

    @Bean
    public RouterFunction<ServerResponse> routerFunctionUser(UserHandler handler) {

        return route(GET(userPath.getUser()), handler::listenExistsByIdUser);

    }
}
