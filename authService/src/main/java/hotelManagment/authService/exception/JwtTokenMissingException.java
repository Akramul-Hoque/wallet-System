package hotelManagment.authService.exception;


public class JwtTokenMissingException extends RuntimeException {
    private  String field;
    private  String message;
    private  String path;

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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public JwtTokenMissingException(String field, String message, String path) {
        this.field = field;
        this.message = message;
        this.path = path;
    }
}
