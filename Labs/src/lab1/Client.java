package lab1;

import java.io.IOException;
import java.net.*;

/**
 * Created by Duarte on 25/02/2016.
 */
public class Client {

    final static int PORT = 4445;
    final static String IP = "192.168.43.48";

    public static void main(String[] args){

        try {
            System.out.println(InetAddress.getByName(IP));
            DatagramSocket socket = new DatagramSocket();

            String bufTemp = "OLA";

            DatagramPacket packet = new DatagramPacket(bufTemp.getBytes(), bufTemp.getBytes().length, InetAddress.getByName(IP), PORT);

            socket.send(packet);

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
