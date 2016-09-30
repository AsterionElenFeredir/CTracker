package gui;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

import model.Actor;
import persistence.DataManager;
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
	private JPopupMenu popup = new JPopupMenu();
	private boolean dragFromMouseButton1 = false;
	private boolean dragged = false;
	private int dX = 0;

	/**
	 * Constructor.
	 * 
	 * @param person
	 */
	public Box(Actor actor) {
		this.actor = actor;
		caIconLabel = new JLabel();
		initIconLabel = new JLabel();
		
		setMinimumSize(new Dimension(Constants.BOX_SIZE, Constants.BOX_SIZE));
		setPreferredSize(new Dimension(Constants.BOX_SIZE, Constants.BOX_SIZE));
		setMaximumSize(new Dimension(Constants.BOX_SIZE, Constants.BOX_SIZE));
		
		// Set the image of the Box.
		setImage(actor.imagePath);

		setLayout(new BorderLayout());
		add(buildTitlePanel(), BorderLayout.NORTH);
		add(new HPPanel(actor), BorderLayout.SOUTH);

		
		buildPopupMenu();

		addMouseListener(this);
		//on mouse dragged
		addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				dragPanel(e);
			}
		});

		setBorder(blackBorder);
		setFocusable(true);
	}

	/**
	 * Build Popup Menu.
	 */
	public void buildPopupMenu() {
		JMenuItem deleteActorsMenuItem = new JMenuItem("Supprimer les acteurs sélectionnés");
		JMenuItem loadImageMenuItem = new JMenuItem("Charger une image");
		JMenuItem editHiddenValuesMenuItem = new JMenuItem("Editer les valeurs cachées");

		deleteActorsMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0));
		loadImageMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, InputEvent.CTRL_MASK));

		popup.add(loadImageMenuItem);
		popup.add(editHiddenValuesMenuItem);
		popup.addSeparator();
		popup.add(deleteActorsMenuItem);

		popup.setBorder(new BevelBorder(BevelBorder.RAISED));

		deleteActorsMenuItem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				ActorsOrderPanel actorsOrderPanel = (ActorsOrderPanel)getParent();
				actorsOrderPanel.removeSelectedBoxes();
			}
		});

		loadImageMenuItem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				ActorsOrderPanel actorsOrderPanel = (ActorsOrderPanel)getParent();
				DataManager.loadImage(actorsOrderPanel);
			}
		});

		editHiddenValuesMenuItem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				UpdateHiddenValuesForm form = new UpdateHiddenValuesForm(Box.this);
				form.showForm();
				actor.updateHiddenValues(actor);
			}
		});
	}

	/**
	 * Getter for Image.
	 * @return
	 */
	public Image getImage() {
		return image;
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
//		panel.add(initIconLabel, BorderLayout.EAST);

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

						//						JTabbedPane tabbedPane = CTracker.getInstance().getTabbedPane();
						//						if (tabbedPane.getTabCount() > 0) {
						//							EncounterPanel encounterPanel = (EncounterPanel)tabbedPane.getSelectedComponent();
						//							encounterPanel.rebuild();
						//						}
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
			ActorsOrderPanel actorsOrderPanel = (ActorsOrderPanel)getParent();
			actorsOrderPanel.removeSelection(this);
		}
		else {
			setBorder(redBorder);
			ActorsOrderPanel actorsOrderPanel = (ActorsOrderPanel)getParent();
			actorsOrderPanel.addSelection(this);
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

	private void dragPanel(MouseEvent e) {
		if (dragFromMouseButton1) {
			dragged = true;
			ActorsOrderPanel actorsOrderPanel = (ActorsOrderPanel)getParent();
			actorsOrderPanel.moveToFront(Box.this);
			Box.this.setLocation(e.getLocationOnScreen().x-dX, Constants.ACTORS_Y_MARGIN);
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1)
			swichtSelection();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.isPopupTrigger())
			popup.show(this, e.getX(), e.getY());
		
		ActorsOrderPanel actorsOrderPanel = (ActorsOrderPanel)getParent();
		dX = actorsOrderPanel.getLocationOnScreen().x;
//		dY = actorsOrderPanel.getLocationOnScreen().y;
		
		if (e.getButton() == MouseEvent.BUTTON1) {
			dragFromMouseButton1 = true;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			if (dragged == true) {
				ActorsOrderPanel actorsOrderPanel = (ActorsOrderPanel)getParent();
				actorsOrderPanel.rebuild(Box.this, e.getLocationOnScreen().x-dX);
			}
		} else
			if (e.isPopupTrigger())
				popup.show(this, e.getX(), e.getY());
		
		dragFromMouseButton1 = false;
		dragged = false;
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

		Color oldColor = g.getColor();
		float thickness = 5;
		Graphics2D g2 = (Graphics2D) g;
		Stroke oldStroke = g2.getStroke();

		g2.setStroke(new BasicStroke(thickness));

		if (isHighlighted)
			g2.setColor(Color.RED);
		else
			g2.setColor(Color.BLACK);

		// Draw Border.
		g2.drawRect(0, 0, getWidth(), getHeight());

		g2.setColor(oldColor);
		g2.setStroke(oldStroke);
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
				repaint();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setBorder(Border border) {
		repaint();
	}
}




































































