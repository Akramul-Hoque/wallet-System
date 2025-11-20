package walletSystem.userService.common.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

//@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ResponseBaseData<T> {

    @JsonProperty("statusCode")
    protected Integer statusCode;

    @JsonProperty("success")
    protected Boolean success;

    @JsonProperty("messageCode")
    protected String messageCode;

    @JsonProperty("message")
    protected String message;

    @JsonProperty("data")
    private T data;

}
