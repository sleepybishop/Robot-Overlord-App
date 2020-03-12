package com.marginallyclever.robotOverlord.engine.undoRedo.actions;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import com.marginallyclever.robotOverlord.engine.translator.Translator;
import com.marginallyclever.robotOverlord.engine.undoRedo.commands.UserCommandSelectNumber;

/**
 * Undoable action to select a number.
 * <p>
 * Some Entities have decimal number (float) parameters.  This class ensures changing those parameters is undoable.
 *  
 * @author Dan Royer
 *
 */
public class UndoableActionSelectNumber extends AbstractUndoableEdit {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UserCommandSelectNumber actionSelectNumber;
	private float oldValue,newValue;
	private String label;
	
	public UndoableActionSelectNumber(UserCommandSelectNumber actionSelectNumber,String label,float newValue) {
		super();
		
		this.actionSelectNumber = actionSelectNumber;
		this.newValue = newValue;
		this.oldValue = actionSelectNumber.getValue();
		this.label = label;
		
		doIt();
	}

	@Override
	public String getPresentationName() {
		return Translator.get("change ")+label;
	}

	@Override
	public void redo() throws CannotRedoException {
		super.redo();
		doIt();
	}
	
	protected void doIt() {
		actionSelectNumber.setValue(newValue);
	}

	@Override
	public void undo() throws CannotUndoException {
		super.undo();
		actionSelectNumber.setValue(oldValue);
	}
}
