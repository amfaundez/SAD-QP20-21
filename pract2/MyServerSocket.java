import java.net.*;
//import java.util.concurrent.ConcurrentHashMap;
import java.io.*;

public class MyServerSocket extends ServerSocket {

    ///Key son clientes, values son server al que esté conectado
    //private ConcurrentHashMap<String, MySocket> clientes= new ConcurrentHashMap<String, MySocket>(); 

    public MyServerSocket(int port) throws IOException{
        super(port);
    }

    //listens for a connection to be made to this socket and accepts it.
    @Override
    public MySocket accept(){
        try{
            return new MySocket(super.accept());
        }catch(IOException e){
            return null;
        }
    }

    //close the current server socket 
    @Override
    public void close(){
        try{
            super.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    //binds the socket to a specific address
    @Override
    public void bind(SocketAddress endpoint){
        try{
            super.bind(endpoint);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void setReceiveBufferSize(int size){
        try{
            super.setReceiveBufferSize(size);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void setSoTimeout(int timeout){
        try{
            super.setSoTimeout(timeout);
        }catch(IOException e){
            e.printStackTrace();
        }
    }   
}
