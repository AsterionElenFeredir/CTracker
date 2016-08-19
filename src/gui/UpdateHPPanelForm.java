package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import model.Actor;

public class UpdateHPPanelForm extends JDialog {
	private static final long serialVersionUID = 1L;

	private HPPanel hpPanel;
	private JTextField damageTextField = new JTextField(0);
	private JTextField cureTextField = new JTextField(0);
	private JTextField tempPVTextField = new JTextField(0);
	private JTextField tempPVTimeTextField = new JTextField(0);
//	private JTextField removeTempPVTextField = new JTextField(0);
	private JTextField hitPointsTextField = new JTextField(0);
	private JTextField regenerationTextField = new JTextField(0);
	private JTextField regenerationTimeTextField = new JTextField(0);
	private JTextField damageReductionTimeTextField = new JTextField(0);
	private JTextField damageReductionTextField = new JTextField(0);
	
	/**
	 * Constructor.
	 */
	public UpdateHPPanelForm(HPPanel hpPanel) {
		this.hpPanel = hpPanel;
		this.setTitle("Modifier HP");
		this.setIconImage(new ImageIcon("images/dd_sigle.png").getImage());
		//				this.setSize(800,500);
		//		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		//		this.setSize(dim.width, dim.height);
		this.setResizable(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		damageTextField.setPreferredSize(new Dimension(60, 20));
		cureTextField.setPreferredSize(new Dimension(60, 20));
		hitPointsTextField.setPreferredSize(new Dimension(60, 20));
		tempPVTextField.setPreferredSize(new Dimension(60, 20));
		tempPVTimeTextField.setPreferredSize(new Dimension(60, 20));
//		removeTempPVTextField.setPreferredSize(new Dimension(60, 20));
		regenerationTextField.setPreferredSize(new Dimension(60, 20));
		regenerationTimeTextField.setPreferredSize(new Dimension(60, 20));
		regenerationTextField.setPreferredSize(new Dimension(60, 20));
		damageReductionTextField.setPreferredSize(new Dimension(60, 20));
		damageReductionTimeTextField.setPreferredSize(new Dimension(60, 20));


		JPanel mainPanel = new JPanel(new BorderLayout());
		JPanel fieldsPanel = new JPanel();
		fieldsPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 1;

		JLabel damageLabel = new JLabel("Dégâts");
		JLabel cureLabel = new JLabel("Soins");
		JLabel addTempPVLabel = new JLabel("Ajout de PV Temp");
//		JLabel removeTempPVLabel = new JLabel("Réduction de PV Temp");
		JLabel maxHPLabel = new JLabel("HP");
		JLabel damageReductionLabel = new JLabel("Réduction de dégâts");
		JLabel timeLabel = new JLabel("Durée (Rd ou Mn)");
		JLabel regenerationLabel = new JLabel("Régénération");


//		JButton validateButton = new JButton("Modifier");
		Action action = new AbstractAction("doSomething") {

			@Override
			public void actionPerformed(ActionEvent e) {
				validate(hpPanel);
				dispose();
			}

		};
		// configure the Action with the accelerator (aka: short cut)
		action.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0));

		// create a button, configured with the Action
		JButton validateButton = new JButton(action);
		// manually register the accelerator in the button's component input map
		validateButton.getActionMap().put("myAction", action);
		validateButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				(KeyStroke) action.getValue(Action.ACCELERATOR_KEY), "myAction");
		validateButton.setText("Modifier");

//		validateButton.addMouseListener(new ValidateMouseListener());

		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(3,5,3,5);
		fieldsPanel.add(damageLabel, c);

		c.gridx = 1;
		c.gridy = 0;
		fieldsPanel.add(damageTextField, c);

		c.gridx = 0;
		c.gridy = 1;
		fieldsPanel.add(cureLabel, c);

		c.gridx = 1;
		c.gridy = 1;
		fieldsPanel.add(cureTextField, c);

		c.gridx = 0;
		c.gridy = 2;
		fieldsPanel.add(addTempPVLabel, c);

		c.gridx = 1;
		c.gridy = 2;
		fieldsPanel.add(tempPVTextField, c);

		c.gridx = 0;
		c.gridy = 3;
		fieldsPanel.add(damageReductionLabel, c);

		c.gridx = 1;
		c.gridy = 3;
		fieldsPanel.add(damageReductionTextField, c);

		c.gridx = 0;
		c.gridy = 4;
		fieldsPanel.add(maxHPLabel, c);

		c.gridx = 1;
		c.gridy = 4;
		fieldsPanel.add(hitPointsTextField, c);
		
		mainPanel.add(fieldsPanel, BorderLayout.CENTER);
		mainPanel.add(validateButton, BorderLayout.SOUTH);

		this.getContentPane().add(mainPanel);
		this.setAlwaysOnTop(true);
		//        this.setLocationRelativeTo(CTracker.getInstance());
		this.pack();
	}

	/**
	 * Affiche le Formulaire.
	 */
	public void showForm() {
		setModal(true);;
		//		(this, Dialog.ModalityType.APPLICATION_MODAL);
		setVisible(true);
		damageTextField.requestFocusInWindow();
	}

	/**
	 * Validate actor after clicking on "Modifier".
	 */
	public synchronized void validate(HPPanel hpPanel) {
		Actor actor = hpPanel.getActor();

		int damage = 0;
		try {
			damage = Integer.parseInt(damageTextField.getText());

			// PAs de dommages négatifs !
			if (damage < 0)
				damage = 0;

		} catch (Exception e) {
			// NOTHING TO DO.
		}

		int cure = 0;
		try {
			cure = Integer.parseInt(cureTextField.getText());

			// Pas de soins négatifs !
			if (cure < 0)
				cure = 0;

		} catch (Exception e) {
			// NOTHING TO DO.
		}

		int hp = 0;
		try {
			hp = Integer.parseInt(hitPointsTextField.getText());

			// Au moins 1 PV !
			if (hp < 1)
				hp = 1;
			
			actor.hp = hp;
			
			// Reinit current HP if HP are modified !
			actor.currentHp = actor.hp;

		} catch (Exception e) {
			// NOTHING TO DO.
		}
		
		//		int addTempHP = 0;
		//		try {
		//			addTempHP = Integer.parseInt(addTempPVTextField.getText());
		//			if (addTempHP < 0)
		//				addTempHP = 0;
		//		} catch (Exception e) {
		//			// NOTHING TO DO.
		//		}
		//		
		//		int removetmpHP = 0;
		//		try {
		//			removetmpHP = Integer.parseInt(removeTempPVTextField.getText());
		//			if (removetmpHP < 0)
		//				removetmpHP = 0;
		//		} catch (Exception e) {
		//			// NOTHING TO DO.
		//		}

		actor.currentHp = actor.currentHp-damage+cure;

		// Imposible de dépasser son score max de PV avec des soins.
		// Les PV temporaires seront gérés à terme.
		if (actor.currentHp > actor.hp)
			actor.currentHp  =actor.hp;

		//		actor.tempHP = actor.tempHP+addTempHP-removetmpHP;
		//		if (actor.tempHP < 0)
		//			actor.tempHP = 0;

		hpPanel.update();
	}

	private class ValidateMouseListener implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent e) {
			validate(hpPanel);
			dispose();
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
	}
}
