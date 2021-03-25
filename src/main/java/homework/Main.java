package homework;

import homework.student.MyCollection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

public class Main {
    public static void main(String[] args) {
        Collection<Integer> myCollection = new MyCollection<>();
        myCollection.add(1);

        System.out.println(myCollection.size());
        System.out.println(new ArrayList<>(myCollection));
    }
}
