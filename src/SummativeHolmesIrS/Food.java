package SummativeHolmesIrS;

import java.util.Random;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Food {
	double x;
	double y;
	protected Image image;

	public Food() {
		Random rand = new Random();
		x = rand.nextInt(400);
		y = rand.nextInt(400);
	}
	
	/**
	 * regenerates a random location for the food item
	 */
	public void regen() {
		Random rand = new Random();
		x = rand.nextInt(400);
		y = rand.nextInt(400);
	}
	
	/**
	 * Sets an image for the food item, and creates an ImageView to display it
	 * @param imageFile the file of the image
	 * @param root the group to put the ImageView in
	 * @return the ImageView
	 */
	public ImageView setImage(String imageFile, Group root) {
		Image sprite = new Image(imageFile);
		image = sprite;
		ImageView view = new ImageView(sprite);
		view.setCache(true);
		root.getChildren().add(view);
		return view;
	}
}
