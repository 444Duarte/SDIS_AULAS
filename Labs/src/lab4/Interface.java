package lab4;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by m_bot on 17/03/2016.
 */
public interface Interface extends Remote {

    int register(String plate, String owner) throws RemoteException;

    String lookup(String plate) throws RemoteException;
}
