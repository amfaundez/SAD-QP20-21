import java.util.*;


public class Line{
    public List<Character> line; //Representa la linea del terminal
    public int cursor; //La posición del cursor que nos permitirá controlar donde estamos dentro de la linea del termianl
    public boolean modeIns; //Cuando true modo inserción

    public Line(){
        this.line = new ArrayList<Character>;
    }

    public void write(char ch){
        if(modeIns && cursor < line.size()){ //Modo inserción
            line.add(cursor, ch);
            cursor ++;
        }else{ //Modo sobre-escritura
            line.set(cursor, ch);
        }
    }

    public void goLeft(){
        if(cursor > 0)
            cursor --;
    }

    public void goRight(){
        if(cursor < line.size())
            cursor ++;
    }

    public void goHome(){
        cursor = 0;
    }

    public void goEnd(){
        cursor = line.size();
    }

    public void changeMode(){
        modeIns = !modeIns; //si está en inserción cambiamos a sobre-escritura
    }

    public void del(){ //Borra caracter actual
        if(cursor < line.size()){
            line.remove(cursor);
        }
    }

    public void bksp(){ //Borra caracter a la izquierda
        if(cursor != 0){
            cursor --;
            line.remove(cursor);
        }
    }

    public int getCursor(){
        return cursor;
    }
}
