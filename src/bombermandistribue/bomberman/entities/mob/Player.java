package bombermandistribue.bomberman.entities.mob;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import java.rmi.RemoteException;

import bombermandistribue.bomberman.Board;
import bombermandistribue.bomberman.Game;
import bombermandistribue.bomberman.entities.Entity;
import bombermandistribue.bomberman.entities.Message;
import bombermandistribue.bomberman.entities.bomb.Bomb;
import bombermandistribue.bomberman.entities.bomb.DirectionalExplosion;
import bombermandistribue.bomberman.entities.tile.powerup.Powerup;
import bombermandistribue.bomberman.graphics.Screen;
import bombermandistribue.bomberman.graphics.Sprite;
import bombermandistribue.bomberman.input.Keyboard;
import bombermandistribue.bomberman.level.Coordinates;
import server.ServerGame;
import server.PlayerPosition;

import bombermandistribue.bomberman.entities.tile.powerup.PowerupBombs;
import bombermandistribue.bomberman.entities.tile.powerup.PowerupFlames;

public class Player extends Mob {
	
	private List<Bomb> _bombs;
	protected Keyboard _input;
	private ServerGame _serverGame;
	private int _number;
	private boolean _myPlayer = false;

	protected int _timeBetweenPutBombs = 0;
	
	public static List<Powerup> _powerups = new ArrayList<Powerup>();
	   public static StackTraceElement[] getTrace()
	   {
	      //On récupére la pile d'excution
	      Throwable throwable = new Throwable();
	      StackTraceElement[] trace = throwable.getStackTrace();
	      //On enléve le premier élément qui est l'appel de cette méthode
	      StackTraceElement[] reponse = new StackTraceElement[trace.length - 1];
	      System.arraycopy(trace, 1, reponse, 0, reponse.length);
	      //On envoie la pile d'excution
	      return reponse;
	   }
	
	public Player(int num,int x, int y, Board board) {
		super(x, y, board);
		_number = num;
		_bombs = _board.getBombs();
		_input = _board.getInput();
		_serverGame = _board.getServerGame();
		if (_board.getPlayerNum() == _number+1){
			_myPlayer = true;
			_board.setPosPseudo(_x, _y, _number);
			try {
				_serverGame.notifyPlayerMove(new PlayerPosition(_x,_y,_direction), _number);
			} catch(RemoteException e){
				e.printStackTrace();
			}
		}
		_sprite = Sprite.player_right[_number];
	}
	
	
	/*
	|--------------------------------------------------------------------------
	| Update & Render
	|--------------------------------------------------------------------------
	 */
	@Override
	public void update() {
		clearBombs();
		if(_alive == false) {
			afterKill();
			return;
		}
		
		if(_timeBetweenPutBombs < -7500) _timeBetweenPutBombs = 0; else _timeBetweenPutBombs--; //dont let this get tooo big
		
		animate();

		calculateMove();
		detectPlaceBomb();
	}
	
	@Override
	public void render(Screen screen) {
		if(_alive)
			chooseSprite();
		else
			_sprite = Sprite.player_dead1[_number];
		
		screen.renderEntity(_x, _y, this);
	}



	/*
	|--------------------------------------------------------------------------
	| Mob Unique
	|--------------------------------------------------------------------------
	 */
	private void detectPlaceBomb() {
		if(Game.getBombRate(_number) > 0 && _timeBetweenPutBombs < 0) {
			try{
				if (_myPlayer == true && _input.space){
					_serverGame.notifyPlayerBomb(_number);
					placeBomb(_x,_y);
					Game.addBombRate(-1,_number);
					_timeBetweenPutBombs = 30;
				} else {
					if (_serverGame.getBomb(_number)==5){
						Game.addBombRate(-1,_number);
						placeBomb(_x,_y);
					}
				}
			} catch(RemoteException e){
				e.printStackTrace();
			}
		}
	}
	
	protected void placeBomb(int x, int y) {
		int x_case = Coordinates.pixelToCase(x+Game.TILES_SIZE/2);
		int y_case = Coordinates.pixelToCase(y+Game.TILES_SIZE/2);
		int x_pixel = Coordinates.caseToPixel(x_case);
		int y_pixel = Coordinates.caseToPixel(y_case);
		Bomb b = new Bomb(x_pixel, y_pixel, _board, _number); // Pixel coordinate
		_board.addBomb(b);
	}
	
	private void clearBombs() {
		Iterator<Bomb> bs = _bombs.iterator();
		
		Bomb b;
		while(bs.hasNext()) {
			b = bs.next();
			if(b.isRemoved() & b.getNumber()==_number)  {
				bs.remove();
				Game.addBombRate(1, _number);
			}
		}
		
	}
	
	/*
	|--------------------------------------------------------------------------
	| Mob Colide & Kill
	|--------------------------------------------------------------------------
	 */
	@Override
	public void kill() {
		if(!_alive) return;
		
		_alive = false;
		
		_board.addLives(-1);

		Message msg = new Message("-1 LIVE", (int)getXMessage(), (int)getYMessage(), 2, Color.white, 14);
		_board.addMessage(msg);
	}
	
	@Override
	protected void afterKill() {
		if(_timeAfter > 0) --_timeAfter;
		else {
			if(_bombs.size() == 0) {
				
				//if(_board.getLives() == 0)
				if (_number == 0)
					_board.setWinner(1);
				else 
					_board.setWinner(0);
				_board.endGame();
				//else
				//	_board.restartLevel();
			}
		}
	}
	
	/*
	|--------------------------------------------------------------------------
	| Mob Movement
	|--------------------------------------------------------------------------
	 */
	@Override
	protected void calculateMove() {
		int xa = 0, ya = 0;
		try {
			if (_myPlayer != true){
					PlayerPosition playerPos = _serverGame.getMove(_number);
					_x = playerPos.posX;
					_y = playerPos.posY;
					_direction = playerPos.direction;
					_board.setPosPseudo(_x, _y, _number);
					canMove(0,0);
			}else {
					if(_input.up){
							ya--;
					}
					if(_input.down){
						ya++;
					}
					if(_input.left){
						xa--;
					}
					if(_input.right){
						xa++;
					}
					if(xa != 0 || ya != 0)  {
						move(xa * Game.getPlayerSpeed(), ya * Game.getPlayerSpeed());
					} else {
						_moving = false;
					}
				}
			} catch(RemoteException e){
				e.printStackTrace();
			}
	}
	
	@Override
	public boolean canMove(int x, int y) {
		int case_x_1 = Coordinates.pixelToCase(_x +0.1*Game.TILES_SIZE+ x);
		int case_x_2 = Coordinates.pixelToCase(_x +0.9*Game.TILES_SIZE + x);
		int case_y_1 = Coordinates.pixelToCase(_y + 0.1*Game.TILES_SIZE+y);
		int case_y_2 = Coordinates.pixelToCase(_y +0.9*Game.TILES_SIZE + y);

		Entity a_x1_y1 = _board.getEntity(case_x_1, case_y_1, this);
		Entity a_x1_y2 = _board.getEntity(case_x_1, case_y_2, this);
		Entity a_x2_y1 = _board.getEntity(case_x_2, case_y_1, this);
		Entity a_x2_y2 = _board.getEntity(case_x_2, case_y_2, this);
		
		if(a_x1_y1.collide(this) || a_x1_y2.collide(this) || a_x2_y1.collide(this) || a_x2_y2.collide(this) ) 
			return false;
		return true;
	}

	@Override
	public void move(int xa, int ya) {
		if(xa > 0) _direction = 1;
		if(xa < 0) _direction = 3;
		if(ya > 0) _direction = 2;
		if(ya < 0) _direction = 0;
		
		if(canMove(xa, ya)) { //separate the moves for the player can slide when is colliding
			_y += ya;
			_x += xa;
			try{
				_serverGame.notifyPlayerMove(new PlayerPosition(_x,_y,_direction),_number);
				_board.setPosPseudo(_x, _y, _number);
			} catch(RemoteException e){
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public boolean collide(Entity e) {
		if(e instanceof DirectionalExplosion) {
			kill();
			return true;
		}
		
		return false;
	}
	
	/*
	|--------------------------------------------------------------------------
	| Powerups
	|--------------------------------------------------------------------------
	 */
	public void addPowerup(Powerup p) {
		if(p.isRemoved()) return;
		
		_powerups.add(p);
		p.setValues();
	}

	public void addBombPowerup(PowerupBombs p){
		p.setBombValues(_number);
	}

	public void addFlamePowerup(PowerupFlames p){
		p.setFlameValues(_number);
	}
	
	public void clearUsedPowerups() {
		Powerup p;
		for (int i = 0; i < _powerups.size(); i++) {
			p = _powerups.get(i);
			if(p.isActive() == false)
				_powerups.remove(i);
		}
	}
	
	public void removePowerups() {
		for (int i = 0; i < _powerups.size(); i++) {
				_powerups.remove(i);
		}
	}
	
	/*
	|--------------------------------------------------------------------------
	| Mob Sprite
	|--------------------------------------------------------------------------
	 */
	private void chooseSprite() {
		switch(_direction) {
		case 0:
			_sprite = Sprite.player_up[_number];
			if(_moving) {
				_sprite = Sprite.movingSprite(Sprite.player_up1[_number], Sprite.player_up2[_number], _animate, 20);
			}
			break;
		case 1:
			_sprite = Sprite.player_right[_number];
			if(_moving) {
				_sprite = Sprite.movingSprite(Sprite.player_right1[_number], Sprite.player_right2[_number], _animate, 20);
			}
			break;
		case 2:
			_sprite = Sprite.player_down[_number];
			if(_moving) {
				_sprite = Sprite.movingSprite(Sprite.player_down1[_number], Sprite.player_down2[_number], _animate, 20);
			}
			break;
		case 3:
			_sprite = Sprite.player_left[_number];
			if(_moving) {
				_sprite = Sprite.movingSprite(Sprite.player_left1[_number], Sprite.player_left2[_number], _animate, 20);
			}
			break;
		default:
			_sprite = Sprite.player_right[_number];
			if(_moving) {
				_sprite = Sprite.movingSprite(Sprite.player_right1[_number], Sprite.player_right2[_number], _animate, 20);
			}
			break;
		}
	}

	public int getNum(){
		return _number;
	}
}
