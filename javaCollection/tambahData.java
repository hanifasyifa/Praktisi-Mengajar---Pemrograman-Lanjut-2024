package com.mycompany.javacollection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class tambahData {

    public static void main(String[] args) {
        Collection<String> names1 = new ArrayList<>();
        names1.add("ABE");
        names1.addAll(Arrays.asList("CIA", "CHAPAW"));
        
        for(var name: names1){
            System.out.println(name);
        }
    }
}
