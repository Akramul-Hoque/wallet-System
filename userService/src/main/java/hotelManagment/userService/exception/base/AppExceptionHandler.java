package hotelManagment.userService.exception.base;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import hotelManagment.userService.common.response.base.ErrorResponse;
import hotelManagment.userService.exception.exception.ApiException;
import hotelManagment.userService.exception.exception.InvalidCredentialsException;
import hotelManagment.userService.exception.exception.NoDataFoundException;
import hotelManagment.userService.exception.exception.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class AppExceptionHandler   {

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoHandlerFound(NoHandlerFoundException exception) {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND; // 404
        String message = "Requested URL not found: " + exception.getRequestURL();

        ErrorResponse errorResponse = new ErrorResponse(httpStatus, 99, message);
        return ResponseEntity.status(httpStatus)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponse);
    }


    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException exception) {
        HttpStatus httpStatus = HttpStatus.OK;
        String message = exception.getMessage();

        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, 99, message);
        Gson gson = new GsonBuilder().serializeNulls().create();
        String jsonResponse = gson.toJson(errorResponse);
        return ResponseEntity.status(httpStatus).contentType(MediaType.parseMediaType("application/json;charset=UTF-8")).body(jsonResponse);
    }


    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseBody
    public ResponseEntity<?> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException exception) {
        HttpStatus httpStatus = HttpStatus.OK;
        String message = exception.getMessage();

        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, 99, message);
        Gson gson = new GsonBuilder().serializeNulls().create();
        String jsonResponse = gson.toJson(errorResponse);
        return ResponseEntity.status(httpStatus).contentType(MediaType.parseMediaType("application/json;charset=UTF-8")).body(jsonResponse);
    }


    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    public ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException exception) {
         HttpStatus httpStatus = HttpStatus.OK;
         String message = exception.getMessage();

        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, 99, message);
        Gson gson = new GsonBuilder().serializeNulls().create();
        String jsonResponse = gson.toJson(errorResponse);
        return ResponseEntity.status(httpStatus).contentType(MediaType.parseMediaType("application/json;charset=UTF-8")).body(jsonResponse);
    }


    @ExceptionHandler(MissingPathVariableException.class)
    @ResponseBody
    public ResponseEntity<Object>  handleMissingPathVariableException(MissingPathVariableException exception) {
        HttpStatus httpStatus = HttpStatus.OK;
        String message = exception.getMessage();

        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,99, message);
        Gson gson = new GsonBuilder().serializeNulls().create();
        String jsonResponse = gson.toJson(errorResponse);
        return ResponseEntity.status(httpStatus).contentType(MediaType.parseMediaType("application/json;charset=UTF-8")).body(jsonResponse);
    }


    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public ResponseEntity<?> methodNotSupportedException(HttpRequestMethodNotSupportedException exception) {
        HttpStatus httpStatus = HttpStatus.OK;
        String message = exception.getMessage();

        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.METHOD_NOT_ALLOWED, 99, message);
        Gson gson = new GsonBuilder().serializeNulls().create();
        String jsonResponse = gson.toJson(errorResponse);
        return ResponseEntity.status(httpStatus).contentType(MediaType.parseMediaType("application/json;charset=UTF-8")).body(jsonResponse);
    }


    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<?> methodArgumentNotValidException(MethodArgumentNotValidException exception) {
        HttpStatus httpStatus = HttpStatus.OK;
        String message = exception.getMessage();

        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.METHOD_NOT_ALLOWED, 99, message);
        Gson gson = new GsonBuilder().serializeNulls().create();
        String jsonResponse = gson.toJson(errorResponse);
        return ResponseEntity.status(httpStatus).contentType(MediaType.parseMediaType("application/json;charset=UTF-8")).body(jsonResponse);
    }


    @ExceptionHandler({HttpMessageNotReadableException.class})
    @ResponseBody
    public ResponseEntity<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException exception) {
        HttpStatus httpStatus = HttpStatus.OK;
        String message = exception.getMessage();

        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.METHOD_NOT_ALLOWED, 99, message);
        Gson gson = new GsonBuilder().serializeNulls().create();
        String jsonResponse = gson.toJson(errorResponse);
        return ResponseEntity.status(httpStatus).contentType(MediaType.parseMediaType("application/json;charset=UTF-8")).body(jsonResponse);
    }


    @ExceptionHandler (value = {AccessDeniedException.class})
    @ResponseBody
    public ResponseEntity<?> accessDeniedException(AccessDeniedException exception) {
        HttpStatus httpStatus = HttpStatus.OK;
        String message = exception.getMessage();

        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.FORBIDDEN, 99, message);
        Gson gson = new GsonBuilder().serializeNulls().create();
        String jsonResponse = gson.toJson(errorResponse);
        return ResponseEntity.status(httpStatus).contentType(MediaType.parseMediaType("application/json;charset=UTF-8")).body(jsonResponse);
    }


    // custom exception start
//
//    @ExceptionHandler(value = JwtTokenMissingException.class)
//    @ResponseBody
//    public ResponseEntity<?> handleException(JwtTokenMissingException exception) {
//        HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
//        String message = exception.getMessage();
//
//        ErrorResponse errorResponse = new ErrorResponse(httpStatus, 99, message);
//        Gson gson = new GsonBuilder().serializeNulls().create();
//        String jsonResponse = gson.toJson(errorResponse);
//        return ResponseEntity.status(httpStatus).contentType(MediaType.parseMediaType("application/json;charset=UTF-8")).body(jsonResponse);
//    }
//
//
//    @ExceptionHandler(value = TokenExpiredException.class)
//    @ResponseBody
//    public ResponseEntity<?> handleException(TokenExpiredException exception) {
//        HttpStatus httpStatus = HttpStatus.FORBIDDEN;
//        String message = exception.getMessage();
//
//        ErrorResponse errorResponse = new ErrorResponse(httpStatus, 99, message);
//        Gson gson = new GsonBuilder().serializeNulls().create();
//        String jsonResponse = gson.toJson(errorResponse);
//        return ResponseEntity.status(httpStatus).contentType(MediaType.parseMediaType("application/json;charset=UTF-8")).body(jsonResponse);
//    }
//
//
//    @ExceptionHandler(value = PasswordMismatchException.class)
//    @ResponseBody
//    public ResponseEntity<?> handleException(PasswordMismatchException exception) {
//        HttpStatus httpStatus = HttpStatus.OK;
//        String message = exception.getMessage();
//
//        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, 99, message);
//        Gson gson = new GsonBuilder().serializeNulls().create();
//        String jsonResponse = gson.toJson(errorResponse);
//        return ResponseEntity.status(httpStatus).contentType(MediaType.parseMediaType("application/json;charset=UTF-8")).body(jsonResponse);
//    }


    @ExceptionHandler(value = ValidationException.class)
    @ResponseBody
    public ResponseEntity<?> handleException(ValidationException exception) {
        HttpStatus httpStatus = HttpStatus.OK;
        String message = exception.getMessage();

        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, 99, message);
        Gson gson = new GsonBuilder().serializeNulls().create();
        String jsonResponse = gson.toJson(errorResponse);
        return ResponseEntity.status(httpStatus).contentType(MediaType.parseMediaType("application/json;charset=UTF-8")).body(jsonResponse);
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponse> handleApiException(ApiException ex) {
        HttpStatus status = HttpStatus.resolve(ex.getStatusCode());
        if (status == null) {
            status = HttpStatus.BAD_REQUEST;
        }

        ErrorResponse errorResponse = new ErrorResponse(status, ex.getStatusCode(), ex.getMessage());

        return ResponseEntity.status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponse);
    }
    @ExceptionHandler(NoDataFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoDataFound(ApiException ex) {
        HttpStatus status = HttpStatus.resolve(ex.getStatusCode());
        if (status == null) {
            status = HttpStatus.BAD_REQUEST;
        }

        ErrorResponse errorResponse = new ErrorResponse(status, ex.getStatusCode(), ex.getMessage());

        return ResponseEntity.status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponse);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<?> handleInvalidCredentials(InvalidCredentialsException ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("success", false);
        error.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }


    /*@ExceptionHandler(value = MessageResponseValidationException.class)
    @ResponseBody
    public ResponseEntity<?> handleException(MessageResponseValidationException exception) {
        HttpStatus httpStatus = HttpStatus.OK;
        ApiError apiError = new ApiError(exception.getField(),exception.getMessage());

        String message = exception.getClass().getSimpleName();
        String path = null;
        List<ApiError> apiErrors = new ArrayList<>();
        apiErrors.add(apiError);

        ErrorResponse errorResponse = new ErrorResponse(httpStatus,message,path,apiErrors);
        String jsonResponse = new Gson().toJson(errorResponse);
        return ResponseEntity.status(httpStatus).contentType(MediaType.parseMediaType("application/json;charset=UTF-8")).body(exception.getMessage());
    }


    @ResponseBody
    @ExceptionHandler(value = EmailNotVerifiedException.class)
    public ResponseEntity<?> handleException(EmailNotVerifiedException exception) {


        HttpStatus httpStatus = HttpStatus.OK;
        String message = exception.getMessage();

        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST,message);

        Gson gson = new GsonBuilder().serializeNulls().create();
        String jsonResponse = gson.toJson(errorResponse);
        return ResponseEntity.status(httpStatus).contentType(MediaType.parseMediaType("application/json;charset=UTF-8")).body(jsonResponse);
    }


    @ResponseBody
    @ExceptionHandler(value = OtpException.class)
    public ResponseEntity<?> handleException(OtpException exception) {

        HttpStatus httpStatus = HttpStatus.OK;
        String message = exception.getMessage();

        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NO_CONTENT,message);
        Gson gson = new GsonBuilder().serializeNulls().create();
        String jsonResponse = gson.toJson(errorResponse);
        return ResponseEntity.status(httpStatus).contentType(MediaType.parseMediaType("application/json;charset=UTF-8")).body(jsonResponse);
    }*/


}
