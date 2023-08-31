package ru.viljinsky.project2023;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

/**
 *
 * @author viljinsky
 */
public class Tabs extends JTabbedPane {
    
    public JMenu menu = new JMenu("tabs");

    public Tabs() {
    }
    
    public Tabs(DB db) {
        for (Recordset r : db.tables()) {
            Grid grid = new Grid(r);
            Tab tab = new Tab(db, r.getName());
            tab.add(new JScrollPane(grid));
            addTab(r.getName(), tab);
            menu.add(new TabAction(tab));
        }
    }

    public Tab tabByName(String name) {
        for (int i = 0; i < getTabCount(); i++) {
            if (getComponentAt(i).getName().equals(name)) {
                return (Tab) getComponentAt(i);
            }
        }
        return null;
    }

    public void addTab(Tab tab) {
        super.addTab(tab.getTitle(), tab);
        menu.add(new TabAction(tab));
    }

    class TabAction extends AbstractAction {

        Tab tab;

        public TabAction(Tab tab) {
            super(tab.getTitle());
            this.tab = tab;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int index = indexOfComponent(tab);
            setSelectedIndex(index);
        }
    }

    public JMenu menu() {
        return menu;
    }

    public Tab getSelectedTab() {
        return (Tab) getSelectedComponent();
    }
    
}
