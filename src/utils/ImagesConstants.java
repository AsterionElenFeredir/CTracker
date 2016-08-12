package utils;

import java.awt.Image;

import javax.swing.ImageIcon;

public class ImagesConstants {
	public static final ImageIcon CA_ICON = new ImageIcon(new ImageIcon("images/"+Constants.CA_ICON_FILE_NAME).getImage().getScaledInstance(Constants.ICON_SIZE, Constants.ICON_SIZE, Image.SCALE_DEFAULT));
	public static final ImageIcon INITIATIVE_ICON = new ImageIcon(new ImageIcon("images/"+Constants.INITIATIVE_ICON_FILE_NAME).getImage().getScaledInstance(Constants.ICON_SIZE, Constants.ICON_SIZE, Image.SCALE_DEFAULT));


}
