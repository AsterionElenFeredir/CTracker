package persistence;

import gui.ActorsOrderPanel;
import gui.Box;
import gui.CTracker;
import gui.EncounterPanel;

import java.awt.Component;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import model.Actor;
import model.Encounter;
import model.EncounterList;
import model.Preferences;
import utils.Constants;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

public class DataManager {
	private static final FileFilter encounterFileFilter;
	private static final FileFilter encounterListFileFilter;
	private static final Preferences preferences = new Preferences();

	private static final Gson GSON_BUILDER;

	static {
		GsonBuilder builder = new GsonBuilder();
		// builder uniquement pour les variables "Exposées" pour éviter de sérialiser des variables comme gson de la classe Preferences (exception au rechargement sinon)
		builder.setPrettyPrinting().serializeNulls().excludeFieldsWithoutExposeAnnotation();  
		GSON_BUILDER = builder.create();

		encounterFileFilter = new FileFilter() {

			@Override
			public String getDescription() {
				return "Fichiers de Rencontre";
			}

			@Override
			public boolean accept(File f) {
				if(f.isDirectory() || f.getName().endsWith(".enc"))
					return true;
				return false;
			}
		};

		encounterListFileFilter = new FileFilter() {

			@Override
			public String getDescription() {
				return "Fichiers de liste de Rencontres";
			}

			@Override
			public boolean accept(File f) {
				if(f.isDirectory() || f.getName().endsWith(".all"))
					return true;
				return false;
			}
		};
		
		preferences.load();
	}

	/**
	 * Save Encounter.
	 * 
	 * @param file
	 * 
	 * @throws IOException
	 */
	private static void saveEncounter(File file, EncounterPanel encounterPanel) throws IOException {
		// Build Actor list.
		ArrayList<Actor> actorList = encounterPanel.encounter.getActorList();
		actorList.clear();
		for (Box box : encounterPanel.getBoxActors()) {
			actorList.add(box.getActor());
		}
		
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(file);
			GSON_BUILDER.toJson(encounterPanel.encounter, fileWriter);

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
	 * Save all encounters.
	 * 
	 * @param file
	 * 
	 * @throws IOException
	 */
	private static synchronized void saveAllEncounters(File file) {
		FileWriter fileWriter = null;
		EncounterList encounters = new EncounterList();
		try {
			for (Component comp : CTracker.getInstance().getTabbedPane().getComponents()) {
				if (comp instanceof EncounterPanel) {
					EncounterPanel encounterPanel = (EncounterPanel)comp;
					
					// Build Actor list.
					ArrayList<Actor> actorList = encounterPanel.encounter.getActorList();
					actorList.clear();
					for (Box box : encounterPanel.getBoxActors()) {
						actorList.add(box.getActor());
					}
					
					encounters.add(encounterPanel.encounter);
				}

			}

			fileWriter = new FileWriter(file);
			GSON_BUILDER.toJson(encounters, fileWriter);

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
	 * @param file
	 * 
	 * @throws FileNotFoundException
	 */
	private static synchronized void loadEncounter(File file) {
		FileReader fileReader = null;
		try {
			fileReader = new FileReader(file);
			JsonReader reader = new JsonReader(fileReader);
			Encounter loadedEncounter = GSON_BUILDER.fromJson(reader, Encounter.class);
			CTracker.getInstance().setEncounter(loadedEncounter);

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
	 * Load encounter.
	 * 
	 * @param file
	 * 
	 * @throws FileNotFoundException
	 */
	private static synchronized void loadEncounterList(File file) {
		FileReader fileReader = null;
		try {
			fileReader = new FileReader(file);
			JsonReader reader = new JsonReader(fileReader);
			EncounterList encounterList = GSON_BUILDER.fromJson(reader, EncounterList.class);

			CTracker.getInstance().getTabbedPane().removeAll();

			for (Encounter encounter : encounterList.encounters) {
				CTracker.getInstance().addEncounter(encounter);
			}

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
	 * Get previous preferences save into file.
	 */
	public static synchronized Preferences getPreviousPreferences() {
		FileReader fileReader = null;
		Preferences previousPreferences = null;

		try {
			fileReader = new FileReader("resources/preferences");
			JsonReader reader = new JsonReader(fileReader);
			previousPreferences = GSON_BUILDER.fromJson(reader, Preferences.class);

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

		return previousPreferences;
	}

	/**
	 * Save preferences to file.
	 */
	public static synchronized void savePreferences(Preferences prefs) {
		FileWriter fileWriter = null;

		try {
			fileWriter = new FileWriter("resources/preferences");
			GSON_BUILDER.toJson(prefs, fileWriter);

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
	 * Enable to choose a file.
	 * 
	 * @return
	 */
	public static File chooseFile(FileFilter filter, File startPath) {
		try {
			// création de la boîte de dialogue
			JFileChooser dialogue = new JFileChooser(startPath.getParentFile());
			if (null != filter)
				dialogue.setFileFilter(filter);

			// affichage
			dialogue.showOpenDialog(null);

			return dialogue.getSelectedFile();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 
	 */
	public static synchronized void quickSave(EncounterPanel encounterPanel) {
		try {
			saveEncounter(preferences.getCurrentEncounterFile(), encounterPanel);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 */
	public static synchronized void saveEncounter(EncounterPanel encounterPanel) {
		try {
			File startPath = preferences.getCurrentEncounterFile();
			File file = chooseFile(encounterFileFilter, startPath);
			if (null != file) {
				preferences.setCurrentEncounterFile(file);
				saveEncounter(file, encounterPanel);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 
	 */
	public static synchronized void saveAllEncounters() {
		try {
			File startPath = preferences.getCurrentEncounterListFile();
			File file = chooseFile(encounterListFileFilter, startPath);
			if (null != file) {
				preferences.setCurrentEncounterListFile(file);
				saveAllEncounters(file);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 
	 */
	public static synchronized void quickLoad() {
		DataManager.loadEncounter(preferences.getCurrentEncounterFile());
		CTracker.getInstance().repaintTabTitles();
	}

	/**
	 * 
	 */
	public static synchronized void load() {
		try {
			File startPath = preferences.getCurrentEncounterFile();
			File file = chooseFile(encounterFileFilter, startPath);
			
			if (null != file) {
				preferences.setCurrentEncounterFile(file);
				DataManager.loadEncounter(file);
				CTracker.getInstance().repaintTabTitles();
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 
	 */
	public static synchronized void loadEncounterList() {
		try {
			File startPath = preferences.getCurrentEncounterListFile();
			File file = chooseFile(encounterListFileFilter, startPath);
			
			if (null != file) {
				preferences.setCurrentEncounterListFile(file);
				DataManager.loadEncounterList(file);
				CTracker.getInstance().repaintTabTitles();
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static synchronized void quickLoadEncounterlist() {
		loadEncounterList(Constants.DEFAULT_SAVE_FILE);
		CTracker.getInstance().repaint();
	}
	
	public static synchronized void quickSaveEncounterlist() {
		saveAllEncounters(Constants.DEFAULT_SAVE_FILE);
		CTracker.getInstance().repaint();

	}

	public static synchronized Preferences getPreferences() {
		return preferences;
	}
	
	public static synchronized void loadImage(ActorsOrderPanel actorsOrderPanel) {
		File startFile = preferences.getCurrentImageFile();
		File file = chooseFile(null, startFile);
		preferences.setCurrentImageFile(file);
		actorsOrderPanel.setActorAndBoxImage(file);
	}
}
