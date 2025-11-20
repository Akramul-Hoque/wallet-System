package hotelManagment.userService.common.response.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@Data
public class ErrorResponse extends ApiResponse implements Serializable {

    @JsonProperty("data")
    private Object data;

    public ErrorResponse(HttpStatus httpStatus, Integer messageCode, String message) {
        super.statusCode = httpStatus.value();
        super.success = false;
        super.messageCode = messageCode;
        super.message = message;
        this.data = null;
    }

}
