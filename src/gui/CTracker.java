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
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import model.Actor;
import model.ActorList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

public class CTracker extends JFrame{
	private static final long serialVersionUID = 1L;

	private static CTracker cTracker = null;
	private ActorList actors = new ActorList();

	public static CTracker getInstance() {
		if (null == cTracker)
			cTracker = new CTracker();

		return cTracker;
	}

	// Panneau principal.
	private JPanel pan = new JPanel();

	public CTracker(){
		this.setTitle("CTracker");
		this.setIconImage(new ImageIcon("images/dd_sigle.png").getImage());

		//		this.setSize(800,500);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize(dim.width, dim.height);
		this.setResizable(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// Construction de la barre de menu.
		buildMenuBar();

	}

	/**
	 * Build Windows and add Box Actors.
	 * 
	 * @param actors
	 */
	public void build() {
		pan.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		pan.setBackground(Color.BLACK);

		for (Actor actor : actors.getActorList()) {
			pan.add(new Box(actor));
		}

		this.getContentPane().add(pan);
		this.pack();
		this.setVisible(true);

	}

	/**
	 * Build menu bar.
	 */
	public void buildMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu menu = new JMenu("Actions");
		menuBar.add(menu);

		JMenuItem saveMenuItem = new JMenuItem("Enregistrer");
		JMenuItem loadMenuItem = new JMenuItem("Charger");
		JMenuItem addMenuItem = new JMenuItem("Ajouter");
		JMenuItem removeMenuItem = new JMenuItem("Supprimer");

		menu.add(addMenuItem);
		menu.add(removeMenuItem);
		menu.add(saveMenuItem);
		menu.add(loadMenuItem);

		saveMenuItem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				try {
					save(actors);

				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});

		loadMenuItem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				{
					try {
						load(actors);

					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		});

		addMenuItem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				{
					CTracker.getInstance().addActor();
				}
			}
		});
	}


	/**
	 * Get the actors in the current Encounter.
	 * 
	 * @return
	 */
	public ActorList getActorList() {
		return actors;
	}

	/**
	 * Set the actor of the current Encounter and refresh the Windows.
	 * @param actorList
	 */
	public void setActorList(ActorList actorList) {
		this.actors = actorList;
		sortActorList();
		pan.removeAll();
		build();
	}

	/**
	 * Sort and Rebuild the Windows (when init is changed on an actor for example)
	 */
	public void rebuild() {
		sortActorList();
		pan.removeAll();
		build();
	}
	
	/**
	 * Add an Actor to the encounter and rebuild the Windows.
	 */
	public void addActor() {
		// Mise à jour de la liste et redessin de la fenêtre.
		actors.getActorList().add(new Actor(null, null, 0, 0, 0));
		sortActorList();
		pan.removeAll();
		build();
	}

	/**
	 * Trie la liste des acteurs.
	 */
	public void sortActorList() {
		Collections.sort(actors.getActorList());
	}

	/**
	 * Save Encounter.
	 * 
	 * @param actorList
	 * @throws IOException
	 */
	public synchronized static void save(ActorList actorList) throws IOException {
		FileWriter fileWriter = null;

		try {
			GsonBuilder builder = new GsonBuilder();
			builder.setPrettyPrinting().serializeNulls();
			Gson gson = builder.create();
			String path = "resources/data.json";
			File file = new File(path);
			fileWriter = new FileWriter(file);
			//        JsonWriter writer = new JsonWriter(fileWriter);
			String jsonString = gson.toJson(actorList);
			System.out.println(jsonString);
			gson.toJson(actorList, fileWriter);

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
	 * @throws FileNotFoundException
	 */
	public synchronized static void load(ActorList actorList) throws FileNotFoundException {
		FileReader fileReader = null;

		try {
			//		actorList.getActorList().clear();
			GsonBuilder builder = new GsonBuilder();
			builder.setPrettyPrinting().serializeNulls();
			Gson gson = builder.create();

			String path = "resources/data.json";
			File file = new File(path);
			fileReader = new FileReader(file);
			JsonReader reader = new JsonReader(fileReader);
			ActorList newActorList = gson.fromJson(reader, ActorList.class);
			CTracker.getInstance().setActorList(newActorList);

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
}























































