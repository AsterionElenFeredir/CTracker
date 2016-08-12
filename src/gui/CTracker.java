package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import model.Actor;
import model.Encounter;
import model.Preferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

public class CTracker extends JFrame{
	private static final long serialVersionUID = 1L;

	private static CTracker cTracker = null;
//	private Encounter encounter = new Encounter("E1");
	private static Preferences pref;
	private static Gson gson;
	
	// Panneau principal.
	JTabbedPane tabbedPan = new JTabbedPane();

	
	/**
	 * Constructor.
	 * 
	 * @return
	 */
	public static CTracker getInstance() {
		if (null == cTracker)
			cTracker = new CTracker();

		return cTracker;
	}

	public CTracker(){
		this.setTitle("CTracker");
		this.setIconImage(new ImageIcon("images/dd_sigle.png").getImage());

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize(dim.width, dim.height);
		this.setResizable(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		GsonBuilder builder = new GsonBuilder();
		// Builder uniquement pour les variables "Exposées" pour éviter de sérialiser des variables comme gson de la classe Preferences (exception au rechargement sinon)
		builder.setPrettyPrinting().serializeNulls().excludeFieldsWithoutExposeAnnotation();  
		gson = builder.create();
		
		pref = new Preferences(gson);
		pref.load();
		
		tabbedPan.setBackground(Color.BLACK);
		this.getContentPane().add(tabbedPan);
		
		// Construction de la barre de menu.
		buildMenuBar();

		this.pack();
		JFrame.setDefaultLookAndFeelDecorated(true);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setVisible(true);
	}

	/**
	 * Sort and Rebuild the Windows (when init is changed on an actor for example)
	 */
	public void rebuild() {
		EncounterPanel encounterPanel = (EncounterPanel)tabbedPan.getSelectedComponent();
		sortActorList();
		JPanel pan = (JPanel)tabbedPan.getSelectedComponent();
		pan.removeAll();
		for (Actor actor : encounterPanel.encounter.getActorList()) {
			pan.add(new Box(actor));
		}
	}
	
	/**
	 * Build menu bar.
	 */
	public void buildMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu menu = new JMenu("Actions");
		menuBar.add(menu);

		JMenuItem addActorMenuItem = new JMenuItem("Ajouter un acteur");
		JMenuItem addEncounterMenuItem = new JMenuItem("Ajouter une rencontre");
		JMenuItem quickSaveMenuItem = new JMenuItem("Enregistrer");
		JMenuItem quickLoadMenuItem = new JMenuItem("Recharger");
		JMenuItem saveMenuItem = new JMenuItem("Enregistrer sous...");
		JMenuItem loadMenuItem = new JMenuItem("Charger à partir de...");


		menu.add(addActorMenuItem);
		menu.add(addEncounterMenuItem);
		menu.addSeparator();
		menu.add(quickSaveMenuItem);
		menu.add(saveMenuItem);
		menu.addSeparator();
		menu.add(quickLoadMenuItem);
		menu.add(loadMenuItem);

		addActorMenuItem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				{
					CTracker.getInstance().addActor();
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
				try {
					save(pref.getCurrentFile());

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
					File file = chooseFile();
					if (null != file) {
						pref.setCurrentFile(file);
						save(file);
					}

				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});

		quickLoadMenuItem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				{
					try {
						load(pref.getCurrentFile());

					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		});

		loadMenuItem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				{
					try {
						File file = chooseFile();
						if (null != file) {
							pref.setCurrentFile(file);
							load(file);
						}
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		});

	}


	/**
	 * Get the actors in the current Encounter.
	 * 
	 * @return
	 */
	public Encounter getEncounter() {
		EncounterPanel encounterPanel = (EncounterPanel)tabbedPan.getSelectedComponent();
		return encounterPanel.encounter;
	}

	/**
	 * Set the actor of the current Encounter and refresh the Windows.
	 * @param actorList
	 */
	public void setEncounter(Encounter encounter) {
		int index = tabbedPan.getSelectedIndex();
		EncounterPanel encounterPanel = (EncounterPanel)tabbedPan.getSelectedComponent();
		encounterPanel.encounter = encounter;
		tabbedPan.setTitleAt(index, encounter.encounterName);
		encounterPanel.removeAll();
		rebuild();
	}

	/**
	 * Add an Encounter tab.
	 */
	public void addEncounter(String name) {
		EncounterPanel encounterPanel = new EncounterPanel(name);
		encounterPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		encounterPanel.setBackground(Color.BLACK);
		tabbedPan.addTab(encounterPanel.encounter.encounterName, encounterPanel);
		tabbedPan.setSelectedComponent(encounterPanel);
	}
	
	/**
	 * Add an Actor to the encounter and rebuild the Windows.
	 */
	public void addActor() {
		EncounterPanel encounterPanel = (EncounterPanel)tabbedPan.getSelectedComponent();

		// Mise à jour de la liste et redessin de la fenêtre.
		encounterPanel.encounter.getActorList().add(new Actor(null, null, 0, 0, 0));
		rebuild();
	}

	/**
	 * Trie la liste des acteurs.
	 */
	public synchronized void sortActorList() {
		EncounterPanel encounterPanel = (EncounterPanel)tabbedPan.getSelectedComponent();
		Collections.sort(encounterPanel.encounter.getActorList());
	}

	/**
	 * Save Encounter.
	 * 
	 * @param actorList
	 * @param file
	 * 
	 * @throws IOException
	 */
	public synchronized void save(File file) throws IOException {
		FileWriter fileWriter = null;
		try {
			EncounterPanel encounterPanel = (EncounterPanel)tabbedPan.getSelectedComponent();
			fileWriter = new FileWriter(pref.getCurrentFile());
			gson.toJson(encounterPanel.encounter, fileWriter);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			try {
				if (null != fileWriter)
					fileWriter.close();
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
	}

	/**
	 * Load encounter.
	 * 
	 * @param actorList
	 * @param file
	 * 
	 * @throws FileNotFoundException
	 */
	public synchronized void load(File file) throws FileNotFoundException {
		FileReader fileReader = null;
		try {
			fileReader = new FileReader(pref.getCurrentFile());
			JsonReader reader = new JsonReader(fileReader);
			Encounter loadedEncounter = gson.fromJson(reader, Encounter.class);
			setEncounter(loadedEncounter);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			try {
				if (null != fileReader)
					fileReader.close();
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
	}

	/**
	 * Enable to choose a file.
	 * 
	 * @return
	 */
	public static File chooseFile() {
		try {
			// création de la boîte de dialogue
			JFileChooser dialogue = new JFileChooser(pref.getCurrentFile());

			// affichage
			dialogue.showOpenDialog(null);

			return dialogue.getSelectedFile();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Get GSON Builder.
	 * 
	 * @return
	 */
	public Gson getJsonBuilder() {
		return gson;
	}
}























































