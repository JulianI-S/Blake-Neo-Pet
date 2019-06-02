package SummativeHolmesIrS;

import java.util.Random;

public class Blake extends Intelligence {
	
	public Blake() {

	}

	public void changeVape(int amt) {
		if (food >= 1) {
			vape = vape + amt;
		}
	}

	// allows access to vapes outside of function
	public void changefood(int amt) {
		if (vape >= 1) {
			food = food + amt;
		}
	}

	public void effectCheck(long time) {
		//
		long foodCapacity = (20 - endurance) / 2 * time / 240; // every two hours
		double vapeCapacity = (20 - addiction) / 2.5 * time / 360;// every 3 hours
		int tempAgility = agility;
		int tempEndurance = endurance;
		int tempCharisma = charisma;
		int tempaddiction = addiction;
		long hunger = 0;
		long vapeNeed = 0;

		while (hunger < foodCapacity && food >= 1) {
			hunger = hunger + 1;
			food = food - 1;
		} // jazz
		if (hunger < foodCapacity / 2) {
			agility = agility - 2;
			charisma = charisma - 3;
			addiction = addiction + 2;
		} else {
			tempaddiction = addiction;
			tempCharisma = charisma;
			tempAgility = agility;
			if (hunger <= 0) {
				alive = false;
				System.out.println("Blake is dead");
			}
		}
		if (invSize < strength + 2) {
			for (int i = invSize; i >= strength + 2; i = i - 1) {
				agility = agility - i;
				endurance = endurance - i;
			}
		} else {
			agility = tempAgility;
			endurance = tempEndurance;
		}
		while (vapeNeed < vapeCapacity && vape >= 1) {
			vapeNeed = vapeNeed + 1;
			vape = vape - 1;
		}
		if (vapeNeed < vapeCapacity / 2) {
			agility = agility - 1;
			charisma = charisma - 2;
			endurance = endurance - 1;
		} else {
			endurance = tempEndurance;
			charisma = tempCharisma;
			agility = tempAgility;
		}

	}

}
