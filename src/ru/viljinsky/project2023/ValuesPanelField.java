/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.viljinsky.project2023;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author viljinsky
 */
public class ValuesPanelField extends JComponent {
    
    String fieldName;
    Object fieldValue;
    JTextField textField;
    JLabel label;

    public ValuesPanelField(String fieldName, Object fieldValue) {
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

    public Object getValue() {
        return textField.getText();
    }

    public void setValue(Object fieldValue) {
        textField.setText(String.valueOf(fieldValue));
    }
    
}
