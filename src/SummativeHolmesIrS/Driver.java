package SummativeHolmesIrS;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class Driver extends Application{

	public static void main(String[] args) throws IOException {
		Application.launch(args);
		Blake blake = new Blake();
		Food nandos = new Food();
		LocalDateTime dateRaw = exactTime();
		System.out.println("Welcome to the dungeon");
		long elapsed = timePassed(dateRaw);
		if (elapsed < 60) {
			System.out.println(elapsed + " seconds have passed since you last ran the program");
		} else if (elapsed < 3600) {
			System.out.println(elapsed / 60 + " minutes and " + elapsed % 60
					+ " seconds have passed since you last ran the program");
		} else {
			System.out.println(elapsed / 3600 + " hours and " + (elapsed % 3600) / 60
					+ " minutes have passed since you last ran the program");
		}
		
		int[] stats = findBlake();
		if (stats[2] > 0) {
			blake.setStats(stats);
		} else {
			blake.genStats();	
		}
		blake.refreshStats();
		System.out.println(blake.toString());
		
		double fDist = Math.sqrt(Math.pow((blake.x+nandos.x),2)+ Math.pow((blake.y+nandos.y),2));
		double bRad = 10;
		if(fDist < bRad) {
			blake.moveTo(fDist, nandos, getAngle(blake.x,blake.y,nandos.x,nandos.y));
		}
		saveGame(dateRaw, blake);
	}

	private void display() {

	}

	private static long timePassed(LocalDateTime date) throws FileNotFoundException {
		// DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd
		// HH:mm");
		Scanner in = new Scanner(new FileReader("Saves\\sf.txt"));
		if (in.next().equals("time")) {
			in.nextLine();
			LocalDateTime dateOld = null;
			dateOld = LocalDateTime.parse(in.next());
			long passed = Duration.between(dateOld, date).toMillis() / 1000;
			return passed;
		}
		return 0;

	}

	private static int[] findBlake() throws FileNotFoundException {
		Scanner in = new Scanner(new FileReader("Saves\\sf.txt"));
		boolean foundStats = false;
		int[] stats = new int[6];
		while (!foundStats) {
			if (in.nextLine().equals("stats")) {
				// in.nextLine();
				String[] temp = in.nextLine().split(" ");
				for (int r = 0; r < temp.length; r++) {
					stats[r] = Integer.parseInt(temp[r]);
				}
				foundStats = true;
			} else {
				in.nextLine();
			}
		}
		return stats;
	}

	private static void saveGame(LocalDateTime date, Blake blake) throws IOException {
		PrintWriter writer = new PrintWriter("Saves\\sf.txt");
		writer.println("time");
		writer.println(date.toString());
		writer.println("stats");
		for (int i : blake.stats) {
			writer.print(i + " ");
		}
		writer.close();
	}

	private static LocalDateTime exactTime() {
		return LocalDateTime.now();
	}

	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("BlakePet");
		Group root = new Group();
		Scene scene = new Scene(root, 500, 500);
		stage.setScene(scene);
		stage.show();
	}
	
	public static double getAngle(int bx, int by, int fx, int fy) {
		double angleF = Math.atan((by-fy)/(bx-fx));
		if ((by-fy)>0 && (bx-fx)>0) {
			return angleF+Math.PI;
		}else if ((by-fy)>0 && (bx-fx)>0) {
			return (2*Math.PI) - angleF;
		}else if ((by-fy)>0 && (bx-fx)<0) {
			return Math.PI - angleF;
		}
		return angleF;
	}
}
