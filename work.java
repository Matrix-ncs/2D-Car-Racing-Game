
package cars;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;


public class work extends JPanel implements ActionListener,KeyListener{

    private int space;
    private int width=80;
    private int height=70;
    private int speed;
    private int WIDTH=500;
    private int HEIGHT=685;
    private int move=20;
    private int count=1;
    private int score = 0; // Score variable
    private boolean gameOver = false; // Game over flag    
    private Rectangle car;
    private ArrayList <Rectangle> ocars;
    private ArrayList <Rectangle> line;
    private Random rand;
    BufferedImage bg;
    BufferedImage user;
    BufferedImage op1;
    BufferedImage op2;
    BufferedImage road;
    Boolean linef=true;
    Timer t;
    public work(){
        try {
            user=ImageIO.read(new File("F:\\Chrome Downloads\\Cars\\image\\user1.png"));
            op1=ImageIO.read(new File("F:\\Chrome Downloads\\Cars\\image\\op1.png"));
            op2=ImageIO.read(new File("F:\\Chrome Downloads\\Cars\\image\\op3.png"));
            bg=ImageIO.read(new File("F:\\Chrome Downloads\\Cars\\image\\bg.png"));
            road=ImageIO.read(new File("F:\\Chrome Downloads\\Cars\\image\\road.png"));
        } catch (IOException ex) {
            Logger.getLogger(work.class.getName()).log(Level.SEVERE, null, ex);
        }
        t=new Timer(20,this);
        rand=new Random();
        ocars=new ArrayList<Rectangle>();
        line=new ArrayList<Rectangle>();
        car=new Rectangle(WIDTH/2-90,HEIGHT-90,width,height);
        System.out.println(car.y);
        space=300;
        speed=8;
        addKeyListener(this);
        setFocusable(true);
        addocars(true);
        addocars(true);
        addocars(true);
        addocars(true);
        addlines(true);
        addlines(true);
        addlines(true);
        addlines(true);
        addlines(true);
        addlines(true);
        addlines(true);
        addlines(true);
        t.start();
    }
    // Method to add dividing lines on the road
    public void addlines(Boolean first){
        int x=WIDTH/2-2;
        int y=700;
        int width=4;
        int height=100;
        int sp=130;
        if(first){
            line.add(new Rectangle(x,y-(line.size()*sp),width,height));
        }else{
            line.add(new Rectangle(x,line.get(line.size()-1).y-sp,width,height));
        }
    }
    // Method to add opponent cars
    public void addocars(boolean first){
        int positionx=rand.nextInt()%2;
        int x=0;
        int y=0;
        int Width=width;
        int Height=height;
        if(positionx==0){
            x=WIDTH/2-90;
        }else{
            x=WIDTH/2+10;
        }
        if(first){
            ocars.add(new Rectangle(x,y-100-(ocars.size()*space),Width,Height));
        }else{
            ocars.add(new Rectangle(x,ocars.get(ocars.size()-1).y-300,Width,Height));
        }
    }
    public void paintComponent(Graphics g){
        super.paintComponents(g);
        g.drawImage(bg, 0, 0, null);
        g.drawImage(road, WIDTH/2-125,0,null);
        g.setColor(Color.WHITE);
        for(Rectangle rect:line){
            g.fillRect(rect.x, rect.y, rect.width, rect.height);
        }
        // Draw Player's cars
        g.drawImage(user, car.x, car.y, null);
        
        
        // Draw opponent cars
        g.setColor(Color.GREEN);
        for(Rectangle rect:ocars){
            if(rand.nextInt()%2==2){//car colour flick
                g.drawImage(op1, rect.x, rect.y, null);
            
            }else{
                g.drawImage(op2, rect.x, rect.y,null);
            }
            
        }
        // Display score
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.drawString("Score: " + score, 20, 30);

        // Display "Game Over" message if game over
        if (gameOver) {
            g.setColor(Color.BLACK);
            g.setFont(new Font("Wide Latin", Font.BOLD, 40));
            g.drawString("Game Over", WIDTH / 2 - 185, HEIGHT / 6);
            // Draw "ENTER" text
            g.setColor(Color.GREEN);
            g.setFont(new Font("Times New Roman", Font.BOLD, 35));
            g.drawString("<Restart>", WIDTH / 2 - 75, HEIGHT / 3);     
            g.drawString("[ENTER]", WIDTH / 2 - 65, HEIGHT / 3 + 40);
        }
    }
    // Method to handle game actions
    public void actionPerformed(ActionEvent e) {
       if (!gameOver) {        
         Rectangle rect;
         count++;
         for(int i=0;i<ocars.size();i++){
             rect=ocars.get(i);
             if(count%1000==0){
                 if(move<10){
                     speed++;
                     move+=10;
                 }
             }
             rect.y+=speed;//Move Cars
         }
         // Check for collisions with opponent cars
         for(Rectangle r:ocars){
             if(r.intersects(car)){
                gameOver = true; // Set game over flag
             }
         }
         
         for(int i=0;i<ocars.size();i++){
             rect=ocars.get(i);
             if(rect.y+rect.height>HEIGHT){
                 ocars.remove(rect);
                 addocars(false);
                if (!gameOver) { // Only increment score if the game is not over
                 score+=100;
                }
             }
         }
         for(int i=0;i<line.size();i++){
             rect=line.get(i);
             rect.y += speed;
             if(count%1000==0){
                 
             }
             rect.y+=speed;
         }
         for(int i=0;i<line.size();i++){
             rect=line.get(i);
             if(rect.y>HEIGHT){
                 line.remove(rect);
                 addlines(false);
             }
         }
       }
        repaint();
    }
    
    //movingup
    public void moveup(){
      if (!gameOver) {  
        if(car.y-move<0){
            System.out.println("\b");
        }else{
            car.y-=move;
        }
      }
    }
    public void movedown(){
      if (!gameOver) {
        if(car.y+move+car.height>HEIGHT-1){
            System.out.println("\b");
        }else{
            car.y+=move;
        }
      }
    }
   public void moveleft(){
      if (!gameOver) {
        if(car.x-move<WIDTH/2-90){
            System.out.println("\b");
        }else{
            car.x-=move;
        }
      }
    }
    public void moveright(){
      if (!gameOver) {
        if(car.x+move>WIDTH/2+10){
            System.out.println("\b");
        }else{
            car.x+=move;
        }
      }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(KeyEvent e) {
              int key = e.getKeyCode();
        if (key == KeyEvent.VK_ENTER && gameOver) {
            restartGame();
        } else if (!gameOver) {
            // Handle other key events here...
            switch(key){
                case KeyEvent.VK_UP:
                    moveup();
                    break;
                case KeyEvent.VK_DOWN:
                    movedown();
                    break;
                case KeyEvent.VK_LEFT:
                    moveleft();
                    break;
                case KeyEvent.VK_RIGHT:
                    moveright();
                   break;
                default:
                    break ;
            }
        }  
    
    
    }

    @Override
    public void keyReleased(KeyEvent e) {
      if (!gameOver) {
        int key=e.getKeyCode();
        switch(key){
            case KeyEvent.VK_UP:
                moveup();
                break;
            case KeyEvent.VK_DOWN:
                movedown();
                break;
            case KeyEvent.VK_LEFT:
                moveleft();
                break;
            case KeyEvent.VK_RIGHT:
                moveright();
               break;
            default:
                break ;
        }
      }
    }
        private void restartGame() {
        // Reset all game variables to their initial state
        ocars.clear();
        line.clear();
        score = 0;
        gameOver = false;
        car = new Rectangle(WIDTH / 2 - 90, HEIGHT - 90, width, height);
        speed = 3;
        move = 20;
        count = 1;

        // Add initial objects
        for (int i = 0; i < 4; i++) {
            addocars(true);
        }
        for (int i = 0; i < 8; i++) {
            addlines(true);
        }

        // Restart the timer
        t.restart();

        // Repaint the JPanel
        repaint();
    }

    // Implement other methods of ActionListener and KeyListener interfaces...

    
}