package bombermandistribue.bomberman.entities.bomb;

import bombermandistribue.bomberman.Board;
import bombermandistribue.bomberman.Game;
import bombermandistribue.bomberman.entities.AnimatedEntitiy;
import bombermandistribue.bomberman.entities.Entity;
import bombermandistribue.bomberman.entities.mob.Mob;
import bombermandistribue.bomberman.entities.mob.Player;
import bombermandistribue.bomberman.graphics.Screen;
import bombermandistribue.bomberman.graphics.Sprite;
import bombermandistribue.bomberman.level.Coordinates;

public class Bomb extends AnimatedEntitiy {

	//options
	protected double _timeToExplode = 120; //2 seconds
	public int _timeAfter = 20; //time to explosions disapear
	
	protected Board _board;
	protected boolean _allowedToPassThru = true;
	protected DirectionalExplosion[] _explosions = null;
	protected boolean _exploded = false;
	protected int _number;
	public Bomb(int x, int y,Board board, int number) {
		_x = x;
		_y = y;
		_board = board;
		_sprite = Sprite.bomb;
		_number = number;
	}
	
	@Override
	public void update() {
		if(_timeToExplode > 0) 
			_timeToExplode--;
		else {
			if(!_exploded) 
				explosion();
			if(_timeAfter > 0) 
				_timeAfter--;
			else
				remove();
		}
			
		animate();
	}
	
	@Override
	public void render(Screen screen) {
		if(_exploded) {
			_sprite =  Sprite.bomb_exploded1;
			renderExplosions(screen);
		} else
			_sprite = Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, _animate, 60);
		screen.renderEntity(_x, _y , this);
	}
	
	public void renderExplosions(Screen screen) {
		for (int i = 0; i < _explosions.length; i++) {
			_explosions[i].render(screen);
		}
	}
	
	public void updateExplosions() {
		for (int i = 0; i < _explosions.length; i++) {
			_explosions[i].update();
		}
	}
	
	public void explode() {
		_timeToExplode = 0;
	}
	
	protected void explosion() {
		_allowedToPassThru = true;
		_exploded = true;
		int x_case = Coordinates.pixelToCase(_x);
		int y_case = Coordinates.pixelToCase(_y);;
		Mob a = _board.getMobAt(x_case, y_case);
		if(a != null)  {
			a.kill();
		}
		
		_explosions = new DirectionalExplosion[4];
		
		for (int i = 0; i < _explosions.length; i++) {
			_explosions[i] = new DirectionalExplosion(_x, _y, i, Game.getBombRadius(_number), _board);
		}
	}
	
	public Explosion explosionAt(int x, int y) {
		if(!_exploded) return null;
		
		for (int i = 0; i < _explosions.length; i++) {
			if(_explosions[i] == null) return null;
			Explosion e = _explosions[i].explosionAt(x, y);
			if(e != null) return e;
		}
		
		return null;
	}
	
	public boolean isExploded() {
		return _exploded;
	}
	

	@Override
	public boolean collide(Entity e) {
		
		if(e instanceof Player) {
			return false;
		}
		
		if(e instanceof DirectionalExplosion) {
			explode();
			return true;
		}
		
		return false;
	}

	public int getNumber(){
		return _number;
	}
}
