import service.CommandService;

import javax.tools.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
* Динамически компилирует код плагина и загружает его в программу посредством кастомного загрузчика классов
* */
public class PluginManager {
    private CommandService service;

    public PluginManager(CommandService commandService){
        this.service = commandService;
    }
    public String compile(int type){
        System.out.println("Подгружаю плагин....");
        String retMessage=null;
        StringBuilder sb = new StringBuilder(64);
        sb.append("package testcompile;\n"+
                "import data.Node;\n" +
                "import exceptions.FileActionException;\n" +
                "import service.CommandService;\n" +
                "\n" +
                "import java.io.BufferedWriter;\n" +
                "import java.io.File;\n" +
                "import java.io.FileWriter;\n" +
                "import java.io.IOException;\n" +
                "import java.util.List;\n" +
                "\n" +
                "public class Plugin {\n" +
                "    private CommandService commandService;\n" +
                "\n" +"public Plugin(){}\n"+
                "    public Plugin(CommandService commandService) {\n" +
                "        this.commandService = commandService;\n" +
                "    }\n" +
                "\n" +
                "    public void writeToHtml(List<Node> nodes) throws FileActionException {\n" +
                "        String path = System.getProperty(\"user.dir\")+\"//save.html\";\n" +
                "        File htmFile = new File(path);\n" +
                "        FileWriter ostream = null;\n" +
                "        try {\n" +
                "            ostream = new FileWriter(htmFile);\n" +
                "            BufferedWriter out = new BufferedWriter(ostream);\n" +
                "            out.write(\"<html>\");\n" +
                "            out.write(\"<h3>Элементы</h3>\");\n" +
                "            for (Node node:nodes){\n" +
                "                out.write(\"<p> Элемент  \"+node.getElement()+\"  позиия  \"+node.getPosition()+\"</p>\");\n" +
                "                out.write(\"<br>\");\n" +
                "            }\n" +
                "            out.write(\"</html>\");\n" +
                "            out.close();\n" +
                "        } catch (IOException e) {\n" +
                "           throw new FileActionException(\"Ошибка записи в HTML - файл\");\n" +
                "        }\n" +
                "    }\n" +
                "\n" +
                "    public List<Node> bubbleSort(List<Node> list){\n" +
                "        /*По очереди будем просматривать все подмножества\n" +
                "      элементов массива (0 - последний, 1-последний,\n" +
                "      2-последний,...)*/\n" +
                "        for (int i = 0; i < list.size(); i++) {\n" +
                "        /*Предполагаем, что первый элемент (в каждом\n" +
                "           подмножестве элементов) является минимальным */\n" +
                "            Node min = commandService.getElementAtPosition(list,i);\n" +
                "            int min_i = i;\n" +
                "        /*В оставшейся части подмножества ищем элемент,\n" +
                "           который меньше предположенного минимума*/\n" +
                "            for (int j = i+1; j < list.size(); j++) {\n" +
                "                //Если находим, запоминаем его индекс\n" +
                "                if (commandService.getElementAtPosition(list,j).getElement() < min.getElement()) {\n" +
                "                    min = commandService.getElementAtPosition(list,j);\n" +
                "                    min_i = j;\n" +
                "                }\n" +
                "            }\n" +
                "        /*Если нашелся элемент, меньший, чем на текущей позиции,\n" +
                "          меняем их местами*/\n" +
                "            if (i != min_i) {\n" +
                "                Node tmp = commandService.getElementAtPosition(list,i);\n" +
                "                commandService.setElement(list,commandService.getElementAtPosition(list,min_i).getElement(),i);\n" +
                "                commandService.setElement(list,tmp.getElement(),min_i);\n" +
                "            }\n" +
                "        }\n" +
                "        return list;\n" +
                "    }\n" +
                "}\n");

        File pluginJavaFile = new File("testcompile/Plugin.java");
        if (pluginJavaFile.getParentFile().exists() || pluginJavaFile.getParentFile().mkdirs()) {

            try {
                Writer writer = null;
                try {
                    writer = new FileWriter(pluginJavaFile);
                    writer.write(sb.toString());
                    writer.flush();
                } finally {
                    try {
                        writer.close();
                    } catch (Exception e) {
                    }
                }

                DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
                JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
                StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null);
                List<String> optionList = new ArrayList<String>();
                optionList.add("-classpath");
                optionList.add(System.getProperty("java.class.path") + ";dist/InlineCompiler.jar");

                Iterable<? extends JavaFileObject> compilationUnit
                        = fileManager.getJavaFileObjectsFromFiles(Arrays.asList(pluginJavaFile));
                JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnostics, optionList, null, compilationUnit);
                if (task.call()) {
                    System.out.println("Плагин подключен");
                    // Create a new custom class loader, pointing to the directory that contains the compiled
                    // classes, this should point to the top of the package structure!
                    URLClassLoader classLoader = new URLClassLoader(new URL[]{new File("./").toURI().toURL()});
                    Class<?> loadedClass = classLoader.loadClass("testcompile.Plugin");
                    Object obj = loadedClass.newInstance();
                    Object res;
                    if (type==1){
                        Method sortMethod = loadedClass.getDeclaredMethod("bubbleSort",List.class);
                        res= sortMethod.invoke(obj,service.getAllElements());
                        retMessage="Набор отсортирован";
                    }
                    else {
                        Method saveMet = loadedClass.getDeclaredMethod("writeToHtml",List.class);
                        res =  saveMet.invoke(obj,service.getAllElements());
                        retMessage="Набор сохранен";
                    }


                } else {
                    for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics()) {
                        System.out.format("Error on line %d in %s%n",
                                diagnostic.getLineNumber(),
                                diagnostic.getSource().toUri());
                    }
                }
                fileManager.close();
            } catch (IOException | ClassNotFoundException | InstantiationException |
                    IllegalAccessException | NoSuchMethodException | InvocationTargetException exp) {
                exp.printStackTrace();
            }
        }
        return retMessage;
    }
}
