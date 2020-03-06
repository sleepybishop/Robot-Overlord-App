package com.marginallyclever.robotOverlord.engine.undoRedo.commands;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.UndoableEditEvent;

import com.marginallyclever.convenience.PanelHelper;
import com.marginallyclever.robotOverlord.RobotOverlord;
import com.marginallyclever.robotOverlord.engine.undoRedo.actions.UndoableActionSelectBoolean;

/**
 * Panel to alter a boolean parameter.  There is currently no way to limit the length of strings.
 * @author Dan Royer
 *
 */
public class UserCommandSelectBoolean extends JPanel implements ItemListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JCheckBox checkboxField;
	private RobotOverlord ro;
	private boolean value;
	private String label;
	private LinkedList<ChangeListener> changeListeners = new LinkedList<ChangeListener>();
	private boolean allowSetText;
	
	public UserCommandSelectBoolean(RobotOverlord ro,String labelName,boolean defaultValue) {
		super();
		this.ro = ro;
		
		allowSetText=true;
		value=defaultValue;
		this.label = labelName;
		
		this.setLayout(new GridBagLayout());
		GridBagConstraints con1 = PanelHelper.getDefaultGridBagConstraints();
		
		JLabel label=new JLabel(labelName,JLabel.LEFT);
	
		checkboxField = new JCheckBox();
		label.setLabelFor(checkboxField);
		checkboxField.setSelected(defaultValue);
		checkboxField.addItemListener(this);
		
		this.add(checkboxField,con1);
		con1.gridx++;
		con1.weightx=1;
		con1.anchor=GridBagConstraints.NORTHWEST;
		this.add(label,con1);
	}
	
	public boolean getValue() {
		return value;
	}
	
	public void setValue(boolean v) {
		if(value == v) return;
		value = v;
		
		if(allowSetText) {
			allowSetText=false;
			checkboxField.setSelected(v);
			allowSetText=true;
			this.updateUI();
		}
		
		ChangeEvent arg0 = new ChangeEvent(this);
		Iterator<ChangeListener> i = changeListeners.iterator();
		while(i.hasNext()) {
			i.next().stateChanged(arg0);
		}
	}
	
	public void addChangeListener(ChangeListener arg0) {
		changeListeners.add(arg0);
	}
	
	public void removeChangeListner(ChangeListener arg0) {
		changeListeners.remove(arg0);
	}

	@Override
	public void itemStateChanged(ItemEvent arg0) {
		boolean newValue = checkboxField.isSelected();
		if(newValue!=value) {
			allowSetText=false;
			ro.getUndoHelper().undoableEditHappened(new UndoableEditEvent(this,new UndoableActionSelectBoolean(this, label, newValue) ) );
			allowSetText=true;
		}
	}
}
