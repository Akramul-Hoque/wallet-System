package hotelManagment.userService.common.response.base;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SuccessResponse extends ApiResponse implements Serializable {

    @JsonProperty("data")
    private Object data;

    public SuccessResponse(Integer messageCode, String message, Object data) {
//        super.setTimestamp(System.currentTimeMillis());
        super.setStatusCode(HttpStatus.OK.value());
        super.setSuccess(true);
        super.setMessageCode(messageCode);
        super.setMessage(message);
        this.data = data;
    }

}
