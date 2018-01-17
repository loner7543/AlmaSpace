package service;

import data.Node;

import java.util.List;
import java.util.stream.Collectors;

public class CommandService {
    public void ParseCommand(String input){

    }

    public void printList(List<Node> source){
        for (Node node:source){
            System.out.println();
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

    public void updatePosition(List<Node> list){

    }
}
