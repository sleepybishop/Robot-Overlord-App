package com.marginallyclever.robotOverlord.swingInterface.view;

import java.awt.BorderLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.UndoableEditEvent;

import com.marginallyclever.robotOverlord.RobotOverlord;
import com.marginallyclever.robotOverlord.entity.basicDataTypes.IntEntity;
import com.marginallyclever.robotOverlord.swingInterface.actions.ActionChangeInt;

/**
 * Panel to alter a color parameter (four float values).
 * @author Dan Royer
 */
public class ViewElementSlider extends ViewElement implements ChangeListener, Observer {
	private JSlider field;
	private IntEntity e;
	
	public ViewElementSlider(RobotOverlord ro,IntEntity e,int top,int bottom) {
		super(ro);
		this.e=e;

		field = new JSlider();
		field.setMaximum(top);
		field.setMinimum(bottom);
		field.setMinorTickSpacing(1);
		field.setValue(e.get());
		field.addChangeListener(this);

		JLabel label = new JLabel(e.getName(),JLabel.LEADING);
		
		panel.setLayout(new BorderLayout());
		panel.add(label,BorderLayout.LINE_START);
		panel.add(field,BorderLayout.LINE_END);
	}

	/**
	 * entity changed, poke panel
	 */
	@Override
	public void update(Observable o, Object arg) {
		
	}

	@Override
	public void stateChanged(ChangeEvent arg0) {
		int oldValue = e.get();
		int newValue = field.getValue();
		
		if(newValue!=oldValue) {
			ro.undoableEditHappened(new UndoableEditEvent(this,new ActionChangeInt(e,newValue) ) );
		}
	}

	@Override
	public void setReadOnly(boolean arg0) {
		field.setEnabled(!arg0);
	}
}
