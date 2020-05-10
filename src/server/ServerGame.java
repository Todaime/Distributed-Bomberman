package server;

import java.rmi.Remote;
import java.rmi.RemoteException;

import client.ClientGame;
import bombermandistribue.bomberman.Running;

public interface ServerGame extends Remote{
	void sendBroadcastMessage(String message) throws RemoteException;
	void startGame() throws RemoteException;
	void notifyPlayerMove(PlayerPosition playerPos, int playerNum)  throws RemoteException;
	void notifyPlayerBomb(int playerNum)  throws RemoteException;
	void displayPlayersNum() throws RemoteException;
	void resetServer() throws RemoteException;
	boolean isGameRunning() throws RemoteException;
	boolean registerPlayer(ClientGame player) throws RemoteException;
	int getBomb(int playerNum) throws RemoteException;
	PlayerPosition getMove(int playerNum) throws RemoteException;
	String getPlayerID(int playerNum) throws RemoteException;
	int getChosenLevel() throws RemoteException;
	boolean getRunningState() throws RemoteException;
	void endRunningState() throws RemoteException;
	void addRunningGame(Running state)throws RemoteException;
	void setServerReset() throws RemoteException;
 }