/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.viljinsky.project2023;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JMenu;

/**
 *
 * @author viljinsky
 */
public class CommandManager extends ArrayList<Action> {
    
    CommandListener commandListener;

    public CommandManager(CommandListener commandListener) {
        this.commandListener = commandListener;
    }

    public CommandManager(CommandListener commandListener, String... commands) {
        this.commandListener = commandListener;
        for (String cmd : commands) {
            if (cmd != null) {
                Action a = new AbstractAction(cmd) {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        doCommand(e.getActionCommand());
                    }
                };
                a.putValue(Action.ACTION_COMMAND_KEY, cmd);
                add(a);
            } else {
                add(null);
            }
        }
    }

    public void addCommand(String... commands) {
        for (String cmd : commands) {
            if (cmd != null) {
                Action a = new AbstractAction(cmd) {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        doCommand(e.getActionCommand());
                    }
                };
                add(a);
            } else {
                add(null);
            }
        }
    }

    public void doCommand(String command) {
        commandListener.doCommand(command);
        update();
    }

    public void update() {
        for (Action a : this) {
            if (a != null) {
                a.setEnabled(commandListener.updateCommand((String) a.getValue(Action.ACTION_COMMAND_KEY)));
            }
        }
    }

    public Action actionByName(String actionName) {
        for (Action a : this) {
            if (a != null && a.getValue(Action.ACTION_COMMAND_KEY).equals(actionName)) {
                return a;
            }
        }
        return null;
    }

    public JMenu menu(String title) {
        JMenu menu = new JMenu(title);
        for (Action a : this) {
            if (a == null) {
                menu.addSeparator();
            } else {
                menu.add(a);
            }
        }
        return menu;
    }
    
}
