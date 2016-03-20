package lab4;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Created by m_bot on 17/03/2016.
 */
public class Client {

    final static String REMOTE = "Teste";
    final static String HOST_NAME = "DESKTOP-GFCFK3I";

    public static void main(String[] args) {
        args = new String[5];
        args[0] = HOST_NAME;
        args[1] = REMOTE;
        args[2] = "REGISTER";
        args[3] = "AB-CD-01";
        args[4] = "Duarte Pinto";

        String hostName = args[0];
        String remoteObjectName = args[1];
        String command;
        if (args[2].equals("REGISTER")) {
            command = args[2] + " " + args[3] + " " + args[4];
        } else if (args[2].equals("LOOKUP")) {
            command = args[2] + " " + args[3];
        } else {
            return;
        }

        command = command +'\n';

        try {
            Registry registry = null;
            registry = LocateRegistry.getRegistry(hostName);
            RMI_Interface rmiInterface = (RMI_Interface) registry.lookup(remoteObjectName);

            String response = rmiInterface.handleMessage(command);

            switch(response) {
                case "-1":
                    System.out.println("Error");
                    break;
                default:
                    System.out.println("Success");
                    break;
            }

        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }

    }
}
