package impl;

import interf.Processable;

import java.util.*;

public class Lab2 implements Processable {

    private Map<Integer, String[]> allValues;
    private Map<Integer, Set> groups;
    private Map<Integer, Set<String>> groupSets;
    private List<Integer> order;
    private Set<Integer> used;
    private int iteratorForUsed = 0;

    public Lab2(Lab1 lab1) {
        allValues = lab1.getAllValues();
        groups = lab1.getGroups();
        used = new HashSet<>();
    }

    @SuppressWarnings("unchecked")
    private void makeGroupSets(){
        groupSets = new HashMap<>(groups.size());
        Set<String> set;

        for(Integer key: groups.keySet()){
            set = new HashSet<>();
            for(Integer value: (Set<Integer>)groups.get(key)){
                    set.addAll(Arrays.asList(allValues.get(value)));
            }
            groupSets.put(key, set);
        }
    }

    private void sortGroups(){
        order = new ArrayList<>();

        order.addAll(groupSets.keySet());
        order.sort((o1, o2) -> Integer.compare(groupSets.get(o2).size(), groupSets.get(o1).size()));
        order.removeAll(used);
    }

    @SuppressWarnings("unchecked")
    private boolean checkGroups(){
        Set<String> values;

        while (order.size() != 0) {
            values = groupSets.get(order.get(0));
            for(int j = 0; j < allValues.size(); ++j){
                if(!groups.get(order.get(0)).contains(j)) {
                    if (values.containsAll(Arrays.asList(allValues.get(j)))) {
                        removeRow(j);
                        groups.get(order.get(0)).add(j);
                        return true;
                    }
                }
            }
            order.remove(0);
            used.add(iteratorForUsed);
            ++iteratorForUsed;
            //here
        }
        return false;
    }

    private void removeRow(int j){
        for(Integer key: groups.keySet()){
            if(groups.get(key).remove(j))
                return;
        }
    }



    @Override
    public void process() {
        boolean b = true;
        while(b) {
            makeGroupSets();
            sortGroups();
            b = checkGroups();
        }
        System.out.println("process");
    }
}
