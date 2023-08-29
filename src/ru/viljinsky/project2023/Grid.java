/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.viljinsky.project2023;

import java.awt.KeyboardFocusManager;
import javax.swing.JTable;
import javax.swing.table.TableColumnModel;

public class Grid extends JTable {

    public Grid(Recordset recordset) {
        setAutoCreateColumnsFromModel(false);
        setAutoResizeMode(AUTO_RESIZE_OFF);
        setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
        setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);
        setModel(new GridModel(recordset));
        setColumnModel(new GridColumnModel(recordset));
    }

    @Override
    protected TableColumnModel createDefaultColumnModel() {
        return super.createDefaultColumnModel();
    }

    @Override
    public void createDefaultColumnsFromModel() {
        super.createDefaultColumnsFromModel();
    }

    public void setRecordset(Recordset recordset) {
        setModel(new GridModel(recordset));
        setColumnModel(new GridColumnModel(recordset));
    }

    public void reload() {
        GridModel model = (GridModel) getModel();
        model.fireTableDataChanged();
    }

    public Values getSelectedValues() {
        if (getSelectedRow() < 0) {
            return null;
        }
        GridModel model = (GridModel) getModel();
        Object[] p = model.recordset.get(getSelectedRow());
        Values result = new Values();
        for (int i = 0; i < model.recordset.columnCount(); i++) {
            result.put(model.recordset.columnName(i), p[i]);
        }
        return result;
    }
    
    public Iterable<Values> values(){
        GridModel model = (GridModel)getModel();
        return model.recordset.values();
    }
    
}
