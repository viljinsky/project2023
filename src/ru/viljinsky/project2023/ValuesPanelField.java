/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.viljinsky.project2023;

import java.awt.Container;
import java.util.Date;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

class VPF<T> extends Container{
    
    T value;

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
    
    public static void main(String[] args) {
        
        Object[] data  = {10,"String",10.23,new Date()};
        
        VPF<Integer> vpfInteger = new VPF<>();
        
        VPF<String> vpfString = new VPF<>();
        
        VPF<Double> vpfDouble = new VPF<>();
        VPF<Date> vpfDate = new VPF<>();
        
        for(Object p: data){
            VPF vpf = new VPF();
            vpf.setValue(p);
            Object r = vpf.getValue();
            System.out.println(r +" " + r.getClass().getName());
        }
        
//        vpfInteger.setValue(10);
//        System.out.println(vpfInteger.getValue());
//        
//        vpfString.setValue("value");
//        System.out.println(vpfString.getValue());
        
       
        
    }
           
}




/**
 *
 * @author viljinsky
 */
public class ValuesPanelField<T> extends JComponent {
    
    String fieldName;
    T fieldValue;
    JTextField textField;
    JLabel label;

    public ValuesPanelField(String fieldName, T fieldValue) {
        setBorder(new EmptyBorder(0, 0, 3, 0));
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
        textField = new JTextField(fieldValue==null?"":String.valueOf(fieldValue));
        label = new JLabel(fieldName);
        add(label);
        add(Box.createHorizontalStrut(3));
        add(textField);
    }

    public T getValue() {           
        return fieldValue;
    }

    public void setValue(T fieldValue) {
        this.fieldValue = fieldValue;
        textField.setText(String.valueOf(fieldValue));
    }
    
}
