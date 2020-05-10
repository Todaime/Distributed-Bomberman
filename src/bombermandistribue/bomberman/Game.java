package bombermandistribue.bomberman;

import java.util.concurrent.TimeUnit;

import java.rmi.RemoteException;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import bombermandistribue.bomberman.exceptions.BombermanException;
import bombermandistribue.bomberman.graphics.Screen;
import bombermandistribue.bomberman.gui.Frame;
import bombermandistribue.bomberman.input.Keyboard;

public class Game extends Canvas {
	
	/*
	|--------------------------------------------------------------------------
	| Options & Configs
	|--------------------------------------------------------------------------
	 */
	
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public static final int TILES_SIZE = 50,
							WIDTH = TILES_SIZE * 17,
							HEIGHT = 13 * TILES_SIZE;

	public static int SCALE = 1;
	
	public String _title = "Bomberman - Informatique répartie";
	
	//initial configs
	private static final int BOMBRATE = 1;
	private static final int BOMBRADIUS = 1;
	private static final int PLAYERSPEED = 3;
	
	public static final int TIME = 200;
	public static final int LIVES = 3;
	
	protected static int SCREENDELAY = 3;
	
	
	//can be modified with bonus
	private static int[] bombRadius;
	protected static int playerSpeed = PLAYERSPEED;
	private  static int[] bombRate;
	
	//Time in the level screen in seconds
	protected int _screenDelay = SCREENDELAY;
	
	private Keyboard _input;
	private boolean _paused = true;
	
	private Board _board;
	private Screen screen;
	private Frame _frame;
	//this will be used to render the game, each render is a calculated image saved here
	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();

	public Game(Frame frame, int level) throws BombermanException {
		_frame = frame;
		_title = _title + "- Player "+this._frame._playerNum;
		_frame.setTitle(_title);
		screen = new Screen(WIDTH, HEIGHT);
		_input = new Keyboard();
		_board = new Board(this, _input, screen, this._frame._serverGame, this._frame._playerNum, level);
		bombRate = new int[]{1,1};
		bombRadius = new int[]{1,1};
		addKeyListener(_input);
	}
	
	
	private void renderGame() { //render will run the maximum times it can per second
		BufferStrategy bs = getBufferStrategy(); //create a buffer to store images using canvas
		if(bs == null) { //if canvas dont have a bufferstrategy, create it
			createBufferStrategy(3); //triple buffer
			return;
		}
		
		screen.clear();
		
		_board.render(screen);
		
		for (int i = 0; i < pixels.length; i++) { //create the image to be rendered
			pixels[i] = screen._pixels[i];
		}
		
		Graphics g = bs.getDrawGraphics();
		
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		_board.renderMessages(g);
		
		g.dispose(); //release resources
		bs.show(); //make next buffer visible
	}
	
	private void renderScreen() { //TODO: merge these render methods
		BufferStrategy bs = getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(3);
			return;
		}
		
		screen.clear();
		
		Graphics g = bs.getDrawGraphics();
		
		_board.drawScreen(g);

		g.dispose();
		bs.show();
	}

	private void update() {
		_input.update();
		_board.update();
	}
	
	public void start() {
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 60.0; //nanosecond, 60 frames per second
		double delta = 0;
		int frames = 0;
		int updates = 0;
		requestFocus();
		while(_board.getRunningState()) {
			long now;
			do {
				now = System.nanoTime();
				delta += (now - lastTime) / ns;
				lastTime = now;
			} while (delta < 1);
			update();
			updates++;
			delta = 0;
			
			if(_paused) {
				if(_screenDelay <= 0) { //time passed? lets reset status to show the game
					_board.setShow(-1);
					_paused = false;
				}		
				renderScreen();
				if (_board.getEndGameStatut()){
					_board.setShow(1);
					renderScreen();
					try {
						TimeUnit.SECONDS.sleep(5);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					_board.endRunningState();
				}
			} else {
				renderGame();
			}
				
			
			frames++;
			if(System.currentTimeMillis() - timer > 1000) { //once per second
				_frame.setTime(_board.subtractTime());
				_frame.setLives(_board.getLives());
				timer += 1000;
				_frame.setTitle(_title + " | " + updates + " rate, " + frames + " fps");
				updates = 0;
				frames = 0;
				
				if(_board.getShow() == 2)
					--_screenDelay;
			}
		}
	}
	
	/*
	|--------------------------------------------------------------------------
	| Getters & Setters
	|--------------------------------------------------------------------------
	 */

	public static int getPlayerSpeed() {
		return playerSpeed;
	}
	
	public static void setBombRate(int num, int bombNb) {
		bombRate[num] = bombNb;
	}

	public static void setBombRadius(int num, int bombRad) {
		bombRate[num] = bombRad;
	}

	public static int getBombRate(int num) {
		return bombRate[num];
	}
	
	public static int getBombRadius(int numPlayer) {
		return bombRadius[numPlayer];
	}
	
	public static void addPlayerSpeed(int i) {
		playerSpeed += i;
	}
	
	public static void addBombRate(int i, int num) {
		bombRate[num] += i;
	}

	public static void addBombRadius(int i, int num) {
		bombRadius[num] += i;
	}
	//screen delay
	public int getScreenDelay() {
		return _screenDelay;
	}
	
	public void decreaseScreenDelay() {
		_screenDelay--;
	}
	
	public void resetScreenDelay() {
		_screenDelay = SCREENDELAY;
	}

	public Keyboard getInput() {
		return _input;
	}
	
	public Board getBoard() {
		return _board;
	}
	
	public void run() {
		_paused = false;
	}
	
	public void stop() {
		_board.endRunningState();
	}
	
	public boolean isRunning() {
		return _board.getRunningState();
	}
	
	public boolean isPaused() {
		return _paused;
	}
	
	public void pause() {
		_paused = true;
	}
	
}
