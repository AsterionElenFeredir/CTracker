package model;

import com.google.gson.annotations.Expose;

public class Actor implements Comparable<Actor> {
	@Expose(serialize = true)
	public String imagePath = null;
	
	@Expose(serialize = true)
	public String name;
	
	@Expose(serialize = true)
	public int ca;
	
	@Expose(serialize = true)
	public int init;
	
	@Expose(serialize = true)
	public int hp;
	
	@Expose(serialize = true)
	public int currentHp;

	/**
	 * Constructor.
	 * 
	 */
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
		
		// Set Image.
		if (null != imagePath && imagePath.length() > 0)
			this.imagePath = imagePath;
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
