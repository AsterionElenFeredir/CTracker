package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import model.Actor;
import utils.Constants;
import utils.ImagesConstants;

/**
 * Build a box.
 * 
 * @author Bruno Marcon
 *
 */
public class Box extends JPanel implements MouseListener {
	private static final long serialVersionUID = 1L;

	private final Actor actor;
	private final JLabel caIconLabel;
	private final JLabel initIconLabel;
	private Image image = null;
	private final Border blackBorder = BorderFactory.createLineBorder(Color.BLACK, 5);
	private final Border redBorder = BorderFactory.createLineBorder(Color.RED,5);
	private boolean isHighlighted = false;

	/**
	 * Constructor.
	 * 
	 * @param person
	 */
	public Box(Actor actor) {
		this.actor = actor;
		caIconLabel = new JLabel();
		initIconLabel = new JLabel();

		// Set the image of the Box.
		setImage(actor.imagePath);

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

		addMouseListener(this);
		setBorder(blackBorder);
		setFocusable(true);
	}

	/**
	 * Return the actor.
	 * 
	 * @return
	 */
	public Actor getActor() {
		return actor;
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
		addCAMouseListener(caIconLabel);

		initIconLabel.setText(Integer.toString(actor.init));
		if(Constants.TEST_MODE == 0)
			initIconLabel.setIcon(ImagesConstants.INITIATIVE_ICON);
		initIconLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		initIconLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		initIconLabel.setFont(Constants.TITLE_FONT);
		initIconLabel.setForeground(Color.black);
		addInitMouseListener(initIconLabel);

		panel.add(caIconLabel, BorderLayout.WEST);
		panel.add(initIconLabel, BorderLayout.EAST);

		return panel;
	}


	/**
	 * Add a listener on the CA Label to edit the value on mouse click.
	 * 
	 * @param label
	 */
	public void addCAMouseListener(JLabel label) {
		label.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
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
			public void mousePressed(MouseEvent e) {

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
		});		
	}

	/**
	 * Add a listener on the CA Label to edit the value on mouse click.
	 * 
	 * @param label
	 */
	public void addInitMouseListener(JLabel label) {
		label.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
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
			public void mousePressed(MouseEvent e) {
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
		});		
	}

	/**
	 * Switch JPanel selection.
	 */
	public void swichtSelection() {
		if(isHighlighted) {
			setBorder(blackBorder);
			CTracker.getInstance().removeSelection(this);
		}
		else {
			setBorder(redBorder);
			CTracker.getInstance().addSelection(this);
		}

		isHighlighted=!isHighlighted;
	}

	/**
	 * Reset selection in response of the CTracker frame. Do not add "CTracker.getInstance().removeSelection(this);" in 
	 * this method to avoid loop or concurrent modifications of the ArrayList in CTracker.
	 */
	public void resetSelection() {
		if(isHighlighted) {
			setBorder(blackBorder);
			isHighlighted=false;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getButton() > MouseEvent.BUTTON1) {
			File startFile = CTracker.getInstance().getPreferences().getCurrentImageFile();
			File file = CTracker.chooseFile(null, startFile);
			CTracker.getInstance().getPreferences().setCurrentImageFile(file);
			setActorAndBoxImage(file);
			this.repaint();
		} else {
			swichtSelection();		
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		//		swichtSelection();		
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

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(null != image)
			g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		else {
			g.drawImage(Constants.TEST_IMAGE, 0, 0, getWidth(), getHeight(), null);
		}
	}

	/**
	 * Set the image of the BOX.
	 * 
	 * @param imagePath
	 */
	public void setImage(String imagePath) {
		// ImageIcon image = new ImageIcon(new ImageIcon(imagePath).getImage().getScaledInstance(Constants.BOX_SIZE, Constants.BOX_SIZE, Image.SCALE_DEFAULT));
		try {
			if (null != imagePath && imagePath.length() > 0) {
				File file = new File(imagePath);
				if (null != file && file.exists())
					image = ImageIO.read(file);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Set the image of the BOX and the imaegPath of the Actor.
	 * 
	 * @param file
	 */
	public void setActorAndBoxImage(File file) {
		try {
			if (null != file && file.exists()) {
				if (null != file && file.exists()) {
					image = ImageIO.read(file);
					actor.imagePath = file.getPath();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}




































































