package ru.viljinsky.project2023.app2;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.io.File;
import javax.swing.AbstractAction;
import javax.swing.JMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import ru.viljinsky.project2023.Base;
import ru.viljinsky.project2023.CommandListener;
import ru.viljinsky.project2023.CommandManager;
import ru.viljinsky.project2023.DB;
import ru.viljinsky.project2023.FileManager;
import ru.viljinsky.project2023.FileManagerEvent;
import ru.viljinsky.project2023.FileManagerListener;
import ru.viljinsky.project2023.Grid;
import ru.viljinsky.project2023.GridAdapter;
import ru.viljinsky.project2023.GridModel;
import ru.viljinsky.project2023.Recordset;
import ru.viljinsky.project2023.Values;
import ru.viljinsky.project2023.ValuesPanel;
import ru.viljinsky.project2023.app.AppDB;

class Tab2 extends Base implements CommandListener {

    public static final String ADD = "add";
    public static final String EDIT = "edit";
    public static final String DELETE = "delete";

    Grid grid = new Grid();

    Values selectedValues() {
        return grid.getSelectedValues();
    }

    Values defaultValues() {
        return new Values();
    }

    void edit() {
        GridModel model = (GridModel) grid.getModel();
        Recordset recordset = model.getRecordset();
        ValuesPanel valuesPanel = new ValuesPanel(recordset.columns);
        valuesPanel.setTitle(getTitle());
        valuesPanel.setValues(grid.getSelectedValues());
        valuesPanel.valuesPanelListener = e->{
            e.values.print();
        };
        valuesPanel.showModal(getParent());
    }

    void append() {
        GridModel model = (GridModel) grid.getModel();
        Recordset recordset = model.getRecordset();
        ValuesPanel valuesPanel = new ValuesPanel(recordset.columns);
        valuesPanel.setValues(defaultValues());
        valuesPanel.setTitle(getTitle() + " - new record");
        valuesPanel.valuesPanelListener = e->{
            e.values.print();
        };
        valuesPanel.showModal(getParent());
    }

    void delete() {
        if (confirm("rmove selected recordset")) {
        }

    }

    public Tab2() {
        setLayout(new BorderLayout());
        add(new JScrollPane(grid));
    }

    public void setGridAdapter(GridAdapter gridAdapter) {
        grid.setGridAdapter(gridAdapter);
    }

    public void removeGridArapter() {
        grid.removeGridArapter();
    }

    public void setRecordset(Recordset recordset) {
        setTitle(recordset.getName());
        grid.setRecordset(recordset);
    }

    public void doCommand(String command) {
        try {
            switch (command) {
                case ADD:
                    append();
                    break;
                case EDIT:
                    edit();
                    break;
                case DELETE:
                    delete();
                    break;
                default:
                    throw new RuntimeException("command undefine yet");
            }
        } catch (Exception e) {
            showMessage(e);
        }
    }

    @Override
    public boolean updateCommand(String command) {
        return true;
    }

}

class Tabs2 extends JTabbedPane {

    JMenu menu = new JMenu("Tabs");

    class TabAction extends AbstractAction {

        Tab2 tab;

        public TabAction(Tab2 tab) {
            super(tab.getTitle());
            this.tab = tab;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            setSelectedIndex(indexOfComponent(tab));
        }

    }

    public Tabs2(DB db) {
        for (Recordset recordset : db.tables()) {
            Tab2 tab = new Tab2();
            tab.setRecordset(recordset);
            menu.add(new TabAction(tab));
            addTab(recordset.getName(), tab);
        }
    }

    public Tab2 getSelectedTab() {
        return (Tab2) getSelectedComponent();
    }

    public Tab2 getTab(int index) {
        return (Tab2) getComponent(index);
    }

    public JMenu menu() {
        return menu;
    }

}

/**
 *
 * @author viljinsky
 */
public class Test3 extends Base implements FileManagerListener, CommandListener, ChangeListener {

    DB db;
    Tabs2 tabs;
    Tab2 tab2;

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

    CommandManager commandManager = new CommandManager(this, Tab2.ADD, Tab2.EDIT, Tab2.DELETE);

    GridAdapter gridAdapter = new GridAdapter(commandManager, Tab2.ADD, Tab2.EDIT, Tab2.DELETE);

    public void open() {
        tabs = new Tabs2(db);
        tabs.addChangeListener(this);
        for (int i = 0; i < tabs.getTabCount(); i++) {
            Tab2 tab = tabs.getTab(i);
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
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void fileOpen(FileManagerEvent e) throws Exception {
        db = new AppDB2(e.file);
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

    public Test3() {
        setTitle("test3");
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(800, 600));
        addCommandBar(fileManager.commandManager);
        addMenu(fileManager.menu());
        addStatusBar();
    }

    public static void main(String[] args) throws Exception{
        new Test3().run();
    }

}
