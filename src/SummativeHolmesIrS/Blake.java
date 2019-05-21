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
	
	public Blake(int[] stats) {
		endurance = stats[0];
		perception = stats[1];
		strength = stats[2];
		charisma = stats[3];
		agility = stats[4];
		addiction = stats[5];
	}

	public int[] getStats() {
		return stats;
	}
	
	public int getStat(int i) {
		try {
			return stats[i];
		}catch(IndexOutOfBoundsException e){
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

}
