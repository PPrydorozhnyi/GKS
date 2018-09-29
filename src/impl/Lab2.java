package impl;

import interf.Processable;

import java.util.Map;
import java.util.Set;

public class Lab2 implements Processable {

    private Map<Integer, String[]> allValues;
    private Map<Integer, Set> groups;

    public Lab2(Lab1 lab1) {
        allValues = lab1.getAllValues();
        groups = lab1.getGroups();
    }

    @Override
    public void process() {
        System.out.println("process");
    }
}
