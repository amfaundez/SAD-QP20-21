import java.io.*;

public class EditableBufferedReader extends BufferedReader {

    public static final int HOME= 2000; //o -2^32;
    public static final int INS= 2001;
    public static final int DEL=2002;
	public static final int END= 2003;
    public static final int RIGHT= 2004;
	public static final int LEFT= 2005;
    public static final int UP= 2006;
	public static final int DOWN= 2007; 

    protected Line line;

    public EditableBufferedReader(Reader reader) {
        super(reader);
        line = new Line();
    }

    public void setRaw() {
        String[] cmd = {"/bin/sh", "-c", "stty raw </dev/tty"};
        try {
            Runtime.getRuntime().exec(cmd).waitFor();
        } catch (Exception e) {
        }
    }
    
    public void unsetRaw(){
        String[] cmd = {"/bin/sh", "-c", "stty echo cooked </dev/tty"};   //stty echo-> disable echoing of terminal input
        try {
            Runtime.getRuntime().exec(cmd).waitFor();
        } catch (Exception e) {
        }
    }
      
    @Override
    public int read() throws IOException {
        int reeded, reeded_aux;
        reeded=super.read();
        if(reeded!='\033'){ // ESC--> ^[
            return reeded;
        }
        reeded=super.read();
        if(reeded=='0'){
            switch (reeded=super.read()){
                case 'H': return HOME;
                case 'F': return END;
                default: return reeded;
            }
        }
        if(reeded=='['){
            switch (reeded=super.read()){
                case 'A': return UP;
                case 'B': return DOWN;
                case 'C': return RIGHT;
                case 'D': return LEFT;
                case '1': 
                case '2':
                case '3':
                case '4': 
                    if((reeded_aux=super.read())!= '~'){
                        return reeded_aux; 
                    }
                    return HOME+reeded-'1';
                default: return reeded;
            }
        }
        return reeded; 
    }
    
    @Override
	public String readLine() throws IOException{
		int readed;
		setRaw();
		while((readed=this.read()) != 13){	//ENTER
			switch (readed) {
				case LEFT:			
					//metodo en Line
					break;
				case RIGHT: 			
					//metodo en Line
					break;
				case HOME:			
					//metodo en Line
				case END:			
					//metodo en Line
					break;
				case INS:
					//metodo en Line
					break;	
				case DEL:		
                    //metodo en Line
                    break;
				default:
			}
		}
		unsetRaw();
        return null;
	}
}
