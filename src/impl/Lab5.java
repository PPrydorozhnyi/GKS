package impl;

import interf.Processable;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class Lab5 implements Processable {

    private List<Map<String, Set<String>>> matrixRelationships;
    private Map<Integer, String[]> allValues;

    public Lab5(Lab3 lab3) {
        matrixRelationships = lab3.getMatrixRelationships();
        allValues = lab3.getAllValues();
    }

    @Override
    public void process() {
        System.out.println("lab5");
    }
}
