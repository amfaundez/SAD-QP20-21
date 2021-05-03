
import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Image;
import java.util.Scanner;

public class Panel extends JPanel implements ActionListener {
    private Dimension d;
    static final int UNIT_SIZE = 24;
    static final int NUMBER_UNITS =15; 
    static final int SCREEN_SIZE = UNIT_SIZE * NUMBER_UNITS; 
    static final int DELAY = 13; 
    static final int MAX_GHOSTS = 12;

    private boolean running = false;
    private boolean dead = false;

    private int N_GHOSTS;
    private int lives, score;
    private int[] dx, dy; 
    private int[] ghost_x,ghost_y,ghost_dir_x,ghost_dir_y;

    private Image ghost, up, down, right, left, heart;

    private int pacman_x, pacman_y, pacman_dir_x, pacman_dir_y;
    private int ctrl_x, ctrl_y; 
    /*
    0= empty crossroad
    1= left border
    2= top border
    4= rigth border
    8= bottom border
	16= crossroads with food 
    */
    private final short levelData[] = {
    	19, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 22,
        17, 16, 16, 16, 16, 24, 16, 16, 16, 16, 16, 16, 16, 16, 20,
        17, 24, 24, 24, 28, 0, 17, 16, 16, 16, 16, 16, 16, 16, 20,
        21,  0,  0,  0,  0,  0, 17, 16, 16, 16, 16, 16, 16, 16, 20,
        17, 18, 18, 18, 18, 18, 16, 16, 16, 16, 24, 24, 24, 24, 20,
        17, 16, 16, 16, 16, 16, 16, 16, 16, 20, 0,  0,  0,   0, 21,
        17, 16, 16, 16, 16, 16, 16, 16, 16, 20, 0,  0,  0,   0, 21,
        17, 16, 16, 16, 24, 16, 16, 16, 16, 20, 0,  0,  0,   0, 21,
        17, 16, 16, 20, 0, 17, 16, 16, 16, 16, 18, 18, 18, 18, 20,
        17, 24, 24, 28, 0, 25, 24, 24, 16, 16, 16, 16, 16, 16, 20,
        21, 0,  0,  0,  0,  0,  0,   0, 17, 16, 16, 16, 16, 16, 20,
        17, 18, 18, 22, 0, 19, 18, 18, 16, 16, 16, 16, 16, 16, 20,
        17, 16, 16, 20, 0, 17, 16, 16, 16, 16, 16, 16, 16, 16, 20,
        17, 16, 16, 20, 0, 17, 16, 16, 16, 16, 16, 16, 16, 16, 20,
        25, 24, 24, 24, 26, 24, 24, 24, 24, 24, 24, 24, 24, 24, 28
    };
    private short [] screenData;

    private Timer timer;

    public Panel() {
        initialization();
        printImages();
        printLevel();
        this.addKeyListener(new EditableKeyAdapter());
        this.setPreferredSize(d);
        this.setFocusable(true);
        startGame();
    }
    
    public void initialization() {
        screenData = new short[NUMBER_UNITS*NUMBER_UNITS];
        d = new Dimension(360, 390); 
        
        ghost_x = new int[MAX_GHOSTS];
        ghost_dir_x = new int[MAX_GHOSTS];
        ghost_y = new int[MAX_GHOSTS];
        ghost_dir_y = new int[MAX_GHOSTS];
        
        dx = new int[4]; 
        dy = new int[4];

        timer = new Timer(DELAY, this);
        timer.start();
    }
    public void printImages() {
        up = new ImageIcon("/home/alicia/Escritorio/ProjectePacman/img/up2.gif").getImage();
        down = new ImageIcon("/home/alicia/Escritorio/ProjectePacman/img/down2.gif").getImage();
        right = new ImageIcon("/home/alicia/Escritorio/ProjectePacman/img/right2.gif").getImage();
        left = new ImageIcon("/home/alicia/Escritorio/ProjectePacman/img/left2.gif").getImage();
        ghost = new ImageIcon("/home/alicia/Escritorio/ProjectePacman/img/ghost.gif").getImage();
        heart = new ImageIcon("/home/alicia/Escritorio/ProjectePacman/img/heart.png").getImage();
    }

    public void printLevel() {
        for(int i = 0; i < NUMBER_UNITS*NUMBER_UNITS ; i++){
            screenData[i] = levelData[i];
        }  
        printCharacters();
    }

    public void printCharacters() {
        int dx = 1;
        
        for(int i=0; i < N_GHOSTS; i++) {
            ghost_x[i] = 6*UNIT_SIZE; 
            ghost_y[i] = 6*UNIT_SIZE; 
            ghost_dir_x[i] = dx;
            dx = -dx;
            ghost_dir_y[i] = 0;
        }

        pacman_x = 7 * UNIT_SIZE;  
        pacman_y = 11 * UNIT_SIZE;
        pacman_dir_x = 0;	
        pacman_dir_y = 0;

        ctrl_x = 0;
        ctrl_y = 0;

        dead = false;
    }


    public void startGame() {
        running=true;
        lives = 3;
        score = 0;

        System.out.print("¿Cuántos fantasmas? [1-12]: ");
        Scanner sc = new Scanner (System.in);
        N_GHOSTS = sc.nextInt();

    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d=(Graphics2D) g;
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0,0,d.width,d.height);
        drawMaze(g2d);
        drawScore(g2d);
        if(running){
            playGame(g2d);
        }
    }

    public void drawMaze(Graphics2D g2d){
        short i=0;
        for(int y=0; y<SCREEN_SIZE;y+=UNIT_SIZE){
            for(int x=0; x<SCREEN_SIZE;x+=UNIT_SIZE){
                g2d.setColor(Color.CYAN);
                g2d.setStroke(new BasicStroke(2));

                if(screenData[i]==0){ 
                    g2d.setColor(Color.BLACK);
                    g2d.fillRect(x, y, UNIT_SIZE, UNIT_SIZE);
                }
                if((screenData[i] & 1)!=0){ 
                    g2d.drawLine(x, y, x,y+UNIT_SIZE-1);
                    
                }
                if((screenData[i] & 2)!=0){ 
                    g2d.drawLine(x, y, x+UNIT_SIZE-1,y);
                    
                }
                if((screenData[i] & 4)!=0){ 
                    g2d.drawLine(x+UNIT_SIZE-1, y, x+UNIT_SIZE-1,y+UNIT_SIZE-1);
                    
                }
                if((screenData[i] & 8)!=0){ 
                    g2d.drawLine(x,y+UNIT_SIZE-1,x+UNIT_SIZE-1,y+UNIT_SIZE-1);
                    
                }
                if((screenData[i] & 16)!=0){
                    g2d.setColor(Color.GREEN);
                    g2d.fillOval(x+10, y+10, 6, 6);
                    
                }
                i++;

            }
        }
    }
    public void drawScore(Graphics g) {
        if(running){
            g.setColor(Color.ORANGE);
            g.setFont(new Font("Arial Black",Font.BOLD,20)); 
            //FontMetrics metrics= getFontMetrics(g.getFont()); 
            g.drawString("SCORE: "+score, 120, 30);
            for(int i=0; i<lives;i++){
                g.drawImage(heart, (i)*UNIT_SIZE+i, 15* UNIT_SIZE, this);
            }
            if(score==195){
                g.setColor(Color.RED);
                g.setFont(new Font("Arial Black",Font.BOLD,30)); 
                g.drawString("WINNER!!!!!", 80,160);
            }
        }
        else{
            g.setColor(Color.RED);
            g.setFont(new Font("Arial Black",Font.BOLD,30)); 
            g.drawString("GAME OVER ", 80,160);
        }
        
    }
    public void playGame (Graphics2D g2d) {
        if(dead) {
            death();
        } else {
            if(score<195){
                moveGhosts(g2d);
                printPacman(g2d);
                movePacman();
            }
            printPacman(g2d);
        }
    }

    public void death() {
        lives --;
        if(lives == 0) {
            running = false;
        }
        printCharacters();
    }

    public void printGhosts(Graphics2D g2d, int x, int y) {
        g2d.drawImage(ghost, x, y, this);
    }

    public void moveGhosts(Graphics2D g2d) {
        int pos, count;

        for (int i = 0; i < N_GHOSTS; i++) {
            if (ghost_x[i] % UNIT_SIZE == 0 && ghost_y[i] % UNIT_SIZE == 0) { 
                pos = ghost_x[i] / UNIT_SIZE + NUMBER_UNITS* (int) (ghost_y[i] / UNIT_SIZE); 

                count = 0;
                if ((screenData[pos] & 1) == 0 && ghost_dir_x[i] != 1) {
                    dx[count] = -1;
                    dy[count] = 0;
                    count++;
                }
                if ((screenData[pos] & 2) == 0 && ghost_dir_y[i] != 1) {
                    dx[count] = 0;
                    dy[count] = -1;
                    count++;
                }
                if ((screenData[pos] & 4) == 0 && ghost_dir_x[i] != -1) {
                    dx[count] = 1;
                    dy[count] = 0;
                    count++;
                }
                if ((screenData[pos] & 8) == 0 && ghost_dir_y[i] != -1) {
                    dx[count] = 0;
                    dy[count] = 1;
                    count++;
                }
                if (count == 0) {

                    if ((screenData[pos] & 15) == 15) {
                        ghost_dir_x[i] = 0;
                        ghost_dir_y[i] = 0;
                    } else {
                        ghost_dir_x[i] = -ghost_dir_x[i]; 
                        ghost_dir_y[i] = -ghost_dir_y[i];
                    }

                } else { 
                    count = (int) (Math.random() * count);
                    if (count > 3) {
                        count = 3;
                    }

                    ghost_dir_x[i] = dx[count];
                    ghost_dir_y[i] = dy[count];
                }

            }
            ghost_x[i] = ghost_x[i] + ghost_dir_x[i];
            ghost_y[i] = ghost_y[i] + ghost_dir_y[i];
            printGhosts(g2d, ghost_x[i] + 1, ghost_y[i] + 1);

            if (pacman_x > (ghost_x[i] - 12) && pacman_x < (ghost_x[i] + 12) && pacman_y > (ghost_y[i] - 12) && pacman_y < (ghost_y[i] + 12) && running) {
                dead = true;
            }
        }

    }

    public void printPacman(Graphics2D g2d) {
        if(ctrl_x == -1) {
            g2d.drawImage(left, pacman_x + 1, pacman_y + 1, this);
        } else if(ctrl_x == 1) {
            g2d.drawImage(right, pacman_x +1, pacman_y + 1, this);
        } else if(ctrl_y == -1) {
            g2d.drawImage(up, pacman_x +1, pacman_y + 1, this);
        } else {
            g2d.drawImage(down, pacman_x +1, pacman_y + 1, this);
        }
    }

    public void movePacman() {
        int pos;
        short character;
        
        if (pacman_x % UNIT_SIZE == 0 && pacman_y % UNIT_SIZE == 0) {
            pos = pacman_x / UNIT_SIZE + NUMBER_UNITS * (int) (pacman_y / UNIT_SIZE);
            character = screenData[pos];
            if ((character & 16) != 0) {
                screenData[pos] = (short) (character & 15);
                score++;
            }
            if (ctrl_x != 0 || ctrl_y != 0) {
                if (!((ctrl_x == -1 && ctrl_y == 0 && (character & 1) != 0) || (ctrl_x == 1 && ctrl_y == 0 && (character & 4) != 0)
                     || (ctrl_x == 0 && ctrl_y == -1 && (character & 2) != 0) || (ctrl_x == 0 && ctrl_y == 1 && (character & 8) != 0))) {
                    pacman_dir_x = ctrl_x;
                    pacman_dir_y = ctrl_y;
                }
            }
            if ((pacman_dir_x == -1 && pacman_dir_y == 0 && (character & 1) != 0)
                    || (pacman_dir_x == 1 && pacman_dir_y == 0 && (character & 4) != 0)
                    || (pacman_dir_x == 0 && pacman_dir_y == -1 && (character & 2) != 0)
                    || (pacman_dir_x == 0 && pacman_dir_y == 1 && (character & 8) != 0)) {
                pacman_dir_x = 0;
                pacman_dir_y = 0;
            }
        } 
        pacman_x = pacman_x + pacman_dir_x;
        pacman_y = pacman_y + pacman_dir_y;

    }

    @Override
    public void actionPerformed(ActionEvent e) {  
        repaint();
    }

    public class EditableKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if(running) {
                switch(e.getKeyCode()){
                    case KeyEvent.VK_UP:
                        ctrl_x = 0;
                        ctrl_y = -1;
                        break;
                    case KeyEvent.VK_DOWN:
                        ctrl_x = 0;
                        ctrl_y = 1;
                        break;
                    case KeyEvent.VK_RIGHT:
                        ctrl_x = 1;
                        ctrl_y = 0;
                        break;
                    case KeyEvent.VK_LEFT:
                        ctrl_x = -1;
                        ctrl_y = 0;
                        break;
                    }
            } else {
                if(e.getKeyCode() == KeyEvent.VK_SPACE) {
                    initialization();
                    printImages();
                    printLevel();
                    startGame();
                }
            }  
        }
    }
}