package hotelManagment.authService.common.response.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonResponse implements Serializable {

    @JsonProperty("timestamp")
    private Long timestamp = new Date().getTime();

    @JsonProperty("status")
    private String status;

    @JsonProperty("statusCode")
    private Integer statusCode;

    @JsonProperty("message")
    private String message;

    @JsonProperty("messageCode")
    private Integer messageCode;

    @JsonProperty("data")
    private Object data;

    public static CommonResponse success(Object data, String message, Integer messageCode, Integer statusCode) {
        return new CommonResponse(new Date().getTime(), "SUCCESS", statusCode, message, messageCode, data);
    }

    public static CommonResponse failure(String message, Integer messageCode, Integer statusCode) {
        return new CommonResponse(new Date().getTime(), "ERROR", statusCode, message, messageCode, null);
    }
}
