package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import model.Actor;
import utils.Constants;
import utils.ImagesConstants;

/**
 * Build a box.
 * 
 * @author Bruno Marcon
 *
 */
public class Box extends JPanel {
	private static final long serialVersionUID = 1L;

	final Actor actor;
	final JLabel caIconLabel;
	final JLabel initIconLabel;
	/**
	 * Constructor.
	 * 
	 * @param person
	 */
	public Box(Actor actor) {
		this.actor = actor;
		caIconLabel = new JLabel();
		initIconLabel = new JLabel();

		GridBagConstraints c = new GridBagConstraints();
		setLayout(new GridBagLayout());

		// Build box.
		c.gridx = 0;
		c.gridy = 0;
		c.weighty = 1;
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(5,5,5,5);  // padding
		add(buildTitlePanel(), c);

		c.gridy = 1;
		JPanel invisiblePanel = new JPanel();
		invisiblePanel.setOpaque(false);
		invisiblePanel.setPreferredSize(new Dimension(Constants.BOX_SIZE, 200));
		add(invisiblePanel, c);

		c.gridy = 2;
		c.weighty = 0.2;
		add(new HPPanel(actor), c);
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
		JLabel nameLabel = new JLabel(actor.name);
		nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		nameLabel.setFont(Constants.TITLE_FONT);
		nameLabel.setForeground(Color.white);

		caIconLabel.setText(Integer.toString(actor.ca));
		if(Constants.TEST_MODE == 0)
			caIconLabel.setIcon(ImagesConstants.CA_ICON);
		caIconLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		caIconLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		caIconLabel.setFont(Constants.TITLE_FONT);
		caIconLabel.setForeground(Color.yellow);
		caIconLabel.addMouseListener(new CaIconMouseListener());

		initIconLabel.setText(Integer.toString(actor.init));
		if(Constants.TEST_MODE == 0)
			initIconLabel.setIcon(ImagesConstants.INITIATIVE_ICON);
		initIconLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		initIconLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		initIconLabel.setFont(Constants.TITLE_FONT);
		initIconLabel.setForeground(Color.black);
		initIconLabel.addMouseListener(new InitIconMouseListener());

		panel.add(caIconLabel, BorderLayout.WEST);
		panel.add(initIconLabel, BorderLayout.EAST);

		return panel;
	}


	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(null != actor.image)
			g.drawImage(actor.image, 0, 0, getWidth(), getHeight(), null);
		else {
			Color c = g.getColor();
			g.setColor(Color.GRAY.darker());
			g.fillRect(0, 0, getWidth(), getHeight());
			g.setColor(c);
		}
	}

	private class CaIconMouseListener implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent e) {
		}

		@Override
		public void mousePressed(MouseEvent e) {
			String value = JOptionPane.showInputDialog(null,
					"Enter new value",
					"Enter value",
					JOptionPane.QUESTION_MESSAGE);

			try {
				if(null != value && value.length() > 0) {
					int intValue = (Integer.parseInt(value));
					caIconLabel.setText(value);
					actor.ca = intValue;
				}
			} catch (Exception ex) {

			}
		}
		@Override
		public void mouseReleased(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}
	}
	
	private class InitIconMouseListener implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent e) {
		}

		@Override
		public void mousePressed(MouseEvent e) {
			String value = JOptionPane.showInputDialog(null,
					"Enter new value",
					"Enter value",
					JOptionPane.QUESTION_MESSAGE);

			try {
				if(null != value && value.length() > 0) {
					int intValue = (Integer.parseInt(value));
					initIconLabel.setText(value);
					actor.init = intValue;
					CTracker.getInstance().rebuild();
				}
			} catch (Exception ex) {

			}
		}
		@Override
		public void mouseReleased(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}
	}
}




































































