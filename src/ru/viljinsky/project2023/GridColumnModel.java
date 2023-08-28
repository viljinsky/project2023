/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.viljinsky.project2023;

import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author viljinsky
 */
public class GridColumnModel extends DefaultTableColumnModel {
    
    Recordset recordset;

    public GridColumnModel(Recordset recordset) {
        this.recordset = recordset;
        for (int i = 0; i < recordset.columnCount(); i++) {
            TableColumn column = new TableColumn(i);
            column.setHeaderValue(recordset.columnName(i));
            column.setWidth(40);
            addColumn(column);
        }
    }
    
}
