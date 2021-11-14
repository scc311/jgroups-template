package api;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface API extends Remote {

  public float getRandom(int min, int max) throws RemoteException;

}
