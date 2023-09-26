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

    Values values;
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    
    public ValuesPanelListener valuesPanelListener;

    @Override
    public void onResult(EventObject e) {
        BaseDialog baseDialog = (BaseDialog) e.getSource();
        if (valuesPanelListener != null && baseDialog.modalResult == BaseDialog.RESULT_OK) {
            valuesPanelListener.stateChanged(new ValuesPanelEvent(this, getValues()));
        }
    }

    private ValuesPanelField addValuesField(Class<?> c,String fieldName, Object fieldValue) {
        ValuesPanelField field = new ValuesPanelField(c,fieldName, fieldValue);
        add(field);
        return field;
    }
    
    public ValuesPanel(Recordset recordset){
        this(recordset,recordset.columns);
    }
    
    public ValuesPanel(Recordset recordset,String... columns){
        columns = columns.length==0?recordset.columns:columns;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        for (String name : columns) {
            int index = recordset.columnIndex(name);
            Class<?> c = recordset.getColumnClass(index);
            addValuesField(c,name, null).label.setText(recordset.columnLabel(index));
        }
    }

    public ValuesPanel(String... fieldName) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        for (String name : fieldName) {
            addValuesField(Object.class, name, null);
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
        Values result = values;
        for (int i = 0; i < fieldCount(); i++) {
            ValuesPanelField field = field(i);
            result.put(field.fieldName, field.getValue());
        }
        return result;
    }

    int modalResult;
    public Integer showModal(Component parent) {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBorder(new EmptyBorder(6, 3, 6, 3));
        wrapper.add(this, BorderLayout.PAGE_START);
        BaseDialog baseDialog = new BaseDialog(wrapper);
        baseDialog.setTitle(title);
        baseDialog.baseDialogListener = this;
        baseDialog.showModal(parent);
        modalResult = baseDialog.modalResult;
        baseDialog.dispose();
        return modalResult;
        //baseDialog = null;
    }
    
}
