package impl;

import interf.Processable;

import java.util.*;

public class Lab3 implements Processable {

    private Map<Integer, Set> groups;
    private Map<Integer, String[]> allValues;
    private List<Map<String, Set<String>>> matrixRelationships;
    private Set<String> cycle;
    private static final int maxElementsInModule = 5;

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

    /**
     * @param value first element to check for straight relation
     * @param groupNumber number of group we work with
     */
    private boolean checkReversibleRelation(String value, Integer groupNumber){
        Map<String, Set<String>> temp = matrixRelationships.get(groupNumber);
        Set<String> tempSet = temp.get(value);
        cycle = new HashSet<>();

        for(String element : tempSet){
            if(temp.get(element).contains(value)){
                cycle.add(element);
                cycle.add(value);
                return true;
            }
        }
        return false;
    }


    /**
     * @param value checked element (key)
     * @param groupNumber number of group
     * @return checked element has to have one output and one input relation
     */
    private boolean checkOneOneRelation(String value, Integer groupNumber){
        Map<String, Set<String>> temp = matrixRelationships.get(groupNumber);
        int count = 0;

        for(String key: temp.keySet()){
            if(temp.get(key).contains(value)){
                ++count;
            }
        }

        return temp.get(value) != null && temp.get(value).size() == 1 && count == 1;
    }

    private boolean checkOneWayRelation(String value, Integer groupNumber){
        Map<String, Set<String>> temp = matrixRelationships.get(groupNumber);
        String tempValue = "";
        String[] stringTemp = Arrays.copyOf(temp.get(value).toArray(), temp.get(value).size(), String[].class);
        cycle = new HashSet<>();

        for(int i = 0; i < stringTemp.length; ++i){

            if(checkOneOneRelation(stringTemp[i], groupNumber)){
                cycle.add(stringTemp[i]);
                tempValue = stringTemp[i];
                stringTemp = Arrays.copyOf(temp.get(stringTemp[i]).toArray(), temp.get(value).size(), String[].class);
                i = -1;
                if(cycle.size() >= maxElementsInModule){
                    return false;
                }
            }
            else if(temp.get(tempValue) != null){
                if(temp.get(value).contains(stringTemp[i]) && temp.get(tempValue).contains(stringTemp[i])){
                    cycle.add(stringTemp[i]);
                    cycle.add(value);
                    return cycle.size() < maxElementsInModule;
                }
            }
        }
        return false;
    }

    private boolean checkNRelation(String value, Integer groupNumber){
        Map<String, Set<String>> temp = matrixRelationships.get(groupNumber);
        cycle = new HashSet<>();
        for(int i = 3; i <= maxElementsInModule; ++i){
            if(checkNRHelp(value, i, groupNumber, value, 0)){
                return true;
            }
        }
        return false;
    }

    private boolean checkNRHelp(String elementOfRelation, int n, Integer groupNumber, String headElement, int counter){
        Map<String, Set<String>> relations = matrixRelationships.get(groupNumber);
        Set<String> relationsOfElement  = relations.get(elementOfRelation);

        ++counter;
        if(counter == n){
            for(String currentValue: relationsOfElement){
                if(currentValue.equals(headElement)){
                    cycle.add(elementOfRelation);
                    return true;
                }
            }
        }
        else{
            for(String currentValue: relationsOfElement){
                if(checkNRHelp(currentValue, n, groupNumber, headElement, counter)) {
                    cycle.add(elementOfRelation);
                    return true;
                }
            }
        }
        return false;
    }

    private boolean modulesCreator(){

        for(int numberOfGroup = 0; numberOfGroup < matrixRelationships.size(); ++numberOfGroup){
            for(String currentElement: matrixRelationships.get(numberOfGroup).keySet()){
                if(checkReversibleRelation(currentElement, numberOfGroup)
                        && moveElements(numberOfGroup)){
                    return true;
                }
                if(checkNRelation(currentElement, numberOfGroup)
                      && moveElements(numberOfGroup)){
                    return true;
                }
                if(checkOneWayRelation(currentElement, numberOfGroup)
                            && moveElements(numberOfGroup)){
                    return true;
                }
            }
        }
        return false;
    }

    private boolean moveElements(Integer numberOfGroup){
        Map<String, Set<String>> temp = matrixRelationships.get(numberOfGroup);
        Set<String> relation = new HashSet<>();
        String newElement = String.join("", cycle);
        if(newElement.length() > maxElementsInModule * 2){
            return false;
        }

        for(String currentElement: temp.keySet()){
            if(cycle.contains(currentElement)){
                relation.addAll(temp.get(currentElement));
            }
        }
        for(String element: cycle){
            relation.remove(element);
        }

        for(String removedElement: cycle){
            temp.remove(removedElement);
        }

        for(String currentElement: temp.keySet()){
            for(String rel: cycle){
                if(temp.get(currentElement).contains(rel)){
                    temp.get(currentElement).add(newElement);
                    temp.get(currentElement).remove(rel);
                }
            }
        }

        temp.put(newElement, relation);
        return true;
    }

    public List<Map<String, Set<String>>> getMatrixRelationships() {
        return matrixRelationships;
    }

    Map<Integer, String[]> getAllValues() {
        return allValues;
    }

    @Override
    public void process() {
        creatingMatrixR();
        while(modulesCreator());
    }
}
