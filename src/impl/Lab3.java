package impl;

import interf.Processable;

import java.util.*;

public class Lab3 implements Processable {

    private Map<Integer, Set> groups;
    private Map<Integer, String[]> allValues;
    private List<Map<String, Set<String>>> matrixRelationships;

    public Lab3(Lab2 lab2) {
        groups = lab2.getGroups();
        allValues = lab2.getAllValues();
        matrixRelationships = new ArrayList<>();
    }

    @SuppressWarnings("unchecked")
    private void creatingMatrixR(){
        Map<String, Set<String>> tempMap;

        for(Integer numberGroup: groups.keySet()){
             tempMap = new HashMap<>();
            for(Integer numberOfElement: (Set<Integer>)groups.get(numberGroup)){
                for(String value: allValues.get(numberOfElement)){
                    tempMap.put(value, addToMatrix(value, numberGroup));
                }
            }
            matrixRelationships.add(tempMap);
        }
    }

    @SuppressWarnings("unchecked")
    private Set<String> addToMatrix(String value1, Integer groupNum){
        Set<String> tempSet = new HashSet<>();
        String[] tempString;

        for(Integer numberGroup: groups.keySet()){
            for(Integer numberOfElement: (Set<Integer>)groups.get(numberGroup)){
                tempString = allValues.get(numberOfElement);
                for(int i = 0; i < tempString.length; ++i){
                    if(tempString[i].equals(value1) && (i != tempString.length - 1)
                            && groupNum.equals(numberGroup)) {
                        tempSet.add(tempString[i + 1]);
                    }
                }
            }
        }
        return tempSet;
    }

    @Override
    public void process() {

        creatingMatrixR();
        System.out.println("process3");
    }
}
