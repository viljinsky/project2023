/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.viljinsky.project2023.app2;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import ru.viljinsky.project2023.Base;
import ru.viljinsky.project2023.CommandBar;
import ru.viljinsky.project2023.DB;
import ru.viljinsky.project2023.FileManager;
import ru.viljinsky.project2023.FileManagerEvent;
import ru.viljinsky.project2023.FileManagerListener;

/**
 *
 * @author viljinsky
 */
public class Test3 extends Base implements FileManagerListener{
    
    DB db ;

    @Override
    public void fileCreate(FileManagerEvent e) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void fileOpen(FileManagerEvent e) throws Exception {
        db = new AppDB2(e.file);
        setStatusText(e.file.getName());
    }

    @Override
    public void fileSave(FileManagerEvent e) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void fileClose(FileManagerEvent e) throws Exception {
        if (db!=null){
            db.close();
            db = null;
            setStatusText("no connection");
        }
//        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public void fileRename(FileManagerEvent e) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
    ///////////////////////////////////////////////////////////////////////

    @Override
    public void windowOpened(WindowEvent e) {
        try{
            fileManager.open(fileManager.recentFile());
        } catch (Exception h){
            showMessage(h);
        }
    }
    
    
    
    FileManager fileManager = new FileManager(this, this);

    public Test3() {
        setTitle("test3");
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(800,600));
        addCommandBar(fileManager.commandManager);
        addMenu(fileManager.menu());
        addStatusBar();
    }
    
    
    
    public static void main(String[] args) {
        new Test3().run();
    }
    
}
