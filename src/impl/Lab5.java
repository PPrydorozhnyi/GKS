package impl;

import interf.Processable;

import java.util.*;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lab5 implements Processable {

    private List<Map<String, Set<String>>> matrixRelationships;
    private Map<Integer, String[]> allValues;
    private Set<String> uniqueValues;
    private Set<String> allModules;
    private Map<String, Set<String>> relations;
    private Map<String, Set<String>> modulesRelations;
    private String[] orderOfModules;

    public Lab5(Lab3 lab3, Lab1 lab1) {
        matrixRelationships = lab3.getMatrixRelationships();
        allValues = lab3.getAllValues();
        uniqueValues = lab1.getUniqueValues();
        allModules = new HashSet<>();
        relations = new HashMap<>();
        modulesRelations = new HashMap<>();
    }

    private void getAllModules(){

        for(Map<String, Set<String>> map: matrixRelationships) {
            allModules.addAll(map.keySet());
        }
    }

    private void getRidOfDuplicates(){
        String[] modules = allModules.stream().toArray(String[]::new);
        Arrays.sort(modules, Comparator.comparingInt(String::length));

        for(int i = 0; i < modules.length - 1; ++i){
            for(int j = i + 1; j < modules.length; ++j){
                if (modules[j].contains(modules[i])){
                    modules[i] = "";
                }
            }
        }
        allModules.clear();
        Collections.addAll(allModules, modules);
        if(allModules.contains(""))
        allModules.remove("");
    }

    private void createFinalModules(){
        String[] modules = allModules.stream().toArray(String[]::new);
        Arrays.sort(modules, Comparator.comparingInt(String::length));

        for(int i = 0; i < modules.length - 1; ++i){
            for(int j = i + 1; j < modules.length; ++j){
                modules[j] = checkStrings(modules[i], modules[j]);
            }
        }
        allModules.clear();
        Collections.addAll(allModules, modules);
        if(allModules.contains(""))
            allModules.remove("");
    }

    private String checkStrings(String str1, String str2) {
        String[] array1 = parsingStr(str1);
        String[] array2 = parsingStr(str2);

        for (int i = 0; i < array1.length; ++i){
            for(int j = 0; j < array2.length; ++j){
                if(array1[i].equals(array2[j])){
                    array2[j] = "";
                }
            }
        }
        return String.join("", array2);
    }

    private String[] parsingStr(String str){

        String[] matches = Pattern.compile("[A-Za-z]{1}[0-9]{1}")
                .matcher(str)
                .results()
                .map(MatchResult::group)
                .toArray(String[]::new);

        return matches;
    }

    private void createRelation(){
        String[] temp;
        for (String key: uniqueValues){
            relations.put(key, new HashSet<>());
        }

        for (Integer numberOfRow: allValues.keySet()){
            temp = allValues.get(numberOfRow);
            for(int i = 0; i < temp.length - 1; ++i){
                relations.get(temp[i]).add(temp[i + 1]);
            }
        }
    }

    private void moveRelations(){
        Set<String> temp;
        for (String key: allModules){
            modulesRelations.put(key, new HashSet<>());
            for (String element: parsingStr(key)){
                for (String value: relations.get(element))
                    if(!key.contains(value)) {
                        modulesRelations.get(key).add(value);
                    }
            }
        }
        for (String key: modulesRelations.keySet()){
            for (String module: modulesRelations.keySet()) {
                temp = new HashSet<>();
                for (String element : modulesRelations.get(module)) {
                    if (key.contains(element) && !module.equals(key)) {
                        temp.add(element);
                    }
                }
                if(!temp.isEmpty()) {
                    removeRelation(module, temp);
                    modulesRelations.get(module).add(key);

                }
            }
        }
    }

    private void removeRelation(String key, Set<String> set){
        for (String itr: set){
            modulesRelations.get(key).remove(itr);
        }
    }

    private void sortModules(){
        orderOfModules = modulesRelations.keySet().stream().toArray(String[]::new);
        permutation(orderOfModules);
    }

    private void permutation(String[] array){
        String temp;
        String firstElement = array[0];
        String lastEleemnt = array[array.length - 1];
        List<Integer> counts = new ArrayList<>();
        int index;

        for (int i = 0; ; ++i){
            counts.add(amountOfBackwardRelations());
            temp = array[i];
            array[i] = array[i + 1];
            array[i + 1] = temp;
            if(array[0].equals(firstElement) && array[array.length - 1].equals(lastEleemnt)){
                break;
            }
            if(i == array.length - 2){
                i = -1;
            }
        }
        index = counts.indexOf(Collections.min(counts));

        for(int i = 0, j = 0; j < index; ++i, ++j){
            temp = array[i];
            array[i] = array[i + 1];
            array[i + 1] = temp;
            if(i == array.length - 2){
                i = -1;
            }
        }
    }

    private int amountOfBackwardRelations(){
        Set<String> usedKeys = new HashSet<>();
        int count = 0;

        for(int i = 0; i < orderOfModules.length; ++i){
            for(String relation: modulesRelations.get(orderOfModules[i])){
                if(usedKeys.contains(relation)){
                    ++count;
                }
            }
            usedKeys.add(orderOfModules[i]);
        }
        return count;
    }


    @Override
    public void process() {
        getAllModules();
        getRidOfDuplicates();
        createFinalModules();
        createRelation();
        moveRelations();
        sortModules();

        System.out.println("lab5");
    }
}
