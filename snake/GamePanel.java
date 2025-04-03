package snake;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {

    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 15;
    static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
    static final int DELAY = 75;
    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];
    int bodyparts = 5;
    int appleseaten;
    int appleX;
    int appleY;
    char direction = 'R';
    boolean running =false;
    Timer timer;
    Random random;



    GamePanel(){
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startgame();
    }
    
    public void startgame(){
          newApple();
          running = true;
          timer = new Timer(DELAY,this);
          timer.start();  
    }
   
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    
    public void draw(Graphics g){

        
        if(running){
            // gridlines
            //     for(int i=0;i<SCREEN_HEIGHT/UNIT_SIZE;i++){
            //         g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
            //         g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
            //     }

            g.setColor(Color.red);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            for(int i =0;i<bodyparts;i++){
                if(i == 0){
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                    }
                else{
                    g.setColor(new Color(45,180,0));
                    //g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            g.setColor(Color.red);
            g.setFont(new Font("Ink Free",Font.BOLD,75));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("SCORE: " + appleseaten, (SCREEN_WIDTH - metrics.stringWidth("SCORE: " + appleseaten))/2,g.getFont().getSize());
        }
        else{
            gameover(g);
        }
    }
    
    public void newApple(){
        appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
    }
    
    public void move(){
        for(int i = bodyparts;i>0;i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        switch(direction){
            case'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L' :
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R' :
                x[0] = x[0] + UNIT_SIZE;
                break;
        }
    }
    
    public void checkapple(){
        if((x[0] == appleX) && (y[0] == appleY)){
            bodyparts++;
            appleseaten++;
            newApple();
        }
    }
    
    public void checkcollision(){
        //this check head collision with body
        for(int i = bodyparts;i>0;i--){
            if((x[0] == x[i]) && (y[0] == y[i])){
                running = false;
            }
        }
        //check if head touches right border
        if(x[0]<0){
            //running = false;
            x[0] = SCREEN_WIDTH;
        }
        // if head goes right border
        if(x[0] > SCREEN_WIDTH){
            //running = false;
            x[0] = 0;
        }
        //if head touches upper border
        if(y[0]<0){
            //running =false;
            y[0] = SCREEN_HEIGHT;
        }
        //IF HEAD TOUCHES down bottom
        if (y[0]>SCREEN_HEIGHT){
            //running = false;
            y[0] = 0;
        }
    }
    
    public void gameover(Graphics g){
        //score
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free",Font.BOLD,75));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("SCORE: " + appleseaten, (SCREEN_WIDTH - metrics1.stringWidth("SCORE: " + appleseaten))/2,g.getFont().getSize());
        //game over text
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free",Font.BOLD,75));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("GAME OVER", (SCREEN_WIDTH - metrics2.stringWidth("GAME OVER"))/2, SCREEN_HEIGHT/2);

        // retry text
        g.setColor(Color.MAGENTA);
        g.setFont(new Font("Ink Free",Font.BOLD,25));
        FontMetrics metrics3 = getFontMetrics(g.getFont());
        random = new Random();
        int x= random.nextInt((5-0)+1)+0;
        String[] messages = {"chill just try again","try try but dont cry ","its not the end try again","trying again is better then qutting","one last","get a better high score"};
        g.drawString("its not the end try again", (SCREEN_WIDTH - metrics2.stringWidth("GAME OVER")), (SCREEN_HEIGHT - (SCREEN_HEIGHT/3)));
        
        
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {

        if(running) {
            move();
            checkapple();
            checkcollision();
        }
        repaint();
    }


    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            switch(e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    if(direction != 'R'){
                        direction ='L';
                    }
                break;
                case KeyEvent.VK_RIGHT:
                    if(direction != 'L'){
                        direction ='R';
                    }
                break;
                case KeyEvent.VK_UP:
                    if(direction != 'D'){
                        direction ='U';
                    }
                break;
                case KeyEvent.VK_DOWN:
                    if(direction != 'U'){
                        direction ='D';
                    }
                break;
            }
        }
    }
}
