package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.Arrays;
import client.ClientGame;

import bombermandistribue.bomberman.Running;

public class ServerGameImpl extends UnicastRemoteObject implements ServerGame {
	private static final long serialVersionUID = 1L;
	private ArrayList<ClientGame> clientGames;
	private ArrayList<Running> runningGames; 
	private Boolean gameRunning = false;
	private String gameId;
	private int level;
	private PlayerPosition playerTwoPos = new PlayerPosition(0, 0, 0);
	private PlayerPosition playerOnePos = new PlayerPosition(0, 0, 0);
	private int[]  playerBomb = new int[2];
	private Boolean runningState = false;
	private Thread controlThread;
	private Boolean serverIsReset = true;
	protected final int gameTime = 210000;
	protected ServerGameImpl() throws RemoteException {
		this.clientGames = new ArrayList<ClientGame>();
		this.runningGames = new ArrayList<Running>();
		this.gameId = "0";
	}

	public synchronized boolean registerPlayer(ClientGame player) throws RemoteException {
		if (this.clientGames.size()==2){
			player.retrieveMessage("Two players are already connected... Please try again in a few minutes !");
			return false;
		} else if (this.clientGames.size()==1){
			if (this.gameId.equals(player.getGameId())){
				this.level = randomLevelNumber(3);
				sendBroadcastMessage(player.getUserName() + " joined the game !!!");
				this.clientGames.add(player);
				player.setPlayerNum(2);
				displayPlayersNum();
				startGame();
				return true;
			} else {
				player.retrieveMessage(" A game is already in creation with a different id ... Please try again in a few minutes !");
				return false;
			}
		} else {
			this.clientGames.add(player);
			this.gameId = player.getGameId();
			player.retrieveMessage("You joined the game "+clientGames.get(0).getUserName() + " we are waiting for the second player!");
			player.retrieveMessage("Player 1/2");
			player.setPlayerNum(1);
			return true;
		}
	}

	private int randomLevelNumber(int max) {
		return ThreadLocalRandom.current().nextInt(1, max + 1);
	}

	public synchronized void sendBroadcastMessage(String message) throws RemoteException {
		int i = 0;
		while (i < clientGames.size()){
			clientGames.get(i++).retrieveMessage(message);
		}
	}

	public void startGame() throws RemoteException {
		this.gameRunning = true;
		this.controlThread = new Thread(new RunningTimeThread(this, this.gameTime));
		this.controlThread.start();
	}

	public int getChosenLevel() throws RemoteException {
		return this.level;
	}

	public synchronized void notifyPlayerMove(PlayerPosition playerPos, int playerNum)  throws RemoteException {
		if (playerNum == 0)
			this.playerOnePos = playerPos;
		else
			this.playerTwoPos = playerPos;
	}

	public synchronized void notifyPlayerBomb(int playerNum)  throws RemoteException {
		this.playerBomb[playerNum] = 5;
	}

	public void displayPlayersNum() throws RemoteException{
		sendBroadcastMessage("Player 1 : "+clientGames.get(0).getUserName()+ " VS Player 2 "+clientGames.get(1).getUserName()); 
	}

	public synchronized boolean isGameRunning() throws RemoteException{
		return this.gameRunning;
	}

	public synchronized PlayerPosition getMove(int playerNum) throws RemoteException{
		PlayerPosition playerPos;
		if (playerNum==0){
			playerPos = this.playerOnePos;
		}
		else{
			playerPos = this.playerTwoPos;
		}
		return playerPos;
	}

	public synchronized int getBomb(int playerNum) throws RemoteException{
		int state;
		if (playerNum==0){
			state = this.playerBomb[0];
			this.playerBomb[0] = 0;
		}
		else{
			state = this.playerBomb[1];
			this.playerBomb[1] = 0;
		}
		return state;
	}

	public synchronized void resetServer() throws RemoteException{
		if (!serverIsReset){
			runningGames.get(0).setState(false);
			runningGames.get(1).setState(false);
			this.runningGames.clear();
			this.clientGames.clear();
			this.gameRunning = false;
			this.playerBomb[0] = 0;
			this.playerBomb[1] = 0;
			this.playerOnePos = new PlayerPosition(0,0,0);
			this.playerTwoPos = new PlayerPosition(0,0,0);
			serverIsReset = true;
		}
	}

	public synchronized String getPlayerID(int playerNum) throws RemoteException{
		return clientGames.get(playerNum).getUserName();
	}

	public synchronized boolean getRunningState() throws RemoteException{
		return this.runningState;
	}

	public synchronized void endRunningState() throws RemoteException{
		int i = 0;
		while (i < runningGames.size()){
			runningGames.get(i++).setState(false);
		}
		//this.runningState = false;
		this.controlThread.interrupt();
	}

	public synchronized void addRunningGame(Running state) throws RemoteException{
		this.runningGames.add(state);
	}

	public synchronized void setServerReset() throws RemoteException{
		this.serverIsReset = false;
	}
}
