/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.viljinsky.project2023.app2;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import ru.viljinsky.project2023.DB;
import ru.viljinsky.project2023.Recordset;

/**
 *
 * @author viljinsky
 */
public class test {
    
    public static void main(String[] args) throws Exception{
        
        
        
        DB db = new AppDB2();
        
        File file = new File("test.db");
        Class.forName("org.sqlite.JDBC");
        
        try(Connection con = DriverManager.getConnection(String.format("jdbc:sqlite:%s",file.getAbsolutePath()))){
         
            for(Recordset recordset:db.tables()){
            
                System.out.println(recordset.getName());
                
                Statement stmt =  con.createStatement();
                ResultSet res =  stmt.executeQuery(String.format("select * from %s", recordset.getName()));
                ResultSetMetaData meta = res.getMetaData();

                while (res.next()){
                    Object[] p = new Object[recordset.columnCount()];
                    for(int i=0;i<p.length;i++){
                        p[i]= res.getObject(recordset.columns[i]);
                    }
                    recordset.add(p);
//                    for(int i=1;i<=meta.getColumnCount();i++){
//                        System.out.print(res.getObject(i));
//                    }
//                    System.out.println("");
                }
                recordset.print();
            }
        };
        
        
        
    }
    
}
