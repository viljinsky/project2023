package ru.viljinsky.project2023;

import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author viljinsky
 */
public class GridColumnModel extends DefaultTableColumnModel {
    
    public GridColumnModel(Recordset recordset,String[] columns) {
        columns =  columns.length==0?recordset.columns:columns;
        for (int i = 0; i < columns.length; i++) {
            TableColumn column = new TableColumn(recordset.columnIndex(columns[i]));
            column.setHeaderValue(recordset.columnLabel(recordset.columnIndex(columns[i])));//columns[i]);
            column.setWidth(40);
            addColumn(column);
        }
    }
    
}
