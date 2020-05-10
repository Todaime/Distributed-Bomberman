package bombermandistribue.bomberman;

import java.rmi.RemoteException;
import java.rmi.Remote;

public interface Running extends Remote {
	boolean getState() throws RemoteException;
	void setState(boolean state) throws RemoteException;
}