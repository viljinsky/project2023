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
import ru.viljinsky.project2023.Grid;
import ru.viljinsky.project2023.GridAdapter;
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
    GridAdapter gridAdapter = new GridAdapter(commandManager, new String[]{ADD, EDIT, DELETE});
    
    public AppTabs(DB db) {
        for (Recordset r : db.tables()) {
            add(new TabGrid(db, r));
        }
        addChangeListener(e -> {
            TabGrid tGrid = (TabGrid) getSelectedTab();
            if (tGrid != null) {
                System.out.println(tGrid.getName());
                gridAdapter.setGrid(tGrid.grid);
            }
            
        });
    }
}

public class App2 extends Base {
    
    DB db;
    FileManager fileManager = new FileManager(this, null);
    Tabs tabs;
    
    public void open() {
        tabs = new AppTabs(db);
        add(tabs);
        addMenu(tabs.menu());
        revalidate();
        repaint();
    }
    
    public void close() {        
        if (tabs != null) {
            removeMenu(tabs.menu());
            remove(tabs);
            tabs = null;
            repaint();
            revalidate();
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
        db = new AppDB2();
        open();
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
