package exceptions;

import data.Node;

/*
* Возникает когда элемент не найден в наборе на переданной позиции
* */
public class ElementNotFoundException extends Exception {
    private int value;
    private String message;

    public ElementNotFoundException(int value, String message) {
        super(message);
        this.value = value;
        this.message = message;
    }

    public int getNode() {
        return value;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
