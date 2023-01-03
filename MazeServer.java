

import java.awt.AWTException;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;


/**
 *
 * @author Daniel
 */ 
public class MazeServer extends JFrame {
    
    
    ServerSocket listener;
    Socket socket;
    BufferedReader input;
    PrintWriter output;
    
    private int reqdx, reqdy, viewdx, viewdy,dx,dy,ghostx,ghosty;
    private boolean ingame = false;
    
    private void setPositions(Socket socket) {
        this.socket = socket;
        try {
                input = new BufferedReader( new InputStreamReader(socket.getInputStream()));
                output = new PrintWriter(socket.getOutputStream(), true);
                while(true) {
                String command = input.readLine();
                if (command.startsWith("MOVE")) {
                    String moveLocationx = command.substring(5);
                    String moveLocationy = command.substring(5);
                    String sRdx = moveLocationx.substring(0, moveLocationx.lastIndexOf("."));
                    String sRdy = moveLocationy.substring(moveLocationy.lastIndexOf(".") + 1);
                    reqdx = Integer.parseInt(sRdx);
                    reqdy = Integer.parseInt(sRdy);
                }else{
                    ingame = Boolean.valueOf(command);
                    }
                }
                
        } catch (IOException e) {
            System.out.println("Player died: " + e);
        }
    }
    
    public MazeServer() throws IOException {
        listener = new ServerSocket(3000);
        System.out.println("Maze Server is Running");
        initUI();
        try {
            while (true){
                setPositions(listener.accept());
                System.out.println("Still inside");
            }
        } finally {
            System.out.println("Going out");
            listener.close();
        }
    }
    
    public static void main(String[] args) throws Exception {
        MazeServer server = new MazeServer();
    }
    

    
    private void initUI(){
        
        add(new Board());
        setTitle("Server-Maze");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(480, 540);
        setLocationRelativeTo(null);
        setVisible(true);        
    }

    class Board extends JPanel implements ActionListener {

    private Dimension d;
    private final Font smallfont = new Font("Helvetica", Font.BOLD, 14);

    private final Color dotcolor = new Color(192, 192, 0);
    private Color mazecolor;
    private boolean inGame = false;
    private boolean dying = false;
    public boolean v1 = true;
    public boolean v2 = true;
    public boolean v3 = true;
    public boolean v4 = true;
    public boolean v5 = true;
    public boolean v6 = true;

    private final int BLOCK_SIZE = 24;
    private final int N_BLOCKS = 20;
    private int N_GHOSTS = 5;
    private final int scrsize = N_BLOCKS * BLOCK_SIZE;
    private final int pacmanspeed = 4;
    private int currentSpeed = 3;
    private final int validSpeeds[] = {1, 2, 3, 4};
    private int score, scoreTwo;
    private final int MAX_GHOSTS = 6;
    private int[] dx, dy;
    private int pacm2x, pacm2y, pacm2dx, pacm2dy;
    private Image heart;
    private Image pacman1, ghost, left, right, down, up;
    private int[] ghost_x, ghost_y, ghost_dx, ghost_dy, ghostSpeed;

    private int pacmanx, pacmany, pacmandx, pacmandy;
    private int regdx, regdy, viewgdx, viewgdy;
    private final short leveldata[] = {
            0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0, 21,  0,  0,  0,
            0, 19, 26, 26, 18, 26, 18, 26, 26, 26, 26, 18, 26, 18, 26, 26, 24, 26, 22,  0,
            0, 21,  0,  0, 21,  0, 21,  0,  0,  0,  0, 21,  0, 21,  0,  0,  0,  0, 21,  0,
            0, 21,  0,  0, 21,  0, 17, 26, 26, 22,  0, 21,  0, 21,  0,  0,  0,  0, 21,  0,
            0, 21,  0,  0, 21,  0, 21,  0,  0, 21,  0, 21,  0, 21,  0,  0,  0,  0, 21,  0,
            0, 21,  0,  0, 21,  0, 17, 26, 26, 28,  0, 17, 26, 24, 26, 26, 26, 26, 20,  0,
            0, 21,  0,  0, 21,  0, 21,  0,  0,  0,  0, 21,  0,  0,  0,  0,  0,  0, 21,  0,
            0, 21,  0,  0, 21,  0, 17, 26, 26, 26, 26, 24, 26, 26, 26, 26, 22,  0, 21,  0,
            0, 21,  0,  0, 21,  0, 21,  0,  0,  0,  0,  0,  0,  0,  0,  0, 21,  0, 21,  0,
            0, 21,  0,  0, 21,  0, 17, 26, 26, 26, 26, 26, 26, 26, 26, 18, 28,  0, 21,  0,
            0, 21,  0,  0, 21,  0, 21,  0,  0,  0,  0,  0,  0,  0,  0, 21,  0,  0, 21,  0,
            0, 21,  0,  0, 21,  0, 25, 26, 26, 26, 26, 26, 18, 26, 26, 24, 26, 26, 20,  0,
            0, 21,  0,  0, 21,  0,  0,  0,  0,  0,  0,  0, 21,  0,  0,  0,  0,  0, 21,  0,
            0, 21,  0,  0, 17, 26, 26, 26, 26, 26, 26, 26, 24, 26, 26, 26, 26, 26, 20,  0,
            26,16, 26, 26, 28,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0, 17, 26,
            0, 21,  0,  0,  0,  0, 19, 26, 18, 26, 26, 26, 26, 26, 18, 26, 30,  0, 21,  0,
            0, 17, 26, 26, 30,  0, 21,  0, 21,  0,  0,  0,  0,  0, 21,  0,  0,  0, 21,  0,
            0, 21,  0,  0,  0,  0, 21,  0, 21,  0,  0,  0,  0,  0, 21,  0,  0,  0, 21,  0,
            0, 25, 26, 26, 26, 26, 24, 26, 24, 26, 26, 26, 26, 26, 24, 26, 18, 26, 28,  0,
            0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0, 21,  0,  0,  0,
    };
    private short[] screendata;
    private Timer timer;

    public Board() {        //Method being called

        loadImages();
        initVariables();
        
        addKeyListener(new TAdapter());

        setFocusable(true);

        setBackground(Color.black);
        setDoubleBuffered(true);
        
    }
        private void drawPowerUp(Graphics2D g2d)
        {
            if(this.v1){
                g2d.drawImage(heart, 312, 72,this);//1st power up
            }
            if(this.v2){
                g2d.drawImage(heart, 192, 168,this);//2nd power up
            }
            if(this.v3){
                g2d.drawImage(heart, 432, 240,this);//3rd power up
            }
            if(this.v4){
                g2d.drawImage(heart, 96, 336,this);//4th power up
            }
            if(this.v5){
                g2d.drawImage(heart, 96, 384,this);//5th power up
            }
            if(this.v6){
                g2d.drawImage(heart, 288, 360,this);//6th power up
            }

        }

    private void initVariables() {

        screendata = new short[N_BLOCKS * N_BLOCKS];
        mazecolor = new Color(5, 100, 5);
        d = new Dimension(480, 540);
        dx = new int[4];
        dy = new int[4];
        ghost_x = new int[MAX_GHOSTS];
        ghost_dx = new int[MAX_GHOSTS];
        ghost_y = new int[MAX_GHOSTS];
        ghost_dy = new int[MAX_GHOSTS];
        ghostSpeed = new int[MAX_GHOSTS];
        timer = new Timer(40, this);
        timer.start();
    }

    @Override
    public void addNotify() {
        super.addNotify();

        initGame();
    }

    private void playGame(Graphics2D g2d) {

        if (dying) {


        } else {

            movePacman1();
            drawPacman1(g2d);
            drawPacman2(g2d);
            drawPowerUp(g2d);
            movePacman2();
            moveGhosts(g2d);
            checkMaze();
        }
    }

    private void showIntroScreen(Graphics2D g2d) {

        g2d.setColor(new Color(0, 32, 48));
        g2d.fillRect(50, scrsize / 2 - 30, scrsize - 100, 50);
        g2d.setColor(Color.white);
        g2d.drawRect(50, scrsize / 2 - 30, scrsize - 100, 50);

        String s = "Press s to start.";
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = this.getFontMetrics(small);

        g2d.setColor(Color.white);
        g2d.setFont(small);
        g2d.drawString(s, (scrsize - metr.stringWidth(s)) / 2, scrsize / 2);
    }

    private void drawScore(Graphics2D g) {

        int i;
        String s;

        g.setFont(smallfont);
        g.setColor(new Color(96, 128, 255));
        s = "Score: " + score;
        g.drawString(s, scrsize / 2, scrsize + 16);
    }
    
    private void drawScoreTwo(Graphics2D g) {

        int i;
        String s;

        g.setFont(smallfont);
        g.setColor(new Color(255,218,68));
        s = "Score: " + scoreTwo;
        g.drawString(s, scrsize / 2 + 96, scrsize + 16);
    }

    private void checkMaze() {

        short i = 0;
        boolean finished = true;

        while (i < N_BLOCKS * N_BLOCKS && finished) {

            if ((screendata[i] & 48) != 0) {
                finished = false;
            }

            i++;
        }

        if (finished) {
            initLevel();
        }
    }

        private void moveGhosts(Graphics2D g2d) {

            int pos;
            int count;

            for (int i = 0; i < N_GHOSTS; i++) {
                if (ghost_x[i] % BLOCK_SIZE == 0 && ghost_y[i] % BLOCK_SIZE == 0) {
                    pos = ghost_x[i] / BLOCK_SIZE + N_BLOCKS * (int) (ghost_y[i] / BLOCK_SIZE);

                    count = 0;

                    if ((screendata[pos] & 1) == 0 && ghost_dx[i] != 1) {
                        dx[count] = -1;
                        dy[count] = 0;
                        count++;
                    }

                    if ((screendata[pos] & 2) == 0 && ghost_dy[i] != 1) {
                        dx[count] = 0;
                        dy[count] = -1;
                        count++;
                    }

                    if ((screendata[pos] & 4) == 0 && ghost_dx[i] != -1) {
                        dx[count] = 1;
                        dy[count] = 0;
                        count++;

                    }

                    if ((screendata[pos] & 8) == 0 && ghost_dy[i] != -1) {
                        dx[count] = 0;
                        dy[count] = 1;
                        count++;
                    }

                    if (count == 0) {

                        if ((screendata[pos] & 15) == 15) {
                            ghost_dx[i]= 0;
                            ghost_dy[i] = 0;
                        } else {
                            ghost_dx[i] = -ghost_dx[i];
                            ghost_dy[i] = -ghost_dy[i];
                        }

                    } else {

                        count = (int) (Math.random() * count);

                        if (count > 3) {
                            count = 3;
                        }

                        ghost_dx[i] = dx[count];
                        ghost_dy[i] = dy[count];
                    }

                }

                ghost_x[i] = ghost_x[i] + (ghost_dx[i] * ghostSpeed[i]);
                ghost_y[i] = ghost_y[i] + (ghost_dy[i] * ghostSpeed[i]);
                drawGhost(g2d, ghost_x[i] + 1, ghost_y[i] + 1);

                if(ghost_x[i] > 456){
                    ghost_x[i] = 0;
                } else if (ghost_x[i] < 0) {
                    ghost_x[i] = 456;
                }
                if(ghost_y[i] > 456){
                    ghost_y[i] = 0;
                } else if (ghost_y[i] < 0) {
                    ghost_y[i] = 456;
                }

                if (pacmanx > (ghost_x[i] - 12) && pacmanx < (ghost_x[i] + 12)
                        && pacmany > (ghost_y[i] - 12) && pacmany < (ghost_y[i] + 12)
                        && inGame) {

                    dying = true;
                }
            }
        }
        private void drawGhost(Graphics2D g2d, int x , int y) {
            g2d.drawImage(ghost, x, y, this);
        }

    private void movePacman2() {

        short ch;
        int pos;
        
        if (regdx == -pacm2dx && regdy == -pacm2dy) {
            pacm2dx = regdx;
            pacm2dy = regdy;
            viewgdx = pacm2dx;
            viewgdy = pacm2dy;
        }
        
        if (pacm2x % BLOCK_SIZE == 0 && pacm2y % BLOCK_SIZE == 0) {
            pos = pacm2x / BLOCK_SIZE + N_BLOCKS * (int) (pacm2y / BLOCK_SIZE);
            ch = screendata[pos];

            if ((ch & 16) != 0) {
                screendata[pos] = (short) (ch & 15);
                scoreTwo++;
            }

            if (regdx != 0 || regdy != 0) {
                if (!((regdx == -1 && regdy == 0 && (ch & 1) != 0)
                        || (regdx == 1 && regdy == 0 && (ch & 4) != 0)
                        || (regdx == 0 && regdy == -1 && (ch & 2) != 0)
                        || (regdx == 0 && regdy == 1 && (ch & 8) != 0))) {
                    pacm2dx = regdx;
                    pacm2dy = regdy;
                    viewgdx = pacm2dx;
                    viewgdy = pacm2dy;
                }
            }

            // Check for standstill
            if ((pacm2dx == -1 && pacm2dy == 0 && (ch & 1) != 0)
                    || (pacm2dx == 1 && pacm2dy == 0 && (ch & 4) != 0)
                    || (pacm2dx == 0 && pacm2dy == -1 && (ch & 2) != 0)
                    || (pacm2dx == 0 && pacm2dy == 1 && (ch & 8) != 0)) {
                pacm2dx = 0;
                pacm2dy = 0;
            }
        }
        pacm2x = pacm2x + pacmanspeed * pacm2dx;
        pacm2y = pacm2y + pacmanspeed * pacm2dy;

        if(pacm2x == 312 && pacm2y == 72){
            this.v1 = false;
        }
        if(pacm2x == 192 && pacm2y == 168){
            this.v2 = false;
        }
        if(pacm2x == 432 && pacm2y == 240){
            this.v3 = false;
        }
        if(pacm2x == 96 && pacm2y == 336){
            this.v4 = false;
        }
        if(pacm2x == 96 && pacm2y == 384){
            this.v5 = false;
        }
        if(pacm2x == 288 && pacm2y == 360){
            this.v6 = false;
        }

        if(pacm2x > 456){
            pacm2x = 0;
        } else if (pacm2x < 0) {
            pacm2x = 456;
        }
        if(pacm2y > 456){
            pacm2y = 0;
        } else if (pacm2y < 0) {
            pacm2y = 456;
        }
        
    }

    private void drawPacman2(Graphics2D g2d) {

        if (viewgdx == -1) {
            drawPacman2Left(g2d);
        } else if (viewgdx == 1) {
            drawPacman2Right(g2d);
        } else if (viewgdy == -1) {
            drawPacman2Up(g2d);
        } else {
            drawPacman2Down(g2d);
        }
    }

    private void drawPacman2Up(Graphics2D g2d) {
        g2d.drawImage(pacman1, pacm2x + 1, pacm2y + 1, this);
    }

    private void drawPacman2Down(Graphics2D g2d) {
        g2d.drawImage(pacman1, pacm2x + 1, pacm2y + 1, this);
    }

    private void drawPacman2Left(Graphics2D g2d) {

        g2d.drawImage(pacman1, pacm2x + 1, pacm2y + 1, this);
    }

    private void drawPacman2Right(Graphics2D g2d) {

        g2d.drawImage(pacman1, pacm2x + 1, pacm2y + 1, this);
    }

    private void movePacman1() {

        int pos;
        short ch;

        if (reqdx == -pacmandx && reqdy == -pacmandy) {
            pacmandx = reqdx;
            pacmandy = reqdy;
            viewdx = pacmandx;
            viewdy = pacmandy;
        }

        if (pacmanx % BLOCK_SIZE == 0 && pacmany % BLOCK_SIZE == 0) {
            pos = pacmanx / BLOCK_SIZE + N_BLOCKS * (int) (pacmany / BLOCK_SIZE);
            ch = screendata[pos];

            if ((ch & 16) != 0) {
                screendata[pos] = (short) (ch & 15);
                score++;
            }

            if (reqdx != 0 || reqdy != 0) {
                if (!((reqdx == -1 && reqdy == 0 && (ch & 1) != 0)
                        || (reqdx == 1 && reqdy == 0 && (ch & 4) != 0)
                        || (reqdx == 0 && reqdy == -1 && (ch & 2) != 0)
                        || (reqdx == 0 && reqdy == 1 && (ch & 8) != 0))) {
                    pacmandx = reqdx;
                    pacmandy = reqdy;
                    viewdx = pacmandx;
                    viewdy = pacmandy;
                }
            }

            // Check for standstill
            if ((pacmandx == -1 && pacmandy == 0 && (ch & 1) != 0)
                    || (pacmandx == 1 && pacmandy == 0 && (ch & 4) != 0)
                    || (pacmandx == 0 && pacmandy == -1 && (ch & 2) != 0)
                    || (pacmandx == 0 && pacmandy == 1 && (ch & 8) != 0)) {
                pacmandx = 0;
                pacmandy = 0;
            }
        }
        pacmanx = pacmanx + pacmanspeed * pacmandx;
        pacmany = pacmany + pacmanspeed * pacmandy;

        if(pacmanx == 312 && pacmany == 72){
            this.v1 = false;
        }
        if(pacmanx == 192 && pacmany == 168){
            this.v2 = false;
        }
        if(pacmanx == 432 && pacmany == 240){
            this.v3 = false;
        }
        if(pacmanx == 96 && pacmany == 336){
            this.v4 = false;
        }
        if(pacmanx == 96 && pacmany == 384){
            this.v5 = false;
        }
        if(pacmanx == 288 && pacmany == 360){
            this.v6 = false;
        }

        if(pacmanx > 456){
            pacmanx = 0;
        } else if (pacmanx < 0) {
            pacmanx = 456;
        }
        if(pacmany > 456){
            pacmany = 0;
        } else if (pacmany < 0) {
            pacmany = 456;
        }
    }

    private void drawPacman1(Graphics2D g2d) {

        if (viewdx == -1) {
            drawPacman1Left(g2d);
        } else if (viewdx == 1) {
            drawPacman1Right(g2d);
        } else if (viewdy == -1) {
            drawPacman1Up(g2d);
        } else {
            drawPacman1Down(g2d);
        }
    }

    private void drawPacman1Up(Graphics2D g2d) {
        g2d.drawImage(pacman1, pacmanx + 1, pacmany + 1, this);    
    }

    private void drawPacman1Down(Graphics2D g2d) {
        g2d.drawImage(pacman1, pacmanx + 1, pacmany + 1, this);        
    }

    private void drawPacman1Left(Graphics2D g2d) {

        g2d.drawImage(pacman1, pacmanx + 1, pacmany + 1, this);
    }

    private void drawPacman1Right(Graphics2D g2d) {

        g2d.drawImage(pacman1, pacmanx + 1, pacmany + 1, this);
    }

    private void drawMaze(Graphics2D g2d) {

        short i = 0;
        int x, y;

        for (y = 0; y < scrsize; y += BLOCK_SIZE) {
            for (x = 0; x < scrsize; x += BLOCK_SIZE) {

                g2d.setColor(mazecolor);
                g2d.setStroke(new BasicStroke(2));

                if ((screendata[i] & 1) != 0) { 
                    g2d.drawLine(x, y, x, y + BLOCK_SIZE - 1);
                }

                if ((screendata[i] & 2) != 0) { 
                    g2d.drawLine(x, y, x + BLOCK_SIZE - 1, y);
                }

                if ((screendata[i] & 4) != 0) { 
                    g2d.drawLine(x + BLOCK_SIZE - 1, y, x + BLOCK_SIZE - 1,
                            y + BLOCK_SIZE - 1);
                }

                if ((screendata[i] & 8) != 0) { 
                    g2d.drawLine(x, y + BLOCK_SIZE - 1, x + BLOCK_SIZE - 1,
                            y + BLOCK_SIZE - 1);
                }

                if ((screendata[i] & 16) != 0) { 
                    g2d.setColor(dotcolor);
                    g2d.fillRect(x + 11, y + 11, 2, 2);
                }

                i++;
            }
        }
    }

    private void initGame() {
        score = 0;
        scoreTwo = 0;
        initLevel();
        N_GHOSTS = 5;
        currentSpeed = 3;
    }

    private void initLevel() {

        int i;
        for (i = 0; i < N_BLOCKS * N_BLOCKS; i++) {
            screendata[i] = leveldata[i];
        }

        continueLevel();
    }

    private void continueLevel() {

        int dx = 1;
        int random;

        for (int i = 0; i < N_GHOSTS; i++) {

            ghost_y[i] = 11 * BLOCK_SIZE; //start position
            ghost_x[i] = 10 * BLOCK_SIZE;
            ghost_dy[i] = 0;
            ghost_dx[i] = dx;
            dx = -dx;
            random = (int) (Math.random() * (currentSpeed + 1));

            if (random > currentSpeed) {
                random = currentSpeed;
            }

            ghostSpeed[i] = validSpeeds[random];
        }
        pacm2y = 4 * BLOCK_SIZE;
        pacm2x = 4 * BLOCK_SIZE;
        pacm2dy = 0;
        pacm2dx = 0;
        regdy = 0;
        regdx = 0;
        viewgdx = -1;
        viewgdy = 0;

        pacmanx = 14 * BLOCK_SIZE;
        pacmany = 13 * BLOCK_SIZE;
        pacmandx = 0;
        pacmandy = 0;
        reqdx = 0;
        reqdy = 0;
        viewdx = -1;
        viewdy = 0;
    }
    

    private void loadImages() {

        pacman1 = new ImageIcon("./images/pacman.png").getImage();
        heart = new ImageIcon("./images/heart.png").getImage();
        ghost = new ImageIcon("./images/ghost.gif").getImage();

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);
    }

    private void doDrawing(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.black);
        g2d.fillRect(0, 0, d.width, d.height);

        drawMaze(g2d);
        drawScore(g2d);
        drawScoreTwo(g2d);

        if (ingame) {
            playGame(g2d);
        } else {
            showIntroScreen(g2d);
        }

        Toolkit.getDefaultToolkit().sync();
        g2d.dispose();
    }
    

    class TAdapter extends KeyAdapter  {
        
        @Override
        public void keyPressed(KeyEvent e) {
            
            
            int key = e.getKeyCode();
            

            if (ingame) {
                if (key == KeyEvent.VK_LEFT) {
                    regdx = -1;
                    regdy = 0;
                    output.println("MOVE " + regdx + "." + regdy);
                } else if (key == KeyEvent.VK_RIGHT) {
                    regdx = 1;
                    regdy = 0;
                    output.println("MOVE " + regdx + "." + regdy);
                } else if (key == KeyEvent.VK_UP) {
                    regdx = 0;
                    regdy = -1;
                    output.println("MOVE " + regdx + "." + regdy);
                } else if (key == KeyEvent.VK_DOWN) {
                    regdx = 0;
                    regdy = 1;
                    output.println("MOVE " + regdx + "." + regdy);
                } else if (key == KeyEvent.VK_ESCAPE && timer.isRunning()) {
                    ingame = false;
                } else if (key == KeyEvent.VK_PAUSE) {
                    if (timer.isRunning()) {
                        timer.stop();
                    } else {
                        timer.start();
                    }
                }
                output.println("ghost"+ghost_x[0]+"."+ghost_y[0]);
            } else {
                if (key == 's' || key == 'S') {
                    ingame = true;
                    initGame();
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

            int key = e.getKeyCode();

            if (key == Event.LEFT || key == Event.RIGHT
                    || key == Event.UP || key == Event.DOWN) {
                regdx = 0;
                regdy = 0;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        repaint();
    }
    }
}
