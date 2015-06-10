package ua.ks.katyon08;


import java.util.Iterator;

public class Main {
    private static final Integer[] simpleArray =
            { 80, 54, 139, 96, 130, 95, 104, 67, 106, 144, 17, 28, 35, 67, 73, 81, 95, 111, 127};


    public static void main(String[] args) {
        ua.ks.katyon08.TreeMap<Integer, Integer> tm = new ua.ks.katyon08.TreeMap<Integer, Integer>();
        for (int i = 0; i < simpleArray.length; i++) {
            tm.put(simpleArray[i], simpleArray[i]);
        }
        Iterator<TreeMap.Entry> iterator = (Iterator) tm.entryIterator(tm.getEntryPublic(104));
        /*while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }*/
    }

}
