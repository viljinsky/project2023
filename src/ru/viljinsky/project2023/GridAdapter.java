package ru.viljinsky.project2023;

import javax.swing.Action;


/**
 *
 * @author viljinsky
 */
public class GridAdapter{
    
    private CommandManager commandManager;
    private String[] commands;
    
    public String commans(int i){
        return i<commands.length?commands[i]:null;
    }
    
    public GridAdapter(CommandManager commandManager,String... commands){
        this.commandManager = commandManager;
        this.commands = commands;
    }

    public void doCommand(String command) {
        commandManager.doCommand(command);
    }

    public Action actionByName(String actionName) {
        return commandManager.actionByName(actionName);
    }
    
    
    
}
