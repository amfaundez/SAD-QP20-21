import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ChatClient {    
    public static void main(String[]args){

        if (args.length != 3) {
            System.err.println(
                "Usage: java ChatClient.java <host name> <port number> <name>");
            System.exit(1);
        }

        String host=args[0];
        int port=Integer.parseInt(args[1]);
        String name=args[2];
        MySocket s;

        try {
            s = new MySocket(host, port, name);
             //Input thread
            new Thread(){
                public void run(){
                    String user_line;
                    BufferedReader in= new BufferedReader(new InputStreamReader(System.in));
                    try{
                        while ((user_line=in.readLine())!=null)
                            if(user_line.startsWith(name+": "))
                                s.write(user_line);
                            else 
                                s.write(name+": "+user_line);
                        s.shutdownOutput();
                    }catch(IOException e){
                        e.printStackTrace();
                    }
                }
            }.start();

            //Output thread
            new Thread(){
                public void run(){
                    String line;
                    try {
                        while ((line=s.readLine())!=null)
                            if(line.startsWith(name))
                                System.out.println(line);
                            else{
                                System.out.println(line);
                            }
                        } catch (IOException e) {
                        e.printStackTrace();
                    }
                }    
            }.start();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
