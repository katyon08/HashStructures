package ua.ks.katyon08;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
	    String x = "5";
        boolean[] right = new boolean[1024];
        System.out.println(x);
        for (int i = 0; i < 10; i++) {
            x = Integer.toString((x.hashCode() * 10) % 1024);
            System.out.println(x);
        }
    }
}
