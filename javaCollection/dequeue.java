package com.mycompany.javacollection;

import java.util.Deque;
import java.util.LinkedList;

public class dequeue {

    public static void main(String[] args) {
       Deque<String> stack = new LinkedList<>();
       
       stack.offerLast("Ucel");
       stack.offerLast("Cia");
       stack.offerLast("Chava");
       
       for(var item= stack.pollLast(); item != null; item = stack.pollLast()){
           System.out.println(item);
       }
    }
    
}
