package ru.viljinsky.project2023.v0;

import java.awt.BorderLayout;
import java.awt.event.WindowEvent;
import java.io.File;
import javax.swing.JScrollPane;
import ru.viljinsky.project2023.Base;
import ru.viljinsky.project2023.CommandBar;
import ru.viljinsky.project2023.CommandListener;
import ru.viljinsky.project2023.CommandManager;
import ru.viljinsky.project2023.DB;
import ru.viljinsky.project2023.Grid;
import ru.viljinsky.project2023.GridAdapter;
import ru.viljinsky.project2023.GridModel;
import ru.viljinsky.project2023.Recordset;
import ru.viljinsky.project2023.Values;
import ru.viljinsky.project2023.ValuesPanel;

/**
 *
 * @author viljinsky
 */
public class Test extends Base implements CommandListener,DataModel {

    public static final String ADD = "add";
    public static final String EDIT = "edit";
    public static final String DELETE = "delete";

    String[] commands = {ADD, EDIT, DELETE};

    DB db;
    CommandManager commandManager = new CommandManager(this, commands);
    GridAdapter gridAdapter = new GridAdapter(commandManager, commands);
    Grid grid = new Grid();
    //TODO    Grid grid = new Grid(last_name,first_name,shift_name);
    //TODO    Grid grid = new Grid(gridAdapter);

    
    // to grid 
    ValuesPanel valuesPanel(String title){
        GridModel model =(GridModel)grid.getModel();
        Recordset recordset = model.getRecordset();
        ValuesPanel valuesPanel = new ValuesPanel(recordset);
        valuesPanel.setTitle(title);
        return valuesPanel;
    }
    
    Values selectedValues() {
        return grid.getSelectedValues();
    }

    Values defaultValues() {
        Values values = new Values();
        for(String columnName:columns()){
            values.put(columnName, "-1");
        }
        return values;
    }
    
    String[] columns(){
        GridModel model = (GridModel)grid.getModel();
        Recordset recordset = model.getRecordset();
        return recordset.columns;
    }

    //
    
    
    public void add() throws Exception {
        ValuesPanel valuesPanel = valuesPanel(getTitle()+" new record");
        valuesPanel.setValues(defaultValues());
        valuesPanel.valuesPanelListener = e->{
            e.values.print();
        };
        valuesPanel.showModal(getParent());
    }

    public void edit() throws Exception {
        ValuesPanel valuesPanel = valuesPanel(getTitle());
        valuesPanel.setValues(selectedValues());
        valuesPanel.valuesPanelListener = e -> {
            e.values.print();
        };
        valuesPanel.showModal(getParent());
    }

    public void delete() throws Exception {
        if (confirm("delete?")) {
        }
    }

    @Override
    public void doCommand(String command) {
        try {
            switch (command) {
                case ADD:
                    add();
                    break;
                case EDIT:
                    edit();
                    break;
                case DELETE:
                    delete();
                    break;
            }
        } catch (Exception e) {
            showMessage(e);
        }
    }

    @Override
    public boolean updateCommand(String command) {
        return true;
    }

    @Override
    public void open() {
        try {
            db.open();
            grid.setRecordset(db.query("select * from teacher inner join shift using (shift_id)"));
//            grid.setRecordset(db.table(TEACHER));
            db.close();
        } catch (Exception e) {
            showMessage(e);
        }
    }

    @Override
    public void windowOpened(WindowEvent e) {
        try {
            db = new AppDB(new File("test.db"));
            open();
        } catch (Exception h) {
            showMessage(h);
        }
    }

    public Test() {
        setLayout(new BorderLayout());
        add(new JScrollPane(grid));
        add(new CommandBar(commandManager), BorderLayout.PAGE_START);
        addStatusBar();
        setTitle(TEACHER);
        grid.setGridAdapter(gridAdapter);
    }

    public static void main(String[] args) {
        new Test().run();
    }

}
