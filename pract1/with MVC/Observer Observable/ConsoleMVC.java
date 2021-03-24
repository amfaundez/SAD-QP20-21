import java.util.*;

public class ConsoleMVC implements Observer { //VIEW
    private LineMVC line;

    public ConsoleMVC (LineMVC line){
        this.line = line;
    }

    public void setCursor(int cursor){
        System.out.print("\033[0;" + (line.getCursor() + 1) + "H");
    }

    @Override
    public void update(Observable o, Object arg){
        String[] args = (String[]) arg;
        switch(args[0]){
            case "WRITE":
                if (!line.modeIns)
                    System.out.print("\033[@"); //Insert blank character
                System.out.print(args[1]);
                break;
            case "CURSOR":
                setCursor(line.getCursor());
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
