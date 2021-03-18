import java.io.*;

public class EditableBufferedReader extends BufferedReader {

    public static final int HOME = -1; //o -2^32;
    public static final int INS = -2;
    public static final int DEL = -3;
    public static final int END = -4;
    public static final int SUPR = -5;
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
        String[] cmd = {"/bin/sh", "-c", "stty raw </dev/tty"};
        try {
            Runtime.getRuntime().exec(cmd).waitFor();
        } catch (Exception e) {
        }
    }

    public void unsetRaw() {
        String[] cmd = {"/bin/sh", "-c", "stty echo cooked </dev/tty"};   //stty echo-> disable echoing of terminal input
        try {
            Runtime.getRuntime().exec(cmd).waitFor();
        } catch (Exception e) {
        }
    }

    @Override
    public int read() throws IOException {
        int read, read_aux;
        read = super.read();
        if (read != '\033') { // ESC--> ^[
            return read;
        }
        switch(read = super.read()){
            case '0':
                switch (read = super.read()) {
                    case 'H':
                        return HOME;
                    case 'F':
                        return END;
                    default:
                        return read;
                }
            case '[':
                switch (read = super.read()) {
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
                        return HOME + read - '1';
                    default:
                        return read;
                }
            default:
                return read;
        }
    }

    @Override
    public String readLine() throws IOException {
        int readed;
        setRaw();
        while ((readed = this.read()) != 13) {	//ENTER
            switch (readed) {
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
                    System.out.print("\033[H");
                    break;
                case END:
                    line.goEnd();
                    System.out.print("\033[F");
                    break;
                case INS:
                    line.changeMode();
                    break;
                case DEL:
                    line.del(); 
                    System.out.print("\033[P");
                    break;
                case SUPR:
                    line.bksp();
                    System.out.print("\033[D");
                    break;
                default:
            }
        }
        unsetRaw();
        return null;
    }
}
