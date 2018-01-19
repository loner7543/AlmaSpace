package data;

/*
* Описывает элемент списка. Элемент списка включает значение и позицию элемента
* */
public class Node {
    private Integer element;
    private Integer position;

    public Node(int element, int position) {
        this.element = element;
        this.position = position;
    }

    public int getElement() {
        return element;
    }

    public void setElement(int element) {
        this.element = element;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Элемент  ").append(this.element).append("\n").append("Позиция  ").append(position);
        return stringBuilder.toString();
    }

        @Override
    public boolean equals(Object obj) {// for distinct
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Node node = (Node) obj;
        return (element != null && element.equals(node.element));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (element == null ? 0 : element);
        return result;
    }
}


