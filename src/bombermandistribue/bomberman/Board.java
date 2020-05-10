package bombermandistribue.bomberman;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import bombermandistribue.bomberman.entities.Entity;
import bombermandistribue.bomberman.entities.Message;
import bombermandistribue.bomberman.entities.bomb.Bomb;
import bombermandistribue.bomberman.entities.bomb.Explosion;
import bombermandistribue.bomberman.entities.mob.Mob;
import bombermandistribue.bomberman.entities.mob.Player;
import bombermandistribue.bomberman.entities.tile.powerup.Powerup;
import bombermandistribue.bomberman.exceptions.LoadLevelException;
import bombermandistribue.bomberman.graphics.IRender;
import bombermandistribue.bomberman.graphics.Screen;
import bombermandistribue.bomberman.input.Keyboard;
import bombermandistribue.bomberman.level.FileLevel;
import bombermandistribue.bomberman.level.Level;

import server.ServerGame;

public class Board implements IRender {
	private static final long serialVersionUID = 1L;
	public int _width, _height;
	protected Level _level;
	protected Game _game;
	protected Keyboard _input;
	protected Screen _screen;

	public Entity[] _entities;
	public List<Mob> _mobs = new ArrayList<Mob>();
	protected List<Bomb> _bombs = new ArrayList<Bomb>();
	private List<Message> _messages = new ArrayList<Message>();

	private int _screenToShow = -1; //1:endgame, 2:changelevel, 3:paused

	private int _time = Game.TIME;
	private int _lives = Game.LIVES;
	private Running _running;
	private int _winner = -1;
	private ServerGame _serverGame;
	private int _playerNum;
	private List<Message> _pseudo = new ArrayList<Message>();

	protected Board(Game game, 
				 Keyboard input,
				 Screen screen,
				 ServerGame serverGame,
				 int playerNum,
				 int level){
		_game = game;
		_playerNum = playerNum;
		System.out.print(playerNum);
		_input = input;
		_screen = screen;
		_serverGame = serverGame;
		try{ 
			_running = new RunningIMPL();
			_serverGame.addRunningGame(_running);
			_serverGame.setServerReset();
			_pseudo.add(new Message(serverGame.getPlayerID(0), 0, 0, 1, Color.white, 14));
			_pseudo.add(new Message(serverGame.getPlayerID(1), 0, 0, 1, Color.white, 14));
		} catch (RemoteException e){
			e.printStackTrace();
		}
		changeLevel(level); //start the correct map
	}

	/*
	|--------------------------------------------------------------------------
	| Render & Update
	|--------------------------------------------------------------------------
	 */
	@Override
	public void update() {
		if( _game.isPaused() ) return;

		updateEntities();
		updateMobs();
		updateBombs();
		updateMessages();
		detectEndGame();

		for (int i = 0; i < _mobs.size(); i++) {
			Mob a = _mobs.get(i);
			if(((Entity)a).isRemoved()) _mobs.remove(i);
		}
	}


	@Override
	public void render(Screen screen) {
		if( _game.isPaused() ) return;

		for (int i = 0; i < _entities.length; i++)
			_entities[i].render(screen);
		renderBombs(screen);
		renderMobs(screen);
	}

	/*
	|--------------------------------------------------------------------------
	| ChangeLevel
	|--------------------------------------------------------------------------
	 */
	public void newGame() {
		resetProperties();
		changeLevel(1);
	}

	@SuppressWarnings("static-access")
	private void resetProperties() {
		_lives = Game.LIVES;
		Player._powerups.clear();

		_game.playerSpeed = 3;
		_game.setBombRadius(0,1);
		_game.setBombRadius(1,1);
		_game.setBombRate(0,1);
		_game.setBombRate(1,1);

	}

	public void restartLevel() {
		changeLevel(_level.getLevel());
	}

	public void nextLevel() {
		changeLevel(_level.getLevel() + 1);
	}

	public void changeLevel(int level) {
		_time = Game.TIME;
		_screenToShow = 2;
		_game.resetScreenDelay();
		_game.pause();
		_mobs.clear();
		_bombs.clear();
		_messages.clear();
		try {
			_level = new FileLevel("data/Levels/Level" + level + ".txt", this);
			_entities = new Entity[_level.getHeight() * _level.getWidth()];

			_level.createEntities();
		} catch (LoadLevelException e) {
			endGame(); //failed to load.. so.. no more levels?
		}
	}

	public boolean isPowerupUsed(int x, int y, int level) {
		Powerup p;
		for (int i = 0; i < Player._powerups.size(); i++) {
			p = Player._powerups.get(i);
			if(p.getXCase() == x && p.getYCase() == y && level == p.getLevel())
				return true;
		}

		return false;
	}

	/*
	|--------------------------------------------------------------------------
	| Detections
	|--------------------------------------------------------------------------
	 */
	protected void detectEndGame() {
		if(_time <= 0)
			restartLevel();
	}

	public void endGame() {
		_screenToShow = 1;
		_game.resetScreenDelay();
		_game.pause();
	}

	public boolean detectNoEnemies() {
		int total = 0;
		for (int i = 0; i < _mobs.size(); i++) {
			if(_mobs.get(i) instanceof Player == false)
				++total;
		}

		return total == 0;
	}

	/*
	|--------------------------------------------------------------------------
	| Pause & Resume
	|--------------------------------------------------------------------------
	 */
	public void gamePause() {
		_game.resetScreenDelay();
		if(_screenToShow <= 0)
			_screenToShow = 3;
		_game.pause();
	}

	public void gameResume() {
		_game.resetScreenDelay();
		_screenToShow = -1;
		_game.run();
	}

	/*
	|--------------------------------------------------------------------------
	| Screens
	|--------------------------------------------------------------------------
	 */
	public void drawScreen(Graphics g) {
		switch (_screenToShow) {
			case 1:
				_screen.drawEndGame(g,_winner,_playerNum);
				break;
			case 2:
				_screen.drawChangeLevel(g, _level.getLevel());
				break;
		}
	}

	/*
	|--------------------------------------------------------------------------
	| Getters And Setters
	|--------------------------------------------------------------------------
	 */
	public Entity getEntity(int x, int y, Mob m) {

		Entity res = null;

		res = getExplosionAt(x, y);

		if( res != null) return res;

		res = getBombAt(x, y);
		if( res != null) return res;

		res = getMobAtExcluding(x, y, m);
		if( res != null) return res;

		res = getEntityAt(x, y);

		return res;
	}

	public List<Bomb> getBombs() {
		return _bombs;
	}

	public Bomb getBombAt(int x_case, int y_case) {
		Iterator<Bomb> bs = _bombs.iterator();
		Bomb b;
		while(bs.hasNext()) {
			b = bs.next();
			if(b.getXCase() == x_case && b.getYCase() == x_case)
				return b;
		}

		return null;
	}

	public Mob getMobAt(int x, int y) {
		Iterator<Mob> itr = _mobs.iterator();

		Mob cur;
		while(itr.hasNext()) {
			cur = itr.next();

			if(cur.getXCase() == x && cur.getYCase() == y)
				return cur;
		}

		return null;
	}

	public Player getPlayer() {
		Iterator<Mob> itr = _mobs.iterator();

		Mob cur;
		while(itr.hasNext()) {
			cur = itr.next();

			if(cur instanceof Player)
				return (Player) cur;
		}

		return null;
	}

	public Mob getMobAtExcluding(int x, int y, Mob a) {
		Iterator<Mob> itr = _mobs.iterator();

		Mob cur;
		while(itr.hasNext()) {
			cur = itr.next();
			if(cur == a) {
				continue;
			}

			if(cur.getXCase() == x && cur.getYCase() == y) {
				return cur;
			}

		}

		return null;
	}

	public Explosion getExplosionAt(int x, int y) {
		Iterator<Bomb> bs = _bombs.iterator();
		Bomb b;
		while(bs.hasNext()) {
			b = bs.next();

			Explosion e = b.explosionAt(x, y);
			if(e != null) {
				return e;
			}
		}

		return null;
	}

	public Entity getEntityAt(int x, int y) {
		return _entities[x + y * _level.getWidth()];
	}

	/*
	|--------------------------------------------------------------------------
	| Adds and Removes
	|--------------------------------------------------------------------------
	 */
	public void addEntitie(int pos, Entity e) {
		_entities[pos] = e;
	}

	public void addMob(Mob e) {
		_mobs.add(e);
	}

	public void addBomb(Bomb e) {
		_bombs.add(e);
	}

	public void addMessage(Message e) {
		_messages.add(e);
	}

	/*
	|--------------------------------------------------------------------------
	| Renders
	|--------------------------------------------------------------------------
	 */
	protected void renderEntities(Screen screen) {
		for (int i = 0; i < _entities.length; i++) {
			_entities[i].render(screen);
		}
	}

	protected void renderMobs(Screen screen) {
		Iterator<Mob> itr = _mobs.iterator();

		while(itr.hasNext())
			itr.next().render(screen);
	}

	protected void renderBombs(Screen screen) {
		Iterator<Bomb> itr = _bombs.iterator();

		while(itr.hasNext())
			itr.next().render(screen);
	}

	public void renderMessages(Graphics g) {
		Message m;
		for (int i = 0; i < _messages.size(); i++) {
			m = _messages.get(i);
			g.setFont(new Font("Arial", Font.PLAIN, m.getSize()));
			g.setColor(m.getColor());
			g.drawString(m.getMessage(), (int)m.getXPixel() , (int)m.getYPixel());
		}
		renderPseudo(g, 0);
		renderPseudo(g, 1);
	}

	public void renderPseudo(Graphics g, int num){
		g.setFont(new Font("Arial", Font.BOLD, _pseudo.get(num).getSize()));
		g.setColor(_pseudo.get(num).getColor());
		g.drawString(_pseudo.get(num).getMessage(), (int)_pseudo.get(num).getXPixel() , (int)_pseudo.get(num).getYPixel());
	}

	/*
	|--------------------------------------------------------------------------
	| Updates
	|--------------------------------------------------------------------------
	 */
	protected void updateEntities() {
		if( _game.isPaused() ) return;
		for (int i = 0; i < _entities.length; i++) {
			_entities[i].update();
		}
	}

	protected void updateMobs() {
		if( _game.isPaused() ) return;
		Iterator<Mob> itr = _mobs.iterator();

		while(itr.hasNext() && !_game.isPaused())
			itr.next().update();
	}

	protected void updateBombs() {
		if( _game.isPaused() ) return;
		Iterator<Bomb> itr = _bombs.iterator();

		while(itr.hasNext())
			itr.next().update();
	}

	protected void updateMessages() {
		if( _game.isPaused() ) return;
		Message m;
		double left = 0;
		for (int i = 0; i < _messages.size(); i++) {
			m = _messages.get(i);
			left = m.getDuration();

			if(left > 0)
				m.setDuration(left-1);
			else
				_messages.remove(i);
		}
	}

	/*
	|--------------------------------------------------------------------------
	| Getters & Setters
	|--------------------------------------------------------------------------
	 */

	public  void setWinner(int num){
		_winner = num;
	}

	public  void setPosPseudo(int xNew, int yNew, int num){
		_pseudo.get(num).setPos(xNew, yNew);
	}

	public Keyboard getInput() {
		return _input;
	}

	public ServerGame getServerGame(){
		return _serverGame;
	}

	public int getPlayerNum(){
		return _playerNum;
	}

	public Level getLevel() {
		return _level;
	}

	public Game getGame() {
		return _game;
	}

	public int getShow() {
		return _screenToShow;
	}

	public void setShow(int i) {
		_screenToShow = i;
	}

	public int getTime() {
		return _time;
	}

	public int getLives() {
		return _lives;
	}

	public int subtractTime() {
		if(_game.isPaused())
			return this._time;
		else
			return this._time--;
	}

	public void addLives(int lives) {
		this._lives += lives;
	}

	public int getWidth() {
		return _level.getWidth();
	}

	public int getHeight() {
		return _level.getHeight();
	}

	public boolean getEndGameStatut() {
		return this._screenToShow == 1;
	}

	public void endRunningState(){
		try{ 
			_serverGame.endRunningState();
		} catch (RemoteException e){
			e.printStackTrace();
		}
	}

	public boolean getRunningState(){
		try{ 
			//return _serverGame.getRunningState();
			return _running.getState();
		} catch (RemoteException e){
			e.printStackTrace();
		}
		return false;
	}
}
