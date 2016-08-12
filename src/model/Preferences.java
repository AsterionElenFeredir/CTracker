package model;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.stream.JsonReader;

public class Preferences {
	@Expose(serialize = true)
	public String currentFilePath = "resources/data.json";
	public File currentFile = new File(currentFilePath);
	public Gson gson;

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

			if (null != previousPreferences.currentFilePath) {
				currentFilePath = previousPreferences.currentFilePath;
				currentFile = new File(currentFilePath);
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
	 * Met à jour le fichier en cours d'utilisation et enregistre les préferences.
	 * 
	 * @param file
	 */
	public synchronized void setCurrentFile(File file) {
		currentFilePath = file.getAbsolutePath();
		currentFile = file;
		savePreferences();
	}

	/**
	 * Return current file used by the user and stored in the preferences.
	 * 
	 * @return
	 */
	public File getCurrentFile() {
		return currentFile;
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

