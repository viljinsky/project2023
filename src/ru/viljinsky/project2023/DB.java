/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ru.viljinsky.project2023;

/**
 *
 * @author viljinsky
 */
public interface DB extends AutoCloseable {

    public abstract Iterable<Recordset> tables();

    public abstract Recordset table(String tableName);

    public abstract Recordset query(String sql, Object... args);

    public abstract void execute(String sql, Object... args);

    public abstract void commit();

    public abstract void rallback();
    
}
