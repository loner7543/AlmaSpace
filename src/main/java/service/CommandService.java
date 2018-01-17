package service;

import data.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CommandService {
    public void parseCommand(String input){

    }

    public void printList(List<Node> source, char separator){
        for (Node node:source){
            System.out.println("Элемент со значением "+node.getElement()+" находится на позиции "+node.getPosition());
            System.out.println(separator);
        }
}

    public List<Node> sort(Boolean param){
        if (param){

        }
        else {

        }
        return null;
    }

    public List<Node> deleteDistinct(List<Node> nodeList){
        return nodeList.stream().distinct().collect(Collectors.toList());
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

    public Node getElement(List<Node> list,int position){
        List<Node> result = list.stream().filter(node -> node.getPosition()==position).collect(Collectors.toList());
        return result.get(0);
    }

    public long getElementCount(List<Node> list){
        return list.stream().count();
    }

    public List<Node> deleteRange(List<Node> nodeList,int startidx,int endidx){
        //nodeList.subList(startidx,endidx).clear();
        return nodeList;
    }

    public List<Node> deleteElementByIndex(int index,List<Node> nodeList){
//        Map<Integer,Optional<Node>> map=nodeList.stream()
//                .collect(Collectors.toMap(element->element., id -> Optional.empty()));
//        items.forEach(item ->
//                map.computeIfPresent(item.getId(), (i,o)->o.isPresent()? o: Optional.of(item)));
//        for(ListIterator<Integer> it=ids.listIterator(ids.size()); it.hasPrevious();) {
//            map.get(it.previous()).ifPresent(item -> {
//                // do stuff
//            });
//        }
        return null;
    }

    public List<Node> updatePosition(List<Node> list){
        List<Node> nodeList = new ArrayList<>(list.size());
        int i=0;
        list.forEach(node ->nodeList.add(new Node(node.getElement(),i)) );
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
