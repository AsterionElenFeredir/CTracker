package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.Collections;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;

import model.Encounter;
import persistence.DataManager;
import utils.Constants;

public class CTracker extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private static CTracker cTracker = null;

	// Panneau principal.
	private final JTabbedPane tabbedPane;

	/**
	 * Constructor.
	 * 
	 * @return
	 */
	public static CTracker getInstance() {
		if (null == cTracker) {
			cTracker = new CTracker();

			if (Constants.DEFAULT_SAVE_FILE.exists())
				DataManager.quickLoadEncounterlist();
		}

		return cTracker;
	}

	public CTracker() {
		
//		UIDefaults def = UIManager.getLookAndFeelDefaults();
//        def.put( "TabbedPane.foreground", Color.RED );
//        def.put( "TabbedPane.textIconGap", new Integer(16) );
//        def.put( "TabbedPane.background", Color.BLUE );
//        def.put( "TabbedPane.tabsOverlapBorder", true);
//        def.put( "TabbedPane.contentBorderInsets", new Insets(0,0,0,0) );
//		this.setUndecorated(true);

		this.setTitle("Test");
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		tabbedPane = new DraggableTabbedPane();
		tabbedPane.setBackground(Color.BLACK);
		JPanel panel = new JPanel();
		panel.setBackground(Color.black);
		JLabel label = new JLabel("toto");
		label.setOpaque(true);
		label.setBackground(Color.black);
		tabbedPane.addTab("Test", label);
		this.getContentPane().add(tabbedPane);

		this.setTitle("CTracker");
		this.setIconImage(new ImageIcon("images/dd_sigle.png").getImage());

		// Construction de la barre de menu.
		buildMenuBar();
		
		this.pack();
		JFrame.setDefaultLookAndFeelDecorated(true);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setVisible(true);
		
	}
	
	/**
	 * Return TabbedPane.
	 * 
	 * @return
	 */
	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}

//	/**
//	 * Sort and Rebuild the Windows (when init is changed on an actor for example)
//	 */
//	public void rebuildAll() {
//		for (Component comp : tabbedPane.getComponents()) {
//			if (comp instanceof EncounterPanel) {
//				EncounterPanel encounterPanel = (EncounterPanel)comp;
//				sortActorList(((EncounterPanel) comp).encounter);
//				encounterPanel.removeAll();
//				for (Actor actor : encounterPanel.encounter.getActorList()) {
//					encounterPanel.add(new Box(actor));
//				}
//			}
//		}
//
//		CTracker.getInstance().repaint();
//	}

	/**
	 * Redessine proprement les titres et les boutons de fermeture des onglets autrement, le titre
	 * chargé est parfois non visible en entier car masqué par le bouton de fermeture
	 */
	public void repaintTabTitles() {
		for (int i=0; i < tabbedPane.getTabCount(); i++) {
			Component comp = tabbedPane.getTabComponentAt(i);
			comp.invalidate();
		}
		CTracker.getInstance().repaint();
	}
	
	/**
	 * Build menu bar.
	 */
	public void buildMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu menu = new JMenu("Actions");
		menuBar.add(menu);

		
		JMenuItem addEncounterMenuItem = new JMenuItem("Ajouter une rencontre");
		JMenuItem quickSaveMenuItem = new JMenuItem("Enregistrer");
		JMenuItem quickLoadMenuItem = new JMenuItem("Charger");
		JMenuItem saveMenuItem = new JMenuItem("Enregistrer une rencontre...");
		JMenuItem saveAllMenuItem = new JMenuItem("Enregistrer toutes les rencontres...");
		JMenuItem loadMenuItem = new JMenuItem("Charger une rencontre...");
		JMenuItem loadEncounterListMenuItem = new JMenuItem("Charger une liste de rencontre...");
		JMenuItem quitMenuItem = new JMenuItem("Quitter");


		menu.add(addEncounterMenuItem);
		menu.addSeparator();
		menu.add(quickSaveMenuItem);
		menu.add(saveMenuItem);
		menu.add(saveAllMenuItem);
		menu.addSeparator();
		menu.add(quickLoadMenuItem);
		menu.add(loadMenuItem);
		menu.add(loadEncounterListMenuItem);
		menu.addSeparator();
		menu.add(quitMenuItem);

		addEncounterMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,InputEvent.CTRL_MASK));
		quickSaveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F8, 0));
		quickLoadMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F12, 0));

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
				try {
					if(tabbedPane.getComponentCount() > 0) {
						EncounterPanel encounterPanel = (EncounterPanel)tabbedPane.getSelectedComponent();
						DataManager.quickSave(encounterPanel);
					}

				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});

		saveMenuItem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				try {
					if(tabbedPane.getComponentCount() > 0) {
						EncounterPanel encounterPanel = (EncounterPanel)tabbedPane.getSelectedComponent();
						DataManager.saveEncounter(encounterPanel);
					}

				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});

		saveAllMenuItem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(tabbedPane.getComponentCount() > 0)
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
				dispose();
			}
		});
	}

	/**
	 * Get the actors in the current Encounter.
	 * 
	 * @return
	 */
	public Encounter getEncounter() {
		EncounterPanel encounterPanel = (EncounterPanel)tabbedPane.getSelectedComponent();
		return encounterPanel.encounter;
	}

	/**
	 * Set the current Encounter and refresh the Windows.
	 * @param actorList
	 */
	public void setEncounter(Encounter encounter) {
		EncounterPanel encounterPanel = null;

		if (tabbedPane.getTabCount() == 0)
			addEncounter(encounter);
		else {
			int index = tabbedPane.getSelectedIndex();
			encounterPanel = (EncounterPanel)tabbedPane.getSelectedComponent();
			encounterPanel.encounter = encounter;
			tabbedPane.setTitleAt(index, encounter.encounterName);
			encounterPanel.rebuild();
		}
	}

	/**
	 * Add an Encounter tab.
	 */
	public void addEncounter(String name) {
		EncounterPanel encounterPanel = new EncounterPanel(name);
		tabbedPane.addTab(encounterPanel.encounter.encounterName, encounterPanel);
		tabbedPane.setSelectedComponent(encounterPanel);
		int index = tabbedPane.getSelectedIndex();
		tabbedPane.setTabComponentAt(index, new ButtonTabComponent(tabbedPane));
		encounterPanel.rebuild();
	}

	/**
	 * Add an Encounter tab.
	 */
	public void addEncounter(Encounter encounter) {
		EncounterPanel encounterPanel = new EncounterPanel(encounter);
		tabbedPane.addTab(encounterPanel.encounter.encounterName, encounterPanel);
		tabbedPane.setSelectedComponent(encounterPanel);
		int index = tabbedPane.getSelectedIndex();
		tabbedPane.setTabComponentAt(index, new ButtonTabComponent(tabbedPane));
		encounterPanel.rebuild();
	}

	/**
	 * Trie la liste des acteurs.
	 */
	public synchronized void sortActorList(Encounter encounter) {
		Collections.sort(encounter.getActorList());
	}
	
	@Override
	public void dispose() {
		super.dispose();
		DataManager.quickSaveEncounterlist();
	}
}























































