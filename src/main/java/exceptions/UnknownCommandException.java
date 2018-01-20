package exceptions;

/*
* Исключение, возникающее при подаче неверной команды
* */
public class UnknownCommandException extends Exception {
    private String message;

    public UnknownCommandException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
