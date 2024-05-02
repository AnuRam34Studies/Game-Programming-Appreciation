import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;
import java.awt.Dimension;

public class ProjectileMotion2 implements Motion {

       private static final int XSIZE = 10;
       private static final int YSIZE = 10;

       private JPanel panel;
    private ShadowFang bat; // this is the entity that throws the objects // change to attacker
       private int x = 0;
       private int y = 200;
       private int dx = 2;
       private int dy = 2;
    private int xPos, yPos;            // location from which to generate projectile
       private int initialVelocityX = 25;
       private int initialVelocityY = 25;
        private Yunjin player;
       private Dimension dimension;
       
       double timeElapsed;
    boolean active;

    //private Player player;
    private boolean resetProjectile;

    public ProjectileMotion2 (JPanel p, ShadowFang b, Yunjin player) {
          panel = p;
          this.player= player;
        bat = b;
        active = false;
        timeElapsed = 0;
          dimension = panel.getSize();
        xPos = 25;
        yPos = panel.getHeight() - YSIZE;
        resetProjectile = false;
        //this.player = player;
    }


    public boolean isActive() {
        return active;
    }


    public void activate() {
        xPos = bat.getX();
        yPos = bat.getY();
        active = true;
        timeElapsed = 0;

        System.out.println("Bat coordinates: " + xPos + "," + yPos); // here
    }
    

    public void deActivate() {
        active = false;
    }


       public void update () {  

        int oldX, oldY;

          if (!panel.isVisible ()) return;

        oldX = x;
        oldY = y;
     
        timeElapsed = timeElapsed + 0.5;

        x = (int) (initialVelocityX * timeElapsed);
        y = (int) (initialVelocityY * timeElapsed - 4.9 * timeElapsed * timeElapsed);

        if (y > 0)
            y = yPos - y;            // yPos is the height at which ball is thrown
        else
            y = yPos + y * -1;

        if (x < -XSIZE || x > dimension.width) {        // outside left and right boundaries
            active = false;
            return;
        }

        // if (y > 425 - YSIZE) {             // below floor; extrapolate to bring ball on top of floor.
        //     System.out.println ("Going below floor: Y = " + y);
        //     int yDistance = y - oldY;
        //     int xDistance = x - oldX;
        //     y = 425 - YSIZE;        // re-position so that ball is on top of floor
        //     int aboveGround = 425 - (oldY + YSIZE);
        //     x = oldX + (int) (aboveGround * 1.0 / yDistance) * xDistance; 

        //     active = false;
        // }
        if (collision())
        {
            activate();
            /* x = bat.getX();
            y = bat.getY();

            //System.out.println ("Going below floor: Y = " + y);
            int yDistance = y - oldY;
            int xDistance = x - oldX;
            //y = 425 - YSIZE;        // re-position so that ball is on top of floor
            int aboveGround = panel.getHeight() - (oldY + YSIZE);
            x = oldX + (int) (aboveGround * 1.0 / yDistance) * xDistance; 
            active = false;
            resetProjectile = false;*/
        }
        else if(x>= player.getX())
        {
            deActivate();
        }
       /* if(resetProjectile){
            x = bat.getX();
            y = bat.getY();

            //System.out.println ("Going below floor: Y = " + y);
            int yDistance = y - oldY;
            int xDistance = x - oldX;
            //y = 425 - YSIZE;        // re-position so that ball is on top of floor
            int aboveGround = panel.getHeight() - (oldY + YSIZE);
            x = oldX + (int) (aboveGround * 1.0 / yDistance) * xDistance; 
            active = false;
            resetProjectile = false;
        }*/
        /*
        if (y > 425 - YSIZE) {             // below floor; extrapolate to bring ball on top of floor.
            System.out.println ("Y = " + y);
            int amountOver = y - (425 - YSIZE); 
            y = 425 - YSIZE;
            System.out.println ("New Y = " + y);
            double fractionOver = (amountOver * 1.0) / (y - oldY);
            timeElapsed = timeElapsed - (1 - fractionOver) * 0.5;
            x = (int) (initialVelocityX * timeElapsed);
            active = false;
        }
        */
    }
    

       public void draw (Graphics2D g2) { 

        /*
        if (!active)
            return;
        */
              g2.setColor (Color.BLUE);
            
        if (bat.getDirection() == 2)        // going right: add x to xPos
                  g2.fill (new Ellipse2D.Double (x+xPos, y, XSIZE, YSIZE));
        else                    // going left: subtract x from xPos
                  g2.fill (new Ellipse2D.Double (xPos - x, y, XSIZE, YSIZE));

       }

    public Rectangle2D.Double getBoundingRectangle(){
        return new Rectangle2D.Double(x, y, XSIZE, YSIZE);
    }

    // public boolean collideWithPlayer(){
    //     Rectangle2D.Double myRect = getBoundingRectangle();
    //     Rectangle2D.Double batRect = player.getBoundingRectangle();
    //     //System.out.println("YES IT HIT");
    //     //return myRect.intersects(batRect); 

    //     if (myRect.intersects(batRect)) {
    //         System.out.println ("Collision with player!");
    //         return true;
    //     }
    //     else
    //         return false;
    // }
    
    public boolean collision() {
        Rectangle2D.Double myRect = getBoundingRectangle();
        Rectangle2D.Double playerRect = player.getBoundingRectangle();
        
        if (myRect.intersects(playerRect)) {
            System.out.println ("Collision with player!");
            return true;
        }
        else
            return false;
    }

    public void setReset(boolean determine){
        if(determine)
            resetProjectile = true;
        else
            resetProjectile = false;
    }
    

    // if collision with player or object make disappear
    // attacker launchs three times as they pass by // interval 15 secs
}
