package walletSystem.userService.exception.exception;

public class NoDataFoundException extends RuntimeException {
  private final int statusCode;

  public NoDataFoundException(String message, int statusCode) {
    super(message);
    this.statusCode = statusCode;
  }

  public int getStatusCode() {
    return statusCode;
  }
}
