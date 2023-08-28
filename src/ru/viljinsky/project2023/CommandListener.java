/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ru.viljinsky.project2023;

/**
 *
 * @author viljinsky
 */
public interface CommandListener {

    public void doCommand(String command);

    public boolean updateCommand(String command);
    
}
