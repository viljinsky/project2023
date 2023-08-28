/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.viljinsky.project2023;

import java.util.HashMap;

/**
 *
 * @author viljinsky
 */
public class Values extends HashMap<String, Object> {
    
    public Values() {
    }

    public Values(String key, Object value) {
        put(key, value);
    }

    public Values(String[] key) {
        for (String k : key) {
            put(k, null);
        }
    }

    public void print() {
        for (String key : keySet()) {
            System.out.println(key + " = " + String.valueOf(get(key)));
        }
    }

    public Values values(String[] key) {
        Values result = new Values();
        for (String k : keySet()) {
            result.put(k, containsKey(k) ? get(k) : null);
        }
        return result;
    }
    
}
