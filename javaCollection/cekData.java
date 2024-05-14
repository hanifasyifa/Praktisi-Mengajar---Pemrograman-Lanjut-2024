package com.mycompany.javacollection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public static void main(String[] args) {
        Collection<String> names = new ArrayList<>();
        names.addAll(Arrays.asList("Salsa", "Alaia", "Abe"));
        System.out.print("Apakah Abe ada dalam data? ");
        System.out.println(names.contains("Abe"));
         System.out.println("Current Data: ");
        for(var name : names){
            System.out.println(name);
        }
        
        names.remove("Abe");
        System.out.print("\nApakah Abe ada dalam data? ");
        System.out.println(names.contains("Abe"));
        System.out.println("Updated Data:");
        for(var name : names){
            System.out.println(name);
        }
    }
    
}
