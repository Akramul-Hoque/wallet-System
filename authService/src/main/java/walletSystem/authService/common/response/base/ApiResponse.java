package walletSystem.authService.common.response.base;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class ApiResponse implements Serializable {

	@JsonProperty("timestamp")
	private Long timestamp;

	@JsonProperty("statusCode")
	protected Integer statusCode;

	@JsonProperty("success")
	protected Boolean success;

	@JsonProperty("messageCode")
	protected Integer messageCode;

	@JsonProperty("message")
	protected String message;

	/*public ApiResponse(Integer statusCode, Boolean success){
		this.timestamp = new Date().getTime();
		this.statusCode = statusCode;
		this.success = success;
	}*/

//	public ApiResponse(Boolean success, Integer messageCode, String message){
//		this.timestamp = new Date().getTime();
//		this.success = success;
//		this.messageCode = messageCode;
//		this.message = message;
//	}

}
