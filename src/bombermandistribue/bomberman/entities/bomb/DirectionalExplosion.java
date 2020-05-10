package bombermandistribue.bomberman.entities.bomb;

import bombermandistribue.bomberman.Board;
import bombermandistribue.bomberman.Game;
import bombermandistribue.bomberman.entities.Entity;
import bombermandistribue.bomberman.entities.mob.Mob;
import bombermandistribue.bomberman.graphics.Screen;
import bombermandistribue.bomberman.level.Coordinates;

public class DirectionalExplosion extends Entity {

	protected Board _board;
	protected int _direction;
	private int _radius;
	protected double xOrigin, yOrigin;
	protected Explosion[] _explosions;
	
	public DirectionalExplosion(int x, int y, int direction, int radius, Board board) {
		_x = x;
		_y = y;
		_direction = direction;
		_radius = radius;
		_board = board;
		_explosions = new Explosion[ calculatePermitedDistance() ];
		createExplosions();
		}
	
	private void createExplosions() {
		boolean last = false;
		
		int x = _x;
		int y = _y;
		for (int i = 0; i < _explosions.length; i++) {
			last = i == _explosions.length -1 ? true : false;
			
			switch (_direction) {
				case 0: y -= Game.TILES_SIZE; break;
				case 1: x += Game.TILES_SIZE; break;
				case 2: y += Game.TILES_SIZE; break;
				case 3: x -= Game.TILES_SIZE; break;
			}
			_explosions[i] = new Explosion(x, y, _direction, last, _board);
		}
	}
	
	private int calculatePermitedDistance() {
		int radius = 0;
		int x_case = Coordinates.pixelToCase(_x);
		int y_case = Coordinates.pixelToCase(_y);
		while(radius < _radius) {
			if(_direction == 0) y_case--;
			if(_direction == 1) x_case++;
			if(_direction == 2) y_case++;
			if(_direction == 3) x_case--;
			
			Entity a = _board.getEntity(x_case, y_case, null);
		
			if(a instanceof Mob) ++radius; //explosion has to be below the mob
			
			if(a.collide(this) == true) //cannot pass thru
				break;
			
			// In case a part of the top and left entity are crossing the tiles
			Entity b = _board.getEntity(x_case-1, y_case, null);
			Entity c = _board.getEntity(x_case, y_case-1, null);
			if(b instanceof Mob) {
				Mob b_mob = _board.getMobAt(x_case-1, y_case);
				int cote_droit_case_x_b = Coordinates.pixelToCase(b.getXPixel()+0.9*Game.TILES_SIZE);
				if (cote_droit_case_x_b == x_case )
					b_mob.kill();
					break;
			}
			if (c instanceof Mob) {
				Mob c_mob = _board.getMobAt(x_case, y_case-1);
				int cote_bas_case_y_c = Coordinates.pixelToCase(c.getYPixel()+0.9*Game.TILES_SIZE);
				if (cote_bas_case_y_c == y_case )
					c_mob.kill();
					break;
			}
			
			
			
			++radius;
		}
		return radius;
	}
	
	public Explosion explosionAt(int x,int y) {
		for (int i = 0; i < _explosions.length; i++) {
			if(_explosions[i].getXCase() == x && _explosions[i].getYCase() == y) 
				return _explosions[i];
		}
		return null;
	}

	@Override
	public void update() {}
	
	@Override
	public void render(Screen screen) {
		
		for (int i = 0; i < _explosions.length; i++) {
			_explosions[i].render(screen);
		}
	}

	@Override
	public boolean collide(Entity e) {
		
		return true;
	}
}
