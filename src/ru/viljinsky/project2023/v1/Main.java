package ru.viljinsky.project2023.v1;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import ru.viljinsky.project2023.Base;
import ru.viljinsky.project2023.CommandListener;
import ru.viljinsky.project2023.CommandManager;
import ru.viljinsky.project2023.DB;
import ru.viljinsky.project2023.Grid;
import ru.viljinsky.project2023.GridAdapter;
import ru.viljinsky.project2023.GridModel;
import ru.viljinsky.project2023.Recordset;
import ru.viljinsky.project2023.Values;
import ru.viljinsky.project2023.ValuesPanel;

interface DataModel {

    public static final String DAY_ID = "day_id";
    public static final String BELL_ID = "bell_id";
    public static final String SHIFT_ID = "shift_id";
    public static final String SHIFT_TYPE_ID = "shift_type_id";
    public static final String PARE_ID = "pare_id";
    public static final String TIME_BEGIN = "time_begin";
    public static final String TIME_END = "time_end";
}

/**
 *
 * @author viljinsky
 */
class AppDB implements DB, DataModel {

    private static final Map<String, Class<?>> classMap = new HashMap<>();

    static {
        classMap.put(DAY_ID, Integer.class);
        classMap.put(BELL_ID, Integer.class);
        classMap.put(SHIFT_ID, Integer.class);
        classMap.put(PARE_ID, Integer.class);
        classMap.put(PARE_ID, Integer.class);
        classMap.put(SHIFT_TYPE_ID, Integer.class);
    }

    Connection connection;

    public void init() throws Exception {

        StringBuilder stringBuilder = new StringBuilder();
        try (InputStream in = getClass().getResourceAsStream("./test.sql"); InputStreamReader reader = new InputStreamReader(in, "utf-8");) {

            char[] buf = new char[8000];
            int count;
            while ((count = reader.read(buf)) > 0) {
                stringBuilder.append(buf, 0, count);
            }
        }

        try (Statement statement = connection.createStatement()) {

            for (String s : stringBuilder.toString().split(";")) {
                s = s.replaceAll("--.*", "");
                if (s.trim().isEmpty()) {
                    continue;
                }
                statement.execute(s);

            }

        }
        connection.commit();

    }

    public AppDB() throws Exception {
        Class.forName("org.sqlite.JDBC");
    }

    @Override
    public String[] primary(String tableName) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void open() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite::memory:");
            connection.setAutoCommit(false);
        } catch (Exception e) {
            throw new RuntimeException("open : " + e.getMessage());
        }
    }

    @Override
    public Iterable<Recordset> tables() {
        List list = new ArrayList();
        try {
            DatabaseMetaData meta = connection.getMetaData();
            ResultSet cur = meta.getTables(null, null, "%", new String[]{"table"});
            while (cur.next()) {
                list.add(table(cur.getString("table_name")));
            }

        } catch (Exception e) {
        }
        return list;
    }

    @Override
    public Recordset table(String tableName) {

        try (Statement statement = connection.createStatement();) {

            ResultSet cur = statement.executeQuery("select * from " + tableName);
            ResultSetMetaData meta = cur.getMetaData();

            String[] columns = new String[meta.getColumnCount()];
            for (int i = 0; i < columns.length; i++) {
                columns[i] = meta.getColumnName(i + 1);
            }

            Recordset recordset = new Recordset(tableName, columns);
            for (int i = 0; i < columns.length; i++) {
                if (classMap.containsKey(recordset.columnName(i))) {
                    recordset.classMap.put(i, classMap.get(recordset.columnName(i)));
                }
            }

            while (cur.next()) {
                Object[] data = new Object[columns.length];
                for (int i = 0; i < data.length; i++) {
                    data[i] = cur.getString(i + 1);
                }
                recordset.add(data);
            }
            return recordset;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Recordset query(String sql, Object... args) {
        try (PreparedStatement statement = connection.prepareStatement(sql);) {
            for (int i = 0; i < args.length; i++) {
                statement.setObject(i + 1, args[i]);
            }

            ResultSet cur = statement.executeQuery();
            ResultSetMetaData meta = cur.getMetaData();
            String[] columns = new String[meta.getColumnCount()];
            for (int i = 0; i < columns.length; i++) {
                columns[i] = meta.getColumnName(i + 1);
            }
            Recordset result = new Recordset(columns);
            while (cur.next()) {
                Object[] data = new Object[columns.length];
                for (int i = 0; i < columns.length; i++) {
                    data[i] = cur.getObject(i + 1);
                }
                result.add(data);
            }
            return result;
        } catch (Exception e) {
            throw new RuntimeException("Query: \n" + sql + " \n" + Arrays.toString(args) + "\n" + e.getMessage());
        }
    }
    
    boolean updatePanding = false;

    @Override
    public void execute(String sql, Object... args) {
        try (PreparedStatement statement = connection.prepareStatement(sql);) {
            for (int i = 0; i < args.length; i++) {
                statement.setObject(i + 1, args[i]);
            }
           
            int update = statement.executeUpdate();
            
            updatePanding = updatePanding || (update>0);
            
            System.out.println("updatePanding "+update+" "+updatePanding);
        } catch (Exception e) {
            throw new RuntimeException("Execute: \n" + sql + " \n" + Arrays.toString(args) + "\n" + e.getMessage());
        }
    }

    @Override
    public void commit() {
        try {
            connection.commit();
        } catch (Exception e) {
            throw new RuntimeException("commit : " + e.getMessage());
        }
    }

    @Override
    public void rallback() {
        try {
            connection.rollback();
        } catch (Exception e) {
            throw new RuntimeException("rallback : " + e.getMessage());
        }
    }

    @Override
    public void close() throws Exception {
        if (connection != null) {

            connection.close();
            connection = null;
        }
    }
}

class TabsInner extends Base implements CommandListener {

    String tableName = "day_list";
    String[] primary = {"day_id"};
    String sql_insert = "insert into day_list (day_id,day_name) values(?,?)";
    String[] sql_insert_args = {"day_id", "day_name"};
    String sql_update = "update day_list set day_name=? where day_id=?";
    String[] sql_update_args = {"day_name", "day_id"};
    String sql_delete = "delete from day_list where day_id=?";
    String[] sql_delete_args = {"day_id"};

    Grid grid = new Grid();

    DB db;

    Integer auto_increment() {
        Recordset tmp = db.query("select seq from sqlite_sequence where name = ? ", tableName);
        if (!tmp.isEmpty()) {
            return (Integer) tmp.get(0)[0] + 1;
        }
        return null;
    }

    Values defaultValues() {
        Values values = new Values();
        values.put("day_name", tableName);
        return values;
    }

    Values selectedValues() {
        return grid.getSelectedValues();
    }

    public void append() throws Exception {
        GridModel model = (GridModel) grid.getModel();
        Recordset recordset = model.getRecordset();
        ValuesPanel valuesPanel = new ValuesPanel(recordset);
        valuesPanel.setValues(defaultValues());
        valuesPanel.valuesPanelListener = e -> {
            if (e.values.isNull("day_id")) {
                e.values.put("day_id", auto_increment());
            }
            db.execute(sql_insert, e.values.toArray(sql_insert_args));
//            db.commit();
            grid.setRecordset(db.table(tableName));
        };
        valuesPanel.showModal(getParent());
    }

    public void edit() throws Exception {
        GridModel model = (GridModel) grid.getModel();
        Recordset recordset = model.getRecordset();
        ValuesPanel valuesPanel = new ValuesPanel(recordset);
        valuesPanel.setValues(selectedValues());
        valuesPanel.valuesPanelListener = e -> {
            db.execute(sql_update, e.values.toArray(sql_update_args));
//            db.commit();
            grid.setRecordset(db.table(tableName));

        };
        valuesPanel.showModal(getParent());
    }

    public void delete() throws Exception {
        Values values = selectedValues();
        db.execute(sql_delete, values.toArray(sql_delete_args));
//        db.commit();
        grid.setRecordset(db.table(tableName));
    }

    @Override
    public void doCommand(String command) {
        try {
            switch (command) {
                case "add":
                    append();
                    break;
                case "edit":
                    edit();
                    break;
                case "delete":
                    delete();
                    break;
                default:
                    throw new IllegalArgumentException("command " + command + " not defined yet");
            }
        } catch (Exception e) {
            showMessage(e);
        }
    }

    @Override
    public boolean updateCommand(String command) {
        return true;
    }

    CommandManager commandManager = new CommandManager(this, "add", "edit", "delete");

    GridAdapter gridAdapter = new GridAdapter(commandManager, new String[]{"add", "edit", "delete"});

    public TabsInner(DB db, Recordset recordset) {
        setLayout(new BorderLayout());
        add(new JScrollPane(grid));
        this.db = db;
        grid.setRecordset(recordset);
        grid.setGridAdapter(gridAdapter);
        grid.setCommand(commandManager);
    }

}

class Tabs extends JTabbedPane {

    AppDB db;

    public Tabs(AppDB db) {
        this.db = db;
        for (Recordset recordset : db.tables()) {
            addTab(recordset.getName(), new TabsInner(db, recordset));
        }
    }

}

class App extends Base {

    AppDB db;

    Tabs tabs;

    public App() {
        super("App v.1");
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(800, 600));
    }

    @Override
    public void close() {
        if (tabs != null) {
            remove(tabs);
            tabs = null;
        }
    }

    @Override
    public void open() {
        tabs = new Tabs(db);
        add(tabs);
        repaint();
        revalidate();
    }

    @Override
    public void windowClosed(WindowEvent e) {
        if (db != null) {
            try {

                Connection con = db.connection;
                Statement statement = con.createStatement();
                statement.executeUpdate("backup to backup.db");

                db.close();
                db = null;
            } catch (Exception h) {
                showMessage(h);
            }
        }
    }

    @Override
    public void windowOpened(WindowEvent e) {
        try {
            db = new AppDB();
            db.open();

            File file = new File("backup.db");
            if (file.exists()) {
                Statement statement = db.connection.createStatement();
                statement.executeUpdate("restore from " + file.getAbsolutePath());
            } else {
                db.init();
            }
            open();

        } catch (Exception h) {
            showMessage(h);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new App());

    }
}
