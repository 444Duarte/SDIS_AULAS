package lab1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Duarte on 25/02/2016.
 */
public class Server {
    final static int PORT = 4445;
    final static int BUF_SIZE = 256+16;
    private final DatagramSocket socket;

    public class Veiculo{
        String matricula;
        String owner;

        public Veiculo(String matricula, String owner){
            this.matricula = matricula;
            this.owner = owner;
        }
    }

    List<Veiculo> veiculoList;
    public Server(DatagramSocket socket) {
        this.socket = socket;
        veiculoList = new ArrayList<>();
    }

    private void respond(DatagramPacket packet){
        String message = new String(packet.getData(),0, packet.getLength());
        String response = handleMessage(message);

        byte buf[] = response.getBytes();
        DatagramPacket responsePacket = new DatagramPacket(buf,buf.length, packet.getAddress(), packet.getPort());

        try {
            socket.send(responsePacket);
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


    public static void main(String[] args){
        try {
            DatagramSocket socket = new DatagramSocket(PORT);
            Server server = new Server(socket);

            System.out.println("IP: "+ InetAddress.getLocalHost().getHostName());
            System.out.println("Port: "+ socket.getLocalPort());

            while (true) {
                byte[] buf = new byte[BUF_SIZE];
                DatagramPacket received = new DatagramPacket(buf, buf.length);
                socket.receive(received);
                System.out.println("Received a message: "+new String (received.getData()));
                server.respond(received);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
