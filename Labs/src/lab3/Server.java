package lab3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Duarte on 10/03/2016.
 */
public class Server implements Runnable {
    final static int BUFF_SIZE = 256+16;

    private CarDatabase database;
    private Socket socket;

    public Server(Socket socket, CarDatabase database) {
        this.database = database;
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String message = in.readLine();
            String response = this.database.handleMessage(message);

            out.println(response);
            out.flush();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        args = new String[1];
        args[0] = "" + 4444;

        int portNumber = Integer.parseInt(args[0]);

        ServerSocket serverSocket = new ServerSocket(portNumber);
        CarDatabase database = new CarDatabase();

        while (true){
            serverSocket.setReceiveBufferSize(BUFF_SIZE);
            Socket clientSocket = serverSocket.accept();
            Server server = new Server(clientSocket, database);

            new Thread(server).start();
        }
    }
}
