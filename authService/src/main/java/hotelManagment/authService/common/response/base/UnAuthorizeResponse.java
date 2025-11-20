package hotelManagment.authService.common.response.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@Data
public class UnAuthorizeResponse extends ApiResponse implements Serializable {

    @JsonProperty("path")
    private String path;

    @JsonProperty("data")
    private Object data;

    public UnAuthorizeResponse(HttpStatus httpStatus, Integer messageCode, String message, String path) {
        super.statusCode = httpStatus.value();
        super.success = false;
        super.messageCode = messageCode;
        super.message = message;
        this.path = path;
        this.data = null;
    }

}
