import java.io.*;

public class EditableBufferedReader extends BufferedReader {

    public static final int HOME = 2000; //o -2^32;
    public static final int INS = 2001;
    public static final int DEL = 2002;
    public static final int END = 2003;
    public static final int RIGHT = 2004;
    public static final int LEFT = 2005;
    public static final int UP = 2006;
    public static final int DOWN = 2007;

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
        read = super.read();
        if (read == '0') {
            switch (read = super.read()) {
                case 'H':
                    return HOME;
                case 'F':
                    return END;
                default:
                    return read;
            }
        }
        if (read == '[') {
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
        }
        return read;
    }

    @Override
    public String readLine() throws IOException {
        int readed;
        setRaw();
        while ((readed = this.read()) != 13) {	//ENTER
            switch (readed) {
                case LEFT:
                    line.goLeft();
                    break;
                case RIGHT:
                    line.goRight();
                    break;
                case HOME:
                    line.goHome();
                    break;
                case END:
                    line.goEnd();
                    break;
                case INS:
                    line.changeMode();
                    break;
                case DEL:
                    line.del(); 
                    break; //Falta surp y constante
                default:
            }
        }
        unsetRaw();
        return null;
    }
}
