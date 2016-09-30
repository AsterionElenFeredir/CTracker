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

public class UpdateHiddenValuesForm extends JDialog {
	private static final long serialVersionUID = 1L;

	private Box box;
	private JTextField initTextField = new JTextField();
	private JTextField nameTextField = new JTextField();

	/**
	 * Constructor.
	 */
	public UpdateHiddenValuesForm(Box box) {
		this.box = box;
		this.setTitle("Modifier les valeurs cachées");
		this.setIconImage(new ImageIcon("images/dd_sigle.png").getImage());
		//				this.setSize(800,500);
		//		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		//		this.setSize(dim.width, dim.height);
		this.setResizable(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		initTextField.setPreferredSize(new Dimension(60, 20));
		nameTextField.setPreferredSize(new Dimension(60, 20));

		JPanel mainPanel = new JPanel(new BorderLayout());
		JPanel fieldsPanel = new JPanel();
		fieldsPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 1;

		JLabel initLabel = new JLabel("Initiative");
		JLabel nameLabel = new JLabel("Name");

		initValues(box.getActor());

//		JButton validateButton = new JButton("Modifier");
		Action action = new AbstractAction("doSomething") {

			@Override
			public void actionPerformed(ActionEvent e) {
				validate(box);
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
		fieldsPanel.add(nameLabel, c);
//
		c.gridx = 1;
		c.gridy = 0;
		fieldsPanel.add(nameTextField, c);

		c.gridx = 0;
		c.gridy = 1;
		fieldsPanel.add(initLabel, c);

		c.gridx = 1;
		c.gridy = 1;
		fieldsPanel.add(initTextField, c);
//
//		c.gridx = 0;
//		c.gridy = 2;
//		fieldsPanel.add(addTempPVLabel, c);
//
//		c.gridx = 1;
//		c.gridy = 2;
//		fieldsPanel.add(tempPVTextField, c);
//
//		c.gridx = 0;
//		c.gridy = 3;
//		fieldsPanel.add(damageReductionLabel, c);
//
//		c.gridx = 1;
//		c.gridy = 3;
//		fieldsPanel.add(damageReductionTextField, c);
//
//		c.gridx = 0;
//		c.gridy = 4;
//		fieldsPanel.add(maxHPLabel, c);
//
//		c.gridx = 1;
//		c.gridy = 4;
//		fieldsPanel.add(hitPointsTextField, c);
		
		mainPanel.add(fieldsPanel, BorderLayout.CENTER);
		mainPanel.add(validateButton, BorderLayout.SOUTH);

		this.getContentPane().add(mainPanel);
		this.setAlwaysOnTop(true);
		//        this.setLocationRelativeTo(CTracker.getInstance());
		this.pack();
	}

	/**
	 * Init all the values of the form.
	 * 
	 * @param actor
	 */
	private void initValues(Actor actor) {
		initTextField.setText(Integer.toString(actor.init));	
	}
	
	/**
	 * Affiche le Formulaire.
	 */
	public void showForm() {
		setModal(true);;
		//		(this, Dialog.ModalityType.APPLICATION_MODAL);
		setVisible(true);
		initTextField.requestFocusInWindow();
	}

	/**
	 * Validate actor after clicking on "Modifier".
	 */
	public synchronized void validate(Box box) {
		Actor actor = box.getActor();
		
		try {
			actor.init = Integer.parseInt(initTextField.getText());

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			actor.name = nameTextField.getText();

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		EncounterPanel encounterPanel = (EncounterPanel)CTracker.getInstance().getTabbedPane().getSelectedComponent();
		encounterPanel.updateDetailBox(box);
	}

	private class ValidateMouseListener implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent e) {
			validate(box);
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
