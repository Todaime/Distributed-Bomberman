package bombermandistribue.bomberman.entities.bomb;

import bombermandistribue.bomberman.Board;
import bombermandistribue.bomberman.entities.Entity;
import bombermandistribue.bomberman.entities.mob.Mob;
import bombermandistribue.bomberman.graphics.Screen;
import bombermandistribue.bomberman.graphics.Sprite;


public class Explosion extends Entity {

	protected boolean _last = false;
	protected Board _board;
	protected Sprite _sprite1, _sprite2;
	
	public Explosion(int x_pixel, int y_pixel, int direction, boolean last, Board board) {
		_x = x_pixel;
		_y = y_pixel;
		_last = last;
		_board = board;
		
		switch (direction) {
			case 0:
				if(last == false) {
					_sprite = Sprite.explosion_vertical2;
				} else {
					_sprite = Sprite.explosion_vertical_top_last2;
				}
			break;
			case 1:
				if(last == false) {
					_sprite = Sprite.explosion_horizontal2;
				} else {
					_sprite = Sprite.explosion_horizontal_right_last2;
				}
				break;
			case 2:
				if(last == false) {
					_sprite = Sprite.explosion_vertical2;
				} else {
					_sprite = Sprite.explosion_vertical_down_last2;
				}
				break;
			case 3: 
				if(last == false) {
					_sprite = Sprite.explosion_horizontal2;
				} else {
					_sprite = Sprite.explosion_horizontal_left_last2;
				}
				break;
		}
	}
	
	@Override
	public void render(Screen screen) {
		screen.renderEntity(_x, _y , this);
	}
	
	@Override
	public void update() {}

	@Override
	public boolean collide(Entity e) {
		
		if(e instanceof Mob) {
			System.out.println("Tué dans explosion");
			((Mob)e).kill();
		}
		
		return true;
	}
	

}