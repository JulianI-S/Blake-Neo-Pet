package SummativeHolmesIrS;

import java.util.Random;

public class Food {
	int x;
	int y;

	public Food() {
		Random rand = new Random();
		x = rand.nextInt(400);
		y = rand.nextInt(400);

	}

}
