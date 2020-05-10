package client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import server.ServerGame;
import bombermandistribue.bomberman.Bomberman;
import bombermandistribue.bomberman.exceptions.BombermanException;

public class ClientGameImpl extends UnicastRemoteObject implements ClientGame, Runnable {
	private static final long serialVersionUID = 1L;
	private ServerGame serverGame;
	private String userName;
	private String gameId;
	public Bomberman bbman;
	private int playerNum;
	private boolean authorizedConnexion;

	protected ClientGameImpl(String userName, String gameId, ServerGame serverGame) throws RemoteException {
		this.userName = userName;
		this.serverGame = serverGame;
		this.gameId = gameId;
		this.bbman = new Bomberman();
		this.playerNum = 0;
		this.authorizedConnexion = serverGame.registerPlayer(this);
	}

	public void retrieveMessage(String message) throws RemoteException{
		System.out.println(message);
	}

	public String getUserName() throws RemoteException{
		return this.userName;
	}

	public String getGameId() throws RemoteException{
		return this.gameId;
	}

	public void setPlayerNumber(int playerNum) throws RemoteException{
		this.playerNum = playerNum;
	}

	public int getPlayerNum() throws RemoteException{
		return this.playerNum;
	}
	public void setPlayerNum(int playerNum) throws RemoteException{
		this.playerNum = playerNum;
	}

	public void run() {
		if (this.authorizedConnexion)
			while (true){
				try{ 
					if (this.serverGame.isGameRunning() == true){
						Bomberman.play(this.serverGame, this.playerNum, this.serverGame.getChosenLevel());
						this.serverGame.resetServer();
						this.authorizedConnexion = false;
						break;
					}
				} catch (BombermanException e){
						e.printStackTrace();
				} catch (RemoteException e){
						e.printStackTrace();
				}
			}
	}
}
