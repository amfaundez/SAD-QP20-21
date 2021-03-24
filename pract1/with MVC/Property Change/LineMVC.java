import java.util.*;
import java.beans.*;

public class LineMVC { //MODEL
    public List<Character> line;
    public int cursor;
    public boolean modeIns = true;
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    public LineMVC(){
        this.line = new ArrayList<Character>();
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.addPropertyChangeListener(listener);
    }

    public void write(char ch) {
        if (modeIns && (cursor < line.size())) { // Modo inserción
            line.set(cursor, ch);
            cursor++;
        } else{ //Modo sobre-escritura 
            line.add(cursor, ch);
            cursor ++; 
        }
        pcs.firePropertyChange("WRITE", "", ch);
    }

    public void goLeft() {
        if (cursor > 0){
            cursor--;
            pcs.firePropertyChange("CURSOR", "", cursor);
        }    
    }

    public void goRight() {
        if (cursor < line.size()){
            cursor++;
            pcs.firePropertyChange("CURSOR", "", cursor);
        }
    }

    public void goHome() {
        cursor = 0;
        pcs.firePropertyChange("CURSOR", "", cursor);
    }

    public void goEnd() {
        cursor = line.size();
        pcs.firePropertyChange("CURSOR", "", cursor);
    }

    public void changeMode() {
        modeIns = !modeIns; // si está en inserción cambiamos a sobre-escritura
    }

    public void del() { //Borra caracter actual
        if (cursor < line.size()) {
            line.remove(cursor);
            pcs.firePropertyChange("DEL", "", cursor);
        }
    }

    public void bksp() { //Borra caracter a la izquierda
        if (cursor != 0) {
            cursor--;
            line.remove(cursor);
            pcs.firePropertyChange("SUPR", "", cursor);
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
