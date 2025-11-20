package hotelManagment.authService.auth.Dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonResponse<T> {
    private int code;
    private String message;
    private T response;

    // Constructors, getters, and setters

    // Success response
    public static <T> CommonResponse<T> successResponse(T response, String message) {
        CommonResponse<T> commonResponse = new CommonResponse<>();
        commonResponse.setCode(200);
        commonResponse.setMessage(message);
        commonResponse.setResponse(response);
        return commonResponse;
    }

    // Error response
    public static <T> CommonResponse<T> errorResponse(int code, String message) {
        CommonResponse<T> commonResponse = new CommonResponse<>();
        commonResponse.setCode(code);
        commonResponse.setMessage(message);
        commonResponse.setResponse(null);
        return commonResponse;
    }
}
