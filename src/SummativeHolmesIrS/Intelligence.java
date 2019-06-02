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
	
	public void setName(String title) {
		name = title;
	}
	
	public void genStats() {
		for (int i = 0; i < statAmt; i++) {
			Random rand = new Random();
			stats[i] = rand.nextInt(19)+1;
		}
	}

	public void setStats(int[] arr) {
		System.out.println("yuh");

		for (int i = 0; i < arr.length; i++) {
			stats[i] = arr[i];
		}
	}

	public void refreshStats() {
		endurance = stats[0];
		perception = stats[1];
		strength = stats[2];
		charisma = stats[3];
		agility = stats[4];
		addiction = stats[5];
		
		radius = perception*15;
	}

	public int[] getStats() {
		return stats;
	}

	public int getStat(int i) {
		try {
			return stats[i];
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Invalid Stat input");
		}
		return -1;
	}

	// Call refreshStats after each method call
	public void incStat(int stat, int amt) {
		stats[stat] += amt;
	}

	@Override
	public String toString() {
		return (name+": \n" + "	Endurance: " + stats[0] + "\n	Perception: " + stats[1] + "\n	Strength: " + stats[2]
				+ "\n	Charisma: " + stats[3] + "\n	Agility: " + stats[4] + "\n	Addiction: " + stats[5]);
	}

	public void moveTo(double d, double a) {
		x += (agility/10)*Math.cos(a);
		y += (agility/10)*Math.sin(a);
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
