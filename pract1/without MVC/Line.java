import java.util.*;

public class Line {
    public List<Character> line; // Representa la linea del terminal
    public int cursor; // La posición del cursor que nos permitirá controlar donde estamos dentro de la linea del terminal
    public boolean modeIns = true; // Cuando true modo inserción

    //public terminal = new ColumnesTerminal();

    public Line() {
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
           
    }

    public void goLeft() {
        if (cursor > 0)
            cursor--;
    }

    public void goRight() {
        if (cursor < line.size())
            cursor++;
    }

    public void goHome() {
        cursor = 0;
    }

    public void goEnd() {
        cursor = line.size();
    }

    public void changeMode() {
        modeIns = !modeIns; // si está en inserción cambiamos a sobre-escritura
    }

    public void del() { //Borra caracter actual
        if (cursor < line.size()) {
            line.remove(cursor);
        }
    }

    public void bksp() { //Borra caracter a la izquierda
        if (cursor != 0) {
            cursor--;
            line.remove(cursor);
        }
    }

    public int getCursor() {
        //System.out.print((int)cursor);
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
