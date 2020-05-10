package bombermandistribue.bomberman.level;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.io.File;



import bombermandistribue.bomberman.Board;
import bombermandistribue.bomberman.entities.LayeredEntity;
import bombermandistribue.bomberman.entities.mob.Player;
import bombermandistribue.bomberman.entities.tile.GrassTile;
import bombermandistribue.bomberman.entities.tile.WallTile;
import bombermandistribue.bomberman.entities.tile.destroyable.BrickTile;
import bombermandistribue.bomberman.entities.tile.powerup.PowerupBombs;
import bombermandistribue.bomberman.entities.tile.powerup.PowerupFlames;
import bombermandistribue.bomberman.entities.tile.powerup.PowerupSpeed;
import bombermandistribue.bomberman.exceptions.LoadLevelException;
import bombermandistribue.bomberman.graphics.Sprite;

public class FileLevel extends Level {
	
	public FileLevel(String path, Board board) throws LoadLevelException {
		super(path, board);
	}
	
	@Override
	public void loadLevel(String path) throws LoadLevelException {
		try {
			System.out.print(path);
			File file = new File("./" + path);
			BufferedReader in = new BufferedReader(
			        new InputStreamReader(new FileInputStream(file)));

			String data = in.readLine();
			StringTokenizer tokens = new StringTokenizer(data);
			
			_level = Integer.parseInt(tokens.nextToken());
			_height = Integer.parseInt(tokens.nextToken());
			_width = Integer.parseInt(tokens.nextToken());

			_lineTiles = new String[_height];
			
			for(int i = 0; i < _height; ++i) {
				_lineTiles[i] = in.readLine().substring(0, _width);
 			}
			
			in.close();
		} catch (IOException e) {
			throw new LoadLevelException("Error loading level " + path, e);
		}
	}
	
	@Override
	public void createEntities() {
		for (int y = 0; y < getHeight(); y++) {
			for (int x = 0; x < getWidth(); x++) {
				addLevelEntity( _lineTiles[y].charAt(x), x, y );
			}
		}
	}
	
	public void addLevelEntity(char c, int x, int y) {
		int pos = x + y * getWidth();
		int x_pixel = Coordinates.caseToPixel(x);
		int y_pixel = Coordinates.caseToPixel(y);
		switch(c) {
			case '#': 
				_board.addEntitie(pos, new WallTile(x_pixel, y_pixel, Sprite.wall));
				break;
			case 'b': 
				LayeredEntity layer = new LayeredEntity(x_pixel,y_pixel, 
						new GrassTile(x_pixel, y_pixel, Sprite.ground), 
						new BrickTile(x_pixel, y_pixel, Sprite.brick));
				
				if(_board.isPowerupUsed(x_pixel, y_pixel, _level) == false) 
					layer.addBeforeTop(new PowerupBombs(x_pixel, y_pixel, _level, Sprite.powerup_bombs));
				_board.addEntitie(pos, layer);
				break;
			case 's':
				layer = new LayeredEntity(x_pixel, y_pixel, 
						new GrassTile(x_pixel, y_pixel, Sprite.ground), 
						new BrickTile(x_pixel, y_pixel, Sprite.brick));
				
				if(_board.isPowerupUsed(x_pixel, y_pixel, _level) == false) {
					layer.addBeforeTop(new PowerupSpeed(x_pixel, y_pixel, _level, Sprite.powerup_speed));
				}
				
				_board.addEntitie(pos, layer);
				break;
			case 'f': 
				layer = new LayeredEntity(x_pixel, y_pixel, 
						new GrassTile(x_pixel, y_pixel, Sprite.ground), 
						new BrickTile(x_pixel, y_pixel, Sprite.brick));
				
				if(_board.isPowerupUsed(x_pixel, y_pixel, _level) == false) {
					layer.addBeforeTop(new PowerupFlames(x_pixel, y_pixel, _level, Sprite.powerup_flames));
				}
				
				_board.addEntitie(pos, layer);
				break;
			case '*': 
				_board.addEntitie(pos, new LayeredEntity(x_pixel, y_pixel, 
						new GrassTile(x_pixel, y_pixel, Sprite.ground), 
						new BrickTile(x_pixel, y_pixel, Sprite.brick)) );
				break;
			case ' ': 
				_board.addEntitie(pos, new GrassTile(x_pixel, y_pixel, Sprite.ground) );
				break;
			case 'p': 
				_board.addMob( new Player(0,x_pixel, y_pixel, _board) );
				_board.addEntitie(pos, new GrassTile(x_pixel, y_pixel, Sprite.ground) );
				break;
			case 'r': 
				_board.addMob( new Player(1,x_pixel, y_pixel, _board) );
				_board.addEntitie(pos, new GrassTile(x_pixel, y_pixel, Sprite.ground) );
				break;
			default: 
				_board.addEntitie(pos, new GrassTile(x_pixel, y_pixel, Sprite.ground) );
				break;
			}
	}
	
}
