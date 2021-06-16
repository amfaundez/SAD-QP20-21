import java.util.*;

public class LineMVC extends Observable { //MODEL
    public List<Character> line;
    public int cursor;
    public boolean modeIns = true;

    public LineMVC() {
        this.line = new ArrayList<Character>();
    }

    public void write(char ch) {
        if (modeIns && (cursor < line.size())) { // Modo inserción
            line.set(cursor, ch);
            cursor++;
        } else{ //Modo sobre-escritura 
            line.add(cursor, ch);
            cursor ++; 
        }
        String args[] = {"WRITE", "" + ch};
        setChanged();
        notifyObservers(args);
    }

    public void goLeft() {
        if (cursor > 0){
            cursor--;
            String args[] = {"CURSOR", ""};
            setChanged();
            notifyObservers(args);
        }    
    }

    public void goRight() {
        if (cursor < line.size()){
            cursor++;
            String args[] = {"CURSOR", ""};
            setChanged();
            notifyObservers(args);
        }
    }

    public void goHome() {
        cursor = 0;
        String args[] = {"CURSOR", ""};
        setChanged();
        notifyObservers(args);
    }

    public void goEnd() {
        cursor = line.size();
        String args[] = {"CURSOR", ""};
        setChanged();
        notifyObservers(args);
    }

    public void changeMode() {
        modeIns = !modeIns; 
    }

    public void del() { 
        if (cursor < line.size()) {
            line.remove(cursor);
            String args[] = {"DEL", ""};
            setChanged();
            notifyObservers(args);
        }
    }

    public void bksp() { 
        if (cursor != 0) {
            cursor--;
            line.remove(cursor);
            String args[] = {"SUPR", ""};
            setChanged();
            notifyObservers(args);
        }
    }

    public int getCursor() {
        return cursor;
    }

    public String toString() {
        StringBuilder string = new StringBuilder(line.size());
        for (char ch : line) {
            string.append(ch);
        }
        return string.toString();
    }
}
