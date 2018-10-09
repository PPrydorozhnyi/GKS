package impl;

import interf.Processable;

import java.util.Map;
import java.util.Set;

public class Lab3 implements Processable {

    private Map<Integer, Set> groups;
    private Map<Integer, Set<String>> groupSets;

    public Lab3(Lab2 lab2) {
        groups = lab2.getGroups();
        groupSets = lab2.getGroupSets();
    }

    @Override
    public void process() {

        System.out.println("process3");
    }
}
