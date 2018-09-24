package impl;

import interf.Processable;
import main.Start;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Lab1 implements Processable {

    private Start main;
    private Map<Integer, String[]> allValues;

    public Lab1(Start main) {
        this.main = main;
        allValues = new HashMap<>();
    }

    @Override
    public void process() {
        System.out.println("Processing lab1");
        parseRows();
        printMap(allValues);
        System.out.println(Arrays.toString(main.getInputStrings()));
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
}
