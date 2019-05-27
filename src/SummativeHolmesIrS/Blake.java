package SummativeHolmesIrS;

import java.util.Random;

public class Blake {
	private int endurance;
	private int perception;
	private int strength;
	private int charisma;
	private int agility;
	private int addiction;
	private int vape;
	private int food;
	private int invSize = strength+2;

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
		stats[0] += amt;
	}

	public void incPerception(int amt) {
		stats[1] += amt;
	}

	public void incStrength(int amt) {
		stats[2] += amt;
	}

	public void incCharisma(int amt) {
		stats[3] += amt;
	}

	public void incAgility(int amt) {
		stats[4] += amt;
	}

	public void incAddiction(int amt) {
		stats[5] += amt;
	}

	@Override
	public String toString() {
		return ("Blake: \n" + "	Endurance: " + stats[0] + "\n	Perception: " + stats[1] + "\n	Strength: " + stats[2]
				+ "\n	Charisma: " + stats[3] + "\n	Agility: " + stats[4] + "\n	Addiction: " + stats[5]);
	}
	//status effects
	public void changeVape(int amt) {
		if(food >=1) {
		vape = vape + amt;}
	}
	public void changefood(int amt) {
		if(vape >=1) {
			food = food + amt;}
	}
	
	public void effectCheck() {
	int tempAgility = agility;
	int tempEndurance = endurance;
		
		if(invSize < strength+2) {
			for(int i = invSize; i >= strength+2; i = i -1) {
				agility = agility -i;
				endurance = endurance -i;
			}	
		}else {
			agility = tempAgility;
			endurance = tempEndurance;
		}	
		
		
		
	}
}
