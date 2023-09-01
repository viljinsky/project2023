/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.viljinsky.project2023;

import javax.swing.table.AbstractTableModel;

/**
 *
 * @author viljinsky
 */
public class GridModel extends AbstractTableModel {
    
    Recordset recordset;

    public Recordset getRecordset() {
        return recordset;
    }

    public GridModel(Recordset recordset) {
        this.recordset = recordset;
    }

    @Override
    public int getRowCount() {
        return recordset.size();
    }

    @Override
    public int getColumnCount() {
        return recordset.columnCount();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return recordset.get(rowIndex)[columnIndex];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return recordset.getColumnClass(columnIndex);
    }

    @Override
    public String getColumnName(int column) {
        return recordset.columnName(column);
    }
    
}
