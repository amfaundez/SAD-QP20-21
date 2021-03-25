import java.io.*;

public class EditableBufferedReader extends BufferedReader {

    public static final int HOME = -1; // o -2^32;
    public static final int INS = -2;
    public static final int DEL = -3;
    public static final int END = -4;
    public static final int RIGHT = -6;
    public static final int LEFT = -7;
    public static final int UP = -8;
    public static final int DOWN = -9;

    protected Line line;

    public EditableBufferedReader(Reader reader) {
        super(reader);
        line = new Line();
    }


    public void setRaw() {
        String[] cmd = { "/bin/sh", "-c", "stty -echo raw </dev/tty" };
        try {
            Runtime.getRuntime().exec(cmd).waitFor();
        } catch (Exception e) {
        }
    }


    public void unsetRaw() {
        String[] cmd = { "/bin/sh", "-c", "stty echo cooked </dev/tty" }; // stty echo-> disable echoing of terminal
                                                                          // input
        try {
            Runtime.getRuntime().exec(cmd).waitFor();
        } catch (Exception e) {
        }
    }

    @Override
    public int read() throws IOException {
        int read, read_aux;
        read = super.read();
        if (read != '\033')// ESC--> ^[
            return read;
        /*
            switch (read = super.read()) { 
                case '0': 
                    switch (read = super.read()) { 
                        case 'H': return HOME; 
                        case 'F': return END; 
                        default: return read; 
                    } 
                case '[':
                    switch (read = super.read()) { 
                        case 'A': return UP; 
                        case 'B': return DOWN;
                        case 'C': return RIGHT; 
                        case 'D': return LEFT; 
                        case '1': 
                        case '2': 
                        case '3':
                        case '4': 
                            if ((read_aux = super.read()) != '~') 
                                return read_aux; 
                            return HOME - read + '1'; 
                        default: return read; 
                    } 
                default: return read; 
            }
         */
        switch (read = super.read()) {
            case '[':
                switch (read = super.read()) {
                    case 'H':
                        return HOME;
                    case 'F':
                        return END;
                    case 'A':
                        return UP;
                    case 'B':
                        return DOWN;
                    case 'C':
                        return RIGHT;
                    case 'D':
                        return LEFT;
                    case '1':
                    case '2':
                    case '3':
                    case '4':
                        if ((read_aux = super.read()) != '~') {
                            return read_aux;
                        }
                        // return HOME + read - '1';
                        return HOME - read + '1';

                    default:
                        return read;
                }
            default:
                return read;
        }
    }

    @Override
    public String readLine() throws IOException {
        int read;
        try{
            setRaw();
            System.out.print("\033[H\033[J"); //FORMAT TERMINAL
		    System.out.flush();

            while ((read = this.read()) != 13) { // ENTER
                switch (read) {
                    case LEFT:
                        line.goLeft();
                        System.out.print("\033[D");
                        break;
                    case RIGHT:
                        line.goRight();
                        System.out.print("\033[C");
                        break;
                    case HOME:
                        line.goHome();
                        //System.out.print("\033[3;0H"); sin el format terminal
                        System.out.print("\033[H");
                        break;
                    case END:
                        line.goEnd();
                        System.out.print("\033[0;" + (line.getCursor() + 1) + "H");
                        break;
                    case INS:
                        line.changeMode();
                        //System.out.print("\033[2~");
                        break;
                    case DEL:
                        line.del();
                        //System.out.print("\033[3~");
                        System.out.print("\033[P"); //Delete character
                        break;
                    case 127: //BACKSPACE
                        line.bksp();
                        System.out.print("\033[D\033[P"); 
                        //System.out.print("\033[P"); 
                        break;
                    default:
                        line.write((char) read);
                        if (!line.modeIns){
                            System.out.print("\033[@"); //Insert blank character
                        }
                        System.out.print((char) read);
                }
            }
        }catch (Exception e) {
            throw e;
        }finally{
            unsetRaw();
        }
        return line.toString();
    }
}
