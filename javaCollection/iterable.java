package com.mycompany.javacollection;
import java.util.List;

public class iterable {

    public static void main(String[] args) {
        Iterable<String> names = List.of("Abe", "Cia", "Salsa");
        
        for(var name: names){
            System.out.println(name);
        }
    }
}
