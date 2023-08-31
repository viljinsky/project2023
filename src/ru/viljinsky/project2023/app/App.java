package ru.viljinsky.project2023.app;

import ru.viljinsky.project2023.Tabs;
import ru.viljinsky.project2023.Tab;
import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import ru.viljinsky.project2023.Base;
import ru.viljinsky.project2023.BaseDialog;
import ru.viljinsky.project2023.CommandListener;
import ru.viljinsky.project2023.CommandManager;
import ru.viljinsky.project2023.FileManager;
import ru.viljinsky.project2023.FileManagerEvent;
import ru.viljinsky.project2023.FileManagerListener;
import ru.viljinsky.project2023.Grid;
import ru.viljinsky.project2023.Recordset;
import ru.viljinsky.project2023.ValuesPanel;

/**
 *
 * @author viljinsky
 */
public class App extends Base implements CommandListener, FileManagerListener {
    
    AppDB db;
    
    Tabs tabbedPane;

    public void open() {
        tabbedPane = new Tabs(db);
        tabbedPane.addTab(new Teacher(db));
        add(tabbedPane);
        addMenu(tabbedPane.menu());
        repaint();
        revalidate();
        tabbedPane.addChangeListener(e -> {
            Tab tab = tabbedPane.getSelectedTab();
            setStatusText(tab == null ? "no tab" : tab.getTitle());
        });
        tabbedPane.setSelectedIndex(0);
    }

    public void close() {
        if (tabbedPane != null) {
            removeMenu(tabbedPane.menu);
            remove(tabbedPane);
            tabbedPane = null;
            repaint();
            revalidate();
        }
    }

    @Override
    public void fileCreate(FileManagerEvent e) throws Exception {
        close();
        new Thread() {
            @Override
            public void run() {
                setStatusText("Работаю...");
                setCursor(new Cursor(Cursor.WAIT_CURSOR));
                try {
                    db = new AppDB();
                    open();
                } finally {
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    setStatusText("Готово");
                }
            }
        }.start();
    }

    @Override
    public void fileOpen(FileManagerEvent e) {
        close();
        new Thread() {
            @Override
            public void run() {
                try {
                    setStatusText("Работаю...");
                    setCursor(new Cursor(Cursor.WAIT_CURSOR));
                    db = new AppDB(e.file);
                    open();
                } catch (Exception h) {
                    System.err.println(h.getMessage());
                } finally {
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    setStatusText("Готово");
                }
            }
        }.start();
    }

    @Override
    public void fileSave(FileManagerEvent e) throws Exception {
        if (db != null) {
        }
    }

    @Override
    public void fileClose(FileManagerEvent e) throws Exception {
        close();
        if (db != null) {
            db.close();
            db = null;
            setStatusText("no data");
        }
    }

    @Override
    public void fileRename(FileManagerEvent e) {
        if (db != null) {
            db.file = e.file;
        }
    }
    public static final String CMD1 = "cmd1";
    public static final String CMD2 = "cmd2";
    public static final String CMD3 = "cmd3";
    public static final String CMD4 = "cmd4";

    @Override
    public void doCommand(String command) {
        try {
            switch (command) {
                case CMD1:
                    BaseDialog baseDialog = new BaseDialog(new JScrollPane(new JList(new String[]{"819", "819", "819", "819", "819", "819", "819", "819", "819", "819", "819"})));
                    int result = baseDialog.showModal(getParent());
                    showMessage("result " + result);
                    baseDialog.dispose();
                    break;
                case CMD2:
                    ValuesPanel valuesPanel = new ValuesPanel(db.table("r1").columns);
                    valuesPanel.valuesPanelListener = e -> {
                        e.getValues().print();
                    };
                    valuesPanel.showModal(getParent());
                    break;
                case CMD3:
                    Recordset recordset = new Recordset(new String[]{"column1", "column2", "column3", "column4"});
                    recordset.add(new Object[]{1, 2, 3, 4});
                    recordset.add(new Object[]{1, 2, 3, 4});
                    recordset.add(new Object[]{1, 2, 3, 4});
                    recordset.add(new Object[]{1, 2, 3, 4});
                    recordset.print();
                    Grid grid = new Grid(recordset);
                    add(new JScrollPane(grid));
                    repaint();
                    revalidate();
                    break;
                case CMD4:
                    throw new Exception("unsupported yet");
            }
        } catch (Exception e) {
            showMessage(e);
        }
    }

    @Override
    public boolean updateCommand(String command) {
        return true;
    }
    FileManager fileManager = new FileManager(this, this);
    CommandManager commandManager = new CommandManager(this, CMD1, CMD2, CMD3, CMD4);

    @Override
    public void run() {
        super.run();
        try {
            fileManager.open();
        } catch (Exception e) {
            System.err.println("run :" + e.getMessage());
        }
    }

    public App() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(800, 500));
        addStatusBar();
        addCommandBar(commandManager);
        addMenu(fileManager.menu());
        commandManager.update();
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater( new App());
    }
    
}
