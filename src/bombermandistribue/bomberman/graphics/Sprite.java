package bombermandistribue.bomberman.graphics;

public class Sprite {
	
	public final int SIZE;
	private int _x, _y;
	public int[] _pixels;
	protected int _realWidth;
	protected int _realHeight;
	private SpriteSheet _sheet;
	
	public static Sprite voidSprite = new Sprite(50, 0xffffff); //black
	
	/*
	|--------------------------------------------------------------------------
	| Board sprites
	|--------------------------------------------------------------------------
	 */
	public static Sprite ground = new Sprite(50,SpriteSheet.ground, 50,50);
	public static Sprite brick = new Sprite(50,SpriteSheet.brick, 50,50);
	public static Sprite wall = new Sprite(50,SpriteSheet.wall, 50,50);
	
	/*
	|--------------------------------------------------------------------------
	| Player Sprites
	|--------------------------------------------------------------------------
	 */
	public static Sprite[] player_right = {new Sprite(50,SpriteSheet.morty_right, 50,50),
	        new Sprite(50,SpriteSheet.rick_right, 50,50)};
	
	public static Sprite[] player_up = {new Sprite(50,SpriteSheet.morty_up, 50,50),
            new Sprite(50,SpriteSheet.rick_up, 50,50)};
	
	public static Sprite[] player_down = {new Sprite(50,SpriteSheet.morty_down, 50,50),
			new Sprite(50,SpriteSheet.rick_down, 50,50)};
	
	public static Sprite[] player_left = {new Sprite(50,SpriteSheet.morty_left, 50,50),
            new Sprite(50,SpriteSheet.rick_left, 50,50)};
	
	public static Sprite[] player_up1 = {new Sprite(50,SpriteSheet.morty_up1, 50,50),
            new Sprite(50,SpriteSheet.rick_up1, 50,50)};
	
	public static Sprite[] player_up2 = {new Sprite(50,SpriteSheet.morty_up2, 50,50),
            new Sprite(50,SpriteSheet.rick_up2, 50,50)};
	
	public static Sprite[] player_down1 = {new Sprite(50,SpriteSheet.morty_down1, 50,50),
            new Sprite(50,SpriteSheet.rick_down1, 50,50)};
	
	public static Sprite[] player_down2 = {new Sprite(50,SpriteSheet.morty_down2, 50,50),
            new Sprite(50,SpriteSheet.rick_down2, 50,50)};
	
	public static Sprite[] player_left1 = {new Sprite(50,SpriteSheet.morty_left1, 50,50),
            new Sprite(50,SpriteSheet.rick_left1, 50,50)};
	
	public static Sprite[] player_left2 = {new Sprite(50,SpriteSheet.morty_left2, 50,50),
            new Sprite(50,SpriteSheet.rick_left2, 50,50)};
	
	public static Sprite[] player_right1 = {new Sprite(50,SpriteSheet.morty_right1, 50,50),
            new Sprite(50,SpriteSheet.rick_right1, 50,50)};
	
	public static Sprite[] player_right2 = {new Sprite(50,SpriteSheet.morty_right2, 50,50),
            new Sprite(50,SpriteSheet.rick_right2, 50,50)};
	
	public static Sprite[] player_dead1 = {new Sprite(50,SpriteSheet.morty_dead1, 50,50),
            new Sprite(50,SpriteSheet.rick_dead1, 50,50)};

	public static Sprite[] player_dead2 = {new Sprite(50,SpriteSheet.ground, 50,50),
            new Sprite(50,SpriteSheet.ground, 50,50)};
	
	public static Sprite[] player_dead3 = {new Sprite(50,SpriteSheet.ground, 50,50),
            new Sprite(50,SpriteSheet.ground, 50,50)};
	
	/*
	|--------------------------------------------------------------------------
	| Bomb Sprites
	|--------------------------------------------------------------------------
	 */
	public static Sprite bomb = new Sprite(50,SpriteSheet.bomb0, 50,50);
	public static Sprite bomb_1 = new Sprite(50,SpriteSheet.bomb1, 50,50);
	public static Sprite bomb_2 = new Sprite(50,SpriteSheet.bomb2, 50,50);
	
	/*
	|--------------------------------------------------------------------------
	| Explosion Sprites
	|--------------------------------------------------------------------------
	 */
	public static Sprite bomb_exploded = new Sprite(50,SpriteSheet.bomb_exploded, 50,50);
	public static Sprite bomb_exploded1 = new Sprite(50,SpriteSheet.bomb_exploded1, 50,50);
	public static Sprite bomb_exploded2 = new Sprite(50,SpriteSheet.bomb_exploded2, 50,50);
	
	public static Sprite explosion_vertical = new Sprite(50,SpriteSheet.bomb_exploded, 50,50);
	public static Sprite explosion_vertical1 = new Sprite(50,SpriteSheet.bomb_exploded, 50,50);
	public static Sprite explosion_vertical2 = new Sprite(50,SpriteSheet.bomb_exploded, 50,50);
	
	public static Sprite explosion_horizontal = new Sprite(50,SpriteSheet.bomb_exploded, 50,50);
	public static Sprite explosion_horizontal1 = new Sprite(50,SpriteSheet.bomb_exploded, 50,50);
	public static Sprite explosion_horizontal2 = new Sprite(50,SpriteSheet.bomb_exploded, 50,50);
	
	public static Sprite explosion_horizontal_left_last = new Sprite(50,SpriteSheet.bomb_exploded, 50,50);
	public static Sprite explosion_horizontal_left_last1 = new Sprite(50,SpriteSheet.bomb_exploded, 50,50);
	public static Sprite explosion_horizontal_left_last2 = new Sprite(50,SpriteSheet.bomb_exploded, 50,50);
	
	public static Sprite explosion_horizontal_right_last = new Sprite(50,SpriteSheet.bomb_exploded, 50,50);
	public static Sprite explosion_horizontal_right_last1 = new Sprite(50,SpriteSheet.bomb_exploded, 50,50);
	public static Sprite explosion_horizontal_right_last2 = new Sprite(50,SpriteSheet.bomb_exploded, 50,50);
	
	public static Sprite explosion_vertical_top_last = new Sprite(50,SpriteSheet.bomb_exploded, 50,50);
	public static Sprite explosion_vertical_top_last1 = new Sprite(50,SpriteSheet.bomb_exploded, 50,50);
	public static Sprite explosion_vertical_top_last2 = new Sprite(50,SpriteSheet.bomb_exploded, 50,50);
	
	public static Sprite explosion_vertical_down_last = new Sprite(50,SpriteSheet.bomb_exploded, 50,50);
	public static Sprite explosion_vertical_down_last1 = new Sprite(50,SpriteSheet.bomb_exploded, 50,50);
	public static Sprite explosion_vertical_down_last2 = new Sprite(50,SpriteSheet.bomb_exploded, 50,50);
	
	/*
	|--------------------------------------------------------------------------
	| Brick Explosion
	|--------------------------------------------------------------------------
	 */
	public static Sprite brick_exploded = new Sprite(50,SpriteSheet.ground, 50,50);
	public static Sprite brick_exploded1 = new Sprite(50,SpriteSheet.ground, 50,50);
	public static Sprite brick_exploded2 = new Sprite(50,SpriteSheet.ground, 50,50);
	
	/*
	|--------------------------------------------------------------------------
	| Powerups
	|--------------------------------------------------------------------------
	 */
	public static Sprite powerup_bombs = new Sprite(50,SpriteSheet.powerup_bombs, 50,50);
	public static Sprite powerup_flames = new Sprite(50,SpriteSheet.powerup_flames, 50,50);
	public static Sprite powerup_speed = new Sprite(50,SpriteSheet.powerup_speed, 50,50);
	public static Sprite powerup_wallpass = new Sprite(50,SpriteSheet.ground, 50,50);
	public static Sprite powerup_detonator = new Sprite(50,SpriteSheet.ground, 50,50);
	public static Sprite powerup_bombpass = new Sprite(50,SpriteSheet.ground, 50,50);
	public static Sprite powerup_flamepass = new Sprite(50,SpriteSheet.ground, 50,50);
	
	public Sprite(int size,SpriteSheet sheet, int rw, int rh) {
		SIZE = size;
		_pixels = new int[SIZE * SIZE];
		_x = 0;
		_y = 0;
		_sheet = sheet;
		_realWidth = rw;
		_realHeight = rh;
		load();
	}
	
	public Sprite(int size, int color) {
		SIZE = size;
		_pixels = new int[SIZE * SIZE];
		setColor(color);
	}
	
	private void setColor(int color) {
		for (int i = 0; i < _pixels.length; i++) {
			_pixels[i] = color;
		}
	}

	private void load() {
		for (int y = 0; y < SIZE; y++) {
			for (int x = 0; x < SIZE; x++) {
				_pixels[x + y * SIZE] = _sheet._pixels[(x + _x) + (y + _y) * _sheet.SIZE];
			}
		}
	}
	
	/*
	|--------------------------------------------------------------------------
	| Moving Sprites
	|--------------------------------------------------------------------------
	 */
	public static Sprite movingSprite(Sprite normal, Sprite x1, Sprite x2, int animate, int time) {
		int calc = animate % time;
		int diff = time / 3;
		
		if(calc < diff) {
			return normal;
		}
			
		if(calc < diff * 2) {
			return x1;
		}
			
		return x2;
	}
	
	public static Sprite movingSprite(Sprite x1, Sprite x2, int animate, int time) {
		int diff = time / 2;
		return (animate % time > diff) ? x1 : x2; 
	}
	
	public int getSize() {
		return SIZE;
	}
	
	public int[] getPixels() {
		return _pixels;
	}
	
	public int getPixel(int i) {
		return _pixels[i];
	}
	
	public int getRealWidth() {
		return _realWidth;
	}
	
	public int getRealHeight() {
		return _realHeight;
	}

}
