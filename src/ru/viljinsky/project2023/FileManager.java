/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.viljinsky.project2023;

import java.awt.Component;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JOptionPane;

/**
 *
 * @author viljinsky
 */
public class FileManager implements CommandListener {
    
    File file = new File("test.db");
    
    public File recentFile(){
        if (file.exists()){
            return file;
        }
        return null;
    }
    
    Component parent;
    public static final String REOPEN = "reopen";
    public static final String CREATE = "create";
    public static final String OPEN = "open";
    public static final String CLOSE = "close";
    public static final String SAVE = "save";
    public static final String SAVE_AS = "save_as";
    public static final String EXIT = "exit";
    FileManagerListener fileManagerListener;

    public void create() throws Exception {
        file = null;
        fileManagerListener.fileCreate(new FileManagerEvent(this, null));
    }

    public void open() throws Exception {
        JFileChooser fileChooser = new JFileChooser(".");
        int retVal = fileChooser.showOpenDialog(parent);
        if (retVal == JFileChooser.APPROVE_OPTION){
            open(fileChooser.getSelectedFile());
            
        }
    }
    
    public void open(File file) throws Exception{
        this.file = file;
        fileManagerListener.fileOpen(new FileManagerEvent(this,file ));
    }

    public void close() throws Exception {
        file = null;
        fileManagerListener.fileClose(new FileManagerEvent(this));
    }

    public void save() throws Exception {
        fileManagerListener.fileRename(new FileManagerEvent(this, null));
    }

    @Override
    public void doCommand(String command) {
        try {
            switch (command) {
                case CREATE:
                    create();
                    break;
                case OPEN:
                    open();
                    break;
                case CLOSE:
                    close();
                    break;
                case SAVE:
                    save();
                    break;
                case SAVE_AS:
                    save();
                    break;
                case EXIT:
                    break;
                default:
                    throw new Exception("command \"" + command + "\" - unsupported yet");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(parent, e.getMessage(), "FileManager", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public boolean updateCommand(String command) {
        return true;
    }
    public CommandManager commandManager = new CommandManager(this, CREATE, null, OPEN, REOPEN, null, SAVE, SAVE_AS, CLOSE, null, EXIT);

    public FileManager(Component parent, FileManagerListener fileManagerListener) {
        this.parent = parent;
        this.fileManagerListener = fileManagerListener;
    }

    public JMenu menu() {
        return commandManager.menu("File");
    }
    
}
