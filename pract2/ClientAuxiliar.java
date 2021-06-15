import java.util.ArrayList;
import java.io.*;

public class ClientAuxiliar implements Runnable{
    private MySocket s;
    private ArrayList<ClientAuxiliar> clients;

    public ClientAuxiliar(MySocket s, ArrayList<ClientAuxiliar> clients) {
        this.s = s;
        this.clients=clients;
    }

    public void run(){
        String line;

        try{

            while ((line =s.readLine()) != null) {
                outToAll(line);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void outToAll (String line){
        for (ClientAuxiliar activeClient: clients){
            activeClient.s.write(line);
        }
    }
}
