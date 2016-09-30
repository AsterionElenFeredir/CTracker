package gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JLayeredPane;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;
import javax.swing.border.BevelBorder;

import model.Actor;
import persistence.DataManager;

public class ActorsOrderPanel extends JLayeredPane implements MouseListener {

	private static final long serialVersionUID = 1L;

	private JPopupMenu popup = new JPopupMenu();
	private ArrayList<Box> boxActors = new ArrayList<Box>();
	private ArrayList<Box> selection = new ArrayList<Box>();
	private final EncounterPanel encounterPanel;

	public ActorsOrderPanel(EncounterPanel encounterPanel) {
		this.encounterPanel = encounterPanel;
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		setBackground(Color.BLACK);
		setOpaque(true);

		buildPopupMenu();
		addMouseListener(this);

	}

	/**
	 * Get list of Box components containing an actor.
	 * 
	 * @return
	 */
	public ArrayList<Box> getBoxActors() {
		return boxActors;
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
	public synchronized void addActor() {
		// Create and a new Actor and Box.
		Actor actor = new Actor(null, null, 0, 0, 0);
		Box box = new Box(actor);

		// Create and add a new Box for the Actor.
		boxActors.add(box);

		// DetailBox must point on new Actor created
		EncounterPanel encounterPanel = (EncounterPanel)CTracker.getInstance().getTabbedPane().getSelectedComponent();
		encounterPanel.updateDetailBox(box);

		rebuild();
	}

	//	/**
	//	 * Trie la liste des acteurs.
	//	 */
	//	public synchronized void sortActorList() {
	//		if (null != encounter) {
	//			Collections.sort(encounter.getActorList());
	//		}
	//	}

	/**
	 * Sort and Rebuild the Windows (after drag and drop of an actor for exemple)
	 */
	public synchronized void rebuild() {
		// Suppression de toutes les box.
		removeAll();

		for (Box box : boxActors) {
			add(box);
		}
	}

	/**
	 * Recompute index when borns are out of range.
	 * 
	 * @param boxMoved
	 * @param x
	 * @return
	 */
	private int trimIndex(Box boxMoved, int x) {
		int width = boxMoved.getWidth();

		// Compute position.
		int index = x/width;
		// Limite la position aux nombre d'éléments dans la liste.
		// A noter que la liste ici contient forcément au moins 1 Box puisque l'on est en train de la déplacer.
		if (index > boxActors.size()-1)
			index = boxActors.size()-1;
		else if (index < 0)
			index = 0;

		return index;
	}

	/**
	 * Redraw Actor boxes after a drag.
	 * 
	 * @param boxMoved
	 * @param index
	 */
	public void redrawAfterDrag(Box boxMoved, int index) {
		// Déplacement uniquement si la Box a changé de position.
		Box boxToIndex = boxActors.get(index);
		if (boxToIndex != boxMoved) {
			// Reorder list.
			ArrayList<Box> newBoxList = new ArrayList<Box>();
			int i = 0;
			for (Box boxPanel : boxActors) {
				if (i == index) {
					newBoxList.add(boxMoved);
					i++;
				}

				if(boxPanel != boxMoved) {
					newBoxList.add(boxPanel);
					i++;
				}
			}

			// Cas particulier : la position de placement est la dernière position de la liste
			// Dans ce cas, la boite n'a pas été ajoutée alors qu'on a finis de parcourir la liste originale et qu'on est sortie de la boucle.
			// On ajoute la boite maintenant.
			if (index == boxActors.size()-1)
				newBoxList.add(boxMoved);

			boxActors = newBoxList;
		}
		// Rebuild panel.
		removeAll();
		rebuild();
		revalidate();	
	}

	public synchronized void rebuild(Box boxMoved, int x) {
		int index = trimIndex(boxMoved, x);
		redrawAfterDrag(boxMoved, index);
	}

	/**
	 * Build Boxes GUI components containing actors
	 */
	public synchronized void build(List<Actor> actorList) {
		// Suppression de toutes les box.
		removeAll();

		for (Actor actor : actorList) {
			boxActors.add(new Box(actor));
		}

		rebuild();
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
				boxActors.remove(box);
				remove(box);
			}
			selection.clear();
		}
		repaint();
	}

	public void setActorAndBoxImage(File file) {
		synchronized (selection) {
			//Update DetailBox.
			EncounterPanel encounterPanel = (EncounterPanel)CTracker.getInstance().getTabbedPane().getSelectedComponent();

			if (selection.isEmpty())
				return;

			for (Box box : selection) {
				box.setActorAndBoxImage(file);
				// Will be updated only if detailBox is displaying this box.
				encounterPanel.updateDetailBox(box);
			}
		}
	}

	/**
	 * Get the EncounterPanel linked to this actor panel.
	 * 
	 * @return
	 */
	public EncounterPanel getEncounterPanel() {
		return encounterPanel;
	}

	/**
	 * Build Popup Menu.
	 */
	public void buildPopupMenu() {
		JMenuItem addActorMenuItem = new JMenuItem("Ajouter un acteur");
		JMenuItem deleteActorsMenuItem = new JMenuItem("Supprimer les acteurs sélectionnés");
		JMenuItem addEncounterMenuItem = new JMenuItem("Ajouter une rencontre");
		JMenuItem quickSaveMenuItem = new JMenuItem("Enregistrer");
		JMenuItem quickLoadMenuItem = new JMenuItem("Charger");
		JMenuItem saveMenuItem = new JMenuItem("Enregistrer...");
		JMenuItem saveAllMenuItem = new JMenuItem("Enregistrer tout...");
		JMenuItem loadMenuItem = new JMenuItem("Charger...");
		JMenuItem loadEncounterListMenuItem = new JMenuItem("Charger tout...");
		JMenuItem quitMenuItem = new JMenuItem("Quitter");

		addActorMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,InputEvent.CTRL_MASK));
		addEncounterMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,InputEvent.CTRL_MASK));
		quickSaveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F8, 0));
		quickLoadMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F12, 0));
		deleteActorsMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0));

		popup.add(addActorMenuItem);
		popup.add(deleteActorsMenuItem);
		popup.addSeparator();
		popup.add(addEncounterMenuItem);
		popup.add(quickSaveMenuItem);
		popup.add(saveMenuItem);
		popup.add(saveAllMenuItem);
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
					ActorsOrderPanel.this.addActor();
				}
			}
		});

		deleteActorsMenuItem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				{
					ActorsOrderPanel.this.removeSelectedBoxes();
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
				DataManager.quickSave(ActorsOrderPanel.this.getEncounterPanel());
			}
		});

		saveMenuItem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				DataManager.saveEncounter(ActorsOrderPanel.this.getEncounterPanel());
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
	
//	@Override
//	protected void paintComponent(Graphics g) {
//		super.paintComponent(g);
//		if(Constants.TEST_MODE == 0)
//			g.drawImage(Constants.DETAILS_PANEL_BACKGROUND, 0, 0, getWidth(), getHeight(), null);
//	}
}
