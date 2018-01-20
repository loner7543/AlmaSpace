package exceptions;

/*
*
* */
public class InvalidCommandFormatException extends Exception {
    private String message;

    public InvalidCommandFormatException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
