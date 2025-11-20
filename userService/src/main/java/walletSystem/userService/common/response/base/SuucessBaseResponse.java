package walletSystem.userService.common.response.base;

import org.springframework.http.HttpStatus;

import java.io.Serializable;

public class SuucessBaseResponse extends ApiResponse implements Serializable {

    public SuucessBaseResponse(Integer messageCode, String message) {
        super.statusCode = HttpStatus.OK.value();
        super.success = true;
        super.messageCode = messageCode;
        super.message = message;
    }

}
