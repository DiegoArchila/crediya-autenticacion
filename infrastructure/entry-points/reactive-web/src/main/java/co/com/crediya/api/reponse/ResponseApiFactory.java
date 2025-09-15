package co.com.crediya.api.reponse;

import java.util.Date;

public class ResponseApiFactory {

    public static <T> ResponseApi<T> responseApi(String typeException, String code,String timestamp, String path, Boolean success,
                                                 String message, T data) {
        return ResponseApi.<T>builder()
                .typeException(typeException)
                .code(code)
                .timestamp(timestamp != null ? new Date().toString() : timestamp)
                .path(path == null ? null : path)
                .success(success)
                .message(message)
                .data(Boolean.TRUE.equals(success) ? data : null)
                .build();

    }

}
