package service;

import data.Node;
import org.omg.PortableInterceptor.INACTIVE;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CommandService {
    private List<Node> allElements;

    public CommandService() {
        this.allElements = new ArrayList<>(0);
    }

    public List<Node> getAllElements() {
        return allElements;
    }

    public void setAllElements(List<Node> allElements) {
        this.allElements = allElements;
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
        String s = input;
        String[] commandElements;
        String returnMessage=null;
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
                    returnMessage="Не задан аргумент команды";
                }
                else {
                    if (addElement(allElements,Integer.parseInt(commandElements[1]))){
                        returnMessage="Элемент добавлен";
                    }
                    else returnMessage="Не удалось добавить элемент";
                }
                break;
            }
            case "list":{
                //char separator = s.replace("list","");
                String output = this.printList(allElements,null);
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append(output).append("\n").append("Команда выполнена");
                returnMessage = stringBuffer.toString();
                break;
            }
            case "clear":{
                allElements.clear();
                return "Все элементы удалены";
            }
            case "del":{
                if (commandElements.length>3){
                    returnMessage="Команда имеет недоп. формат";
                }
                else if (commandElements.length==2){
                    this.deleteElementByIndex(Integer.parseInt(commandElements[1]),allElements);
                    returnMessage="Элемент удален";
                }
                else{
                    int startIndex = Integer.parseInt(commandElements[1]);
                    int endIndex = Integer.parseInt(commandElements[2]);
                    deleteRange(allElements,startIndex,endIndex);
                    returnMessage="Элементы удалены";
                }
                break;
            }
            case "find":{
                returnMessage=findValue(allElements,Integer.parseInt(commandElements[1]));
                break;
            }
            case "set":{
                int index=Integer.parseInt(commandElements[1]);
                int value = Integer.parseInt(commandElements[2]);
                if (commandElements.length<3){
                    returnMessage="Команда имеет недоп. формат";
                }
                else returnMessage=setElement(allElements,value,index);
                break;
            }
            case "get":{
                int pos = Integer.parseInt(commandElements[1]);
                Node node = getElementAtPosition(allElements,pos);
                if (node==null){

                }
                break;
            }
            case "sort":{
                break;
            }
            case "unique":{
                break;
            }
            case "save":{
                break;
            }
            case "load":{
                break;
            }
            case "count":{
                break;
            }
            default:{
                returnMessage="Неизвестная команда";
            }
        }
        return returnMessage;

    }

    public String printList(List<Node> source, String separator){
        StringBuilder stringBuilder =  new StringBuilder();
        if(separator==null){
            separator="\t";
        }
        for (Node node:source){
            stringBuilder.append("Элемент со значением "+node.getElement()+" находится на позиции "+node.getPosition())
                    .append(separator);
        }
        return stringBuilder.toString();
}
    /*
    * Сортирует по возрастанию
    * */
    public void sortAsc(List<Node> source){
        int startIndex = 0;
        int endIndex = source.size() - 1;
        doSort(startIndex, endIndex,source);
    }

    private  void doSort(int start, int end,List<Node> source) {
        if (start >= end)
            return;
        int i = start, j = end;
        int cur = i - (i - j) / 2;
        while (i < j) {
            while (i < cur && (this.getElementAtPosition(source,i).getElement() <=
                    this.getElementAtPosition(source,cur).getElement())) {
                i++;
            }
            while (j > cur && (getElementAtPosition(source,cur).getElement() <=
                    getElementAtPosition(source,j).getElement())) {
                j--;
            }
            if (i < j) {
                int temp = getElementAtPosition(source,i).getElement();

                getElementAtPosition(source,i).setElement(getElementAtPosition(source,j).getElement());
                setElement(source,temp,j);
                //array[j] = temp;
                if (i == cur)
                    cur = j;
                else if (j == cur)
                    cur = i;
            }
        }
        doSort(start, cur,source);
        doSort(cur+1, end,source);
    }

    public List<Node> deleteDistinct(List<Node> nodeList){
        List<Node> uniqueElemnts = nodeList.stream().distinct().collect(Collectors.toList());
        return updatePosition(uniqueElemnts);
    }

    public String findValue(List<Node> nodeList,int value){
        String result=null;
        Predicate<Node> findPred = node -> node.getElement()==value;
        Node node = nodeList.stream().filter(findPred).findFirst().get();
        if (node!=null){
            result="Значение  "+node.getElement()+"  найдено в позиции  "+node.getPosition();
        }
        else result ="Значение  "+node.getElement()+"  не найдено";
        return result;
    }

    public String setElement(List<Node> nodeList,int element,int position){
        for (Node node:nodeList){
            if (node.getPosition()==position){
                node.setElement(element);
            }
        }
        return "Значение установлено";
    }

    public Node getElementAtPosition(List<Node> list, int position){
        Node elem;
        try{
            List<Node> result = list.stream().filter(node -> node.getPosition()==position).collect(Collectors.toList());
            elem=result.get(0);
        }
        catch (IndexOutOfBoundsException e){
            elem=null;
        }

        return elem;
    }

    public long getElementCount(List<Node> list){
        return list.stream().count();
    }

    public void deleteRange(List<Node> nodeList,int startidx,int endidx){
        nodeList.subList(startidx,endidx).clear();
        deleteElementByIndex(endidx,nodeList);
    }

    public void deleteElementByIndex(int index,List<Node> nodeList){
        Node removed = null;
        for (Node node:nodeList){
            if(node.getPosition()==index){
                removed=node;
            }
        }
        nodeList.remove(removed);
        updatePosition(nodeList);
    }

    public List<Node> updatePosition(List<Node> list){
        List<Node> nodeList = new ArrayList<>(list.size());
        int i=0;
        for (Node node:list){
            Node elem = new Node(node.getElement(),i);
            nodeList.add(elem);
            i++;
        }
        return nodeList;
    }

    public boolean addElement(List<Node> nodeList,int value){//+
        Node node;
        if (nodeList.size()==0){
            node = new Node(value,0);
        }
        else node = new Node(value,nodeList.size());
        return nodeList.add(node);
    }
}
