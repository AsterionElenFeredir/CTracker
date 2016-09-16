package model;

import java.io.File;

import persistence.DataManager;

import com.google.gson.annotations.Expose;

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

	/**
	 * Load previous preferences save into file.
	 */
	public void load() {
		Preferences previousPreferences = DataManager.getPreviousPreferences();

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
			DataManager.savePreferences(this);
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
			DataManager.savePreferences(this);
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
			DataManager.savePreferences(this);
		}
	}
}

