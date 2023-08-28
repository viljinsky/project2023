/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.viljinsky.project2023;

import java.util.EventObject;

/**
 *
 * @author viljinsky
 */
public class ValuesPanelEvent extends EventObject {
    
    public Values values;

    public ValuesPanelEvent(Object source) {
        super(source);
    }

    public ValuesPanelEvent(Object source, Values values) {
        super(source);
        this.values = values;
    }

    public Values getValues() {
        return values;
    }
    
}
