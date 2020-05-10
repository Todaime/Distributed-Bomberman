package bombermandistribue.bomberman.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.io.File;

import javax.imageio.ImageIO;

public class SpriteSheet {

	private String _path;
	public final int SIZE;
	public int[] _pixels;
	// Board SpriteSheet
	public static SpriteSheet ground = new SpriteSheet("/textures/grass00.png",50);
	public static SpriteSheet brick = new SpriteSheet("/textures/brick.png",50);
	public static SpriteSheet wall = new SpriteSheet("/textures/wall.png",50);
	public static SpriteSheet bomb0 = new SpriteSheet("/textures/bomb/bomb0.png",50);
	public static SpriteSheet bomb1 = new SpriteSheet("/textures/bomb/bomb1.png",50);
	public static SpriteSheet bomb2 = new SpriteSheet("/textures/bomb/bomb2.png",50);

	// Player Morty
	public static SpriteSheet morty_down = new SpriteSheet("/textures/morty/morty_down.png",50);
	public static SpriteSheet morty_up = new SpriteSheet("/textures/morty/morty_up.png",50);
	public static SpriteSheet morty_right = new SpriteSheet("/textures/morty/morty_right.png",50);
	public static SpriteSheet morty_left = new SpriteSheet("/textures/morty/morty_left.png",50);
	public static SpriteSheet morty_down1 = new SpriteSheet("/textures/morty/morty_down1.png",50);
	public static SpriteSheet morty_down2 = new SpriteSheet("/textures/morty/morty_down2.png",50);
	public static SpriteSheet morty_up1 = new SpriteSheet("/textures/morty/morty_up1.png",50);
	public static SpriteSheet morty_up2 = new SpriteSheet("/textures/morty/morty_up2.png",50);
	public static SpriteSheet morty_left1 = new SpriteSheet("/textures/morty/morty_left1.png",50);
	public static SpriteSheet morty_left2 = new SpriteSheet("/textures/morty/morty_left2.png",50);
	public static SpriteSheet morty_right1 = new SpriteSheet("/textures/morty/morty_right1.png",50);
	public static SpriteSheet morty_right2 = new SpriteSheet("/textures/morty/morty_right2.png",50);
	public static SpriteSheet morty_dead1 = new SpriteSheet("/textures/morty/morty_dead1.png",50);

	// Player Rick
	public static SpriteSheet rick_down = new SpriteSheet("/textures/rick/rick_down.png",50);
	public static SpriteSheet rick_up = new SpriteSheet("/textures/rick/rick_up.png",50);
	public static SpriteSheet rick_right = new SpriteSheet("/textures/rick/rick_right.png",50);
	public static SpriteSheet rick_left = new SpriteSheet("/textures/rick/rick_left.png",50);
	public static SpriteSheet rick_down1 = new SpriteSheet("/textures/rick/rick_down1.png",50);
	public static SpriteSheet rick_down2 = new SpriteSheet("/textures/rick/rick_down2.png",50);
	public static SpriteSheet rick_up1 = new SpriteSheet("/textures/rick/rick_up1.png",50);
	public static SpriteSheet rick_up2 = new SpriteSheet("/textures/rick/rick_up2.png",50);
	public static SpriteSheet rick_left1 = new SpriteSheet("/textures/rick/rick_left1.png",50);
	public static SpriteSheet rick_left2 = new SpriteSheet("/textures/rick/rick_left2.png",50);
	public static SpriteSheet rick_right1 = new SpriteSheet("/textures/rick/rick_right1.png",50);
	public static SpriteSheet rick_right2 = new SpriteSheet("/textures/rick/rick_right2.png",50);
	public static SpriteSheet rick_dead1 = new SpriteSheet("/textures/rick/rick_dead1.png",50);

	public static SpriteSheet bomb_exploded = new SpriteSheet("/textures/bomb/bomb_exploded.png",50);
	public static SpriteSheet bomb_exploded1 = new SpriteSheet("/textures/bomb/bomb_exploded1.png",50);
	public static SpriteSheet bomb_exploded2 = new SpriteSheet("/textures/bomb/bomb_exploded2.png",50);

	public static SpriteSheet powerup_bombs = new SpriteSheet("/textures/bonus/x2.png",50);
	public static SpriteSheet powerup_speed = new SpriteSheet("/textures/bonus/speed.png",50);
	public static SpriteSheet powerup_flames = new SpriteSheet("/textures/bonus/flame.png",50);
	public SpriteSheet(String path, int size) {
		_path = path;
		SIZE = size;
		_pixels = new int[SIZE * SIZE];
		load();
	}

	private void load() {
		try {
			File file = new File("./data/res" + _path);
			URL a = file.toURI().toURL();
			BufferedImage image = ImageIO.read(a);
			int w = image.getWidth();
			int h = image.getHeight();
			image.getRGB(0, 0, w, h, _pixels, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
			//TODO: what should this do? stop the program? yes i think
			System.exit(0);
		}
	}
}
