package SummativeHolmesIrS;

import java.util.Random;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Intelligence {
	protected int endurance;
	protected int perception;
	protected int strength;
	protected int charisma;
	protected int agility;
	protected int addiction;
	protected int vape;
	protected int food;
	protected int invSize = strength + 2;
	protected boolean alive = true;
	
	String name;
	
	protected Image image;

	int radius;

	double x = 200;
	double y = 200;

	// change if adding more stat numbers
	int statAmt = 6;

	int[] stats = new int[statAmt];

	public Intelligence() {
		
	}
	
	/**
	 * sets a name for the intelligence
	 * @param title to set as name
	 */
	public void setName(String title) {
		name = title;
	}
	
	/**
	 * generates random stats for the intelligence
	 */
	public void genStats() {
		for (int i = 0; i < statAmt; i++) {
			Random rand = new Random();
			stats[i] = rand.nextInt(19)+1;
		}
	}
	
	/**
	 * sets the stats to an int[] array
	 * @param arr to set stats to
	 */
	public void setStats(int[] arr) {
		System.out.println("yuh");

		for (int i = 0; i < arr.length; i++) {
			stats[i] = arr[i];
		}
	}
	
	/**
	 * Applies every index in the stats[] array to their specified variables
	 */
	public void refreshStats() {
		endurance = stats[0];
		perception = stats[1];
		strength = stats[2];
		charisma = stats[3];
		agility = stats[4];
		addiction = stats[5];
		
		radius = perception*15;
	}
	
	/**
	 * gets the current stats[] array
	 * @return the stats as an array
	 */
	public int[] getStats() {
		return stats;
	}
	
	/**
	 * gets and individual stat value
	 * @param i the index that correlates to the desired stat
	 * @return the value of the stat
	 */
	public int getStat(int i) {
		try {
			return stats[i];
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Invalid Stat input");
		}
		return -1;
	}

	// Call refreshStats after each method call
	/**
	 * increases the desired stat by the set amount
	 * @param stat to increase
	 * @param amt to increase by
	 */
	public void incStat(int stat, int amt) {
		stats[stat] += amt;
	}

	@Override
	/**
	 * toString method
	 */
	public String toString() {
		return (name+": \n" + "	Endurance: " + stats[0] + "\n	Perception: " + stats[1] + "\n	Strength: " + stats[2]
				+ "\n	Charisma: " + stats[3] + "\n	Agility: " + stats[4] + "\n	Addiction: " + stats[5]);
	}
	
	public void moveTo(double d, double a) {
		//Add speed multiplier
		x += Math.cos(a);
		y += Math.sin(a);
	}
	
	public ImageView setImage(String imageFile, Group root) {
		Image sprite = new Image(imageFile);
		image = sprite;
		ImageView view = new ImageView(sprite);
		view.setCache(true);
		root.getChildren().add(view);
		return view;
	}

}
