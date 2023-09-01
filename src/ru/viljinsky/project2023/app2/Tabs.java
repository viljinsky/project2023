/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.viljinsky.project2023.app2;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JMenu;
import javax.swing.JTabbedPane;
import ru.viljinsky.project2023.DB;
import ru.viljinsky.project2023.Recordset;

/**
 *
 * @author viljinsky
 */
class Tabs extends JTabbedPane {
    
    JMenu menu = new JMenu("Tabs");

    class TabAction extends AbstractAction {

        BaseTab tab;

        public TabAction(BaseTab tab) {
            super(tab.getTitle());
            this.tab = tab;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            setSelectedIndex(indexOfComponent(tab));
        }
    }

    public Tabs(DB db) {
        for (Recordset recordset : db.tables()) {
            BaseTab tab = new BaseTab();
            tab.setRecordset(recordset);
            menu.add(new TabAction(tab));
            addTab(recordset.getName(), tab);
        }
    }

    public BaseTab getSelectedTab() {
        return (BaseTab) getSelectedComponent();
    }

    public BaseTab getTab(int index) {
        return (BaseTab) getComponent(index);
    }

    public JMenu menu() {
        return menu;
    }
    
}
