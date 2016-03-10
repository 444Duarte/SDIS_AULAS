package lab3;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by Duarte on 10/03/2016.
 */
public class Client {
    final static int PORT = 4444;
    final static int BUFF_SIZE = 256+16;
    final static String HOST_NAME = "DUARTE-HP";

    public static void main(String[] args) {
        args = new String[5];
        args[0] = HOST_NAME;
        args[1] = "" + PORT;
        args[2] = "REGISTER";
        args[3] = "AB-CD-01";
        args[4] = "Duarte Pinto";

        String hostName = args[0];
        int port = Integer.parseInt(args[1]);
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
            Socket socket = new Socket(InetAddress.getByName(hostName),port);
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();
            out.write(command.getBytes());
            out.flush();

            byte[] buff = new byte[BUFF_SIZE];
            int buff_length = in.read(buff);
            String response = new String(buff,0,buff_length);
            System.out.println(response);

            socket.shutdownOutput();

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
