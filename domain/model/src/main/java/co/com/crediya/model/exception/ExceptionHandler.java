package co.com.crediya.model.exception;

public class ExceptionHandler extends RuntimeException{

    private final MessageException error;

    public ExceptionHandler(MessageException error) {
        super(error.getMessage());
        this.error = error;
    }

    public ExceptionHandler(MessageException error, Throwable cause) {
        super(error.getMessage(), cause);
        this.error = error;
    }

    public MessageException getEstadoError() {
        return error;
    }


}
