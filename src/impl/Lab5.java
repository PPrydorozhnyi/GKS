package impl;

import Entities.Relation;
import interf.Processable;

import java.util.*;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

public class Lab5 implements Processable {

    private List<Map<String, Set<String>>> matrixRelationships;
    private Map<Integer, String[]> allValues;
    private Set<String> uniqueValues;
    private Set<String> allModules;
    private Map<String, Set<String>> relations;
    private Map<String, Set<String>> modulesRelations;
    private String[] orderOfModules;
    private Map<String, Relation<Integer>> amountOfRelations;

    public Lab5(Lab3 lab3, Lab1 lab1) {
        matrixRelationships = lab3.getMatrixRelationships();
        allValues = lab3.getAllValues();
        uniqueValues = lab1.getUniqueValues();
        allModules = new HashSet<>();
        relations = new HashMap<>();
        modulesRelations = new HashMap<>();
        amountOfRelations = new HashMap<>();
    }

    private void getAllModules(){

        for(Map<String, Set<String>> map: matrixRelationships) {
            allModules.addAll(map.keySet());
        }
    }

    private void getRidOfDuplicates(){
        String[] modules = allModules.toArray(new String[0]);
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
        allModules.remove("");
    }

    private void createFinalModules(){
        String[] modules = allModules.toArray(new String[0]);
        Arrays.sort(modules, Comparator.comparingInt(String::length));

        for(int i = 0; i < modules.length - 1; ++i){
            for(int j = i + 1; j < modules.length; ++j){
                modules[j] = checkStrings(modules[i], modules[j]);
            }
        }
        allModules.clear();
        Collections.addAll(allModules, modules);
        allModules.remove("");
    }

    private String checkStrings(String str1, String str2) {
        String[] array1 = parsingStr(str1);
        String[] array2 = parsingStr(str2);

        for (String anArray1 : array1) {
            for (int j = 0; j < array2.length; ++j) {
                if (anArray1.equals(array2[j])) {
                    array2[j] = "";
                }
            }
        }
        return String.join("", array2);
    }

    private String[] parsingStr(String str){

        return Pattern.compile("[A-Za-z]{1}[0-9]{1}")
                .matcher(str)
                .results()
                .map(MatchResult::group)
                .toArray(String[]::new);
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
        orderOfModules = modulesRelations.keySet().toArray(new String[0]);
        permutation(orderOfModules);
    }

    private void permutation(String[] array){
        String temp;
        String firstElement = array[0];
        String lastElement = array[array.length - 1];
        List<Integer> counts = new ArrayList<>();
        Map<Integer, String[]> minOptions = new HashMap<>();
        int min;

//        for (int i = 0; ; ++i){
//            counts.add(amountOfBackwardRelations());
//            temp = array[i];
//            array[i] = array[i + 1];
//            array[i + 1] = temp;
//            if(array[0].equals(firstElement) && array[array.length - 1].equals(lastElement)){
//                break;
//            }
//            if(i == array.length - 2){
//                i = -1;
//            }
//        }
        permute(0, array, counts);

        min = Collections.min(counts);

//        for (int i = 0, j = 0; ; ++i){
//            if(min == amountOfBackwardRelations()){
//                minOptions.put(j, Arrays.copyOf(array, array.length));
//                ++j;
//            }
//            temp = array[i];
//            array[i] = array[i + 1];
//            array[i + 1] = temp;
//            if(array[0].equals(firstElement) && array[array.length - 1].equals(lastElement)){
//                break;
//            }
//            if(i == array.length - 2){
//                i = -1;
//            }
//        }

        permute2(0, array, counts, min, minOptions, 0);

        outputInputRelations();
        optimalOrder(minOptions);
    }

    private void permute(int k, String[] arr, List<Integer> counts){

        for(int i = k; i < arr.length; i++){
            swapElements(arr, i, k);
            permute(k+1, arr, counts);
            swapElements(arr, k, i);
        }
        if (k == arr.length -1){
            counts.add(amountOfBackwardRelations());
        }
    }

    private int permute2(int k, String[] arr, List<Integer> counts, int min, Map minOptions, int j){

        for(int i = k; i < arr.length; i++){
            swapElements(arr, i, k);
            j = permute2(k+1, arr, counts, min, minOptions, j);
            swapElements(arr, k, i);
        }
        if (k == arr.length -1 && min == amountOfBackwardRelations()){
            minOptions.put(j, Arrays.copyOf(arr, arr.length));
            ++j;
        }
        return j;
    }


    private void swapElements(String[] array, int first, int second){
        String temp = array[first];
        array[first] = array[second];
        array[second] = temp;
    }

    private int amountOfBackwardRelations(){
        Set<String> usedKeys = new HashSet<>();
        int count = 0;

        for (String orderOfModule : orderOfModules) {
            for (String relation : modulesRelations.get(orderOfModule)) {
                if (usedKeys.contains(relation)) {
                    ++count;
                }
            }
            usedKeys.add(orderOfModule);
        }
        return count;
    }

    private void outputInputRelations() {

        for (int i = 0; i < orderOfModules.length; ++i) {
            amountOfRelations.put(orderOfModules[i], new Relation<>(0, 0));
        }

        for (Integer value : allValues.keySet()) {
            for (int i = 0; i < orderOfModules.length; ++i){
                if (orderOfModules[i].contains(allValues.get(value)[0])) {
                    amountOfRelations.get(orderOfModules[i])
                            .setAmountOfInputRelations(amountOfRelations
                                    .get(orderOfModules[i])
                                    .getAmountOfInputRelations() + 1);
                }
                if (orderOfModules[i].contains(allValues.get(value)[allValues.get(value).length - 1])) {
                amountOfRelations.get(orderOfModules[i])
                        .setAmountOfOutputRelations(amountOfRelations
                                .get(orderOfModules[i])
                                .getAmountOfOutputRelations() + 1);
                }
            }
        }
    }

    private void optimalOrder(Map<Integer, String[]> map){
        Relation<Integer> rel;
        int input;
        int output = 0;

        Set<String> firstElements = new HashSet<>();

        for (Integer i: map.keySet()){
            firstElements.add(map.get(i)[0]);
        }
        input = findMaxInput(firstElements);

        for (Integer i: map.keySet()){
            rel = amountOfRelations.get(map.get(i)[0]);
            if (input == rel.getAmountOfInputRelations()){
                rel = amountOfRelations.get(map.get(i)[orderOfModules.length - 1]);
                if(output < rel.getAmountOfOutputRelations()){
                    output = rel.getAmountOfOutputRelations();
                    orderOfModules = map.get(i);
                }
            }
        }


    }

    private int findMaxInput(Set<String> set){
        int in = 0;
        Relation<Integer> rel;

        for (String value: set){
            rel = amountOfRelations.get(value);
            if(in < rel.getAmountOfInputRelations()){
                in = rel.getAmountOfInputRelations();
            }

        }
        return in;
    }

    public Map<String, Set<String>> getModulesRelations() {
        return modulesRelations;
    }

    public String[] getOrderOfModules() {
        return orderOfModules;
    }

    @Override
    public void process() {
        getAllModules();
        getRidOfDuplicates();
        createFinalModules();
        createRelation();
        moveRelations();
        sortModules();
    }
}
