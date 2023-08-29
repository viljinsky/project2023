/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.viljinsky.project2023;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author viljinsky
 */
public class Recordset extends ArrayList<Object[]> {
    
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String[] columns = {};

    public Recordset() {
    }

    public Recordset(String[] columns) {
        this.columns = columns;
    }

    public Recordset(String name, String[] columns) {
        this.name = name;
        this.columns = columns;
    }

    public Values values(int index) {
        Object[] p = get(index);
        Values values = new Values();
        for (int i = 0; i < columns.length; i++) {
            values.put(columns[i], p[i]);
        }
        return values;
    }

    public Iterable<Values> values() {
        List l = new ArrayList();
        for (Object[] p : this) {
            Values values = new Values();
            for (int i = 0; i < columnCount(); i++) {
                values.put(columnName(i), p[i]);
            }
            l.add(values);
        }
        return l;
    }

    public void print() {
        for(String column:columns){
            System.out.print(column+" ");
        }
        System.out.println();
        
        if (isEmpty()) {
            System.out.println("recordset is empty");
        }
        
        for(Object[] p:this){
            for(int i=0;i<columnCount();i++){
                System.out.print(String.valueOf(p[i])+" ");
            }
            System.out.println("");
        }
    }

    public int columnCount() {
        return columns.length;
    }

    public String columnName(int index) {
        return columns[index];
    }

    public int columnIndex(String column) {
        for (int i = 0; i < columns.length; i++) {
            if (columns[i].equals(column)) {
                return i;
            }
        }
        return -1;
    }

    public Class<?> getColumnClass(int index) {
        return Integer.class;
    }

    public void append() {
        Object[] p = new Object[columnCount()];
        add(p);
    }

    public void append(Values values) {
        Object[] p = new Object[columnCount()];
        for (String column : values.keySet()) {
            p[columnIndex(column)] = values.get(column);
        }
        add(p);
    }

    public void update(int index, Values values) {
        Object[] p = get(index);
        for (String key : values.keySet()) {
            p[columnIndex(key)] = values.get(key);
        }
        set(index, p);
    }

    public void delete(int index) {
        remove(index);
    }

    public int indexOf(Values values) {
        label:
        for (Object[] p : this) {
            for (int i = 0; i < columnCount(); i++) {
                if (values.containsKey(columns[i])) {
                    Object value = values.get(columns[i]);
                    if (!((value == null && p[i] == null) || (value != null && value.equals(p[i])))) {
                        continue label;
                    }
                }
            }
            return indexOf(p);
        }
        return -1;
    }
    
}