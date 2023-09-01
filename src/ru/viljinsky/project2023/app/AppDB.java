package ru.viljinsky.project2023.app;

import java.io.File;
import java.util.ArrayList;
import ru.viljinsky.project2023.DB;
import ru.viljinsky.project2023.Recordset;

/**
 *
 * @author viljinsky
 */
public class AppDB extends ArrayList<Recordset> implements DB {
    
    File file;

    public AppDB() {
        Recordset r1 = new Recordset("r1", new String[]{"column1", "column2", "column3", "column4"});
        Recordset r2 = new Recordset("r2", new String[]{"column5", "column6", "column7", "column8"});
        Recordset r3 = new Recordset("r3", new String[]{"column9", "column10", "column11", "column12"});
        Recordset r4 = new Recordset("r4", new String[]{"column13", "column14", "column15", "column16"});
        add(r1);
        add(r2);
        add(r3);
        add(r4);
        long t = System.currentTimeMillis() + 1000;
        while (System.currentTimeMillis() < t) {
        }
    }

    public AppDB(File file) {
        this();
        this.file = file;
    }

    @Override
    public Iterable<Recordset> tables() {
        return this;
    }

    @Override
    public Recordset table(String tableName) {
        for (Recordset r : this) {
            if (tableName.equals(tableName)) {
                return r;
            }
        }
        return null;
    }

    @Override
    public Recordset query(String sql, Object... args) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void execute(String sql, Object... args) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void commit() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void rallback() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void close() throws Exception {
    }

    @Override
    public String[] primary() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void open() {
    }
    
    
    
}
