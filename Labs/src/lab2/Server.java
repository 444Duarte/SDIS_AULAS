package lab2;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by m_bot on 03/03/2016.
 */
public class Server implements Runnable {

    final static int PORT = 4446;
    final static String IP = "228.5.6.7";
    final static int BUFF_SIZE = 256+16;

    MulticastSocket msocket;

    int mport;
    InetAddress mserver;

    int port;


    public class Veiculo{
        String matricula;
        String owner;

        public Veiculo(String matricula, String owner){
            this.matricula = matricula;
            this.owner = owner;
        }
    }

    List<Veiculo> veiculoList;

    public Server(int mport, String ip, int port) {
        try {
            msocket = new MulticastSocket(mport);
            this.mport = mport;
            this.port = port;
            this.mserver = InetAddress.getByName(ip);
            veiculoList = new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private void respond(DatagramPacket packet, DatagramSocket socket){
        String message = new String(packet.getData(),0, packet.getLength());
        String response = handleMessage(message);

        byte buf[] = response.getBytes();
        DatagramPacket responsePacket = new DatagramPacket(buf,buf.length, packet.getAddress(), packet.getPort());

        try {
            socket.send(responsePacket);
            System.out.println("Message sent to: "+packet.getAddress().getHostAddress()+":"+ packet.getPort());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String handleMessage(String message){
        String lookupPattern = "LOOKUP \\w{2}-\\w{2}-\\w{2}$";
        String registerPattern = "REGISTER \\w{2}-\\w{2}-\\w{2} [\\s|a-zA-Z]{1,256}";

        String retString;
        final String failMessage = "-1";

        if(message.matches(lookupPattern)){
            String matricula = message.substring("LOOKUP ".length()-1, "LOOKUP ".length()+"XX-XX-XX".length()-1);
            for(Veiculo veiculo : veiculoList){
                if(veiculo.matricula.equals(matricula)){
                    retString = "" + veiculoList.size()+"\n";
                    retString.concat(veiculo.matricula + " " + veiculo.owner);
                    return retString;
                }
            }
            return failMessage;
        }else if(message.matches(registerPattern)){
            String matricula = message.substring("REGISTER ".length()-1, "REGISTER ".length()+"XX-XX-XX".length()-1);
            String owner = message.substring("REGISTER ".length()+"XX-XX-XX".length() -1);
            veiculoList.add(new Veiculo(matricula, owner));
            retString = "" + veiculoList.size()+"\n";
            retString.concat(matricula + " " + owner);

            System.out.println("Registado veiculo: "+matricula+" com o dono: "+owner);
            return retString;
        }else{
            return failMessage;
        }

    }

    public void receive() {

        try {
            DatagramSocket responseSocket =  new DatagramSocket(this.port);

            while (true) {
                byte[] buf = new byte[BUFF_SIZE];
                DatagramPacket receivedPacket = new DatagramPacket(buf, buf.length);
                responseSocket.receive(receivedPacket);
                respond(receivedPacket, responseSocket);
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

        try {
            this.msocket.setTimeToLive(1);
            while (true) {
                String msg = "" + this.port;
                DatagramPacket packet = new DatagramPacket(msg.getBytes(), msg.getBytes().length, this.mserver, this.mport);
                this.msocket.send(packet);
                String logmsg = "multicast: " + this.mserver + " " + this.mport + ": " + InetAddress.getLocalHost().getHostAddress() + " " + this.port;
                System.out.println(logmsg);
                Thread.sleep(1000);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        Server server = new Server(PORT, IP, 4445);

        new Thread(server).start();

        server.receive();
    }
}
