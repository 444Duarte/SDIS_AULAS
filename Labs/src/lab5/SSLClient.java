package lab5;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

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

        SSLSocket s = null;
        SSLSocketFactory ssf = null;

        ssf = (SSLSocketFactory) SSLSocketFactory.getDefault();

        try {
            s = (SSLSocket) ssf.createSocket(InetAddress.getByName(args[0]), Integer.parseInt(args[1]));
        }
        catch( IOException e) {
            System.out.println("Server - Failed to create SSLSocket");
            e.getMessage();
            return;
        }

    }
}
