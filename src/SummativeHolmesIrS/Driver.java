package SummativeHolmesIrS;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.Scanner;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
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

/**
 * Your personal blake pet
 * @author Garrett and Julian
 *
 */
public class Driver extends Application {

	public static void main(String[] args) throws IOException {

		Application.launch(args);

	}

	@Override
	public void start(Stage stage) throws Exception {
		//Creating the stage
		stage.setTitle("BlakePet");
		Group root = new Group();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		
		LocalDateTime dateRaw = exactTime();
		System.out.println("Welcome to the dungeon");
		long elapsed = timePassed(dateRaw);
		//If time is less than a minutes
		if (elapsed < 60) {
			System.out.println(elapsed + " seconds have passed since you last ran the program");
		//Less than an hour
		} else if (elapsed < 3600) {
			System.out.println(elapsed / 60 + " minutes and " + elapsed % 60
					+ " seconds have passed since you last ran the program");
		//More than an hour
		} else {
			System.out.println(elapsed / 3600 + " hours and " + (elapsed % 3600) / 60
					+ " minutes have passed since you last ran the program");
		}

		Canvas canvas = new Canvas(500, 500);
		root.getChildren().add(canvas);

		GraphicsContext gc = canvas.getGraphicsContext2D();
		
		//Font tings
		gc.setStroke(Color.BLACK);
		gc.setLineWidth(1);
		Font theFont = Font.font("Times New Roman", 14);
		gc.setFont(theFont);
		
		//the amount of food to spawn initially (to do: random food spawns at set intervals)
		int foodSpwn = 3;
		
		//multiple Food objects are kept in an array
		Food[] f = new Food[foodSpwn];

		Blake blake = new Blake();
		ImageView bView = blake.setImage("file:Resources\\BlakeSprite1.png", root);
		
		//initialize blake object
		Intelligence blake2 = new Intelligence();
		// ImageView bView2 = blake.setImage("file:Resources\\BlakeSprite1.png", root);
		blake2.genStats();
		blake2.refreshStats();

		Button btn = new Button("Reset");
		btn.setLayoutY(490);
		root.getChildren().add(btn);

		stage.show();
		
		//code to check if saved stats should be used, or generate new ones
		int[] stats = findBlake();
		if (stats[2] > 0) {
			blake.setStats(stats);
		} else {
			blake.genStats();
		}
		blake.refreshStats();

		System.out.println(blake.toString());
		
		//ImageView array for food objects
		ImageView[] views = new ImageView[f.length];
		for (int i = 0; i < f.length; i++) {
			f[i] = new Food();
			views[i] = f[i].setImage("file:Resources\\food.jpg", root);
			views[i].setFitWidth(50);
			views[i].setPreserveRatio(true);
			views[i].setX(f[i].x);
			views[i].setY(f[i].y);
		}

		new AnimationTimer() {
			//Random movement vaiables
			int intCounter = 0;
			double randDist = 0;
			double randA = 0;
			
			//the food item blake is focused on
			int currentFocus = 0;

			public void handle(long currentNanoTime) {
				gc.clearRect(0, 0, 500, 500);
				
				//lots of debugging text
				gc.strokeText("Bot-X: " + String.valueOf(blake.x), 0, 20);
				gc.strokeText("Bot-Y: " + String.valueOf(blake.y), 0, 40);

				//gc.strokeText("Food-X: " + String.valueOf(f[0].x), 0, 20);
			//	gc.strokeText("Food-Y: " + String.valueOf(f[0].y), 0, 40);

				btn.setOnAction(new EventHandler<ActionEvent>() {
					//code to reset positions on button press
					public void handle(ActionEvent arg0) {
						blake.x = 200;
						blake.y = 200;
						blake2.x = 300;
						blake2.y = 300;
						for (int n = 0; n < f.length; n++) {
							f[n].regen();
						}
						
						randA = 0;
						randDist = 0;
						intCounter = 0;
					}
				});
				
				//calculate the distance for each food item to blake
				double[] fDist = new double[f.length];
				for (int d = 0; d < f.length; d++) {
					fDist[d] = Math.sqrt(Math.pow((blake.x - f[d].x), 2) + Math.pow((blake.y - f[d].y), 2));
					gc.strokeText("Distance "+ d+": " + String.valueOf(fDist[d]), 0, 60+d*20);
				}

				
				gc.strokeText("counter: " + String.valueOf(intCounter), 300, 40);
				// gc.strokeText("Angle: " + String.valueOf(getAngle(blake.x, blake.y, f.x,
				// f.y)), 0, 20);
				gc.strokeText("radius: " + String.valueOf(blake.radius), 300, 20);
				
				//stops blake from leaving screen area
				if (blake.x >= canvas.getWidth() + 50) {
					blake.x = -40;
				} else if (blake.x <= -50) {
					blake.x = canvas.getWidth() + 40;
				}
				if (blake.y >= canvas.getHeight() + 50) {
					blake.y = -40;
				} else if (blake.y <= -50) {
					blake.y = canvas.getHeight() + 40;
				}
				
				//for every food item, if blake is wihin range, change currentFocus to the current index
				for (int s = currentFocus; s < f.length; s++) {
					if (fDist[s] < blake.radius) {
						currentFocus = s;
						
						//intCounter freezes
						intCounter = 0;
						
						//move blake towards the angle for the currentFocus food item
						blake.moveTo(getAngle(blake.x, blake.y, f[currentFocus].x, f[currentFocus].y));
						if (fDist[s] < 10) {
							f[currentFocus].regen();
							currentFocus = 0;
						}
					} else {
						//if int counter is increasing, random movement is enabled
						if (intCounter == 1 || intCounter == blake.endurance * 10) {
							Random rand = new Random();
							randDist = rand.nextDouble();
							randA = rand.nextDouble();
							intCounter = 2;
						}
						blake.moveTo(randA * 359);
					}
				}
				//thaw intCounter
				intCounter += 1;
				bView.setX(blake.x);
				bView.setY(blake.y);
				
				for (int d = 0; d < f.length; d++) {
					views[d].setX(f[d].x);
					views[d].setY(f[d].y);

				}
				/*
				 * if (f2Dist < blake2.radius) { gc.strokeText("2", blake.x+10, blake.y);
				 * bView2.setX(blake2.x); bView2.setY(blake2.y); blake2.moveTo(fDist,
				 * getAngle(blake2.x, blake2.y, f2.x, f2.y)); if (f2Dist < 10) { f2.regen(); } }
				 * else { Random rand = new Random(); blake2.moveTo(rand.nextDouble()*500,
				 * rand.nextDouble()*359); }
				 */
			}
		}.start();
		saveGame(dateRaw, blake);
	}
	
	/**
	 * compares a chosen LocalDateTime with the time from the current save file 
	 * @param date the LocalDateTime to compare
	 * @return a long, the amount of time passed in seconds
	 * @throws FileNotFoundException
	 */
	private static long timePassed(LocalDateTime date) throws FileNotFoundException {
		//reads the LocalDateTime in the save as text and converts back and into a long of seconds
		Scanner in = new Scanner(new FileReader("Saves\\sf.txt"));
		if (in.next().equals("time")) {
			in.nextLine();
			//temp null variable
			LocalDateTime dateOld = null;
			dateOld = LocalDateTime.parse(in.next());
			long passed = Duration.between(dateOld, date).toMillis() / 1000;
			return passed;
		}
		return 0;

	}

	/**
	 * Pulls an array of stats from the current save file, or creates and empty array
	 * @return the stats from the save file or an empty int[] array
	 * @throws FileNotFoundException
	 */
	private static int[] findBlake() throws FileNotFoundException {
		Scanner in = new Scanner(new FileReader("Saves\\sf.txt"));
		boolean foundStats = false;
		//empty array
		int[] stats = new int[6];
		while (!foundStats) {
			if (in.nextLine().equals("stats")) {
				// in.nextLine();
				//splits numbers into list by whitespace
				String[] temp = in.nextLine().split(" ");
				//fills array
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

	/**
	 * Saves the game in a txt file
	 * @param date The LocalDateTime to be saved
	 * @param blake the blake object to save
	 * @throws IOException
	 */
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
	
	/**
	 * gets the exact LocalDateTime when the method is called
	 * @return
	 */
	private static LocalDateTime exactTime() {
		return LocalDateTime.now();
	}
	
	/**
	 * Finds the angle in degrees of between to objects
	 * @param x the X coordinate of the central object
	 * @param y the Y coordinate of the central object
	 * @param x2 the X coordinate of the outer object
	 * @param y2 the Y coordinate of the outer object
	 * @return the angle A in degrees as a double
	 * @throws ArithmeticException
	 */
	public static double getAngle(double x, double y, double x2, double y2) throws ArithmeticException {
		double angleF;
		//code to make sure that (y-y2) is not divided by 0
		if ((x - x2) != 0) {
			angleF = Math.atan((y - y2) / (x - x2));
		} else {
			x += 1;
			angleF = Math.atan((y - y2) / (x - x2));
		}
		
		//code to get true 360 angle instead of reference angle 
		if ((y - y2) > 0 && (x - x2) > 0) {
			return angleF * (180.0 / Math.PI) + 180.0;
		} else if ((y - y2) < 0 && (x - x2) > 0) {
			return -180 + angleF * (180.0 / Math.PI);
		} else if ((y - y2) > 0 && (x - x2) < 0) {
			return 360.0 + angleF * (180.0 / Math.PI);
		}
		return angleF * (180 / Math.PI);
	}
}
