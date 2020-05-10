package server;

import java.rmi.RemoteException;
import java.net.MalformedURLException;
import java.rmi.Naming;

public class Server {
  public static void main(String args[]) throws RemoteException, MalformedURLException{
    String ip;
    if (args.length > 0) {
      ip = args[0];
    } else {
      ip = "127.0.0.1";
    }
  	System.setProperty("java.rmi.server.hostname", ip);
    Naming.rebind("RMIGameServer", new ServerGameImpl());
    System.out.println("Bomberman server operational");
  }
}


