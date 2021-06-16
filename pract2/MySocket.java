import java.net.*;
import java.io.*;

public class MySocket extends Socket{
    private int port;
    private String host, name;
    private BufferedReader in;
    private PrintWriter out;
    private Socket socket;

    public MySocket (String host, int port, String name)throws IOException{
        try{
            this.name=name;
            this.host=host;
            this.port=port;
            this.socket=new Socket(this.host,this.port);
            this.in= new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.out= new PrintWriter(this.socket.getOutputStream(),true);
        }catch(IOException e){
            e.printStackTrace();        
        }
    }
    
    public void setName(String name){
        this.name=name;
    }
    

    public String getName(){
        return this.name;
    }

    public MySocket(Socket socket) {
        try {
	    this.socket = socket;
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.out = new PrintWriter(socket.getOutputStream(),true);
	    }catch (IOException e) {
	        System.out.println(e);
	    }
    }
    

    public int read() throws IOException { 
        try {
            return in.read();
        } catch (IOException e) {
            System.out.println(e);
            return -1;
        }
    }

    public void write(String text) {
        out.println(text);
    }

    public void clean() {
        out.flush();
    }

    public String readLine() throws IOException {
        String str = null;
        try {
            str = in.readLine();
        } catch (IOException e) {
			e.printStackTrace();
        }
        return str;
    }

    public BufferedReader getIn(){
        return in;
    }

    public PrintWriter getOut(){
        return out;
    }

    @Override
    public void connect(SocketAddress endpoint) {
        try {
            this.socket.connect(endpoint);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close()throws IOException {
        try {
            out.close();
            in.close();
            socket.close();
       } 
       catch (IOException e) {
          e.printStackTrace();
       }
   }   
}
