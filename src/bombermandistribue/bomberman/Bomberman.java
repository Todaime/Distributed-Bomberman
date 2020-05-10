package bombermandistribue.bomberman;

import bombermandistribue.bomberman.exceptions.BombermanException;
import bombermandistribue.bomberman.gui.Frame;

import server.ServerGame;

public class Bomberman {
	
	public static void play(ServerGame serverGame, int playerNum, int level) throws BombermanException {
		new Frame(serverGame, playerNum, level);
	}
}
