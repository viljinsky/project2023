package ru.viljinsky.project2023;

import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author viljinsky
 */
public class GridColumnModel extends DefaultTableColumnModel {
    
    //String[] columns;
//    Recordset recordset;

    public GridColumnModel(Recordset recordset,String[] columns) {
  //      this.recordset = recordset;
        columns =  columns.length==0?recordset.columns:columns;
        for (int i = 0; i < columns.length; i++) {
            TableColumn column = new TableColumn(recordset.columnIndex(columns[i]));
            column.setHeaderValue(columns[i]);
            column.setWidth(40);
            addColumn(column);
        }
    }
    
}
