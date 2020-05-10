package bombermandistribue.bomberman.entities.tile.powerup;

import bombermandistribue.bomberman.Game;
import bombermandistribue.bomberman.entities.Entity;
import bombermandistribue.bomberman.entities.mob.Player;
import bombermandistribue.bomberman.graphics.Sprite;

public class PowerupBombs extends Powerup {

	public PowerupBombs(int x, int y, int level, Sprite sprite) {
		super(x, y, level, sprite);
	}
	
	@Override
	public boolean collide(Entity e) {
		
		if(e instanceof Player) {
			((Player) e).addBombPowerup(this);
			remove();
			return true;
		}
		
		return false;
	}
	
	public void setBombValues(int number) {
		_active = true;
		Game.addBombRate(1, number);
	}

	@Override
	public void setValues() {
		_active = true;
	}
}
