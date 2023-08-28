/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.viljinsky.project2023;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.EventObject;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

/**
 *
 * @author viljinsky
 */
public class BaseDialog extends JDialog implements ActionListener {
    
    String title = "BaseDialog";
    Container content;
    BaseDialogListener baseDialogListener;
    public static final String BTN_OK = "OK";
    public static final String BTN_CANCEL = "Cancel";
    public static final String BTN_ABORT = "Abort";
    public static final String BTN_RETRY = "Retry";
    public static final String BTN_IGNORE = "Ignore";
    public static final String BTN_HELP = "Help";
    public static final int RESULT_NONE = 0;
    public static final int RESULT_OK = 1;
    public static final int RESULT_CANCEL = 2;
    public static final int RESULT_ABORT = 3;
    public static final int RESULT_RETRY = 4;
    public static final int RESULT_HELP = 5;
    public static final int RESULT_IGNORE = 6;
    int modalResult = RESULT_NONE;
    public static final String A = "a";
    Action a1 = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(getParent(), "OK");
        }
    };

    public BaseDialog(Container content) {
        setLayout(new BorderLayout());
        setTitle(title);
        setModal(true);
        setMinimumSize(new Dimension(400, 200));
        this.content = content;
        getRootPane().registerKeyboardAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modalResult = RESULT_OK;
                if (baseDialogListener != null) {
                    try {
                        baseDialogListener.onResult(new EventObject(BaseDialog.this));
                        setVisible(false);
                    } catch (Exception h) {
                        JOptionPane.showMessageDialog(BaseDialog.this, h.getMessage());
                    }
                }
                //                doCommand(OK);
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
        getRootPane().registerKeyboardAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modalResult = RESULT_CANCEL;
                //                    if (baseDialogListener != null) {
                //                        baseDialogListener.onResult(new EventObject(BaseDialog.this));
                //                    }
                setVisible(false);
                //                doCommand(CANCEL);
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        switch (button.getName()) {
            case BTN_OK:
                modalResult = RESULT_OK;
                break;
            case BTN_CANCEL:
                modalResult = RESULT_CANCEL;
                break;
            case BTN_ABORT:
                modalResult = RESULT_ABORT;
                break;
            case BTN_RETRY:
                modalResult = RESULT_RETRY;
                break;
            case BTN_IGNORE:
                modalResult = RESULT_IGNORE;
                break;
        }
        if (baseDialogListener != null) {
            try {
                baseDialogListener.onResult(new EventObject(this));
            } catch (Exception h) {
                JOptionPane.showMessageDialog(this, h.getMessage());
                return;
            }
        }
        setVisible(false);
    }

    public void setButtons(String... btns) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        for (String btn : btns) {
            JButton button = new JButton(btn);
            button.setName(btn);
            button.addActionListener(this);
            panel.add(button);
        }
        getContentPane().add(panel, BorderLayout.PAGE_END);
    }

    public int showModal(Component parent) {
        getContentPane().add(content);
        setButtons(BTN_OK, BTN_CANCEL);
        pack();
        setLocationRelativeTo(parent);
        setVisible(true);
        return modalResult;
    }
    
}
