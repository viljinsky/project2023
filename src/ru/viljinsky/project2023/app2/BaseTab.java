package ru.viljinsky.project2023.app2;

import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import ru.viljinsky.project2023.Base;
import ru.viljinsky.project2023.CommandListener;
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
public class BaseTab extends Base implements CommandListener {
    
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
        valuesPanel.valuesPanelListener = e -> {
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
        valuesPanel.valuesPanelListener = e -> {
            e.values.print();
        };
        valuesPanel.showModal(getParent());
    }

    void delete() {
        if (confirm("rmove selected recordset")) {
        }
    }

    public BaseTab() {
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
