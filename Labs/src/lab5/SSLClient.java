package lab5;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.Provider;
import java.security.Security;

/**
 * Created by Miguel on 10/03/2016.
 */
public class SSLClient {

    public static void main(String[] args) {
        args = new String[6];
        args[0] = "192.16.16.16";
        args[1] = "4444";
        args[2] = "store";
        args[3] = "nome";
        args[4] = "pass";
        args[5] = "SHA-512";

        PrintWriter out = null;
        SSLSocket sock = null;
        SSLSocketFactory ssf = null;

        ssf = (SSLSocketFactory) SSLSocketFactory.getDefault();

        try {
            sock = (SSLSocket) ssf.createSocket(InetAddress.getByName(args[0]), Integer.parseInt(args[1]));
            sock.startHandshake();

            out = new PrintWriter(sock.getOutputStream(), true);

            for (int i = 2; i < args.length - 1; i++) {
                out.println(args[i]);
            }
            out.close();
            sock.close();
        }
        catch( IOException e) {
            System.out.println("Server - Failed to create SSLSocket");
            e.getMessage();
            return;
        }

    }
}
