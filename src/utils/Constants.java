package utils;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Constants {
	private static final String TEST_MODE_PROPERTY = "test";
	private static final String BOX_SIZE_PROPERTY = "box.size";
	private static final String INIT_LABEL_PROPERTY = "box.init.label";
	private static final String NAME_LABEL_PROPERTY = "box.name.label";
	private static final String HP_LABEL_PROPERTY = "box.hp.label";
	private static final String CA_LABEL_PROPERTY = "box.ca.label";
	private static final String TEXT_WIDTH_PROPERTY = "box.text.width.value";
	private static final String TEXT_HEIGHT_PROPERTY = "box.text.height.value";
	private static final String TITLE_FONT_PROPERTY = "box.title.font.size";
	private static final String CA_ICON_FILE_NAME_PROPERTY = "ca.icon.filename";
	private static final String JP_REF_ICON_FILE_NAME_PROPERTY = "jp.ref.icon.filename";
	private static final String INITIATIVE_ICON_FILE_NAME_PROPERTY = "initiative.icon.filename";
	private static final String ICON_SIZE_PROPERTY = "icon.size";
	private static final String HP_PANEL_SIZE_PROPERTY = "hp.panel.size";
	
	public static final int BOX_SIZE = PropertiesLoader.getValue(BOX_SIZE_PROPERTY);
	public static final int HP_PANEL_SIZE = PropertiesLoader.getValue(HP_PANEL_SIZE_PROPERTY);
	public static final int TEST_MODE = PropertiesLoader.getValue(TEST_MODE_PROPERTY);
	public static final int HP_OPACITY = PropertiesLoader.getValue("box.hp.opacity");

	public static final String NAME_LABEL = PropertiesLoader.getLabel(NAME_LABEL_PROPERTY);
	public static final String INIT_LABEL = PropertiesLoader.getLabel(INIT_LABEL_PROPERTY);
	public static final String HP_LABEL = PropertiesLoader.getLabel(HP_LABEL_PROPERTY);
	public static final String CA_LABEL = PropertiesLoader.getLabel(CA_LABEL_PROPERTY);
	public static final Dimension BOX_TEXT_DIMENSION = new Dimension(PropertiesLoader.getValue(TEXT_WIDTH_PROPERTY), PropertiesLoader.getValue(TEXT_HEIGHT_PROPERTY));
	public static final int TITLE_FONT_SIZE = PropertiesLoader.getValue(TITLE_FONT_PROPERTY);

	
	public static final Font TITLE_FONT = new Font(" TimesRoman ",Font.BOLD+Font.ITALIC, TITLE_FONT_SIZE);

	
	public static final String CA_ICON_FILE_NAME = PropertiesLoader.getLabel(CA_ICON_FILE_NAME_PROPERTY);
	public static final int ICON_SIZE = PropertiesLoader.getValue(ICON_SIZE_PROPERTY);
	public static final String INITIATIVE_ICON_FILE_NAME = PropertiesLoader.getLabel(INITIATIVE_ICON_FILE_NAME_PROPERTY);

	public static final String JP_REF_ICON_FILE_NAME = PropertiesLoader.getLabel(JP_REF_ICON_FILE_NAME_PROPERTY);
	public static final int JP_ICON_SIZE = ICON_SIZE;
	public static final int ACTORS_Y_MARGIN = 10;
	
	/** File used to save all modifications when closing CTracker and to load last encounters when starting CTracker. */
	public static final File DEFAULT_SAVE_FILE = new File("resources/default.all");
	
	public static Image TEST_IMAGE = null;
	public static Image DETAILS_PANEL_BACKGROUND = null;

	static {
		try {
			TEST_IMAGE = ImageIO.read(new File("images/test.jpg"));
			DETAILS_PANEL_BACKGROUND = ImageIO.read(new File("images/Parchment2.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

}
