package co.com.crediya.api.reponse;

public class ResponseApiFactory {

    public static <T> ResponseApi<T> responseApi(String typeException, String code, Boolean success,
                                                 String message, T data) {
        return ResponseApi.<T>builder()
                .typeException(typeException)
                .code(code)
                .success(success)
                .message(message)
                .data(Boolean.TRUE.equals(success) ? data : null)
                .build();

    }

}
