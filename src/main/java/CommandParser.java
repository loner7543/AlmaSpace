import data.Constants;
import data.Node;
import exceptions.ElementNotFoundException;
import exceptions.FileActionException;
import exceptions.InvalidCommandFormatException;
import exceptions.UnknownCommandException;
import service.CommandService;

import java.util.Collections;
import java.util.Objects;

/*
* Поизводит разбор входнойстроки, валидацию и вызор методов обработки
* */
public class CommandParser {
    private CommandService commandService;

    public CommandParser() {
        commandService = new CommandService();
    }

    public String parseCommand(String input){
//        Pattern pattern = Pattern.compile("^\\[a-z\\]{3,6}( \\[0-9;\\]{1})?(\\[0-9\\]{0,1})?([0-9a-z]{0,1})?([0-9]{0,2})?$");
//        Matcher matcher = pattern.matcher(input);
//        if (matcher.find()){
//            System.out.println("Valid command");
//        }
//        else {
//            System.out.println("bad");
//        }
        String returnMessage=null;
        try {
            String s = input;
            String[] commandElements;
            if (input.contains("\\t")||input.contains("\\s")){
                input=input.replace("\\t"," ");
                input = input.replace("\\s"," ");
                commandElements = input.split(" ");
            }
            else {
                commandElements = input.split(" ");
            }
            switch (commandElements[0]){
                case "add":{
                    if (commandElements.length==1){
                        throw new InvalidCommandFormatException("Не задан аргумент команды");
                    }
                    else {
                        if (commandService.addElement(commandService.getAllElements(),Integer.parseInt(commandElements[1]))){
                            returnMessage="Элемент добавлен";
                        }
                        else returnMessage="Не удалось добавить элемент";
                    }
                    break;
                }
                case "list":{
                    //char separator = s.replace("list","");
                    String output = commandService.printList(commandService.getAllElements(),null);
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append(output).append("\n").append("Команда выполнена");
                    returnMessage = stringBuffer.toString();
                    break;
                }
                case "clear":{
                    commandService.getAllElements().clear();
                    returnMessage= "Все элементы удалены";
                    break;
                }
                case "del":{
                    if (commandElements.length>3){
                        throw new InvalidCommandFormatException("Команда имеет недоп. формат");
                    }
                    else if (commandElements.length==2){
                        commandService.deleteElementByIndex(Integer.parseInt(commandElements[1]),commandService.getAllElements());
                        returnMessage="Элемент удален";
                    }
                    else{
                        int startIndex = Integer.parseInt(commandElements[1]);
                        int endIndex = Integer.parseInt(commandElements[2]);
                        commandService.deleteRange(commandService.getAllElements(),startIndex,endIndex);
                        returnMessage="Элементы удалены";
                    }
                    break;
                }
                case "find":{
                    returnMessage=commandService.findValue(commandService.getAllElements(),Integer.parseInt(commandElements[1]));
                    break;
                }
                case "set":{
                    if (commandElements.length<3){
                        throw new InvalidCommandFormatException("Команда имеет недопустимый формат");
                    }
                    else {
                        int index=Integer.parseInt(commandElements[1]);
                        int value = Integer.parseInt(commandElements[2]);
                        returnMessage=commandService.setElement(commandService.getAllElements(),value,index);
                    }
                    break;
                }
                case "get":{
                    int pos = Integer.parseInt(commandElements[1]);
                    Node node = commandService.getElementAtPosition(commandService.getAllElements(),pos);
                    if (node==null){
                        throw new ElementNotFoundException(pos,"Элемент на позиции  "+pos+"  не найден");
                    }
                    break;
                }
                case "sort":{
                    if (commandElements.length==1){
                        commandService.sortAsc(commandService.getAllElements());
                        returnMessage="Набор отсортирован по возрастанию";
                    }
                    else {
                        String order = commandElements[1];
                        if (order.equals("asc")){
                            commandService.sortAsc(commandService.getAllElements());
                            returnMessage="Набор отсортирован по возрастанию";
                        }
                        else if (order.equals("desc")){
                            Collections.reverse(commandService.sortAsc(commandService.getAllElements()));
                            returnMessage="Набор отсортирован по убыванию";
                        }
                        else throw new InvalidCommandFormatException("Неверно указан параметр сортировки");
                    }

                    break;
                }
                case "unique":{
                    commandService.deleteDistinct(commandService.getAllElements());
                    returnMessage="Дубликаты удалены";
                    break;
                }
                case "save":{
                    String path = commandElements[1];
                    String message=commandService.saveElements(path);
                    if (message.equals(null)){
                        throw new FileActionException("Ошибка при сохранении набора");
                    }
                    returnMessage = Constants.SET_SAVED;
                    break;
                }
                case "load":{
                    if (commandElements.length>3){
                        throw new InvalidCommandFormatException("Неверно указаны параетры команды load");
                    }
                    else {
                        String filePath = commandElements[1];
                        String separator=commandElements[2];
                        returnMessage = commandService.loadElements(filePath,separator);
                        if (returnMessage.equals(null)){
                            throw new FileActionException("Ошибка при загрузке файла");
                        }
                        else returnMessage= Constants.SET_SAVED;
                    }
                    break;

                }
                case "count":{
                    returnMessage="Число элементов  "+commandService.getAllElements().size();
                    break;
                }
                default:{
                    throw new UnknownCommandException("Неизвестная команда");
                }
            }
        }
        catch (UnknownCommandException e){
            System.out.println(e.getMessage());
            returnMessage=null;
        } catch (InvalidCommandFormatException e) {
            System.out.println(e.getMessage());
            returnMessage=null;
        } catch (ElementNotFoundException e) {
            System.out.println(e.getMessage());
            returnMessage=null;
        } catch (FileActionException e) {
            System.out.println(e.getMessage());
            returnMessage=null;
        }
        return returnMessage;

    }
}