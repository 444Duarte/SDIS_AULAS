package lab2;

import com.sun.org.apache.xpath.internal.operations.Mult;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.*;

/**
 * Created by m_bot on 03/03/2016.
 */
public class Client {

    final static int MPORT = 4445;
    final static int BUFF_SIZE = 256+16;
    final static String MIP = "227.0.0.254";



    public static void main(String[] args) {
        args = new String[5];
        args[0] = MIP;
        args[1] = "" + MPORT;
        args[2] = "REGISTER";
        args[3] = "AB-CD-01";
        args[4] = "Duarte Pinto";


        String msocketIP = args[0];
        int mport = Integer.parseInt(args[1]);

        InetAddress hostIP;
        int port;

        try {
            MulticastSocket multicastSocket = new MulticastSocket(mport);
            multicastSocket.setTimeToLive(1);
            multicastSocket.joinGroup(InetAddress.getByAddress(msocketIP.getBytes()));

            byte[] mbuff = new byte[BUFF_SIZE];
            DatagramPacket mpacket = new DatagramPacket(mbuff,mbuff.length);
            multicastSocket.receive(mpacket);

            hostIP = mpacket.getAddress();
            String message = new String(mpacket.getData(),0, mpacket.getLength());
            port = Integer.parseInt(message);

            multicastSocket.leaveGroup(InetAddress.getByAddress(msocketIP.getBytes()));

            String command;
            if(args[2].equals("REGISTER")) {
                command = args[2] + " " + args[3] + " " + args[4];
            }else if(args[2].equals("LOOKUP")){
                command = args [2] + " " + args[3];
            }else{
                return ;
            }


            DatagramSocket socket = new DatagramSocket();

            DatagramPacket packet = new DatagramPacket(command.getBytes(), command.getBytes().length, hostIP,port);

            socket.send(packet);
            byte[] buff = new byte[BUFF_SIZE];
            socket.receive(new DatagramPacket(buff, BUFF_SIZE));
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
