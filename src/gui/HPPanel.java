package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;

import model.Actor;
import utils.Constants;

public class HPPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private Actor actor;
	JPanel hpLostPanel;
	JPanel hpMaxPanel;
	GridBagConstraints c = new GridBagConstraints();

	/**
	 * Constructor.
	 * 
	 * @param actor
	 */
	public HPPanel(Actor actor) {
		super();
		this.actor = actor;

		setPreferredSize(new Dimension (Constants.BOX_SIZE, Constants.HP_PANEL_SIZE));
		setLayout(new GridBagLayout());
		setOpaque(false);
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.FIRST_LINE_START;

		buildHPPanel();

		addMouseListener(new HPPanelMouseListener(this));
	}

	public void buildHPPanel() {
		hpLostPanel = new JPanel();
		hpMaxPanel = new JPanel();

		if(Constants.TEST_MODE == 0)
		{
			hpLostPanel.setBackground(Color.GREEN);
			hpMaxPanel.setBackground(Color.RED);
		} else {
			hpLostPanel.setBackground(Color.GRAY);
			hpMaxPanel.setBackground(Color.BLACK);
		}

		float ratio = ((float)actor.currentHp/(float)actor.hp);

		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(3,5,3,5);  // padding
		c.weighty = 1;
		c.weightx = 1;

		if (ratio == 1) {
			add(hpLostPanel, c);

		} else { 
			if (ratio > 0) {
				c.insets = new Insets(3,5,3,0);  // padding
				c.weightx = ratio;
				add(hpLostPanel, c);
				c.gridx = 1;
				c.weightx = 1-ratio;
				c.insets = new Insets(3,0,3,5);  // padding
			}

			add(hpMaxPanel, c);
		}
	}
	
	/**
	 * Return the actor associated to this HPPanel.
	 * 
	 * @return {@link Actor} the actor associated to this HPPanel.
	 */
	public Actor getActor() {
		return actor;
	}
	
	/**
	 * Update the HPPanel.
	 */
	public void update() {
		removeAll();
		buildHPPanel();
		revalidate();
		repaint();
	}
}
