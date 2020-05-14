package com.marginallyclever.robotOverlord.swingInterface.commands;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

import com.marginallyclever.robotOverlord.log.Log;

/**
 * go back one step in the undo/redo history.
 * @author Dan Royer
 */
public class CommandUndo extends AbstractAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UndoManager undo;
	private CommandRedo redoAction;
	
    public CommandUndo(UndoManager undo) {
        super("Undo");  
    	this.undo=undo;
        setEnabled(false);
        
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
    }

	@Override
    public void actionPerformed(ActionEvent e) {
        try {
            undo.undo();
        } catch (CannotUndoException ex) {
            Log.message("Unable to undo: " + ex);
            ex.printStackTrace();
        }
        updateUndoState();
        if(redoAction!=null) redoAction.updateRedoState();
    }

    public void updateUndoState() {
        if (undo.canUndo()) {
            setEnabled(true);
            putValue(Action.NAME, undo.getUndoPresentationName());
        } else {
            setEnabled(false);
            putValue(Action.NAME, "Undo");
        }
    }
    
    public void setRedoCommand(CommandRedo redoCommand) {
    	this.redoAction=redoCommand;
    }
}
