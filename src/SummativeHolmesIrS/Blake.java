package SummativeHolmesIrS;

import java.util.Random;

public class Blake {
	private int endurance;
	private int perception;
	private int strength;
	private int charisma;
	private int agility;
	private int addiction;

	// change if adding more stat numbers
	int statAmt = 6;

	int[] stats = new int[statAmt];

	public Blake() {
		
	}
	
	public void genStats() {
		for (int i = 0; i < statAmt; i++) {
			Random rand = new Random();
			stats[i] = rand.nextInt(20);
		}
		endurance = stats[0];
		perception = stats[1];
		strength = stats[2];
		charisma = stats[3];
		agility = stats[4];
		addiction = stats[5];
	}
	
	public void setStats(int[] arr) {
		System.out.println("yuh");
		endurance = arr[0];
		perception = arr[1];
		strength = arr[2];
		charisma = arr[3];
		agility = arr[4];
		addiction = arr[5];
		for (int i = 0; i < arr.length; i++) {
			stats[i] = arr[i];
		}
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

	public void incEndurance(int amt) {
		endurance += amt;
	}

	public void incPerception(int amt) {
		perception += amt;
	}

	public void incStrength(int amt) {
		strength += amt;
	}

	public void incCharisma(int amt) {
		charisma += amt;
	}

	public void incAgility(int amt) {
		agility += amt;
	}

	public void incAddiction(int amt) {
		addiction += amt;
	}

	@Override
	public String toString() {
		return ("Blake: \n" + "	Endurance: " + stats[0] + "\n	Perception: " + stats[1] + "\n	Strength: " + stats[2]
				+ "\n	Charisma: " + stats[3] + "\n	Agility: " + stats[4] + "\n	Addiction: " + stats[5]);
	}
}
