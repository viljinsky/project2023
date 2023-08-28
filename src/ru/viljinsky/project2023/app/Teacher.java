package ru.viljinsky.project2023.app;

import ru.viljinsky.project2023.Tab;
import java.awt.event.WindowEvent;
import javax.swing.JScrollPane;
import ru.viljinsky.project2023.CommandManager;
import ru.viljinsky.project2023.DB;
import ru.viljinsky.project2023.Grid;
import ru.viljinsky.project2023.GridAdapter;
import ru.viljinsky.project2023.Recordset;
import ru.viljinsky.project2023.Values;
import ru.viljinsky.project2023.ValuesPanel;


/**
 *
 * @author viljinsky
 */
public class Teacher extends Tab {

    public static final String ADD = "add";
    public static final String EDIT = "edit";
    public static final String DELETE = "delete";
    String[] commands = {ADD, EDIT, DELETE, "upps"};
    
    Grid grid = new Grid(new Recordset());
    CommandManager commandManager = new CommandManager(this, commands);
    
    Values defaultValues(){
        Values values = new Values();
        for(String column:db.table("r1").columns){
            values.put(column, column);
        }
        return values;
    }
    
    Values selectedValues(){
        return grid.getSelectedValues();
    }

    @Override
    public void doCommand(String command) {
        ValuesPanel valuesPanel;
        Values values;
        try {
            switch (command) {
                case ADD:
                    valuesPanel = new ValuesPanel(db.table("r1").columns);
                    valuesPanel.setValues(defaultValues());
                    valuesPanel.valuesPanelListener = e -> {
                        Values v  = e.values;
                        if (db.table("r1").indexOf(v.values(new String[]{"column1"}))>=0){
                            throw new RuntimeException("primary key exception");
                        }
                        db.table("r1").append(v);
                        int i = db.table("r1").indexOf(v);
                        grid.reload();
                        grid.getSelectionModel().setSelectionInterval(i,i);
                        grid.requestFocus();
                        
                    };
                    valuesPanel.showModal(getParent());
                    break;
                    
                case EDIT:
                    valuesPanel = new ValuesPanel(db.table("r1").columns);
                    values = selectedValues();
                    valuesPanel.setValues(values);
                    valuesPanel.valuesPanelListener = e -> {
                        db.table("r1").update(grid.getSelectedRow(),e.values);
                        grid.reload();
                        grid.requestFocus();
                    };
                    valuesPanel.showModal(getParent());
                    break;
                    
                case DELETE:
                    db.table("r1").delete(grid.getSelectedRow());
                    grid.reload();
                    grid.requestFocus();
                    break;
                default:
                    throw new Exception("unsupported eyt");
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
    public void windowOpened(WindowEvent e) {
        super.windowOpened(e); 
        grid.requestFocus();
    }
    
    

    public Teacher(DB db) {
        super(db, "teacher");
        addCommandBar(commandManager);
        grid.setRecordset(db.table("r1"));
        add(new JScrollPane(grid));        
        GridAdapter adapter = new GridAdapter(grid, commandManager, ADD,EDIT,DELETE);        
    }

    public static void main(String[] args) {
        DB db = new AppDB();
        new Teacher(db).run();
    }

}
