package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import utils.Constants;
import utils.ImagesConstants;

/**
 * Build a box.
 * 
 * @author Bruno Marcon
 *
 */
public class DetailsBox extends JPanel {
	private static final long serialVersionUID = 1L;
	private Box box = null;
	private final JLabel nameLabel = new JLabel();
	private final JLabel imageLabel = new JLabel();
	
	/**
	 * Constructor.
	 * 
	 * @param person
	 */
	public DetailsBox() {
		setLayout(new BorderLayout());
		setFocusable(true);
		
		imageLabel.setIcon(ImagesConstants.CA_ICON);
		Dimension preferredSize = new Dimension(Constants.BOX_SIZE*3,Constants.BOX_SIZE*3); 
		imageLabel.setPreferredSize(preferredSize);
		imageLabel.setMinimumSize(preferredSize);
		imageLabel.setMaximumSize(preferredSize);
		
		add(buildTitlePanel(), BorderLayout.NORTH);
		add(imageLabel, BorderLayout.CENTER);
	}

	/**
	 * Build name label.
	 * 
	 * @return
	 */
	public JPanel buildTitlePanel() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setOpaque(false);

		// Build JLabel for name.
//		nameLabel.setText("TOTO");
		nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		nameLabel.setFont(Constants.TITLE_FONT);
		nameLabel.setForeground(Color.white);

		panel.add(nameLabel);
		return panel;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
//		if(null != box && null != box.getImage())
//			g.drawImage(box.getImage(), 0, 0, getWidth(), getHeight(), null);
//		else {
		if(Constants.TEST_MODE == 0)
			g.drawImage(Constants.DETAILS_PANEL_BACKGROUND, 0, 0, getWidth(), getHeight(), null);
	}

	/**
	 * Set the box to display.
	 * 
	 * @param box
	 */
	public void setActor(Box box) {
		if (null != box) {
			this.box = box;
			nameLabel.setText(box.getActor().name);
		}
	}
	
	public Box getBox() {
		return box;
	}
}




































































