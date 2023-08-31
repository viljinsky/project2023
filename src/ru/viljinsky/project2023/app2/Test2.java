/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.viljinsky.project2023.app2;

import java.awt.BorderLayout;
import java.awt.event.WindowEvent;
import java.io.File;
import javax.swing.Action;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import ru.viljinsky.project2023.Base;
import ru.viljinsky.project2023.CommandBar;
import ru.viljinsky.project2023.CommandListener;
import ru.viljinsky.project2023.CommandManager;
import ru.viljinsky.project2023.DB;
import ru.viljinsky.project2023.Grid;
import ru.viljinsky.project2023.GridAdapter;
import ru.viljinsky.project2023.Recordset;

/**
 *
 * @author viljinsky
 */
public class Test2 extends Base implements CommandListener {

    public static final String ADD = "add";
    public static final String EDIT = "edit";
    public static final String DELETE = "delete";
    public static final String POST = "post";
    public static final String CANCEL = "cancel";

    public static final String[] commands = {ADD, EDIT, DELETE, null, POST, CANCEL,"remove","set"};

    DB db;
    Grid grid = new Grid();

    @Override
    public void doCommand(String command) {
        try {
            switch (command) {
                case "set":
                    grid.setGridAdapter(gridAdapter);
                    break;
                case "remove":
                    grid.removeGridArapter();
                    break;
                case ADD:
                case EDIT:
                case DELETE:
                case POST:
                case CANCEL:
                    showMessage(command);
                    break;
                default:
                    new RuntimeException("command undefined yet");
            }
        } catch (Exception e) {

            showMessage(e);
        }
    }

    @Override
    public boolean updateCommand(String command) {
        return true;
    }

    CommandManager commandManager = new CommandManager(this, commands);
    CommandBar commandBar = new CommandBar(commandManager);    
    GridAdapter gridAdapter = new GridAdapter(commandManager,new String[]{ADD,EDIT,DELETE});

    public Test2() {
        setTitle(DataModel.TEACHER);
        setLayout(new BorderLayout());
        add(new JScrollPane(grid));
        add(commandBar, BorderLayout.PAGE_START);

    }

    public void open() {
        Recordset recordset = db.table(DataModel.TEACHER);
        grid.setRecordset(recordset);
        grid.setGridAdapter(gridAdapter);
        JPopupMenu popupMenu = new JPopupMenu();
        for(Action a:commandManager){
            if (a==null){
                popupMenu.addSeparator();
            } else {
                popupMenu.add(a);
                        
            }
        }
        grid.setPopupMenu(popupMenu);
    }

    @Override
    public void windowOpened(WindowEvent e) {
        db = new AppDB2(new File("test.db"));
        open();
    }

    public static void main(String[] args) {
        new Test2().run();
    }

}
