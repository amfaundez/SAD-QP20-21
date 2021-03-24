import java.util.*;
import java.beans.*;

public class ConsoleMVC implements PropertyChangeListener { //VIEW
    public LineMVC line;

    public ConsoleMVC (LineMVC line){
        this.line = line;
    }

    public void setCursor(int cursor){
        System.out.print("\033[0;" + (line.getCursor() + 1) + "H");
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt){
        switch(evt.getPropertyName()){
            case "WRITE":
                if (!line.modeIns)
                    System.out.print("\033[@"); //Insert blank character
                System.out.print(evt.getNewValue());
                break;
            case "CURSOR":
                setCursor((int) evt.getNewValue());
                break;
            case "DEL":
                System.out.print("\033[P"); //Delete character
                break;
            case "SUPR":
                System.out.print("\033[D\033[P");
                break;
        }
    }
}
