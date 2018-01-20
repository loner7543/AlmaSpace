import data.Constants;
import data.Node;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

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
                System.out.println("Выберите тип ввода:");
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
