package service;

import data.Node;
import org.junit.*;
import org.junit.experimental.theories.Theory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class CommandServiceTest {

    private List<Node> nodes;
    private CommandService commandService;

    @Before
    public void beforeTest(){
        commandService = new CommandService();
        nodes = new ArrayList<>();
        nodes.add(new Node(0,0));
        nodes.add(new Node(1,1));
        nodes.add(new Node(2,2));
        nodes.add(new Node(3,3));
        nodes.add(new Node(3,4));
    }

    @After
    public void afterTest(){
        commandService=null;
        nodes=null;
    }

    @Test
    public void testAddElement(){
        boolean act = commandService.addElement(nodes,10);
        assertEquals(true,act);
    }

    @Test
    public void testGetElementCount() {
        assertEquals(5,commandService.getElementCount(nodes));
    }

    @Test
    public void testGetElementAtPosition(){
        assertEquals(1,commandService.getElementAtPosition(nodes,1).getElement());
    }

    @Test
    public void testGetLastElementValue(){
        final int lastElem = commandService.getLastElement(nodes).getElement();
        assertEquals(3,lastElem);
    }

    @Test
    public void testSetElement(){
       String res =  commandService.setElement(nodes,10,1);
        assertEquals("Значение установлено",res);
    }


    @BeforeClass
    public static void beforeClass(){
        System.out.println("Testing started...");
    }

    @AfterClass
    public static void afterClass(){
        System.out.println("Testing completed!");
    }

}