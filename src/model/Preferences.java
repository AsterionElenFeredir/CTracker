package model;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.stream.JsonReader;

public class Preferences {
	@Expose(serialize = true)
	private String currentEncounterFilePath = "encounters/default.enc";
	private File currentEncounterFile = new File(currentEncounterFilePath);

	@Expose(serialize = true)
	private String currentEncounterListFilePath = "encounters/default.all";
	private File currentEncounterListFile = new File(currentEncounterListFilePath);

	@Expose(serialize = true)
	private String currentImageFilePath = "images/default.jpg";
	private File currentImageFile = new File(currentImageFilePath);

	private Gson gson;

	/**
	 * Constructor.
	 * 
	 * @param gson
	 */
	public Preferences(Gson gson) {
		this.gson = gson;
	}

	/**
	 * Load previous preferences save into file.
	 */
	public void load() {
		FileReader fileReader = null;

		try {
			fileReader = new FileReader("resources/preferences");
			JsonReader reader = new JsonReader(fileReader);
			Preferences previousPreferences = gson.fromJson(reader, Preferences.class);

			if (null != previousPreferences.currentEncounterFilePath) {
				currentEncounterFilePath = previousPreferences.currentEncounterFilePath;
				currentEncounterFile = new File(currentEncounterFilePath);
			}

			if (null != previousPreferences.currentEncounterListFilePath) {
				currentEncounterListFilePath = previousPreferences.currentEncounterListFilePath;
				currentEncounterListFile = new File(currentEncounterListFilePath);
			}

			if (null != previousPreferences.currentImageFilePath) {
				currentImageFilePath = previousPreferences.currentImageFilePath;
				currentImageFile = new File(currentImageFilePath);
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
	 * Récupère le fichier de rencontre en cours d'utilisation.
	 * 
	 * @param file
	 */
	public synchronized File getCurrentEncounterFile() {
		return currentEncounterFile;
	}

	/**
	 * Récupère le fichier de liste de rencontres en cours d'utilisation.
	 * 
	 * @param file
	 */
	public synchronized File getCurrentEncounterListFile() {
		return currentEncounterListFile;
	}

	/**
	 * Récupère le dernier fichier image chargé (pour obtenir son répertoire d'origine).
	 * 
	 * @param file
	 */
	public synchronized File getCurrentImageFile() {
		return currentImageFile;
	}

	/**
	 * Met à jour le fichier de rencontre en cours d'utilisation et enregistre les préferences.
	 * 
	 * @param file
	 */
	public synchronized void setCurrentEncounterFile(File file) {
		if (null != file) {
			currentEncounterFilePath = file.getAbsolutePath();
			currentEncounterFile = file;
			savePreferences();
		}
	}

	/**
	 * Met à jour le fichier de liste de rencontres en cours d'utilisation et enregistre les préferences.
	 * 
	 * @param file
	 */
	public synchronized void setCurrentEncounterListFile(File file) {
		if (null != file) {
			currentEncounterListFilePath = file.getAbsolutePath();
			currentEncounterListFile = file;
			savePreferences();
		}
	}

	/**
	 * Met à jour le dernier fichier image chargé.
	 * 
	 * @param file
	 */
	public synchronized void setCurrentImageFile(File file) {
		if (null != file) {
			currentImageFilePath = file.getAbsolutePath();
			currentImageFile = file;
			savePreferences();
		}
	}

	/**
	 * Save preferences to file.
	 */
	private synchronized void savePreferences() {
		FileWriter fileWriter = null;

		try {
			fileWriter = new FileWriter("resources/preferences");
			gson.toJson(this, fileWriter);

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
}

