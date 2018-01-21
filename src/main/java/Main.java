import data.Constants;
import data.Node;

import javax.tools.*;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

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
                            System.out.printf("Подгружаю плагин....");
                            System.out.printf(System.getProperty("user.dir"));
                            String fullName = System.getProperty("user.dir")+"//src//main//resources//Plugin";//"d:/NetBeansProjects/Test/DynaCompTest2/outer/MyClass";

                            // We get an instance of JavaCompiler. Then
                            // we create a file manager
                            // (our custom implementation of it)
                            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

                            DiagnosticCollector<JavaFileObject> diagnostics
                                    = new DiagnosticCollector<JavaFileObject>();
                            StandardJavaFileManager fileManager
                                    = compiler.getStandardFileManager(diagnostics, null, null);

                            List<File> fileList = new ArrayList<File>();
                            fileList.add(new File(fullName + ".java"));
                            Iterable<? extends JavaFileObject> compilationUnits = fileManager
                                    .getJavaFileObjectsFromFiles(fileList);

                            // We specify a task to the compiler. Compiler should use our file
                            // manager and our list of "files".
                            // Then we run the compilation with call()
                            JavaCompiler.CompilationTask task = compiler.getTask(
                                    null, fileManager, null, null, null, compilationUnits);
                            boolean result = task.call();
                            if (result) {
                                System.out.println("Compilation was successful");
                            } else {
                                System.out.println("Compilation failed");
                            }

                            URL url = new URL("file:///d:/NetBeansProjects/Test/DynaCompTest2/"
                                    + "outer/");
                            System.out.println("url = " + url);
                            URL[] classUrls = { url };
                            URLClassLoader ucl = new URLClassLoader(classUrls);
                            Class c = ucl.loadClass("Plugin");

                            Method staticMethod = c.getDeclaredMethod("writeToHtml");
                            staticMethod.invoke(null);

                            Object o = c.newInstance();
                            Method instanceMethod = c.getDeclaredMethod("myInstanceMethod");
                            instanceMethod.invoke(o);
                            System.out.printf("Введите 1 для записи элементов в HTML-файл");
                            System.out.printf("Введите 2 для применения другого алгоритма сортировки");
                            choice=Integer.parseInt(bufferedReader.readLine());
                            switch (choice){
                                case 1:{
                                    break;
                                }
                                case 2:{
                                    break;
                                }
                            }
//                            System.out.printf(System.getProperty("user.dir"));
//                            Plugin plugin = new Plugin();
//                            plugin.writeToHtml(parser.getCommandService().getAllElements());
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
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
//https://javatalks.ru/topics/32121
//https://stokito.wordpress.com/2013/02/14/%D0%B4%D0%B8%D0%
// BD%D0%B0%D0%BC%D0%B8%D1%87%D0%B5%D1%81%D0%BA%D0%B0%D1%8F-%D0%BA%D0%BE%D0%BC%D0%BF%D0%B8%D0%BB%D1%8F%D1%86%D0%B8%D1%8F-java/
//https://stackoverflow.com/questions/21544446/how-do-you-dynamically-compile-and-load-external-java-classes
