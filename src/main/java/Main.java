import data.Constants;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Выберите тип ввода:");
        System.out.println("1- Из файла");
        System.out.println("2- Ручной ввод");
        System.out.println("3- Добавить плагин");
        System.out.println("4- Выход");
        try {
            int choice = Integer.parseInt(bufferedReader.readLine());
            switch (choice) {
                case 1: {
                    System.out.println(Constants.ENTER_FILENAME);
                    String path = bufferedReader.readLine();
                    File file = new File(path);
                    break;
                }
                case 2: {
                    System.out.println(Constants.WELCOME_MESSAGE);
                    String input = bufferedReader.readLine().trim().toLowerCase();
                    String[] f = input.split(" ");
                    int a = 4;
                    break;
                }
                case 3: {
                    break;
                }
                case 4: {
                    bufferedReader = null;
                    System.exit(0);
                    break;
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
