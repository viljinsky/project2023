/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.viljinsky.project2023;

import java.io.File;
import java.util.EventObject;

/**
 *
 * @author viljinsky
 */
public class FileManagerEvent extends EventObject {
    
    public File file;

    public FileManagerEvent(Object source) {
        super(source);
    }

    public FileManagerEvent(Object source, File file) {
        super(source);
        this.file = file;
    }
    
}
