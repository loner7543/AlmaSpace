import data.Constants;
import data.Node;
import service.CommandService;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class Main {

    public static void main(String[] args) throws IOException {
        ArrayList<Node> nodes;
        CommandService commandService;
        if (args.length!=0){
            commandService = new CommandService();
            BufferedReader reader = new BufferedReader(new FileReader(args[0]));
            String str;
            while ((str = reader.readLine()) != null){
                commandService.parseCommand(str);// todo парсит команды
            }
            // Выполнить команды из предложенного файла
            System.exit(0);
        }
        else {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            nodes = new ArrayList<Node>(0);
            commandService= new CommandService();
            while (true){
                System.out.println("Выберите тип ввода:");
                System.out.println("1- Из файла");
                System.out.println("2- Ручной ввод");
                System.out.println("3- Добавить плагин");
                System.out.println("4- Сохранить набор в файл");
                System.out.println("5- Выход");
                try {
                    int choice = Integer.parseInt(bufferedReader.readLine());
                    switch (choice) {
                        case 1: {
                            System.out.println(Constants.ENTER_FILENAME);
                            String path = bufferedReader.readLine();
                            BufferedReader reader = new BufferedReader(new FileReader(path));
                            String str;
                            while ((str = reader.readLine()) != null){
                                String[] data = str.split(" ");
                                Node node = new Node(Integer.parseInt(data[0]),Integer.parseInt(data[1]));
                                nodes.add(node);
                            }
                            reader.close();
                            System.out.println(Constants.SET_LOADED);
                            //commandService.printList(nodes,'\n');
                            break;
                        }
                        case 2: {
                            System.out.println(Constants.WELCOME_MESSAGE);
                            String input = bufferedReader.readLine().trim().toLowerCase();
                            String result = commandService.parseCommand(input);
                            System.out.println(result);
                           // commandService.printList(commandService.getAllElements(),null);
                            break;
                        }
                        case 3: {
                            break;
                        }
                        case 4: {
                            System.out.println(Constants.ENTER_FILENAME);
                            String path = bufferedReader.readLine();
                            path="D:\\Sbt\\src\\main\\resources\\1.txt";// todo fix!
                            PrintWriter  file = new  PrintWriter(path,"UTF-8");
                            for (Node node:nodes){
                                file.write(node.getElement()+" "+node.getPosition()+"\n");
                            }
                            file.close();
                            System.out.println(Constants.SET_SAVED);// todo перенести в сервис
                            break;
                        }
                        case 5:{
                            bufferedReader = null;
                            System.exit(0);
                            break;
                        }
                        case 6:{
                            commandService.sortAsc(nodes);
                            Collections.reverse(nodes);
                            commandService.printList(nodes,null);

                        }
                        default: {

                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println(Constants.INPUT_ERROR);
                }
            }
        }
    }
}
