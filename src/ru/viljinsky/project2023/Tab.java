package ru.viljinsky.project2023;

import java.awt.BorderLayout;

/**
 *
 * @author viljinsky
 */
public class Tab extends Base implements CommandListener {
    
    protected DB db;

    public Tab(DB db, String name) {
        setLayout(new BorderLayout());
        setName(name);
        this.title = name;
        this.db = db;
        setName(name);
    }

    @Override
    public void doCommand(String command) {
        showMessage("doCommand not difined");
    }

    @Override
    public boolean updateCommand(String command) {
        return false;
    }
    
}
