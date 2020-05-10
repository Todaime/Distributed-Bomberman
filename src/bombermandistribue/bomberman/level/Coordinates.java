package bombermandistribue.bomberman.level;

import bombermandistribue.bomberman.Game;

public class Coordinates {
	
	public static int pixelToCase(int i) {
		return (int)(i / Game.TILES_SIZE);
	}
	
	public static int pixelToCase(double i) {
		return (int)(i / Game.TILES_SIZE);
	}
	
	public static int caseToPixel(int i) {
		return i * Game.TILES_SIZE;
	}		
}
