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
import ru.viljinsky.project2023.CommandBar;
import ru.viljinsky.project2023.CommandListener;
import ru.viljinsky.project2023.CommandManager;
import ru.viljinsky.project2023.DB;
import ru.viljinsky.project2023.FileManager;
import ru.viljinsky.project2023.FileManagerEvent;
import ru.viljinsky.project2023.FileManagerListener;
import ru.viljinsky.project2023.Grid;
import ru.viljinsky.project2023.GridAdapter;
import ru.viljinsky.project2023.Recordset;
import ru.viljinsky.project2023.Values;
import ru.viljinsky.project2023.ValuesPanel;
import sun.swing.table.DefaultTableCellHeaderRenderer;

interface DataModel {
    
    public static final String PARE = "pare";
    public static final String PARE_NAME = "pare_name";
    public static final String PARE_DETAIL = "pare_detail";
    
    
    public static final String LESSON_TYPE = "lesson_type";
    public static final String LESSON_TYPE_NAME = "lesson_type_name";

    public static final String ATTR = "attr";
    public static final String PARAM_NAME = "param_name";
    public static final String PARAM_VALUE ="param_value";
    
    public static final String BELL_LIST = "bell_list";
    public static final String SKILL = "skill";
    public static final String SKILL_NAME = "skill_name";
    public static final String DAY_LIST = "day_list";
    public static final String DAY_NAME = "day_name";
    public static final String DAY_SHORT_NAME = "day_short_name";
    public static final String BUILDING = "building";
    public static final String BUILDING_NAME = "building_name";
    
    public static final String SHIFT = "shift";
    public static final String SHIFT_NAME = "shift_name";
    
    public static final String SHIFT_TYPE = "shift_type";
    public static final String SHIFT_TYPE_NAME = "shift_type_name";
    
    public static final String DEPART = "depart";

    public static final String PARE_ID = "pare_id";
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

    public static final String TIME_START = "time_start";
    public static final String TIME_END = "time_end";

    public static final Set<String> BOOLEAN = new HashSet(Arrays.asList(ENABLE, MULTY, READY));

    public static final Set<String> INTEGER = new HashSet(Arrays.asList(
            SKILL_ID, CURRICULUM_ID, DEPART_ID, GROUP_ID, TEACHER_ID, ROOM_ID, DAY_ID, BELL_ID, SUBJECT_ID, SHIFT_TYPE_ID, PROFILE_TYPE_ID, WEEK_ID,
            GROUP_TYPE_ID, SHIFT_ID, PROFILE_ID, HOUR_PER_DAY, HOUR_PER_WEEK, DIFFICULTY, STREAM_ID, BOY_COUNT, GIRL_COUNT, PUPIL_COUNT,
            LESSON_TYPE_ID, GROUP_SEQUENCE_ID, SORT_ORDER, SUBJECT_DOMAIN_ID, SCHEDULE_STATE_ID, BUILDING_ID,PARE_ID
    ));

    public static final Set<String> TIME = new HashSet(Arrays.asList(TIME_START, TIME_END));
}

class RecordsetMeta implements DataModel {

    public String[] defs;
    public String tableName;
    public String auto_inc;
    public String sql_insert;
    public String[] sql_insert_args;

    public String sql_update;
    public String[] sql_update_args;

    public String sql_delete;
    public String[] sql_delete_args;

    static final Object[][] data = {
        {PARE_DETAIL,null,null,
            "insert into pare_detail (pare_id,day_id,bell_id) values(?,?,?)",new String[]{PARE_ID,DAY_ID,BELL_ID},
            "",new String[]{},
            "delete from pare_detail where pare_id=? and day_id=? and bell_id=?",new String[]{PARE_ID,DAY_ID,BELL_ID}
        },
        {PARE,PARE_ID,new String[]{PARE_NAME},
            "insert into pare (pare_id,pare_name) values (?,?)",new String[]{PARE_ID,PARE_NAME},
            "update pare set pare_name=? where pare_id=?",new String[]{PARE_ID},
            "delete from pare where pare_id=?",new String[]{PARE_ID}
        },
        {LESSON_TYPE,null,null,
            "insert into lesson_type(lesson_type_id,lesson_type_name) values(?,?)",new String[]{LESSON_TYPE_ID,LESSON_TYPE_NAME},
            "update lesson_type set lesson_type_name=? where lesson_type_id=?",new String[]{LESSON_TYPE_NAME,LESSON_TYPE_ID},
            "delete from lesson_type where lesson_type_id=?",new String[]{LESSON_TYPE_ID}
        },
        {ATTR,null,null,
            "insert into attr (param_name,param_value) values(?,?)",new String[]{PARAM_NAME,PARAM_VALUE},
            "update attr set param_value=? where param_name=?",new String[]{PARAM_VALUE,PARAM_NAME},
            "delete from attr where param_name=?",new String[]{PARAM_NAME},
        },
        {SHIFT,SHIFT_ID,new String[]{SHIFT_NAME},
                "insert into shift(shift_id,shift_name,shift_type_id) values (?,?,?)",new String[]{SHIFT_ID,SHIFT_NAME,SHIFT_TYPE_ID},
                "update shift set shift_name=?,shift_type_id=? where shift_id=?",new String[]{SHIFT_NAME,SHIFT_TYPE_ID,SHIFT_ID},
                "delete from shift where shift_id=?",new String[]{SHIFT_ID}
        },
        {SHIFT_TYPE,SHIFT_TYPE_ID,new String[]{SHIFT_TYPE_NAME},
            "insert into shift_type (shift_type_id,shift_type_name,shift_id) values(?,?,?)",new String[]{SHIFT_TYPE_ID,SHIFT_TYPE_NAME,SHIFT_ID},
            "update shift_type set shift_type_name=?, shift_id=? where shift_type_id=?",new String[]{SHIFT_TYPE_NAME,SHIFT_ID,SHIFT_TYPE_ID},
            "delete from shift_type where shift_type_id=?",new String[]{SHIFT_TYPE_ID},
        },
        
        {DEPART,DEPART_ID,
            null,
            null,
            null,
            null,
            null,
            "delete from depart where depart_id=?", new String[]{DEPART_ID},
        },
        {BELL_LIST,
            BELL_ID,
            new String[]{TIME_START, TIME_END},
            "insert into bell_list (bell_id,time_start,time_end) values (?,?,?)", new String[]{BELL_ID, TIME_START, TIME_END},
            "update bell_list set time_start=?,time_end=? where bell_id=?", new String[]{TIME_START, TIME_END, BELL_ID},
            "delete from bell_list where bell_id=?",new String[]{BELL_ID}

        },
        {DAY_LIST,
            DAY_ID,
            new String[]{DAY_NAME, DAY_SHORT_NAME},
            "insert into day_list(day_id,day_name,day_short_name) values (?,?,?)",
            new String[]{DAY_ID, DAY_NAME, DAY_SHORT_NAME},
            "update day_list set day_name=?,day_short_name=? where day_id=?",
            new String[]{DAY_NAME,DAY_SHORT_NAME,DAY_ID},
            "delete from day_list where day_id=?",
            new String[]{DAY_ID}
        },
        {BUILDING,
            BUILDING_ID,
            new String[]{BUILDING_NAME},
            "insert into building (building_id,building_name) values(?,?)",
            new String[]{BUILDING_ID, BUILDING_NAME},
            "update building set building_name = ? where building_id=?",
            new String[]{BUILDING_NAME, BUILDING_ID},
            "delete from building where building_id=?",
            new String[]{BUILDING_ID}

        },
        {SKILL,
            SKILL_ID,
            new String[]{SKILL_NAME},
            "insert into skill (skill_id,skill_name) values(?,?)",
            new String[]{SKILL_ID, SKILL_NAME},
            "update skill set skill_name=? where skill_id=?",
            new String[]{SKILL_NAME, SKILL_ID},
            "delete from skill where skill_id=?",
            new String[]{SKILL_ID}

        }

    };

    public RecordsetMeta(String tableName) {
        this.tableName = tableName;
        for (Object[] p : data) {
            if (p[0].equals(tableName)) {
                auto_inc = (String) p[1];
                defs = (String[]) p[2];
                sql_insert = (String) p[3];
                sql_insert_args = (String[]) p[4];
                sql_update = (String) p[5];
                sql_update_args = (String[]) p[6];
                sql_delete = (String) p[7];
                sql_delete_args = (String[]) p[8];
                break;
            }
        }
    }

}

class TabGrid extends Base implements CommandListener, ListSelectionListener {

    public static final String INSERT = "insert";
    public static final String UPDATE = "update";
    public static final String DELETE = "delete";
    public static final String RELOAD = "reload";

    String[] commands = {INSERT, UPDATE, DELETE, RELOAD};

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
        if (meta.defs != null) {
            for (String s : meta.defs) {
                String name = s;
                int n = 0;
                while (!db.query("select * from "+recordset.getName()+" where "+s+" = '"+name+"'").isEmpty()){
                    name = s+ " "+(n++);
                };
                
                values.put(s, name);
            }
        }
        return values;
    }

    RecordsetMeta meta;

    void reload() {
        recordset = db.table(meta.tableName);
        grid.setRecordset(recordset);

    }

    int last_value(String tableName) {

        Recordset tmp = db.query("select seq from sqlite_sequence where name =?", new String[]{tableName});
        tmp.print();

        return tmp.isEmpty() ? 0 : (Integer) tmp.get(0)[0];
    }

    void insert() throws Exception {
        ValuesPanel valuesPanel = new ValuesPanel(recordset);
        valuesPanel.valuesPanelListener = e -> {
            try {
                if (e.values.isNull(meta.auto_inc)){
                    e.values.put(meta.auto_inc, last_value(meta.tableName) + 1);
                }
                db.execute(meta.sql_insert, e.values.toArray(meta.sql_insert_args));
                reload();
            } catch (NullPointerException h) {
                throw new RuntimeException("meta undefined");
            } catch (Exception h) {
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

            try {
                db.execute(meta.sql_update, e.values.toArray(meta.sql_update_args));
                reload();
            } catch (NullPointerException h) {
                throw new RuntimeException("meta undefined");
            } catch (Exception h) {
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
            try {
                db.execute(meta.sql_delete, values.toArray(meta.sql_delete_args));
                reload();
            } catch (NullPointerException h) {
                throw new RuntimeException("meta undefined");
            } catch (Exception h) {
                throw new RuntimeException(h);
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
                    reload();
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

    TableCellRenderer rendererInteger = new DefaultTableCellRenderer() {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
            setHorizontalAlignment(JLabel.CENTER);
            setBackground(Color.red);
            return this;
        }

    };

    TableCellRenderer rendererBoolean = new DefaultTableCellHeaderRenderer() {
        @Override
        public Component getTableCellRendererComponent(JTable jtable, Object o, boolean bln, boolean bln1, int i, int i1) {
            super.getTableCellRendererComponent(jtable, o, bln, bln1, i, i1); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
            setText("*" + String.valueOf(o));
            setBackground(Color.yellow);
            return this;
        }

    };

    TableCellRenderer rendererLong = new DefaultTableCellRenderer() {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
            setText(String.valueOf(value) + " long");
            return this;
        }

    };

    public TabGrid(DB db, Recordset recordset) {
        super(recordset.getName());
        this.recordset = recordset;
        this.meta = new RecordsetMeta(recordset.getName());
        this.db = db;
        setLayout(new BorderLayout());
        add(new JScrollPane(grid));
        addCommandBar(commandManager);
        grid.getSelectionModel().addListSelectionListener(this);
        grid.setGridAdapter(gridAdapter);
        grid.setCommand(commandManager);
        grid.setFocusable(true);
        grid.setDefaultRenderer(Integer.class, rendererInteger);
        grid.setDefaultRenderer(Boolean.class, rendererBoolean);
        grid.setDefaultRenderer(Long.class, rendererLong);
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

    File file = null;

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
        try (InputStream in = getClass().getResourceAsStream("../resource/table.sql"); InputStreamReader reader = new InputStreamReader(in, "utf-8");) {
        

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

        } catch (NullPointerException e){
            throw new RuntimeException("resource not found");
        } catch (Exception e) {
            throw new RuntimeException(e.getClass().getSimpleName()+" - "+ e.getMessage());
        }
    }

    @Override
    public void open() {
        Statement statement;
        try {
//            updatePending = false;
            connection = DriverManager.getConnection("jdbc:sqlite::memory:");
            statement = connection.createStatement();

            if (file == null) {
                create();
            } else if (file.exists()) {
                statement.executeUpdate("restore from " + file.getAbsolutePath());
            }
            statement.execute("PRAGMA foreign_keys = ON;");
//
        } catch (Exception h) {
            throw new RuntimeException("open : " + h.getMessage());
        }
    }

    @Override
    public void close() throws Exception {
        if (connection != null) {

//            Statement statement = connection.createStatement();
//            statement.executeUpdate("backup to "+file.getAbsolutePath());
            connection.close();
            connection = null;
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

                if (DataModel.TIME.contains(columns[i])) {
                    map.put(i, Long.class);
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
            for (int i = 0; i < args.length; i++) {
                statement.setObject(i + 1, args[i]);
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

}

/**
 *
 * @author viljinsky
 */
public class App2 extends Base implements FileManagerListener{

    DB db;

    Tabs2 tabs;

    @Override
    public void fileCreate(FileManagerEvent e) throws Exception {
        
        if(db!=null){
            close();
        }
        db = new AppDB2();
        open();
        
    }

    @Override
    public void fileOpen(FileManagerEvent e) throws Exception {
        
        if(db!=null){
            close();
        }
        db = new AppDB2(e.file);
        open();
    }

    @Override
    public void fileSave(FileManagerEvent e) throws Exception {
        
        AppDB2 a = (AppDB2)db;
        try(Statement statement = a.connection.createStatement();){
            statement.executeUpdate("backup to "+e.file.getAbsolutePath());
            showMessage("save"+e.file.getAbsolutePath());
        }
        
    }

    @Override
    public void fileClose(FileManagerEvent e) throws Exception {
        close();
    }

    @Override
    public void fileRename(FileManagerEvent e) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
    
    FileManager fileManager = new FileManager(this, this);

    public App2() {
        super("App2");
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(800, 600));
        add(new CommandBar(fileManager.commandManager),BorderLayout.PAGE_START);
    }

    @Override
    public void open() {
        super.open();
        db.open();
        tabs = new Tabs2(db);
        tabs.setSelected("skill");
        add(tabs);
        repaint();
        revalidate();
    }

    @Override
    public void close() {
        if (db != null) try {
            if (tabs != null) {
                remove(tabs);
                tabs = null;
            }
            db.close();
            db = null;
            super.close();
        } catch (Exception e) {
            showMessage(e);
        }
        repaint();
        revalidate();
    }

    @Override
    public void windowClosed(WindowEvent e) {
        close();
        super.windowClosed(e);
    }

    @Override
    public void windowOpened(WindowEvent e) {
        super.windowOpened(e);
        try {
            File file = new File("test.db");
//            db = new AppDB2(file);
            db = new AppDB2();
            open();
            setTitle(file.getAbsolutePath());
        } catch (Exception h) {
            showMessage(h);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new App2());
    }

}
