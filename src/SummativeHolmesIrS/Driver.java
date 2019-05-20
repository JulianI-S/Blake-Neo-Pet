package SummativeHolmesIrS;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class Driver {

	public static void main(String[] args) throws IOException {
		LocalDateTime dateRaw = exactTime();
		System.out.println("Welcome to the dungeon");
		long elapsed = timePassed(dateRaw)+3660;
		if (elapsed < 60) {
			System.out.println(elapsed + " seconds have passed since you last ran the program");
		} else if (elapsed < 3600) {
			System.out.println(elapsed/60 + " minutes and " + elapsed%60 + " seconds have passed since you last ran the program");
		} else {
			System.out.println(elapsed/3600 + " hours and " + (elapsed%3600)/60 + " minutes have passed since you last ran the program");

		}
		saveGame(dateRaw);
	}

	private void display() {

	}

	private static long timePassed(LocalDateTime date) throws FileNotFoundException {
		// DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd
		// HH:mm");
		Scanner in = new Scanner(new FileReader("Saves\\sf.txt"));
		LocalDateTime dateOld = null;
		dateOld = LocalDateTime.parse(in.next());
		long passed = Duration.between(dateOld, date).toMillis() / 1000;
		return passed;
	}

	private static void saveGame(LocalDateTime date) throws IOException {
		PrintWriter writer = new PrintWriter("Saves\\sf.txt");
		writer.println(date.toString());
		writer.close();
	}

	private static LocalDateTime exactTime() {
		return LocalDateTime.now();
	}
}
