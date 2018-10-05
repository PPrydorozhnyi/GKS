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
        order.sort((o1, o2) -> {
            int result = Integer.compare(groupSets.get(o2).size(), groupSets.get(o1).size());

            if (result == 0) {
                result = sortEquals(o2, o1);
            }

            return result;
        });
        order.removeAll(used);
    }

    @SuppressWarnings("unchecked")
    private int sortEquals(int o1, int o2) {
        Set<String> string1 = groupSets.get(order.get(o1));
        Set<String> string2 = groupSets.get(order.get(o2));
        Set<Integer> group1 = groups.get(order.get(o1));
        Set<Integer> group2 = groups.get(order.get(o2));
        int stringCount1 = 0;
        int stringCount2 = 0;

        for(int i = 0; i < allValues.size(); ++i) {
            if (string1.containsAll(Arrays.asList(allValues.get(i))) && !group1.contains(i) && isUnused(i)) {
                ++stringCount1;
            }
            if (string2.containsAll(Arrays.asList(allValues.get(i))) && !group2.contains(i) && isUnused(i)) {
                ++stringCount2;
            }
        }
        if(stringCount1 <= stringCount2)
            return -1;
        else
            return 1;
    }

    private boolean isUnused(int i){

        for(Integer value: used){
                if(groups.get(value).contains(i)){
                    return false;
                }
        }
        return true;
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
