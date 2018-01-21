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
                parser.parseCommand(str);// todo парсит команды
            }
            // Выполнить команды из предложенного файла
            System.exit(0);
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
                            String result = parser.parseCommand(input);
                            if (result!=null){
                                System.out.println(result);
                            }
                            break;
                        }
                        case 2: {
                            System.out.println("Введите 1 для подключения плагина SORT");
                            System.out.println("Введите 2 для подключения плагина SAVE");
                            choice = Integer.parseInt(bufferedReader.readLine());
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
                    System.out.println("Ошибка ввода при выборе пункта меню");
                }
            }
        }
    }
}
//https://javatalks.ru/topics/32121
//https://stokito.wordpress.com/2013/02/14/%D0%B4%D0%B8%D0%
// BD%D0%B0%D0%BC%D0%B8%D1%87%D0%B5%D1%81%D0%BA%D0%B0%D1%8F-%D0%BA%D0%BE%D0%BC%D0%BF%D0%B8%D0%BB%D1%8F%D1%86%D0%B8%D1%8F-java/
//https://stackoverflow.com/questions/21544446/how-do-you-dynamically-compile-and-load-external-java-classes