/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.viljinsky.project2023;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;

/**
 *
 * @author viljinsky
 */
public class Base extends Container implements WindowListener, Runnable {

    JFrame frame;
    StatusBar statusBar;
    CommandBar commandBar;
    JMenuBar menuBar;
    String title = "no title";

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    protected final void addMenu(JMenu menu) {
        if (menuBar == null) {
            menuBar = new JMenuBar();
        }
        menuBar.add(menu);
    }

    protected void removeMenu(JMenu menu) {
        menuBar.remove(menu);
    }

    protected final void addStatusBar() {
        if (statusBar == null) {
            statusBar = new StatusBar();
            add(statusBar, BorderLayout.PAGE_END);
        }
    }

    public void setStatusText(String text) {
        if (statusBar != null) {
            statusBar.setStatusText(text);
        }
    }

    protected final void addCommandBar(ArrayList<Action> actions) {
        if (commandBar == null) {
            commandBar = new CommandBar(actions);
            add(commandBar, BorderLayout.PAGE_START);
        }
    }

    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowClosing(WindowEvent e) {
        frame.setVisible(false);
        frame.dispose();
        frame = null;
    }

    @Override
    public void windowClosed(WindowEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }

    public void run() {
        frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(this);
        frame.setContentPane(this);
        if (menuBar != null) {
            frame.setJMenuBar(menuBar);
        }
        frame.pack();
        frame.setLocationRelativeTo(getParent());
        frame.setVisible(true);
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(getParent(), message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public void showMessage(Exception e) {
        JOptionPane.showMessageDialog(getParent(), e.getMessage() == null ? "exception" : e.getMessage(), title, JOptionPane.ERROR_MESSAGE);
    }

    public boolean confirm(String message) {
        return JOptionPane.showConfirmDialog(getParent(), message, title, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }
    
    public void open(){
    }
    
    public void close(){
    }

}
