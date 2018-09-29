package impl;

import interf.Processable;

import java.util.*;

public class Lab2 implements Processable {

    private Map<Integer, String[]> allValues;
    private Map<Integer, Set> groups;
    private Map<Integer, Set<String>> groupSets;
    private List<Integer> order;

    public Lab2(Lab1 lab1) {
        allValues = lab1.getAllValues();
        groups = lab1.getGroups();
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
    }

    private boolean checkGroups(){
        Set<String> values;
        Set<Integer> grop;

        for(int i = 0; i < order.size(); ++i) {
            values = groupSets.get(order.get(i));
            for(int j = 0; j < allValues.size(); ++j){
                if(!groups.get(order.get(i)).contains(j)) {
                    if (values.containsAll(Arrays.asList(allValues.get(j)))) {
                        removeRow(j);
                        groups.get(order.get(i)).add(j);

                        return true;
                    }
                }
            }
            order.remove(i);
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
