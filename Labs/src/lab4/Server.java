package lab4;

/**
 * Created by m_bot on 17/03/2016.
 */
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class Server implements RMI_Interface {

    List<CarDatabase.Veiculo> veiculoList = new ArrayList<>();

    public static void main(String[] args) {

        try {
            args = new String[1];
            args[0] = "Teste";
            Server server = new Server();
            LocateRegistry.createRegistry(1099);

            RMI_Interface rmiInterface = (RMI_Interface) UnicastRemoteObject.exportObject(server, 0);
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind(args[0], rmiInterface);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String handleMessage(String message) throws RemoteException {
        String lookupPattern = "LOOKUP \\w{2}-\\w{2}-\\w{2}$";
        String registerPattern = "REGISTER \\w{2}-\\w{2}-\\w{2} [\\s|a-zA-Z]{1,256}";

        String retString;
        final String failMessage = "-1";

        if(message.matches(lookupPattern)){
            String matricula = message.substring("LOOKUP ".length()-1, "LOOKUP ".length()+"XX-XX-XX".length()-1);
            for(CarDatabase.Veiculo veiculo : veiculoList){
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
            veiculoList.add(new CarDatabase().new Veiculo(matricula, owner));
            retString = "" + veiculoList.size()+"\n";
            retString.concat(matricula + " " + owner);

            System.out.println("Registado veiculo: "+matricula+" com o dono: "+owner);
            return retString;
        }else{
            return failMessage;
        }

    }
}
