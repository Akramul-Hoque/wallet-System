package walletSystem.authService.exception;

/**
 * Created by Faisal on on 2021-06-27.
 */
public class TokenExpiredException extends RuntimeException {
    private  String message;
    private  String field;

    public TokenExpiredException(String field,String message) {
        this.field = field;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }
}
