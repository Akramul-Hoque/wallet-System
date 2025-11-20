package walletSystem.userService.exception.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ValidationException extends RuntimeException {

    private  String field;
    private  String message;

}
