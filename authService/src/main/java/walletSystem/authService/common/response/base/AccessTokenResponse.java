package walletSystem.authService.common.response.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccessTokenResponse {

    @JsonProperty("statusCode")
    private Integer statusCode;

    @JsonProperty("success")
    private Boolean success;

    @JsonProperty("messageCode")
    private String messageCode;

    @JsonProperty("message")
    private String message;

    @JsonProperty("accessToken")
    private String accessToken;
}
