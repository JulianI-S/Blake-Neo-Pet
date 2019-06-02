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
	
	public void regen() {
		Random rand = new Random();
		x = rand.nextInt(400);
		y = rand.nextInt(400);
	}
	
	public ImageView setImage(String imageFile, Group root) {
		Image sprite = new Image(imageFile);
		image = sprite;
		ImageView view = new ImageView(sprite);
		view.setCache(true);
		root.getChildren().add(view);
		return view;
	}
}
