package impl;

import interf.Processable;
import main.Start;

import java.util.*;

public class Lab1 implements Processable {

    private Start main;
    private Map<Integer, String[]> allValues;
    private Map<Integer, Integer[]> newMatrixValues;
    private Set uniqueVal;

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

    private void goTable(int amountUniqeValues){

        String[] values;
        String[] values2;
        int countOfColizion;
        int allStringValues;
        Integer[] newMatrixValue = new Integer [main.getRankOfTheMatrix()];
        newMatrixValues = new HashMap<>();


        for (int i = 0; i < main.getRankOfTheMatrix() - 1; ++i) {
            for (int j = i + 1; j < main.getRankOfTheMatrix(); ++j) {
                values = allValues.get(i);
                values2 = allValues.get(j);

                countOfColizion = checkCollisions(values, values2);

                allStringValues = values.length + values2.length;
                newMatrixValue[j] = amountUniqeValues - (allStringValues - (2 * countOfColizion));
            }
                newMatrixValues.put(i, newMatrixValue);
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

}
