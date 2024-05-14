package com.mycompany.javacollection;

import java.util.LinkedList;
import java.util.List;

public class list {
    public static void main(String[] args) {
        List<String> names = new LinkedList<>();
        
        names.add("Salsa");
        names.add("Nisa");
        names.add("Dian");
        
        System.out.println(names);
    }
    
}
