package co.com.crediya.api.modules.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Nuevo usuario")
public class NewUserRequestDto {

    @NotNull
    @Schema(description = "Nombre del usuario", example = "Juan")
    private String firstname;

    @NotNull
    @Schema(description = "Apellido del usuario", example = "Pérez")
    private String lastname;

    @NotNull
    @Schema(description = "Correo electrónico del usuario", example = "ejemplo@ejemplo.com")
    private String email;

    @NotNull
    @Schema(description = "Número de identificación del usuario", example = "123456789")
    private String dniNumber;


    @Schema(description = "Fecha de nacimiento del usuario", example = "1990-01-01")
    private LocalDate bornDate;

    @NotNull
    @Schema(description = "Número de teléfono del usuario", example = "+1234567890")
    private String phone;

    @NotNull
    @Schema(description = "ID del rol del usuario", example = "1")
    private Integer rolId;

    @NotNull
    @Schema(description = "Salario base del usuario", example = "3000000.00")
    private BigDecimal baseSalary;

}
