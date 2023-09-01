/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.viljinsky.project2023;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.util.EventObject;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author viljinsky
 */
public class ValuesPanel extends Container implements BaseDialogListener {
    String title = "valuesPanel";

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    Values values;
    public ValuesPanelListener valuesPanelListener;

    @Override
    public void onResult(EventObject e) {
        BaseDialog baseDialog = (BaseDialog) e.getSource();
        if (valuesPanelListener != null && baseDialog.modalResult == BaseDialog.RESULT_OK) {
            valuesPanelListener.stateChanged(new ValuesPanelEvent(this, getValues()));
        }
    }

    private void addValuesField(String fieldName, Object fieldValue) {
        ValuesPanelField field = new ValuesPanelField(fieldName, fieldValue);
        add(field);
    }

    public ValuesPanel(String... fieldName) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.values = values;
        for (String name : fieldName) {
            addValuesField(name, null);
        }
    }

    int fieldCount() {
        return getComponentCount();
    }

    ValuesPanelField field(int index) {
        return (ValuesPanelField) getComponent(index);
    }

    ValuesPanelField fieldByName(String fieldName) {
        for (int i = 0; i < fieldCount(); i++) {
            if (field(i).fieldName.equals(fieldName)) {
                return field(i);
            }
        }
        return null;
    }

    public void setValues(Values values) {
        this.values = values;
        for (String key : values.keySet()) {
            ValuesPanelField field = fieldByName(key);
            if (field != null) {
                field.setValue(values.get(key));
            }
        }
    }

    public Values getValues() {
        Values result = new Values();
        for (int i = 0; i < fieldCount(); i++) {
            ValuesPanelField field = field(i);
            result.put(field.fieldName, field.getValue());
        }
        return result;
    }

    public void showModal(Component parent) {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBorder(new EmptyBorder(6, 3, 6, 3));
        wrapper.add(this, BorderLayout.PAGE_START);
        BaseDialog baseDialog = new BaseDialog(wrapper);
        baseDialog.setTitle(title);
        baseDialog.baseDialogListener = this;
        baseDialog.showModal(parent);
        baseDialog.dispose();
        //baseDialog = null;
    }
    
}
