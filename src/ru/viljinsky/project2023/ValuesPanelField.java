package ru.viljinsky.project2023;

import java.util.Date;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

class ValuesPanelEditor extends JTextField {
    
    public ValuesPanelEditor(Object p) {
        if (p != null) {
            if (p instanceof Integer) {
                setText(String.valueOf((Integer) p));
            }
            if (p instanceof Double) {
                setText(String.valueOf((Double) p));
            }
            if (p instanceof String) {
                setText((String) p);
            }            
            
        }
    }
    
    public void setValue(Object p) {
        if (p != null) {
            setText("");
        }
        if (p instanceof Integer) {
            setText(String.valueOf((Integer) p));
        }
        if (p instanceof Double) {
            setText(String.valueOf((Double) p));
        }
        if (p instanceof String) {
            setText((String) p);
        }        
        
    }
    
    public Object getValue() {
        String s = getText().trim();
        return s.isEmpty()?null:getText();
    }
    
    public Integer integerValue(){
        String s = getText().trim();
        return s.isEmpty()? null : Integer.valueOf(getText());
    }
    
    public Double doubleValue(){
        String s = getText().trim();
        
        return s.isEmpty()?null:Double.valueOf(getText());
    }
    
}

/**
 *
 * @author viljinsky
 */
public class ValuesPanelField<T> extends JComponent {
    
    String fieldName;
    T fieldValue;
    Class c = Object.class;
    ValuesPanelEditor editor;
    JLabel label;
    
    public ValuesPanelField(String fieldName) {
        this(fieldName, null);
    }
    
    public ValuesPanelField(String fieldName, T fieldValue) {
        this(Object.class, fieldName, fieldValue);
    }
    
    public ValuesPanelField(Class c, String fieldName, T fieldValue) {
        this.c = c;
        setBorder(new EmptyBorder(0, 0, 3, 0));
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
        editor = new ValuesPanelEditor(fieldValue);
        label = new JLabel(fieldName);
        add(label);
        add(Box.createHorizontalStrut(3));
        add(editor);
    }
    
    public T getValue() { 
        if (c == Integer.class){
            return (T)editor.integerValue();
        }
        if(c == Double.class){
            return(T) editor.doubleValue();
        }
        
        return (T)editor.getValue();
    }
    
    
    public void setValue(T fieldValue) {
        editor.setValue(fieldValue);
    }
    
    public static void main(String[] args) {
        Recordset recordset = new Recordset(new String[]{"field1", "field2", "field3", "field4"});
        recordset.classMap.put(0, Integer.class);
        recordset.classMap.put(1, String.class);
        recordset.classMap.put(2, Double.class);
        recordset.add(new Object[]{1, "str", 1.23, new Date()});
        ValuesPanel valuesPanel = new ValuesPanel(recordset);
        valuesPanel.setValues(recordset.values(0));
        valuesPanel.valuesPanelListener = e -> {
            e.values.print();
        };
        valuesPanel.showModal(null);
    }
}
