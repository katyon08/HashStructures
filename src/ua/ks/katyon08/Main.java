package ua.ks.katyon08;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        Random rnd = new Random();
        int x = rnd.nextInt()+50, y[] = { 1, 2, 3};
        System.out.print("count = " + x + "\n{ ");
        for (int i = 0; i < 1000; i++) {
            System.out.print((rnd.nextInt(30000) - 15000) + ", ");
            if ((i % 10 == 0) && (i != 0)) System.out.println();
        }
        System.out.println(" }");
    }
}
