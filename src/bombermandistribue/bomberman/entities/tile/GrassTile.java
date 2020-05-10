package bombermandistribue.bomberman.entities.tile;


import bombermandistribue.bomberman.entities.Entity;
import bombermandistribue.bomberman.graphics.Sprite;

public class GrassTile extends Tile {

	public GrassTile(int x, int y, Sprite sprite) {
		super(x, y, sprite);
	}
	
	@Override
	public boolean collide(Entity e) {
		return false;
	}
}
