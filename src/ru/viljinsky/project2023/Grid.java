/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.viljinsky.project2023;

import java.awt.KeyboardFocusManager;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.table.TableColumnModel;

public class Grid extends JTable {
    
    private GridAdapter gridAdapter;
    private MouseAdapter mouseAdater = new MouseAdapter() {
        @Override
        public void mouseReleased(MouseEvent e) {
            showPopupMenu(e);
        }


        @Override
        public void mousePressed(MouseEvent e) {
            showPopupMenu(e);
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if(e.getClickCount()==2){
                gridAdapter.doCommand(gridAdapter.commands(1));
            }
        }
    };
    
    JPopupMenu popupMenu  = new JPopupMenu();
    
    public void setCommand(ArrayList<Action> actions){
        popupMenu = new JPopupMenu();
        for(Action a:actions){
            if (a==null){
                popupMenu.addSeparator();
            } else {
                popupMenu.add(a);
            }
        }
    }
    
    public void showPopupMenu(MouseEvent e){
        if (e.isPopupTrigger()){
            popupMenu.show(this, e.getX(),e.getY());
        }
    }
    
    public void showPopupMenu(){
        Rectangle r = getCellRect(getSelectedRow(), getSelectedColumn(), true);
        popupMenu.show(this, r.x,r.y+r.height);
    }

    public void setPopupMenu(JPopupMenu popuMenu) {
        this.popupMenu = popuMenu;
    }
    
    public void setGridAdapter(GridAdapter gridAdapter){
        if (this.gridAdapter!=null){
            removeGridArapter();
        }
        
        this.gridAdapter = gridAdapter;
        
        String command;
        
        command = gridAdapter.commands(0);
        getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_INSERT,0), command);
        getActionMap().put(command, gridAdapter.actionByName(command));
        
        command = gridAdapter.commands(1);
        getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0), command);
        getActionMap().put(command, gridAdapter.actionByName(command));
        
        command = gridAdapter.commands(2);
        getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE,0), command);
        getActionMap().put(command, gridAdapter.actionByName(command));
        
        getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_CONTEXT_MENU,0),"popup");
        getActionMap().put("popup",new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPopupMenu();
            }
        });
                
        addMouseListener(mouseAdater);
        
    }
    
    public void removeGridArapter(){
        if (gridAdapter!=null){
            
            String command ;
            command = gridAdapter.commands(0);
            getInputMap().remove(KeyStroke.getKeyStroke(KeyEvent.VK_INSERT,0));
            
            command = gridAdapter.commands(1);
            getInputMap().remove(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0));
            command = gridAdapter.commands(2);
            getInputMap().remove(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE,0));
            
            getInputMap().remove(KeyStroke.getKeyStroke(KeyEvent.VK_CONTEXT_MENU,0));
            
            removeMouseListener(mouseAdater);
                        
            gridAdapter = null;
        }
    }

//    public Grid(String[] columns){
//        this(new Recordset());
//    } 
    
    public Grid() {
        this(new Recordset());
    }

    String[] columns = new String[]{};
        
    public Grid(String... columns){
        this(new Recordset(columns));
    } 
    
    public Grid(Recordset recordset,String... columns) {
        setAutoCreateColumnsFromModel(false);
        setAutoResizeMode(AUTO_RESIZE_OFF);
        this.columns = columns.length==0?recordset.columns:columns;
        setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
        setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);
        KeyStroke enter = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
        getInputMap(WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(enter, "Solve");
        
        setModel(new GridModel(recordset));
        setColumnModel(new GridColumnModel(recordset,columns));
    }

    @Override
    protected TableColumnModel createDefaultColumnModel() {
        return super.createDefaultColumnModel();
    }

    @Override
    public void createDefaultColumnsFromModel() {
        super.createDefaultColumnsFromModel();
    }

    public void setRecordset(Recordset recordset) {
        setModel(new GridModel(recordset));
        setColumnModel(new GridColumnModel(recordset,columns));
    }

    public void reload() {
        GridModel model = (GridModel) getModel();
        model.fireTableDataChanged();
    }

    public Values getSelectedValues() {
        if (getSelectedRow() < 0) {
            return null;
        }
        GridModel model = (GridModel) getModel();
        Object[] p = model.recordset.get(getSelectedRow());
        Values result = new Values();
        for (int i = 0; i < model.recordset.columnCount(); i++) {
            result.put(model.recordset.columnName(i), p[i]);
        }
        return result;
    }
    
    public Iterable<Values> values(){
        GridModel model = (GridModel)getModel();
        return model.recordset.values();
    }
    
}
