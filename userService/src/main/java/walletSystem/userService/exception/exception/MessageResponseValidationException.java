package walletSystem.userService.exception.exception;

/**
 * Created by DELL on 12-Dec-19.
 */
public class MessageResponseValidationException extends RuntimeException {
    private String message;


    public MessageResponseValidationException(String dummy,String message){
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
