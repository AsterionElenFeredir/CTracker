package gui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import model.Encounter;

public class EncounterPanel extends JPanel implements MouseListener {

	private static final long serialVersionUID = 1L;
	
	public Encounter encounter = null;
	
	public EncounterPanel() {
		setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		setBackground(Color.BLACK);
		addMouseListener(this);
	}
	
	/**
	 * Constructor.
	 * 
	 * @param name
	 */
	public EncounterPanel(String name) {
		this();
		encounter = new Encounter(name);
	}

	/**
	 * Constructor.
	 * 
	 * @param encounter
	 */
	public EncounterPanel(Encounter encounter) {
		this();
		this.encounter = encounter;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		CTracker.getInstance().resetSelection();		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
