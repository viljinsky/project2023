/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.viljinsky.project2023;

import java.awt.BorderLayout;

/**
 *
 * @author viljinsky
 */
public class Tab extends Base implements CommandListener {
    
    protected DB db;

    public Tab(DB db, String name) {
        setLayout(new BorderLayout());
        this.title = name;
        this.db = db;
        setName(name);
    }

    @Override
    public void doCommand(String command) {
        showMessage("doCommand not difined");
    }

    @Override
    public boolean updateCommand(String command) {
        return false;
    }
    
}
