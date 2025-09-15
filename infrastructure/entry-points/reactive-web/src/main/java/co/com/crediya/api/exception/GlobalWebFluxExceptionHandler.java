package co.com.crediya.api.exception;

import co.com.crediya.model.exception.ExceptionHandler;
import co.com.crediya.model.exception.TypeException;
import co.com.crediya.api.reponse.ResponseApi;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;
import java.time.OffsetDateTime;


@Component
@Order(-2) // Prioridad alta para que se ejecute antes que el default
public class GlobalWebFluxExceptionHandler implements WebExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalWebFluxExceptionHandler.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {

        String errorCode;
        String message;
        int statusCode;
        String typeException;


        if (ex instanceof ExceptionHandler) {

            ExceptionHandler businessEx = (ExceptionHandler) ex;

            errorCode= businessEx.getEstadoError().getCode();
            message = businessEx.getMessage();
            statusCode = businessEx.getEstadoError().getStatus();
            typeException = businessEx.getEstadoError().getTypeException();

            logger.error("Error", businessEx.getMessage());

        } else {

            errorCode = String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR);
            message = "Error interno del servidor: " + ex.getMessage();
            statusCode = 500;
            typeException = TypeException.SystemException.toString();

            logger.error("Error inesperado: ", ex);

        }

        ResponseApi<Object> response = ResponseApi.builder()
                .typeException(typeException)
                .code(errorCode)
                .timestamp(OffsetDateTime.now().toString())
                .path(exchange.getRequest().getPath().value())
                .success(false)
                .message(message)
                .data(null)
                .build();

        exchange.getResponse().setStatusCode(HttpStatusCode.valueOf(statusCode));
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

        try {
            byte[] bytes = objectMapper.writeValueAsBytes(response);
            DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
            return exchange.getResponse().writeWith(Mono.just(buffer));
        } catch (Exception e) {
            logger.error("Error serializando la respuesta de error", e);
            return exchange.getResponse().setComplete();
        }

    }

}