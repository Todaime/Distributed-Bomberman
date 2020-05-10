package server;


import java.util.concurrent.atomic.AtomicBoolean;
import java.rmi.RemoteException;

public class RunningTimeThread implements Runnable {
 
    private Thread worker;
    private AtomicBoolean running = new AtomicBoolean(false);
    private int interval;
    private ServerGame server;
    private boolean stopped;
    protected RunningTimeThread(ServerGame server, int gameDuration){
        this.interval = gameDuration;
        this.server = server;
    }

    public void interrupt() {
        running.set(false);
        worker.interrupt();
    }
 
    boolean isRunning() {
        return running.get();
    }
 
    boolean isStopped() {
        return stopped;
    }
 
    public void run() {
        running.set(true);
        stopped = false;
        while (running.get()) {
            try {
                Thread.sleep(interval);
                running.set(false);
            } catch (InterruptedException e){
                Thread.currentThread().interrupt();
                running.set(false);
            }      
        }
        try{ 
            server.resetServer();
        } catch (RemoteException e){
            e.printStackTrace();
        }
        stopped = true;
    }
}