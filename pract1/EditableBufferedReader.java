import java.io.*;


public class EditableBufferedReader extends BufferedReader {

    public EditableBufferedReader(Reader reader) {
        super(reader);
    }

    public void setRaw() {
        String[] cmd = {"/bin/sh", "-c", "stty raw </dev/tty"};
        try {
            Runtime.getRuntime().exec(cmd).waitFor();
        } catch (Exception e) {
        }
    }
    
    public void unsetRaw(){
        String[] cmd = {"/bin/sh", "-c", "stty echo cooked </dev/tty"};
        try {
            Runtime.getRuntime().exec(cmd).waitFor();
        } catch (Exception e) {
        }
    }
      
    @Override
    public int read(){
        return 0; 
    }
    
    @Override
    public String readLine(){
        return null;
    }
}
