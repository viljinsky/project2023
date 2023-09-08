/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.viljinsky.project2023.app2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

/**
 *
 * @author viljinsky
 */
public class Test2 implements Runnable{

    @Override
    public void run() {
        try{
            Class.forName("org.sqlite.JDBC");
            try(Connection con = DriverManager.getConnection("jdbc:sqlite:test.db");){
                Statement stmp = con.createStatement();
                ResultSet res = stmp.executeQuery("select * from sqlite_master where type='view'");
                ResultSetMetaData meta = res.getMetaData();
                while(res.next()){
                    for(int i=0;i<meta.getColumnCount();i++){
                        System.out.println(meta.getColumnName(i+1)+" "+res.getObject(i+1));
                        
                    }                    
                    System.out.println("------------------");
                }
            }
            
        } catch (Exception e){
            System.err.println(e.getMessage());
        }
        
    }
    
    public static void main(String[] args) {
        new Test2().run();
    }
    
    
    
}
