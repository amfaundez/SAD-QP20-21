import java.util.*;

public class Line {
    public List<Character> line; 
    public int cursor;
    public boolean modeIns = true;

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
        modeIns = !modeIns; 
    }

    public void del() { 
        if (cursor < line.size()) {
            line.remove(cursor);
        }
    }

    public void bksp() { 
        if (cursor != 0) {
            cursor--;
            line.remove(cursor);
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
