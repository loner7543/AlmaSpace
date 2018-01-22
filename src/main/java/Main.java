import data.Constants;
import data.Node;
import exceptions.PluginException;

import javax.tools.*;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
// 813 комн
public class Main {

    public static void main(String[] args) throws IOException {
        CommandParser parser = new CommandParser();
        if (args.length!=0){
            BufferedReader reader = new BufferedReader(new FileReader(args[0]));
            String str;
            while ((str = reader.readLine()) != null){
                String res = parser.parseCommand(str,true);
                if (str!=null){
                    System.out.println(res);
                }
                else  System.exit(0);
            }
        }
        else {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            while (true){
                System.out.println("Выберите пункт меню:");
                System.out.println("1- Ручной ввод");
                System.out.println("2- Добавить плагин");
                System.out.println("3- Выход");
                try {
                    int choice = Integer.parseInt(bufferedReader.readLine());// todo catch
                    switch (choice) {
                        case 1: {
                            System.out.println(Constants.WELCOME_MESSAGE);
                            String input = bufferedReader.readLine().trim().toLowerCase();
                            String result = parser.parseCommand(input,false);
                            if (result!=null){
                                System.out.println(result);
                            }
                            break;
                        }
                        case 2: {
                            try {
                                System.out.println("Введите 1 для подключения плагина SORT");
                                System.out.println("Введите 2 для подключения плагина SAVE");
                                choice = Integer.parseInt(bufferedReader.readLine());
                            }
                            catch (NumberFormatException e){
                                System.out.println("Ошибка ввода при выборе пункта меню");
                                return;
                            }

                            PluginManager pluginManager = new PluginManager(parser.getCommandService());
                            Object ret = pluginManager.compile(choice);
                            if (ret!=null){
                                List<Node> nodes = (List<Node>) ret;
                            }
                            else System.out.println("Набор сохранен в HTML файл");
//                            else throw new PluginException("Ошибка при работе с плагинами");
                            break;
                        }
                        case 3: {
                            bufferedReader = null;
                            System.exit(0);
                            break;
                        }
                        default: {
                            System.out.println("Выбран несуществующий пункт меню. Повтоите выбор");
                        }
                    }
                } catch (IOException e) {
                    System.out.println(Constants.INPUT_ERROR);
                }
                catch (NumberFormatException e){
                    System.out.println("Ошибка при выборе пункта меню!");
                }
            }
        }
    }
}
//home/alma/bank/AlmaSpace/1/s2.txt