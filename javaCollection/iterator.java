package com.mycompany.javacollection;

import java.util.Iterator;
import java.util.List;

public class iterator {

    public static void main(String[] args) {
        List<String> names = List.of("ABE", "CIA", "SALSA", "CHAPAW");
       Iterator<String> iterator = names.iterator();
       
       while(iterator.hasNext()){
           String name = iterator.next();
           System.out.println(name);
       }
    }
}
