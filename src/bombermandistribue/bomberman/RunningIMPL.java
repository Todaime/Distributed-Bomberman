package bombermandistribue.bomberman;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RunningIMPL extends UnicastRemoteObject implements Running {
	private static final long serialVersionUID = 1L;
	private boolean state;
	protected RunningIMPL() throws RemoteException{
		this.state = true;
	}

	public boolean getState() throws RemoteException{
		return this.state;
	}
	public void setState(boolean state) throws RemoteException{
		this.state = state;
	}
}