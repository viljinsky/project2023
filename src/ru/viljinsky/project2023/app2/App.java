package ru.viljinsky.project2023.app2;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import ru.viljinsky.project2023.Base;
import ru.viljinsky.project2023.BaseDialog;
import ru.viljinsky.project2023.CommandListener;
import ru.viljinsky.project2023.CommandManager;
import ru.viljinsky.project2023.DB;
import ru.viljinsky.project2023.FileManager;
import ru.viljinsky.project2023.FileManagerEvent;
import ru.viljinsky.project2023.FileManagerListener;
import ru.viljinsky.project2023.GridAdapter;
import ru.viljinsky.project2023.ValuesPanel;

/**
 *
 * @author viljinsky
 */
public class App extends Base implements FileManagerListener, CommandListener, ChangeListener {

    DB db;
    Tabs tabs;
    BaseTab tab2;

    @Override
    public void stateChanged(ChangeEvent e) {
        if (tab2 != null) {
            tab2.removeGridArapter();
        }
        tab2 = tabs.getSelectedTab();
        if (tab2 != null) {
            tab2.setGridAdapter(gridAdapter);
        }
    }

    @Override
    public void doCommand(String command) {
        if (tab2 != null) {
            tab2.doCommand(command);
        }
    }

    @Override
    public boolean updateCommand(String command) {
        return tab2 == null ? false : tab2.updateCommand(command);
    }

    CommandManager commandManager = new CommandManager(this, BaseTab.ADD, BaseTab.EDIT, BaseTab.DELETE);

    GridAdapter gridAdapter = new GridAdapter(commandManager, BaseTab.ADD, BaseTab.EDIT, BaseTab.DELETE);

    public void open() {
        tabs = new Tabs(db);
        tabs.addChangeListener(this);
        for (int i = 0; i < tabs.getTabCount(); i++) {
            BaseTab tab = tabs.getTab(i);
            tab.grid.setCommand(commandManager);
        }

        addMenu(tabs.menu());
        add(tabs);
        repaint();
        revalidate();
        stateChanged(new ChangeEvent(tabs));

    }

    public void close() {
        if (tabs != null) {
            tabs.removeChangeListener(this);
            removeMenu(tabs.menu());
            remove(tabs);
            tabs = null;
            repaint();
            revalidate();
        }
    }

    @Override
    public void fileCreate(FileManagerEvent e) throws Exception {

        ValuesPanel valuesPanel = new ValuesPanel();
        int result = valuesPanel.showModal(getParent());
        if (result == BaseDialog.RESULT_OK) {
            if (e.file.exists()){
                if(!e.file.delete()){
                    throw new RuntimeException("File exists and cant bea deleted");
                }
            }
            new CreateDB(e.file).run();
            fileManager.open(e.file);
        }
    }

    @Override
    public void fileOpen(FileManagerEvent e) throws Exception {
        if (db != null) {
            db.close();
            db = null;
        }
        close();
        db = new AppDB(e.file);
        db.open();
        open();
        db.close();
        setStatusText(e.file.getName());
    }

    @Override
    public void fileSave(FileManagerEvent e) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void fileClose(FileManagerEvent e) throws Exception {
        if (db != null) {
            close();
            db.close();
            db = null;
            setStatusText("no connection");
        }
    }

    @Override
    public void fileRename(FileManagerEvent e) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    ///////////////////////////////////////////////////////////////////////
    @Override
    public void windowOpened(WindowEvent e) {
        try {
            fileManager.open(fileManager.recentFile());
        } catch (Exception h) {
            showMessage(h);
        }
    }

    FileManager fileManager = new FileManager(this, this);

    public App() {
        setTitle("test3");
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(800, 600));
        addCommandBar(fileManager.commandManager);
        addMenu(fileManager.menu());
        addStatusBar();
    }

    public static void main(String[] args) throws Exception {
        new App().run();
    }

}
