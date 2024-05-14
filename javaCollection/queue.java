package com.mycompany.javacollection;

import java.util.ArrayDeque;
import java.util.Queue;

public class queue {
    public static void main(String[] args) {
        Queue<String> queues = new ArrayDeque<>(10);
        queues.offer("Melany");
        queues.offer("Dean");
        queues.offer("Cindy");
        
        for(String next = queues.poll(); next != null; next = queues.poll()){
            System.out.print(" (");
            System.out.print(queues.size());
            System.out.print(")");
            System.out.println(next);
        }   
    }
}
