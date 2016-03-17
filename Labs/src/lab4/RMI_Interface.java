package lab4;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by m_bot on 17/03/2016.
 */
public interface RMI_Interface extends Remote {

    String handleMessage(String message) throws RemoteException;
}
