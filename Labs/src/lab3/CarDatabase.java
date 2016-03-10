package lab3;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Duarte on 10/03/2016.
 */
public class CarDatabase {
    public class Veiculo{
        String matricula;
        String owner;

        public Veiculo(String matricula, String owner){
            this.matricula = matricula;
            this.owner = owner;
        }
    }
    List<Veiculo> veiculoList;

    public CarDatabase(){
        veiculoList = new ArrayList<>();
    }

    public synchronized String handleMessage(String message){
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
}
