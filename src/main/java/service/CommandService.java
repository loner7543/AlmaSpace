package service;

import data.Node;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CommandService {
    public void parseCommand(String input){

    }

    public void printList(List<Node> source, char separator){
        if(separator=='\u0000'){
            separator='\t';
        }
        for (Node node:source){
            System.out.println("Элемент со значением "+node.getElement()+" находится на позиции "+node.getPosition());
            System.out.println(separator);
        }
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

    public Node findValue(List<Node> nodeList,int value){
        Predicate<Node> findPred = node -> node.getElement()==value;
        Node node = nodeList.stream().filter(findPred).findFirst().get();
        return node;
    }

    public List<Node> setElement(List<Node> nodeList,int element,int position){
        for (Node node:nodeList){
            if (node.getPosition()==position){
                node.setElement(element);
            }
        }
        return nodeList;
    }

    public Node getElementAtPosition(List<Node> list, int position){
        List<Node> result = list.stream().filter(node -> node.getPosition()==position).collect(Collectors.toList());
        return result.get(0);
    }

    public long getElementCount(List<Node> list){
        return list.stream().count();
    }

    public List<Node> deleteRange(List<Node> nodeList,int startidx,int endidx){
        nodeList.subList(startidx,endidx).clear();
        return deleteElementByIndex(endidx,nodeList);
    }

    public List<Node> deleteElementByIndex(int index,List<Node> nodeList){
        Node removed = null;
        for (Node node:nodeList){
            if(node.getPosition()==index){
                removed=node;
            }
        }
        nodeList.remove(removed);
        return  updatePosition(nodeList);
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

    public void addElement(List<Node> nodeList,int value){//+
        Node node;
        if (nodeList.size()==0){
            node = new Node(value,0);
        }
        else node = new Node(value,nodeList.size());
        nodeList.add(node);
    }
}
