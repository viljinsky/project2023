/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.viljinsky.project2023;

import java.awt.FlowLayout;
import java.util.ArrayList;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;

/**
 *
 * @author viljinsky
 */
public class CommandBar extends JComponent {
    
    public CommandBar() {
        setLayout(new FlowLayout(FlowLayout.LEFT));
    }

    public CommandBar(ArrayList<Action> actions) {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        for (Action a : actions) {
            if (a != null) {
                JButton button = new JButton(a);
                add(button);
            } else {
                add(new JLabel("|"));
            }
        }
    }
    
}
