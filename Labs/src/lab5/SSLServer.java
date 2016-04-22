package lab5;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Miguel on 10/03/2016.
 */
public class SSLServer implements Runnable {

    private Database database;
    private SSLServerSocket socket = null;

    public SSLServer(SSLServerSocket socket, Database database) {
        this.database = database;
        this.socket = socket;
    }

    @Override
    public void run() {

    }

    public static void main(String[] args) throws IOException {
        args = new String[2];
        args[0] = "4444";
        args[1] = "SHA-512";

        int portNumber = Integer.parseInt(args[0]);

        SSLServerSocketFactory ssf = null;

        ssf = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
        Database database = new Database();

        try {
            SSLServer server = new SSLServer((SSLServerSocket) ssf.createServerSocket(Integer.parseInt(args[0])), database);
        } catch (IOException e) {
            System.out.println("Server - Failed to create SSLServerSocket");
            e.getMessage();
            return;
        }
    }


}
