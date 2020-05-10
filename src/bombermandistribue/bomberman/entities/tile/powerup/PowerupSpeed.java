package bombermandistribue.bomberman.entities.tile.powerup;

import bombermandistribue.bomberman.Game;
import bombermandistribue.bomberman.entities.Entity;
import bombermandistribue.bomberman.entities.mob.Player;
import bombermandistribue.bomberman.graphics.Sprite;

public class PowerupSpeed extends Powerup {

	public PowerupSpeed(int x, int y, int level, Sprite sprite) {
		super(x, y, level, sprite);
	}
	
	@Override
	public boolean collide(Entity e) {
		
		if(e instanceof Player) {
			((Player) e).addPowerup(this);
			remove();
			return true;
		}
		
		return false;
	}
	
	@Override
	public void setValues() {
		_active = true;
		Game.addPlayerSpeed(1);
	}
	


}
