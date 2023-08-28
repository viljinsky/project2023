/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.viljinsky.project2023;

import java.awt.BorderLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author viljinsky
 */
public class StatusBar extends JComponent {
    
    JLabel label = new JLabel("status bar");

    public StatusBar() {
        setLayout(new BorderLayout());
        label.setBorder(new EmptyBorder(6, 3, 6, 3));
        add(label);
    }

    public void setStatusText(String text) {
        label.setText(text);
    }
    
}
