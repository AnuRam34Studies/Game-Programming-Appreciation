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

public class Lantern {

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

   public Lantern (JPanel p, Player girl) {
      panel = p;
      dimension = panel.getSize();
      backgroundColour = panel.getBackground ();

      width = 100;
      height = 100;

      random = new Random();


      //setLocation();

      dx = 0;            // no movement along x-axis
      dy = 1;            // would like the alien to drop down

      this.girl = girl;
      x = girl.getX()+random.nextInt(300);
      y = 100;
      spriteImage = ImageManager.loadImage ("images/lantern1.2.png");
      /*soundManager = SoundManager.getInstance();
      soundManager.setVolume("fall",0.6f);
      soundManager.playClip("fall", false);
      
      soundManager.setVolume("voice1",0.9f);
      soundManager.playClip("voice1", false);*/
   }

   
  /*public void setLocation() {
      //int panelWidth = panel.getWidth();
      int panelWidth = girl.getX()+400;
      x = random.nextInt (panelWidth - width);
      y = 10;
   }*/


   public void draw (Graphics2D g2) {

      g2.drawImage(alienImage, x, y, width, height, null);

   }


   public void update() {

      if (!panel.isVisible ()) return;

       x = x + dx;
       y = y + dy;

       /* x += girl.getX();
        y += girl.getY();*/

      int height = 500;
      boolean collision = collidesWithPlayer();
      pointsPassed();
      if (collision) {
          heartsDraining();
          heartsRemaining();
          collidesWithPlayer();
        }
      if (collision)
      {
      
      collidesWithPlayer();
      }

      if (collision) {
      //setLocation();
     // int panelWidth = girl.getX()+200;
     // x =random.nextInt(panelWidth-width);
      int panelWidth = girl.getX();
      //x = panelWidth;//

      int ran=random.nextInt (400);
      x= panelWidth+ran;
      y = 10;
      collidesWithPlayer();

      }
      
      if(collision)
      {
          collidesWithPlayer();
      }
      if(y>480)
      {
          /*soundManager.setVolume("landing", 0.8f);
          soundManager.playClip("landing", false);*/
      }
      if (y > height) {
      int panelWidth = girl.getX();
      //x = panelWidth;//

      int ran=random.nextInt (600);
      x= panelWidth+ran;
      y = 10;
      //setLocation();
      /*soundManager.setVolume("fall", 0.8f);
      soundManager.playClip("fall", false);*/
      //dy = dy + 1;            
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
   
    public void updatePosition(int playerX) {
        x = originalX + (playerX - originalX);
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