import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatServer {   
  
    static final int MAX_CLIENTS = 3;             
    private static ArrayList<ClientAuxiliar> clients= new ArrayList<>();
    private static ExecutorService pool= Executors.newFixedThreadPool(MAX_CLIENTS); 

    public static void main(String[]args){
        if (args.length != 1) {
            System.err.println("Usage: java ChatServer.java <port number>");
            System.exit(1);
        }

        int port= Integer.parseInt(args[0]);
        boolean listening=true;
        MyServerSocket ss;

        try {
            ss = new MyServerSocket(port);

            while(listening){
                MySocket s= ss.accept();
                ClientAuxiliar clientThread= new ClientAuxiliar(s,clients);
                clients.add(clientThread);
                pool.execute(clientThread);

                //new SendMessage(clients);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
