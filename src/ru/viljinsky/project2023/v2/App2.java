package ru.viljinsky.project2023.v2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import ru.viljinsky.project2023.Base;
import ru.viljinsky.project2023.CommandListener;
import ru.viljinsky.project2023.CommandManager;
import ru.viljinsky.project2023.DB;
import ru.viljinsky.project2023.Grid;
import ru.viljinsky.project2023.GridAdapter;
import ru.viljinsky.project2023.Recordset;
import ru.viljinsky.project2023.Values;
import ru.viljinsky.project2023.ValuesPanel;
import sun.swing.table.DefaultTableCellHeaderRenderer;

interface DataModel {

    public static final String SORT_ORDER = "sort_order";
    public static final String SKILL_ID = "skill_id";
    public static final String CURRICULUM_ID = "curriculum_id";
    public static final String DEPART_ID = "depart_id";
    public static final String GROUP_ID = "group_id";
    public static final String SHIFT_ID = "shift_id";
    public static final String PROFILE_ID = "profile_id";
    public static final String DAY_ID = "day_id";
    public static final String BELL_ID = "bell_id";
    public static final String TEACHER_ID = "teacher_id";
    public static final String ROOM_ID = "room_id";
    public static final String SUBJECT_ID = "subject_id";
    public static final String SHIFT_TYPE_ID = "shift_type_id";
    public static final String PROFILE_TYPE_ID = "profile_type_id";
    public static final String WEEK_ID = "week_id";
    public static final String GROUP_TYPE_ID = "group_type_id";
    public static final String HOUR_PER_WEEK = "hour_per_week";
    public static final String HOUR_PER_DAY = "hour_per_day";
    public static final String DIFFICULTY = "difficulty";
    public static final String STREAM_ID = "stream_id";
    public static final String PUPIL_COUNT = "pupil_count";
    public static final String BOY_COUNT = "boy_count";
    public static final String GIRL_COUNT = "girl_count";
    public static final String LESSON_TYPE_ID = "lesson_type_id";
    public static final String GROUP_SEQUENCE_ID = "group_sequence_id";
    public static final String SUBJECT_DOMAIN_ID = "subject_domain_id";
    public static final String SCHEDULE_STATE_ID = "schedule_state_id";
    public static final String BUILDING_ID = "building_id";

    public static final String ENABLE = "enable";
    public static final String MULTY = "multy";
    public static final String READY = "ready";

    public static final Set<String> BOOLEAN = new HashSet(Arrays.asList(ENABLE, MULTY, READY));

    public static final Set<String> INTEGER = new HashSet(Arrays.asList(
            SKILL_ID, CURRICULUM_ID, DEPART_ID, GROUP_ID, TEACHER_ID, ROOM_ID, DAY_ID, BELL_ID, SUBJECT_ID, SHIFT_TYPE_ID, PROFILE_TYPE_ID, WEEK_ID,
            GROUP_TYPE_ID, SHIFT_ID, PROFILE_ID, HOUR_PER_DAY, HOUR_PER_WEEK, DIFFICULTY, STREAM_ID, BOY_COUNT, GIRL_COUNT, PUPIL_COUNT,
            LESSON_TYPE_ID, GROUP_SEQUENCE_ID, SORT_ORDER, SUBJECT_DOMAIN_ID, SCHEDULE_STATE_ID, BUILDING_ID
    ));
}

class RecordsetMeta{
    
    public String tableName;
    public String auto_inc;
    public String sql_insert;
    public String sql_insrt_args;
    
    public String sql_update;
    public String sql_update_args;
    
    public String sql_delete;
    public String sql_delete_args;

    public RecordsetMeta(String tableName) {
        this.tableName = tableName;
    }
    
    
    
}

class TabGrid extends Base implements CommandListener, ListSelectionListener {

    public static final String INSERT = "insert";
    public static final String UPDATE = "update";
    public static final String DELETE = "delete";
    public static final String RELOAD = "reload";

    String[] commands = {INSERT, UPDATE, DELETE,RELOAD};

    Grid grid = new Grid();

    Recordset recordset;

    DB db;

    CommandManager commandManager = new CommandManager(this, commands);

    GridAdapter gridAdapter = new GridAdapter(commandManager, commands);

    @Override
    public String getTitle() {
        return recordset.getName();
    }

    Values selectedValues() {
        return grid.getSelectedValues();
    }

    Values defaultValues() {
        Values values = new Values();
        for(String s:defs){
            values.put(s, s);
        }
        return values;
    }
    
    String tableName = "skill";

    String auto_inc = "skill_id";
    
    String[] defs = {"skill_name"};
    
    String sql_update = "update skill set skill_name=? where skill_id=?";
    String[] sql_update_args = {"skill_name","skill_id"};
    
    String sql_insert = "insert into skill (skill_id,skill_name) values (?,?)";
    String[] sql_insert_args = {"skill_id","skill_name"};
    
    String sql_delete = "delete from skill where skill_id=?";
    String[] sql_delete_args = {"skill_id"}; 

    void reload(){
        recordset = db.table(tableName);
        grid.setRecordset(recordset);
        
    }
    
    int last_value(String tableName){
        
        Recordset tmp = db.query("select seq from sqlite_sequence where name =?", new String[]{tableName});
        tmp.print();
        
        return tmp.isEmpty()?0:(Integer)tmp.get(0)[0];
    }
    
    void insert() throws Exception {
        ValuesPanel valuesPanel = new ValuesPanel(recordset);
        valuesPanel.valuesPanelListener = e -> {
            try{
                db.open();
                e.values.put(auto_inc, last_value(tableName)+1);
                db.execute(sql_insert, e.values.toArray(sql_insert_args));
                reload();
                db.close();
            } catch (Exception h){
                throw new RuntimeException(h);
            }
        };
        valuesPanel.setValues(defaultValues());
        valuesPanel.setTitle(getTitle());
        valuesPanel.showModal(getParent());
    }
    
    void update() throws Exception {
        ValuesPanel valuesPanel = new ValuesPanel(recordset);
        valuesPanel.valuesPanelListener = e -> {
            
            try{
                db.open();
                db.execute(sql_update,e.values.toArray(sql_update_args));
                reload();
                db.close();
            } catch (Exception h){
                throw new RuntimeException(h);
            }
            
            
            e.values.print();
        };
        valuesPanel.setValues(selectedValues());
        valuesPanel.setTitle(getTitle());
        valuesPanel.showModal(getParent());
    }

    void delete() throws Exception {
        if (confirm("delete from " + getTitle() + "?")) {
            Values values = selectedValues();
            try{
                db.open();
                db.execute(sql_delete, values.getValues(sql_delete_args));
                reload();
                db.close();
            } catch (Exception e){
                showMessage(e);
            }            
        }
    }

    @Override
    public void doCommand(String command) {
        try {
            switch (command) {
                case INSERT:
                    insert();
                    break;
                case UPDATE:
                    update();
                    break;
                case DELETE:
                    delete();
                    break;
                case RELOAD:
                    db.open();
                    reload();
                    db.close();
                    break;
            }
        } catch (Exception e) {
            showMessage(e);
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        commandManager.update();
    }

    @Override
    public boolean updateCommand(String command) {
        switch (command) {
            case INSERT:
            case RELOAD:
                return true;
            case UPDATE:
                return grid.getSelectedRowCount() > 0;
            case DELETE:
                return grid.getSelectedRowCount() > 0;
            default:
                return false;
        }
    }

    @Override
    public void open() {
        super.open();
        grid.setRecordset(recordset);
        commandManager.update();
    }

    TableCellRenderer renderer = new DefaultTableCellRenderer() {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
            setHorizontalAlignment(JLabel.CENTER);
            setBackground(Color.red);
            return this;
        }

    };

    TableCellRenderer renderer2 = new DefaultTableCellHeaderRenderer() {
        @Override
        public Component getTableCellRendererComponent(JTable jtable, Object o, boolean bln, boolean bln1, int i, int i1) {
            super.getTableCellRendererComponent(jtable, o, bln, bln1, i, i1); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
            setText("*" + String.valueOf(o));
            setBackground(Color.yellow);
            return this;
        }

    };

    public TabGrid(DB db, Recordset recordset) {
        super(recordset.getName());
        this.recordset = recordset;
        this.db = db;
        setLayout(new BorderLayout());
        add(new JScrollPane(grid));
        addCommandBar(commandManager);
        grid.getSelectionModel().addListSelectionListener(this);
        grid.setGridAdapter(gridAdapter);
        grid.setCommand(commandManager);
        grid.setFocusable(true);
        grid.setDefaultRenderer(Integer.class, renderer);
        grid.setDefaultRenderer(Boolean.class, renderer2);
    }

}

class Tabs2 extends JTabbedPane {

    DB db;

    public Tabs2(DB db) {
        for (Recordset recordset : db.tables()) {
            TabGrid grid = new TabGrid(db, recordset);
            grid.open();
            addTab(recordset.getName(), grid);
        }
    }

    public void setSelected(String tabName) {
        for (int i = 0; i < getTabCount(); i++) {
            TabGrid tab = (TabGrid) getComponentAt(i);
            if (tab.recordset.getName().equals(tabName)) {
                setSelectedIndex(i);
                tab.grid.requestFocus();
                break;
            }
        }
    }

}

class AppDB2 implements DB {

    File file = new File("~tmp.sqlite");

    Connection connection;

    public AppDB2() throws Exception {
        Class.forName("org.sqlite.JDBC");
    }

    public AppDB2(File file) throws Exception {
        Class.forName("org.sqlite.JDBC");
        this.file = file;
    }

    @Override
    public String[] primary(String tableName) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    void create() {
        try (InputStream in = getClass().getResourceAsStream("./table.sql"); InputStreamReader reader = new InputStreamReader(in, "utf-8");) {

            StringBuilder stringBuilder = new StringBuilder();
            char[] buf = new char[8000];
            int count;
            while ((count = reader.read(buf)) > 0) {
                stringBuilder.append(buf, 0, count);
            }

            try (Statement statement = connection.createStatement();) {
                for (String str : stringBuilder.toString().replaceAll("--.*", "").split(";")) {
                    if (str.trim().isEmpty()) {
                        continue;
                    }
                    statement.execute(str);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void open() {
        try {
            updatePending = false;
            connection = DriverManager.getConnection("jdbc:sqlite::memory:");
            if (file == null || !file.exists()) {
                create();
            } else if (file.exists()) {
                Statement statement = connection.createStatement();
                statement.executeUpdate("restore from " + file.getAbsolutePath());
            }

        } catch (Exception h) {
            throw new RuntimeException("open : " + h.getMessage());
        }
    }

    @Override
    public Iterable<Recordset> tables() {
        try {
            List list = new ArrayList();
            DatabaseMetaData meta = connection.getMetaData();
            ResultSet cur = meta.getTables(null, null, "%", new String[]{"table"});
            while (cur.next()) {
                list.add(table(cur.getString("table_name")));
            }
            return list;

        } catch (Exception e) {
            throw new RuntimeException("tables : " + e.getMessage());
        }
    }

    @Override
    public Recordset table(String tableName) {
        try (Statement statement = connection.createStatement();) {
            ResultSet cur = statement.executeQuery("select * from " + tableName);
            ResultSetMetaData meta = cur.getMetaData();
            String[] columns = new String[meta.getColumnCount()];
            Map<Integer, Class<?>> map = new HashMap<>();
            for (int i = 0; i < columns.length; i++) {
                columns[i] = meta.getColumnName(i + 1);

                if (DataModel.INTEGER.contains(columns[i])) {
                    map.put(i, Integer.class);
                }

                if (DataModel.BOOLEAN.contains(columns[i])) {
                    map.put(i, Boolean.class);
                }
            }
            Recordset recordset = new Recordset(tableName, columns);
            recordset.classMap = map;
            while (cur.next()) {
                Object[] data = new Object[columns.length];
                for (int i = 0; i < data.length; i++) {
                    data[i] = cur.getObject(i + 1);
                }
                recordset.add(data);
            }
            return recordset;
        } catch (Exception e) {
            throw new RuntimeException("table : " + tableName + " " + e.getMessage());
        }
    }

    @Override
    public Recordset query(String sql, Object... args) {
        try (PreparedStatement statement = connection.prepareStatement(sql);) {
            for(int i=0;i<args.length;i++){
                statement.setObject(i+1, args[i]);
            }
            ResultSet cur = statement.executeQuery();
            ResultSetMetaData meta = cur.getMetaData();
            String columns[] = new String[meta.getColumnCount()];
            Recordset recordset = new Recordset(columns);
            while (cur.next()) {
                Object[] data = new Object[columns.length];
                for (int i = 0; i < columns.length; i++) {
                    data[i] = cur.getObject(i + 1);
                }
                recordset.add(data);
            }
            return recordset;
        } catch (Exception e) {
            throw new RuntimeException("query : " + sql + "\n" + Arrays.toString(args) + "\n" + e.getMessage());
        }
    }

    boolean updatePending = false;

    @Override
    public void execute(String sql, Object... args) {
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            for (int i = 0; i < args.length; i++) {
                statement.setObject(i + 1, args[i]);
            }
            int up = statement.executeUpdate();
            updatePending = updatePending || (up > 0);
        } catch (Exception e) {
            throw new RuntimeException("execute : " + sql + " \n" + Arrays.toString(args) + "\n" + e.getMessage());
        }
    }

    @Override
    public void commit() {
        try {
            connection.commit();
            updatePending = false;
        } catch (Exception e) {
            throw new RuntimeException("commit : " + e.getMessage());
        }
    }

    @Override
    public void rallback() {
        try {
            connection.rollback();
            updatePending = false;
        } catch (Exception e) {
            throw new RuntimeException("rallback : " + e.getMessage());
        }
    }

    @Override
    public void close() throws Exception {
        if (connection != null) {
            
            Statement statement = connection.createStatement();
            statement.executeUpdate("backup to "+file.getAbsolutePath());
            
            connection.close();
            connection = null;
        }
    }
}

/**
 *
 * @author viljinsky
 */
public class App2 extends Base {

    DB db;

    Tabs2 tabs;

    public App2() {
        super("App2");
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(800, 600));
    }

    @Override
    public void open() {
        super.open();
        tabs = new Tabs2(db);
        tabs.setSelected("skill");
        add(tabs);
        repaint();
        revalidate();
    }

    @Override
    public void close() {
        if (tabs != null) {
            remove(tabs);
            tabs = null;
        }
        super.close();
        repaint();
        revalidate();
    }

    @Override
    public void windowClosed(WindowEvent e) {
        if (db != null) {
            try {
                close();
                db.close();
                db = null;
            } catch (Exception h) {
                showMessage(h);
            }
        }
        super.windowClosed(e);
    }

    @Override
    public void windowOpened(WindowEvent e) {
        super.windowOpened(e);
        try {
            File file = new File("test.db");

            db = new AppDB2();

            db.open();
            open();
            db.close();

            setTitle(file.getAbsolutePath());
        } catch (Exception h) {
            showMessage(h);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new App2());
    }

}
