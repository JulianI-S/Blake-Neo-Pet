package SummativeHolmesIrS;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class Driver extends Application {

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
		Scene scene = new Scene(root);
		stage.setScene(scene);

		Blake blake = new Blake();
		Food nandos = new Food();

		Canvas canvas = new Canvas(500, 500);
		root.getChildren().add(canvas);

		GraphicsContext gc = canvas.getGraphicsContext2D();

		gc.setFill(Color.RED);
		gc.setStroke(Color.BLACK);
		gc.setLineWidth(2);
		Font theFont = Font.font("Times New Roman", FontWeight.BOLD, 48);
		gc.setFont(theFont);
		gc.strokeText("Welcome to the dungeon", 0, 40);

		// File file = new File("BlakeSprite1.png");
		// System.out.println(file.toURI().toURL().toString());
		Image pIcon = new Image("file:Resources\\BlakeSprite1.png");
		ImageView bView = new ImageView(pIcon);
		bView.setImage(pIcon);
		bView.setCache(true);
		root.getChildren().add(bView);

		Image vIcon = new Image("file:Resources\\juul.png");
		ImageView jView = new ImageView(vIcon);
		jView.setFitWidth(50);
		jView.setPreserveRatio(true);
		jView.setImage(vIcon);
		jView.setCache(true);
		root.getChildren().add(jView);

		stage.show();

		int[] stats = findBlake();
		if (stats[2] > 0) {
			blake.setStats(stats);
		} else {
			blake.genStats();
		}
		blake.refreshStats();
		System.out.println(blake.toString());

		new AnimationTimer() {
			public void handle(long currentNanoTime) {

				jView.setX(nandos.x);
				jView.setY(nandos.y);

				double fDist = Math.sqrt(Math.pow((blake.x + nandos.x), 2) + Math.pow((blake.y + nandos.y), 2));
				System.out.println("Food X:" + nandos.x + " | Food Y: " + nandos.y);

				if (fDist > 0) {
					// System.out.println("mmm");
					// System.out.println("Distance: " + fDist);
					// System.out.println("");
					// System.out.println("Blake X:" + blake.x + "| Blake Y: " + blake.y);
					blake.moveTo(fDist, getAngle(blake.x, blake.y, nandos.x, nandos.y));
					bView.setX(blake.x);
					bView.setY(blake.y);
				}

			}
		}.start();

		// boolean running = true;
		/*
		 * while (running) {
		 * 
		 * 
		 * 
		 * double fDist = Math.sqrt(Math.pow((blake.x + nandos.x), 2) +
		 * Math.pow((blake.y + nandos.y), 2)); System.out.println("Food X:" + nandos.x +
		 * " | Food Y: " + nandos.y);
		 * 
		 * if (fDist <= 1000) { System.out.println("mmm");
		 * System.out.println("Distance: " + fDist); System.out.println("");
		 * System.out.println("Blake X:" + blake.x + "| Blake Y: " + blake.y);
		 * //blake.moveTo(fDist, nandos, getAngle(blake.x, blake.y, nandos.x,
		 * nandos.y)); System.out.println("gottem"); running = false; }
		 * 
		 * }
		 */

	}

	public static double getAngle(int bx, int by, int fx, int fy) {
		double angleF = Math.atan((by - fy) / (bx - fx));
		if ((by - fy) > 0 && (bx - fx) > 0) {
			return Math.toDegrees(angleF) + 180;
		} else if ((by - fy) > 0 && (bx - fx) > 0) {
			return 360 - Math.toDegrees(angleF);
		} else if ((by - fy) > 0 && (bx - fx) < 0) {
			return 180 - Math.toDegrees(angleF);
		}
		return Math.toDegrees(angleF);
	}
}
