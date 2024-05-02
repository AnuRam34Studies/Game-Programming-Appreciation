import javax.swing.JPanel;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Color;
import java.awt.geom.Rectangle2D;

/**
   A component that displays all the game entities
*/

public class GamePanel extends JPanel
               implements Runnable {

    private SoundManager soundManager;

    private boolean isRunning;
    private boolean isPaused;
    
    private Thread gameThread;

    private BufferedImage image;
     private Image backgroundImage;

    //private BirdAnimation animation;
    private volatile boolean isAnimShown;
    private volatile boolean isAnimPaused;

    private ImageEffect imageEffect;        // sprite demonstrating an image effect

    private TileMapManager tileManager;
    private TileMap    tileMap;

    private boolean levelChange;
    private int level;
    private boolean gameOver;
    private int health;
    private Health healthBar;
    private int power;
    private Power powerBar;


    public GamePanel () {
        
                isRunning = false;
        isPaused = false;
        isAnimShown = false;
        isAnimPaused = false;
        
        soundManager = SoundManager.getInstance();

        image = new BufferedImage (600, 500, BufferedImage.TYPE_INT_RGB);

        level = 1;
        levelChange = false;
    }


        public void createGameEntities() {
        imageEffect = new ImageEffect (this);
        healthBar = new Health(this);
        powerBar = new Power(this);

    }


    public void run () {
        try {
            isRunning = true;
            while (isRunning) {
                if (!isPaused && !gameOver)
                    gameUpdate();
                gameRender();
                Thread.sleep (50);    
            }
        }
        catch(InterruptedException e) {}
    }


    public void gameUpdate() {

        tileMap.update();

        if (levelChange) {
            levelChange = false;
            tileManager = new TileMapManager (this);

            try {
                String filename = "maps/map" + level + ".txt";
                tileMap = tileManager.loadMap(filename) ;
                int w, h;
                w = tileMap.getWidth();
                h = tileMap.getHeight();
                System.out.println ("Changing level to Level " + level);
                System.out.println ("Width of tilemap " + w);
                System.out.println ("Height of tilemap " + h);
            }
            catch (Exception e) {        // no more maps: terminate game
                gameOver = true;
                System.out.println(e);
                System.out.println("Game Over"); 
                return;
/*
                System.exit(0);
*/
            }

            createGameEntities();
            return;
                
        }

        imageEffect.update();
    }


       public void gameRender() {

        // draw the game objects on the image

        Graphics2D imageContext = (Graphics2D) image.getGraphics();

        tileMap.draw (imageContext);

        //imageEffect.draw(imageContext);            // draw the image effect

        if (gameOver) {
            Color darken = new Color (0, 0, 0, 125);
            imageContext.setColor (darken);
            imageContext.fill (new Rectangle2D.Double (0, 0, 800, 700));
        }

        Graphics2D g2 = (Graphics2D) getGraphics();    // get the graphics context for the panel
        g2.drawImage(image, 0, 0, 800, 700, null);    // draw the image on the graphics context
        g2.drawImage(healthBar.showHealth(health), 10, 10, 80, 20, null);
        g2.drawImage(powerBar.showPower(power), 100, 10, 80, 20, null);

        imageContext.dispose();
    }


    public void startGame() {                // initialise and start the game thread 

        if (gameThread == null) {
            soundManager.playSound ("theme", true);

            gameOver = false;

            tileManager = new TileMapManager (this);

            try {
                tileMap = tileManager.loadMap("maps/map1.txt");
                int w, h;
                w = tileMap.getWidth();
                h = tileMap.getHeight();
                System.out.println ("Width of tilemap " + w);
                System.out.println ("Height of tilemap " + h);
            }
            
            catch (Exception e) {
                System.out.println(e);
                System.exit(0);
            }

            createGameEntities();

            gameThread = new Thread(this);
            gameThread.start();            

        }
    }


    public void startNewGame() {                // initialise and start a new game thread 
        if (gameThread != null || !isRunning) {
            soundManager.playSound ("theme", true);

            endGame();

            gameOver = false;
            level = 1;

            tileManager = new TileMapManager (this);

            try {
                tileMap = tileManager.loadMap("maps/map1.txt");
                int w, h;
                w = tileMap.getWidth();
                h = tileMap.getHeight();
                System.out.println ("Width of tilemap " + w);
                System.out.println ("Height of tilemap " + h);
            }
            catch (Exception e) {
                System.out.println(e);
                System.exit(0);
            }

            createGameEntities();

            gameThread = new Thread(this);
            gameThread.start();            

        }
    }


    public void pauseGame() {                // pause the game (don't update game entities)
        if (isRunning) {
            if (isPaused)
                isPaused = false;
            else
                isPaused = true;
        }
    }


    public void endGame() {                    // end the game thread
        isRunning = false;
        soundManager.stopSound ("theme");
    }

    
    public void moveUp() {
        if (!gameOver)
            tileMap.moveUp();
    }


    public void moveRight() {
        if (!gameOver)
            tileMap.moveRight();
            tileMap.right();
    }
    
    public void moveDown() {
        if (!gameOver)
            tileMap.moveDown();
            tileMap.left();
    }


    public void jump() {
        if (!gameOver)
            tileMap.jump();
            tileMap.jump2();
    }
    
    public int getlevel(){
        return level;
    }


    public void endLevel() {
        level = level + 1;
        levelChange = true;
    }
    
    public void calculateHealth(){
        //if(attacker2.attackCollideWithPlayer())
            health = health - 1;
    }

    public void calculatePower(){
        //if(lanterns[0].collidesWithPlayer())
            power = power + 1;
        //if(lanterns[1].collidesWithPlayer())
            power = power + 1;
    }

}