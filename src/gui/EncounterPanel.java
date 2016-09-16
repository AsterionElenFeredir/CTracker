package gui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;
import javax.swing.border.BevelBorder;

import model.Actor;
import model.Encounter;
import persistence.DataManager;

public class EncounterPanel extends JPanel implements MouseListener {

	private static final long serialVersionUID = 1L;
	
	// Used in the ActionListener.
	private final EncounterPanel instance;
	
	public Encounter encounter = null;
	private JPopupMenu popup = new JPopupMenu();

	private ArrayList<Box> selection = new ArrayList<Box>();


	public EncounterPanel() {
		setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		setBackground(Color.BLACK);
		buildPopupMenu();
		addMouseListener(this);
		this.instance = this;
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
	 * Add an Actor to the encounter and rebuild the Windows.
	 */
	public void addActor() {
		if (null != encounter) {
			encounter.getActorList().add(new Actor(null, null, 0, 0, 0));
			rebuild();
		}
	}
	
	/**
	 * Trie la liste des acteurs.
	 */
	public synchronized void sortActorList() {
		if (null != encounter) {
			Collections.sort(encounter.getActorList());
		}
	}
	
	/**
	 * Sort and Rebuild the Windows (when init is changed on an actor for example)
	 */
	public void rebuild() {
		if (null != encounter) {
			// Suppression de toutes les box.
			removeAll();

			// Trie des acteurs.
			sortActorList();

			// Recréer les box.
			for (Actor actor : encounter.getActorList()) {
				add(new Box(actor));
			}
		}
	}
	
	/**
	 * Sort and Rebuild the Windows (when init is changed on an actor for example)
	 */
	public void rebuildAndRepaintTitle() {
		rebuild();

		// Redessine l'onglet en entier.
		CTracker.getInstance().repaintTabTitles();
	}
	
	/**
	 * Reset the box selection (do not remove boxes !).
	 */
	public int countSelection() {
		synchronized (selection) {
			if (selection.isEmpty())
				return 0;

			return selection.size();
		}
	}
	
	/**
	 * Add a Box to the selection.
	 * 
	 * @param box
	 */
	public void addSelection(Box box) {
		synchronized (selection) {
			selection.add(box);
		}
	}

	/**
	 * Remove a Box from the selection.
	 * 
	 * @param box
	 */
	public void removeSelection(Box box) {
		synchronized (selection) {
			selection.remove(box);
		}
	}

	/**
	 * Reset the box selection (do not remove boxes !).
	 */
	public void resetSelection() {
		synchronized (selection) {
			if (selection.isEmpty())
				return;

			for (Box box : selection) {
				box.resetSelection();
			}
			selection.clear();
		}
		repaint();
	}

	/**
	 * Remove all box selected.
	 */
	public void removeSelectedBoxes() {
		synchronized (selection) {
			if (selection.isEmpty())
				return;

			for (Box box : selection) {
				encounter.getActorList().remove(box.getActor());
				remove(box);
			}
			selection.clear();
		}
		repaint();
	}

	public void setActorAndBoxImage(File file) {
		synchronized (selection) {
			if (selection.isEmpty())
				return;

			for (Box box : selection) {
				box.setActorAndBoxImage(file);
			}
		}
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
					instance.addActor();
				}
			}
		});

		deleteActorsMenuItem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				{
					instance.removeSelectedBoxes();
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
				DataManager.quickSave(instance);
			}
		});

		saveMenuItem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				DataManager.saveEncounter(instance);
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
	
	/**
	 * Constructor.
	 * 
	 * @param encounter
	 */
	public EncounterPanel(Encounter encounter) {
		this();
		this.encounter = encounter;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		resetSelection();		
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
