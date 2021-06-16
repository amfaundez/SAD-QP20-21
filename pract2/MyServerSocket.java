import java.net.*;
import java.io.*;

public class MyServerSocket extends ServerSocket {

    public MyServerSocket(int port) throws IOException{
        super(port);
    }

    @Override
    public MySocket accept(){
        try{
            return new MySocket(super.accept());
        }catch(IOException e){
            return null;
        }
    }

    @Override
    public void close(){
        try{
            super.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

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
