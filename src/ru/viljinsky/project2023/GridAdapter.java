/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.viljinsky.project2023;

import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.KeyStroke;

/**
 *
 * @author viljinsky
 */
public class GridAdapter {
    
    public GridAdapter(Grid grid, CommandManager commandManager, String... commands) {
        if (commands.length < 1) {
            return;
        }
        String ADD = commands[0];
        grid.getActionMap().put(ADD, commandManager.actionByName(ADD));
        grid.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_INSERT, 0), ADD);
        if (commands.length < 2) {
            return;
        }
        String EDIT = commands[1];
        grid.getActionMap().put(EDIT, commandManager.actionByName(EDIT));
        grid.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), EDIT);
        grid.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    commandManager.doCommand(EDIT);
                }
            }
        });
        if (commands.length < 3) {
            return;
        }
        String DELETE = commands[2];
        grid.getActionMap().put(DELETE, commandManager.actionByName(DELETE));
        grid.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), DELETE);
    }
    
}
