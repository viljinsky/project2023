/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.viljinsky.project2023.app2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.SwingUtilities;
import ru.viljinsky.project2023.Base;
import ru.viljinsky.project2023.DB;
import ru.viljinsky.project2023.Recordset;

/**
 *
 * @author viljinsky
 */
class AppDB2 extends HashMap<String, Recordset> implements DB {

    private static final String TABLE1 = "table1";
    private static final String TABLE2 = "table2";
    private static final String TABLE3 = "table3";
    private static final String TABLE4 = "table4";
    private static final String TABLE5 = "table5";

    private static final String COLUMN1 = "column1";
    private static final String COLUMN2 = "column2";
    private static final String COLUMN3 = "column3";
    private static final String COLUMN4 = "column4";
    private static final String COLUMN5 = "column5";
    private static final String COLUMN6 = "column6";

    Recordset createRecordset(String tableName) {
        switch (tableName) {
            case TABLE1:
            case TABLE2:
            case TABLE3:
            case TABLE4:
            case TABLE5:
                return new Recordset(tableName, new String[]{COLUMN1, COLUMN2, COLUMN3, COLUMN4, COLUMN5, COLUMN6});
            default:
                throw new RuntimeException("unknow tableName " + tableName);
        }
    }

    public AppDB2() {
        for (String tableName : new String[]{TABLE1, TABLE2, TABLE3, TABLE4, TABLE5}) {
            put(tableName, createRecordset(tableName));
        }
    }

    @Override
    public Iterable<Recordset> tables() {
        List list = new ArrayList();
        for (String tableName : keySet()) {
            list.add(get(tableName));
        }
        return list;
    }

    @Override
    public Recordset table(String tableName) {
        return containsKey(tableName) ? get(tableName) : null;
    }

    @Override
    public Recordset query(String sql, Object... args) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void execute(String sql, Object... args) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void commit() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void rallback() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void close() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}

public class App2 extends Base {

    DB db = new AppDB2();

    public App2() {

        for (Recordset recordset : db.tables()) {
            recordset.print();
        }

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new App2());
    }

}
