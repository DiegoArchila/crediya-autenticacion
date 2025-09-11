package co.com.crediya.api.reponse;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseApi<T> {

    @JsonInclude(NON_NULL)
    private String typeException;

    @JsonInclude(NON_NULL)
    private String code;

    @JsonInclude(NON_NULL)
    private Boolean success;

    private String message;

    @JsonInclude(NON_NULL)
    private T data;


}
