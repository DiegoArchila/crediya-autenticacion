package co.com.crediya.api.modules.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto extends NewUserRequestDto{

    @Schema(description = "ID del usuario", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID id;

}
