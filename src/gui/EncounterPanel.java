package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.border.BevelBorder;

import model.Encounter;
import persistence.DataManager;
import utils.Constants;

public class EncounterPanel extends JPanel implements MouseListener {

	private static final long serialVersionUID = 1L;

	public Encounter encounter = null;
	private JPopupMenu popup = new JPopupMenu();
	public final ActorsOrderPanel actorsOrderPanel;
	private final JScrollPane scrollPane;
	private final DetailsBox detailsPanel = new DetailsBox();

	public EncounterPanel() {
		actorsOrderPanel = new ActorsOrderPanel(this);
		setLayout(new BorderLayout());
		actorsOrderPanel.setLayout(new BoxLayout(actorsOrderPanel, BoxLayout.X_AXIS));

		scrollPane = new JScrollPane(actorsOrderPanel);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(0, 0, Constants.BOX_SIZE, CTracker.getInstance().getHeight());
		scrollPane.setMinimumSize(new Dimension(Constants.BOX_SIZE, Constants.BOX_SIZE+Constants.ACTORS_Y_MARGIN*2));
		scrollPane.setPreferredSize(new Dimension(Constants.BOX_SIZE, Constants.BOX_SIZE+Constants.ACTORS_Y_MARGIN*2));
		
		detailsPanel.setMinimumSize(new Dimension(Constants.BOX_SIZE*3, Constants.BOX_SIZE*3));
		detailsPanel.setMaximumSize(new Dimension(Constants.BOX_SIZE*3, Constants.BOX_SIZE*3));
		detailsPanel.setPreferredSize(new Dimension(Constants.BOX_SIZE*3, Constants.BOX_SIZE*3));
		
		add(scrollPane, BorderLayout.NORTH);
		add(detailsPanel, BorderLayout.CENTER);
//		setPreferredSize(new Dimension (400, 800));

		
		actorsOrderPanel.setBackground(Color.BLACK);
		actorsOrderPanel.setOpaque(true);

		detailsPanel.setBackground(Color.DARK_GRAY);
		scrollPane.setBackground(Color.BLACK);
		setBackground(Color.BLACK);
		
		buildPopupMenu();
		addMouseListener(this);
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
	 
	/**
	 * Constructor.
	 * 
	 * @param name
	 */
	 public EncounterPanel(String name) {
		this();
		encounter = new Encounter(name);
		popup.setLabel(encounter.encounterName);
	 }

	 /**
	  * Get list of Box components containing an actor.
	  * 
	  * @return
	  */
	 public ArrayList<Box> getBoxActors() {
		 return actorsOrderPanel.getBoxActors();
	 }
	 
	 public void fillActors() {
		 if (null != encounter) {
			 actorsOrderPanel.build(encounter.getActorList());
			 detailsPanel.setActor(actorsOrderPanel.getBoxActors().get(0));
			 detailsPanel.repaint();
			 	
		 }
	 }
	 
	 public void updateDetailBox(Box box) {
		 if (box == detailsPanel.getBox());
		 	detailsPanel.setActor(box);
		 	detailsPanel.repaint();
	 }
	 
	 public void resetActors() {
		 if (null != encounter) {
			 actorsOrderPanel.getBoxActors().clear();
			 actorsOrderPanel.build(encounter.getActorList());
		 }
	 }
	 
	 /**
	  * Show popup menu.
	  * 
	  * @param e
	  */
	 private void checkPopup(MouseEvent e) {
		 if (e.isPopupTrigger()) {
			 popup.show(this, e.getX(), e.getY());
		 }
	 }

	 /**
	  * Sort and Rebuild the Windows (when init is changed on an actor for example)
	  */
	 public synchronized void repaintTitle() {
		 // Redessine l'onglet en entier.
		 CTracker.getInstance().repaintTabTitles();
	 }

	 /**
	  * Build Popup Menu.
	  */
	 public void buildPopupMenu() {
		 JMenuItem addActorMenuItem = new JMenuItem("Ajouter un acteur");
		 JMenuItem addEncounterMenuItem = new JMenuItem("Ajouter une rencontre");
		 JMenuItem quickSaveMenuItem = new JMenuItem("Enregistrer");
		 JMenuItem quickLoadMenuItem = new JMenuItem("Charger");
		 JMenuItem saveMenuItem = new JMenuItem("Enregistrer une rencontre...");
		 JMenuItem saveAllMenuItem = new JMenuItem("Enregistrer toutes les rencontres...");
		 JMenuItem loadMenuItem = new JMenuItem("Charger une rencontre...");
		 JMenuItem loadEncounterListMenuItem = new JMenuItem("Charger une liste de rencontre...");
		 JMenuItem deleteActorsMenuItem = new JMenuItem("Supprimer les acteurs sélectionnés");
		 JMenuItem quitMenuItem = new JMenuItem("Quitter");

		 addActorMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,InputEvent.CTRL_MASK));
		 addEncounterMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,InputEvent.CTRL_MASK));
		 quickSaveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F8, 0));
		 quickLoadMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F12, 0));
		 deleteActorsMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0));

		 popup.add(addActorMenuItem);
		 popup.add(deleteActorsMenuItem);
		 popup.add(addEncounterMenuItem);
		 popup.addSeparator();
		 popup.add(quickSaveMenuItem);
		 popup.add(saveMenuItem);
		 popup.add(saveAllMenuItem);
		 popup.addSeparator();
		 popup.add(quickLoadMenuItem);
		 popup.add(loadMenuItem);
		 popup.add(loadEncounterListMenuItem);
		 popup.addSeparator();
		 popup.add(quitMenuItem);

		 popup.setBorder(new BevelBorder(BevelBorder.RAISED));

		 addActorMenuItem.addActionListener(new ActionListener()
		 {
			 public void actionPerformed(ActionEvent e)
			 {
				 {
					 EncounterPanel.this.actorsOrderPanel.addActor();
				 }
			 }
		 });

		 deleteActorsMenuItem.addActionListener(new ActionListener()
		 {
			 public void actionPerformed(ActionEvent e)
			 {
				 {
					 EncounterPanel.this.actorsOrderPanel.removeSelectedBoxes();
				 }
			 }
		 });

		 addEncounterMenuItem.addActionListener(new ActionListener()
		 {
			 public void actionPerformed(ActionEvent e)
			 {
				 {
					 CTracker.getInstance().addEncounter("New Encounter");
				 }
			 }
		 });

		 quickSaveMenuItem.addActionListener(new ActionListener()
		 {
			 public void actionPerformed(ActionEvent e)
			 {
				 DataManager.quickSave(EncounterPanel.this);
			 }
		 });

		 saveMenuItem.addActionListener(new ActionListener()
		 {
			 public void actionPerformed(ActionEvent e)
			 {
				 DataManager.saveEncounter(EncounterPanel.this);
			 }
		 });

		 saveAllMenuItem.addActionListener(new ActionListener()
		 {
			 public void actionPerformed(ActionEvent e)
			 {
				 if(CTracker.getInstance().getTabbedPane().getComponentCount() > 0)
					 DataManager.saveAllEncounters();
			 }
		 });

		 quickLoadMenuItem.addActionListener(new ActionListener()
		 {
			 public void actionPerformed(ActionEvent e)
			 {
				 DataManager.quickLoad();
			 }
		 });

		 loadMenuItem.addActionListener(new ActionListener()
		 {
			 public void actionPerformed(ActionEvent e)
			 {
				 DataManager.load();
			 }
		 });

		 loadEncounterListMenuItem.addActionListener(new ActionListener()
		 {
			 public void actionPerformed(ActionEvent e)
			 {
				 DataManager.loadEncounterList();
			 }
		 });

		 quitMenuItem.addActionListener(new ActionListener()
		 {
			 public void actionPerformed(ActionEvent e)
			 {
				 CTracker.getInstance().dispose();
			 }
		 });

	 }


	 @Override
	 public void mouseClicked(MouseEvent e) {
	 }

	 @Override
	 public void mousePressed(MouseEvent e) {
		 checkPopup(e);		
	 }

	 @Override
	 public void mouseReleased(MouseEvent e) {
		 checkPopup(e);		
	 }

	 @Override
	 public void mouseEntered(MouseEvent e) {
	 }

	 @Override
	 public void mouseExited(MouseEvent e) {
		 // TODO Auto-generated method stub

	 }
}
