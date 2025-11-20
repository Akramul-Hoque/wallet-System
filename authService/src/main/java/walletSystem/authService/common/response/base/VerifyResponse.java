package walletSystem.authService.common.response.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@Data
public class VerifyResponse extends ApiResponse implements Serializable {

    @JsonProperty("data")
    private Object data;

    public VerifyResponse(HttpStatus httpStatus, Boolean success, Integer messageCode, String message, Object data) {
        super.statusCode = httpStatus.value();
        super.success = success;
        super.messageCode = messageCode;
        super.message = message;
        this.data = data;
    }

}