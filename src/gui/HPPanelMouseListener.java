package gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class HPPanelMouseListener implements MouseListener {
	private HPPanel hpPanel;
	
	/**
	 * Constructor.
	 * 
	 * @param hpPanel
	 */
	public HPPanelMouseListener(HPPanel hpPanel) {
		this.hpPanel = hpPanel;
}
	
	@Override
	public void mouseClicked(MouseEvent e) {
 
		UpdateHPPanelForm form = new UpdateHPPanelForm(hpPanel);
		form.showForm();
		hpPanel.update();
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
