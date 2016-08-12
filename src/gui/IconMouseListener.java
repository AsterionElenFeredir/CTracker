package gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class IconMouseListener implements MouseListener {
	public JLabel iconLabel;

	/**
	 * Constructor.
	 * 
	 * @param iconLabel JLabel to modify when change value.
	 */
	public IconMouseListener(JLabel iconLabel) {
		this.iconLabel = iconLabel;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		String value = JOptionPane.showInputDialog(null,
                "Enter new value",
                "Enter value",
                JOptionPane.QUESTION_MESSAGE);
		
		if(null != value && value.length() > 0)
			iconLabel.setText(value);
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
