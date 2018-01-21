package exceptions;

public class PluginException extends Exception {
    private String message;

    public PluginException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
