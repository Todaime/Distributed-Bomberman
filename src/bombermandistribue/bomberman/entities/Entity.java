package bombermandistribue.bomberman.entities;

import bombermandistribue.bomberman.graphics.IRender;
import bombermandistribue.bomberman.graphics.Screen;
import bombermandistribue.bomberman.graphics.Sprite;
import bombermandistribue.bomberman.level.Coordinates;

public abstract class Entity implements IRender {

	protected int _x, _y;
	protected boolean _removed = false;
	protected Sprite _sprite;
	
	@Override
	public abstract void update();
	
	@Override
	public abstract void render(Screen screen);
	
	public void remove() {
		_removed = true;
	}
	
	public boolean isRemoved() {
		return _removed;
	}
	
	public Sprite getSprite() {
		return _sprite;
	}
	
	public abstract boolean collide(Entity e);
	
	public int getXPixel() {
		return _x;
	}
	
	public int getYPixel() {
		return _y;
	}
	
	public int getXCase() {
		return Coordinates.pixelToCase(_x);
	}
	
	public int getYCase() {
		return Coordinates.pixelToCase(_y);
	}
	
}
