package walletSystem.authService.exception;


public class PasswordMismatchException extends RuntimeException {
    private  String field;
    private  String message;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public PasswordMismatchException(String field, String message) {
        this.field = field;
        this.message = message;
    }

}
