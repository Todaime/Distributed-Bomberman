package bombermandistribue.bomberman.entities.tile;

import bombermandistribue.bomberman.entities.Entity;
import bombermandistribue.bomberman.graphics.Screen;
import bombermandistribue.bomberman.graphics.Sprite;

public abstract class Tile extends Entity {
	
	
	public Tile(int x_pixel, int y_pixel, Sprite sprite) {
		_x = x_pixel;
		_y = y_pixel;
		_sprite = sprite;
	}
	
	@Override
	public boolean collide(Entity e) {
		return true;
	}
	
	@Override
	public void render(Screen screen) {
		screen.renderEntity( _x, _y, this);
	}
	
	@Override
	public void update() {}
}
