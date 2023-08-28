/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ru.viljinsky.project2023;

/**
 *
 * @author viljinsky
 */
public interface FileManagerListener {

    public void fileCreate(FileManagerEvent e) throws Exception;

    public void fileOpen(FileManagerEvent e) throws Exception;

    public void fileSave(FileManagerEvent e) throws Exception;

    public void fileClose(FileManagerEvent e) throws Exception;

    public void fileRename(FileManagerEvent e) throws Exception;
    
}
