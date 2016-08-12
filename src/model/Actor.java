package model;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Actor implements Comparable<Actor> {
	public Image image = null;
	public String name;
	public int ca;
	public int init;
	public int hp;
	public int currentHp;
	
	public Actor() {
		this(null, null, 0, 0, 0);
	}
	
	/**
	 * Constructor.
	 * 
	 * @param name
	 * @param image
	 * @param ca
	 * @param init
	 * @param hp
	 */
	public Actor(String name, String imagePath, int ca, int init, int hp) {
		this.name = name;
		this.ca = ca;
		this.init = init;
		this.hp = hp;
		
		// Start with full HP.
		this.currentHp = hp;
		
//		ImageIcon image = new ImageIcon(new ImageIcon(imagePath).getImage().getScaledInstance(Constants.BOX_SIZE, Constants.BOX_SIZE, Image.SCALE_DEFAULT));
		try {
			if (null != imagePath && imagePath.length() > 0) {
				File file = new File(imagePath);
				image = ImageIO.read(file);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int compareTo(Actor o) {
		if (init < o.init)
			return 1;
		if (init > o.init)
			return -1;

		// Equals.
		return 0;
	}
}
