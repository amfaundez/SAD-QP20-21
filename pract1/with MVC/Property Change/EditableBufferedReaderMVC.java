import java.io.*;

public class EditableBufferedReaderMVC extends BufferedReader { //CONTROLLER

    public static final int HOME = -1; 
    public static final int INS = -2;
    public static final int DEL = -3;
    public static final int END = -4;
    public static final int RIGHT = -6;
    public static final int LEFT = -7;
    public static final int UP = -8;
    public static final int DOWN = -9;

    protected LineMVC line;
    protected ConsoleMVC console;

    public EditableBufferedReaderMVC(Reader reader) {
        super(reader);
        line = new LineMVC();
        console = new ConsoleMVC(line);
        line.addPropertyChangeListener(console);
    }

    public void setRaw() {
        String[] cmd = { "/bin/sh", "-c", "stty -echo raw </dev/tty" };
        try {
            Runtime.getRuntime().exec(cmd).waitFor();
        } catch (Exception e) {
        }
    }

    public void unsetRaw() {
        String[] cmd = { "/bin/sh", "-c", "stty echo cooked </dev/tty" }; 
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
            System.out.print("\033[H\033[J"); 
	    System.out.flush();

            while ((read = this.read()) != 13) { //Enter
                switch (read) {
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
                        System.out.print("\033[P"); //Delete character
                        break;
                    case 127: //BACKSPACE
                        line.bksp();
                        break;
                    default:
                        line.write((char) read);
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
