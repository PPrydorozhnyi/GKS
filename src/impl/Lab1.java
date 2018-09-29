package impl;

import interf.Processable;
import main.Start;

import java.util.*;

public class Lab1 implements Processable {

    private Start main;
    private Map<Integer, String[]> allValues;
    private Map<Integer, Integer[]> newMatrixValues;
    private Map<Integer, Integer[]> outPutMatrix;
    private Set uniqueVal;
    private Map<Integer, Set> groups;
    private Set<Integer> usedRowNumbers;

    public Lab1(Start main) {
        this.main = main;
        allValues = new HashMap<>();
        uniqueVal = new HashSet<String>();
    }

    @Override
    public void process() {
        System.out.println("Processing lab1");
        parseRows();
        printMap(allValues);
        uniqueValues();
        goTable(uniqueVal.size());
        createGroups();
        System.out.println(1);
    }

    private void parseRows() {

        String[] strings = main.getInputStrings();
        String[] values;

        for (int i = 0; i < strings.length; ++i) {
            // split by whitespace
            values = strings[i].split("\\s+");
            allValues.put(i, values);
        }
    }

    private <K, V>void printMap(Map<K, V> map) {
        for (K name: map.keySet()){

            String key = String.valueOf(name);
            String value = String.valueOf(map.get(name));
            System.out.println(key + " " + value);

        }
    }

    private void goTable(int amountUniqueValues){

        String[] values;
        String[] values2;
        int countOfCollision;
        int allStringValues;
        Integer[] newMatrixValue = new Integer [main.getRankOfTheMatrix()];
        newMatrixValues = new HashMap<>();
        outPutMatrix = new HashMap<>();


        for (int i = 0; i < main.getRankOfTheMatrix() - 1; ++i) {
            for (int j = i + 1; j < main.getRankOfTheMatrix(); ++j) {
                values = allValues.get(i);
                values2 = allValues.get(j);

                countOfCollision = checkCollisions(values, values2);

                allStringValues = values.length + values2.length;
                newMatrixValue[j] = amountUniqueValues - (allStringValues - (2 * countOfCollision));
            }
                newMatrixValues.put(i, newMatrixValue);
                outPutMatrix.put(i, newMatrixValue.clone());
                newMatrixValue = new Integer [main.getRankOfTheMatrix()];


        }


    }

    private int checkCollisions(String[] values, String[] values2){

        int countOfCollision = 0;
        for (String value : values)
            for (String aValues2 : values2)
                if (value.equals(aValues2))
                    ++countOfCollision;
            return countOfCollision;
    }

    @SuppressWarnings("unchecked")
    private void uniqueValues() {
        for (Integer value : allValues.keySet()){
            uniqueVal.addAll(Arrays.asList(allValues.get(value)));
        }
    }

    private int findMaxValue(int maxValue) {
        int max = 0;

        for (Integer value : newMatrixValues.keySet()){
            for (Integer val : newMatrixValues.get(value)) {
                if (val > max && val <= maxValue) {
                    max = val;
                }
            }
        }

        return max;
    }

    private void getRidOfNull(Map<Integer, Integer[]> map) {
        Integer[] array;

        for (Integer value : map.keySet()) {
            array = map.get(value);
            for (int i = 0; i < array.length; ++i) {
                if (array[i] == null) {
                    array[i] = 0;
                }
            }
        }
    }


    private boolean createGroupSet(int index, int currentMax) {

        boolean added = false;

        Integer[] array;
        List<Integer> rows = new ArrayList<>(3);

        for (Integer value : newMatrixValues.keySet()) {
            array = newMatrixValues.get(value);
            for (int i = 0; i < array.length; ++i) {
                if(array[i] == currentMax && rows.isEmpty()){
                        if(!(usedRowNumbers.contains(i) || usedRowNumbers.contains(value)))
                            rows = new ArrayList<>(Arrays.asList(value, i));
                        array[i] = 0;
                }
                if(array[i] == currentMax &&
                        !(usedRowNumbers.contains(i) || usedRowNumbers.contains(value))
                        && (rows.contains(value) || rows.contains(i) ) ) {
                            rows.addAll(Arrays.asList(value, i));
                            array[i] = 0;
                }
            }
        }

        Set<Integer> set = new HashSet<>(rows);

        if (set.size() != 0) {
            groups.put(index, set);
            usedRowNumbers.addAll(set);
            added = true;
        }

        return added;
    }

    private void createGroups() {
        int i;
        int max = uniqueVal.size();
        groups = new HashMap<>();
        usedRowNumbers = new HashSet<>();
        Set<Integer> lastElementSet = new HashSet<>();

        getRidOfNull(newMatrixValues);
        getRidOfNull(outPutMatrix);

        for (i = 0; max != 0;) {
            max = findMaxValue(max);
            if(createGroupSet(i, max))
                ++i;

        }

        for (int j = 0; j < main.getRankOfTheMatrix(); ++j) {
            if (!usedRowNumbers.contains(j)) {
                lastElementSet.add(j);
                groups.put(i, lastElementSet);
            }
        }

    }

    public Map<Integer, Integer[]> getMatrix(){
        return outPutMatrix;
    }

    public Map<Integer, Set> getGroups(){
        return groups;
    }

    public Map<Integer, String[]> getAllValues() {
        return allValues;
    }
}
