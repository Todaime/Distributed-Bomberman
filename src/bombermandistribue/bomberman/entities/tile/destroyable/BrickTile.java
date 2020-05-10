package bombermandistribue.bomberman.entities.tile.destroyable;


import bombermandistribue.bomberman.entities.Entity;
import bombermandistribue.bomberman.entities.bomb.DirectionalExplosion;
import bombermandistribue.bomberman.entities.mob.Player;
import bombermandistribue.bomberman.graphics.Screen;
import bombermandistribue.bomberman.graphics.Sprite;

public class BrickTile extends DestroyableTile {
	
	public BrickTile(int x, int y, Sprite sprite) {
		super(x, y, sprite);
	}
	
	@Override
	public void update() {
		super.update();
	}
	
	@Override
	public void render(Screen screen) {
		
		if(_destroyed) {
			_sprite = movingSprite(Sprite.brick_exploded, Sprite.brick_exploded1, Sprite.brick_exploded2);
			
			screen.renderEntityWithBelowSprite(_x, _y, this, _belowSprite);
		}
		else
			screen.renderEntity( _x, _y, this);
	}
	
	@Override
	public boolean collide(Entity e) {
		
		if(e instanceof DirectionalExplosion)
			destroy();
		if (e instanceof Player)
			return true;
		return false;
	}
	
	
}
