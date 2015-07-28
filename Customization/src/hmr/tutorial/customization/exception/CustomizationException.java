package hmr.tutorial.customization.exception;


public class CustomizationException extends RuntimeException {
    public CustomizationException(String message) {
        super(message);
    }

    public CustomizationException(String message, Throwable cause) {
        super(message, cause);
    }
}
