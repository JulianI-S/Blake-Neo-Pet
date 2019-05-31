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
	private boolean alive = true;
	
	

	
	int radius = 5*perception;


	int x = 200;
	int y = 200;

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

	//Call refreshStats after each method call
	public void incStat(int stat, int amt) {
		stats[stat] += amt;
	}

	@Override
	public String toString() {
		return ("Blake: \n" + "	Endurance: " + stats[0] + "\n	Perception: " + stats[1] + "\n	Strength: " + stats[2]
				+ "\n	Charisma: " + stats[3] + "\n	Agility: " + stats[4] + "\n	Addiction: " + stats[5]);
	}
	//ensures inventory doesn't fill up with all vapes or food  
	//allows access to food outside of function
	public void changeVape(int amt) {
		if(food >=1) {
		vape = vape + amt;}
	}
	//allows access to vapes outside of function
	public void changefood(int amt) {
		if(vape >=1) {
			food = food + amt;}
	}
	
	
	
	public void effectCheck(long time) {
		//
		long foodCapacity = (20-endurance)/2*time/240; // every two hours
		double vapeCapacity = (20-addiction)/2.5*time/360;//every 3 hours
		int tempAgility = agility;
		int tempEndurance = endurance;
		int tempCharisma = charisma;
		int tempaddiction = addiction;
		long hunger = 0;
		long vapeNeed = 0;
		
		
		
		
		while(hunger < foodCapacity && food >= 1 ) {
			hunger = hunger + 1;
			food = food -1;
		} //jazz
		if(hunger < foodCapacity/2) {
			agility = agility -2;
			charisma = charisma -3;
			addiction = addiction +2;
		} else {
			tempaddiction = addiction;
			tempCharisma = charisma;
			tempAgility = agility;
			if (hunger <= 0) {
				alive = false;
				System.out.println("Blake is dead");
			}
		}
		if(invSize < strength+2) {
			for(int i = invSize; i >= strength+2; i = i -1) {
				agility = agility -i;
				endurance = endurance -i;
			}	
		}else {
			agility = tempAgility;
			endurance = tempEndurance;
		}	
		while(vapeNeed < vapeCapacity && vape >= 1 ) {
			vapeNeed = vapeNeed + 1;
			vape = vape -1;
		} 
		if(vapeNeed < vapeCapacity/2) {
			agility = agility -1;
			charisma = charisma -2;
			endurance = endurance -1;
		} else {
			endurance =tempEndurance; 
			charisma = tempCharisma;
			agility = tempAgility;
		}
		
	}
	
	public void moveTo(double d, Food f, double a) {
		while (d > 0) {
			System.out.println("X: "+x);
			System.out.println("Y: "+y);
			System.out.println("Sin: "+Math.sin(a));
			System.out.println("Cos: "+Math.cos(a));
			x += Math.cos(a);
			y += Math.sin(a);
		}
	}
}
