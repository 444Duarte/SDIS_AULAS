package lab1;

import java.io.IOException;
import java.net.*;

/**
 * Created by Duarte on 25/02/2016.
 */
public class Client {

    final static int PORT = 4445;
    final static int BUF_SIZE = 256+16;
    final static String HOST_NAME = "DUARTE-HP";

    public static void main(String[] args){
        /*args = new String[5];
        args[0] = HOST_NAME;
        args[1] = "" + PORT;
        args[2] = "REGISTER";
        args[3] = "AB-CD-01";
        args[4] = "Duarte Pinto";*/

        String hostName = args[0];
        int port = Integer.parseInt(args[1]);
        String command;
        if(args[2].equals("REGISTER")) {
            command = args[2] + " " + args[3] + " " + args[4];
        }else if(args[2].equals("LOOKUP")){
            command = args [2] + " " + args[3];
        }else{
            return ;
        }

        try {
            DatagramSocket socket = new DatagramSocket();

            DatagramPacket packet = new DatagramPacket(command.getBytes(), command.getBytes().length, InetAddress.getByName(hostName),port);

            socket.send(packet);
            byte[] buff = new byte[BUF_SIZE];
            socket.receive(new DatagramPacket(buff, BUF_SIZE));
            System.out.println(new String(buff));

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
