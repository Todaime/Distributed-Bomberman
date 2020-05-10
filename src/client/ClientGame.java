package client;

import java.rmi.RemoteException;
import java.rmi.Remote;

public interface ClientGame extends Remote{
	void retrieveMessage(String message) throws RemoteException;
	void setPlayerNum(int playerNum) throws RemoteException;
	void setPlayerNumber(int playerNum) throws RemoteException;
	String getUserName() throws RemoteException;
	String getGameId() throws RemoteException;
	int getPlayerNum() throws RemoteException;
}