import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Line2D;
import javax.swing.JPanel;
import java.util.Random;
import java.awt.Image;

public class Jade {

   private JPanel panel;

   private int x;
   private int y;
   private static final int XSIZE = 50;        // width of the image
   private static final int YSIZE = 50;        // height of the image
   private int width;
   private int height;

   private int originalX;
   private int originalY;

   private int dx;        // increment to move along x-axis
   private int dy;        // increment to move along y-axis

   private Color backgroundColour;
   private Dimension dimension;

   private Random random;

   private Player girl;
   private SoundManager soundManager;
   private Image alienImage;
   private Image spriteImage; 
   private int hearts=3;
   private int points=0;
    int count=0;
   public Jade (JPanel p, Player girl) {
      panel = p;
      dimension = panel.getSize();
      backgroundColour = panel.getBackground ();

      width = 50;
      height = 45;
        
      random = new Random();

      x = 300;
      y = 100;

      setLocation();

      dx = 0;         
      dy = 1;   

      this.girl = girl;
      spriteImage = ImageManager.loadImage ("images/JADE2.png");
      /*soundManager = SoundManager.getInstance();
      soundManager.setVolume("fall",0.6f);
      soundManager.playClip("fall", false);
      
      soundManager.setVolume("voice1",0.9f);
      soundManager.playClip("voice1", false);*/
   }

   
   public void setLocation() {
      int panelWidth = panel.getWidth();
      x = random.nextInt (panelWidth - width);
      y = random.nextInt (300);//300
   }


   public void draw (Graphics2D g2) {

      g2.drawImage(alienImage, x, y, width, height, null);

   }


   public void update() {
      boolean collision = collidesWithPlayer();
      count=count+1;
      if(count>=100)
      {
        //setLocation();
        int panelWidth = girl.getX();
        int ran=random.nextInt (600);
        x= panelWidth+ran;
        y = random.nextInt (300);
        count=0;
      }
      if (collision) {
          //setLocation();
        int panelWidth = girl.getX();
        int ran=random.nextInt (600);
        x= panelWidth+ran;
        y = random.nextInt (300);
          collidesWithPlayer();
      }

   }
    public void pointsPassed()
    {
        points=points+1;
    }
    public int levelPoints()
    {
        return points;
    }
    public void heartsDraining()
    {
        hearts=hearts-1; 
    }
    
    public int heartsRemaining()
    {
         return hearts;
    }
   
   public boolean collidesWithPlayer () {
        Rectangle2D.Double myRect = getBoundingRectangle();
        Rectangle2D.Double playerRect = girl.getBoundingRectangle();
        
        if (myRect.intersects(playerRect)) {
            System.out.println ("Collision with player!");
            return true;
        }
        else
            return false;
    }


    public Rectangle2D.Double getBoundingRectangle() {
        return new Rectangle2D.Double (x, y, XSIZE, YSIZE);
    }
   
          public int getX() {
              return x;
       }


       public void setX(int x) {
              this.x = x;
       }


       public int getY() {
              return y;
       }


       public void setY(int y) {
              this.y = y;
       }


       public Image getImage() {
              return spriteImage;
       }

}