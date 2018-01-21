package testcompile;
import data.Node;
import exceptions.FileActionException;
import service.CommandService;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Plugin {
    private CommandService commandService;

public Plugin(){}
    public Plugin(CommandService commandService) {
        this.commandService = commandService;
    }

    public void writeToHtml(List<Node> nodes) throws FileActionException {
        String path = System.getProperty("user.dir")+"//save.html";
        File htmFile = new File(path);
        FileWriter ostream = null;
        try {
            ostream = new FileWriter(htmFile);
            BufferedWriter out = new BufferedWriter(ostream);
            out.write("<html>");
            out.write("<h3>Элементы</h3>");
            for (Node node:nodes){
                out.write("<p> Элемент  "+node.getElement()+"  позиия  "+node.getPosition()+"</p>");
                out.write("<br>");
            }
            out.write("</html>");
            out.close();
        } catch (IOException e) {
           throw new FileActionException("Ошибка записи в HTML - файл");
        }
    }

  public void sort(List<Node> nodes) {
        int[] arr = new int[nodes.size()];
        for(int i = 0;i<nodes.size();i++){
            arr[i]=nodes.get(i).getElement();
        }
        for (int i = arr.length - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (arr[j] > arr[j + 1]) {
                    int tmp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = tmp;
                }
            }
        }
        nodes.clear();
        for (int i = 0;i<arr.length;i++){
            nodes.add(new Node(arr[i],i));
        }
    }}
