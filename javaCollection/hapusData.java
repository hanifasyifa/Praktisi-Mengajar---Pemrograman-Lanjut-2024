package com.mycompany.javacollection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class hapusData {
    public static void main(String[] args) {
        Collection<String> names = new ArrayList<>();
        names.addAll(Arrays.asList("Cia", "Chapaw", "Biru", "Salsa", "Abe"));
        System.out.println("Data nama sebelum diremove: ");
        for(var name: names){
            System.out.println(name);
        }
        
        names.remove("Abe");
        System.out.println("\nData nama setelah diremove: ");
        for(var name: names){
            System.out.println(name);
        }
    }
}
