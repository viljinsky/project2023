package ru.viljinsky.project2023.app2;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import ru.viljinsky.project2023.Base;
import ru.viljinsky.project2023.CommandListener;
import ru.viljinsky.project2023.CommandManager;
import ru.viljinsky.project2023.DB;
import ru.viljinsky.project2023.FileManager;
import ru.viljinsky.project2023.FileManagerEvent;
import ru.viljinsky.project2023.FileManagerListener;
import ru.viljinsky.project2023.Grid;
import ru.viljinsky.project2023.Recordset;
import ru.viljinsky.project2023.Tab;
import ru.viljinsky.project2023.Tabs;

class TabGrid extends Tab {
    
    Grid grid;
    
    public TabGrid(DB db, Recordset recordset) {
        super(db, recordset.getName());
        grid = new Grid(recordset);
        add(new JScrollPane(grid));
    }
    
}

class AppTabs extends Tabs implements CommandListener {
    
    public static final String ADD = "add";
    public static final String EDIT = "edit";
    public static final String DELETE = "delete";
    public static final String POST = "post";
    public static final String CANCEL = "cancel";
    public static final String FIND = "find";
    
    @Override
    public void doCommand(String command) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public boolean updateCommand(String command) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    CommandManager commandManager = new CommandManager(this);
    
    public AppTabs(DB db) {
        for (Recordset r : db.tables()) {
            add(new TabGrid(db, r));
        }
        addChangeListener(e -> {
            TabGrid tGrid = (TabGrid) getSelectedTab();
            if (tGrid != null) {
                System.out.println(tGrid.getName());
            }
            
        });
    }
}

public class App2 extends Base implements FileManagerListener{

    @Override
    public void fileCreate(FileManagerEvent e) throws Exception {
        close();
        db = new AppDB2();
        open();
    }

    @Override
    public void fileOpen(FileManagerEvent e) throws Exception {
        close();
        db = new AppDB2(e.file);
        open();
    }

    @Override
    public void fileSave(FileManagerEvent e) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void fileClose(FileManagerEvent e) throws Exception {
        close();
    }

    @Override
    public void fileRename(FileManagerEvent e) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
    
    DB db;
    FileManager fileManager = new FileManager(this, this);
    Tabs tabs;
    
    public void open() {
        tabs = new AppTabs(db);
        add(tabs);
        addMenu(tabs.menu());
        revalidate();
        repaint();
        setStatusText("file :"+fileManager.recentFile());
    }
    
    public void close() {        
        if (tabs != null) {
            removeMenu(tabs.menu());
            remove(tabs);
            tabs = null;
            repaint();
            revalidate();
            setStatusText("no connection");
        }
        
    }
    
    public App2() {
        super();
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(800, 600));
        addStatusBar();
        addMenu(fileManager.menu());
        
    }
    
    @Override
    public void windowOpened(WindowEvent e) {
        try{
            fileManager.open(fileManager.recentFile());
        } catch ( Exception h){
            System.err.println(""+h.getMessage());
        }
    }
    
    @Override
    public void windowClosing(WindowEvent e) {
        if (db != null) try {
            db.close();
            close();
            db = null;
            super.windowClosing(e);
        } catch (Exception h) {
            showMessage(h);
        } else {
            super.windowClosing(e);
        }
        
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new App2());
    }
    
}
