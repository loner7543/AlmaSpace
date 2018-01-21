package exceptions;

/*
*
* */
public class FileActionException extends Exception {
    private String message;

    public FileActionException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
