package service;

import data.Constants;
import data.Node;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public String printList(List<Node> source, String separator){
        StringBuilder stringBuilder =  new StringBuilder();
        Node lastElem = getLastElement(source);
        if(separator==null){
            separator="\t";
        }
        for (Node node:source){
            if (node.getPosition()==lastElem.getPosition()){
                stringBuilder.append("Элемент со значением "+node.getElement()+" находится на позиции "+node.getPosition());
            }
            else {
                stringBuilder.append("Элемент со значением "+node.getElement()+" находится на позиции "+node.getPosition())
                        .append(separator);
            }
        }
        return stringBuilder.toString();
    }
    /*
    * Сортирует по возрастанию
    * */
    public List<Node> sortAsc(List<Node> source){
        int startIndex = 0;
        int endIndex = source.size() - 1;
        doSort(startIndex, endIndex,source);
        return source;
    }

    /*
    * Быстрая сортировка набора элементов
    * */
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
                if (i == cur)
                    cur = j;
                else if (j == cur)
                    cur = i;
            }
        }
        doSort(start, cur,source);// выполнить для левого набора
        doSort(cur+1, end,source);//для правого
    }

    /*
    * Удаляет дубликаты из набора элементов
    */
    public List<Node> deleteDistinct(List<Node> nodeList){
        List<Node> uniqueElemnts = nodeList.stream().distinct().collect(Collectors.toList());
        return updatePosition(uniqueElemnts);
    }

    /*
    * Ищет элемент по индексу
    * */
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

    /*
    * Устанавливает значение в указанной позиции
    * */
    public String setElement(List<Node> nodeList,int element,int position){
        for (Node node:nodeList){
            if (node.getPosition()==position){
                node.setElement(element);
            }
        }
        return "Значение установлено";
    }

    /*
    * Читает значение в указанной позиции
    * */
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

    /*
    * Удаляет элементы в заданном диапазоне
    * */
    public void deleteRange(List<Node> nodeList,int startidx,int endidx){
        nodeList.subList(startidx,endidx).clear();
        deleteElementByIndex(endidx,nodeList);
    }

    /*
    * Удаляет элемент по индексу
    * */
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

    /*
    * Обновляет позиции
    * */
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

    /*
    * Добавляет элемент в конец набора
    * */
    public boolean addElement(List<Node> nodeList,int value){//+
        Node node;
        if (nodeList.size()==0){
            node = new Node(value,0);
        }
        else node = new Node(value,nodeList.size());
        return nodeList.add(node);
    }

    /*
    * Сохранение элементов в файл
    * */
    public String saveElements(String path,String sep){
        String res="";
        Node lastElem = getLastElement(allElements);
        try {
            PrintWriter file = null;
            try {
                file = new PrintWriter(path,"UTF-8");
            } catch (FileNotFoundException | UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            for (Node node:allElements){
                if (node.getPosition()==lastElem.getPosition()){
                    file.write(node.getElement()+" "+node.getPosition());// если эл-т последний
                }
                else {
                    file.write(node.getElement()+" "+node.getPosition());
                    //file.write(sep);
                }
            }
            file.close();
            System.out.println(Constants.SET_SAVED);
        }
        catch (NullPointerException e){
            res=null;
        }
        return res;
    }

    /**
     * Загрузка элементов из файла
     * */
    public String loadElements(String path,String separator){
        String res ="";
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(path));
        } catch (FileNotFoundException e) {
            res=null;
        }
        String str;
        allElements.clear();
        try {
            while ((str = reader.readLine()) != null){
                String[] data = str.split(" ");
                Node node = new Node(Integer.parseInt(data[0]),Integer.parseInt(data[1]));
                allElements.add(node);
            }
        } catch (IOException e) {
            res=null;
        }
        try {
            reader.close();
            res=Constants.SET_SAVED;

        } catch (IOException e) {
            res=null;
        }
        return res;
    }

    public Node getLastElement(List<Node> nodeList){// todo. если элемент 1 в списке - верни его
        if (nodeList.size()==1){
            return nodeList.get(0);
        }
        else {
            long count = nodeList.stream().count();
            Stream<Node> stream = nodeList.stream();
            Node lastElem = stream.skip(count - 1).findFirst().get();
            return lastElem;
        }
    }
}