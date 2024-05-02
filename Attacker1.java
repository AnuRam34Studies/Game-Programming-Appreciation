import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.Image;
import java.awt.Point;
import javax.swing.JPanel;

public class Attacker1{

    private static final int DX = 8; // amount of X pixels to move in one keystroke
    private static final int TILE_SIZE = 64;

    private JPanel panel;
    //private TileMap tileMap;
    //private BackgroundManager bgManager;
    private Yunjin player;

    private int x;
    private int y;
    private Image playerImage, playerLeftImage, playerRightImage;

    private int dx;
    private int dy;

    private ProjectileMotion projectileMotion;
    private boolean isActive;

    public Attacker1(JPanel panel, TileMap t, BackgroundManager b, Yunjin player) {
        this.panel = panel;
        //tileMap = t;
        //bgManager = b;
        this.player = player;
        isActive = false;
        y=350;
        dx = 5;
        dy = 5;

        playerLeftImage = ImageManager.loadImage("images/playerLeft.gif");
        playerRightImage = ImageManager.loadImage("images/playerRight.gif");
        playerImage = ImageManager.loadImage("images/attacker2.gif");
    }

    
    public void chasePlayer() {
     if (!panel.isVisible ()) return;
         if (x > player.getX())
            x = x - dx;
         else
         if (x < player.getX())
            x = x + dx;
         /*if (y > player.getY())
            y = y - dy;
                else
         if (y < player.getY())
            y = y + dy;*/
    }

    public void update() {
        chasePlayer();
        boolean collision = collidesWithPlayer();
        if(collision){

            chasePlayer();
            
        }

    }



    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Image getImage() {
        return playerImage;
    }


    public int getDirection() {
        if (playerImage == playerLeftImage)
           return 1;
        else
           return 2;
    }


    public Rectangle2D.Double getBoundingRectangle() {
        int playerWidth = playerImage.getWidth(null);
        int playerHeight = playerImage.getHeight(null);

        return new Rectangle2D.Double (x, y, playerWidth, playerHeight);
    }
    
    public boolean collidesWithPlayer () {
        Rectangle2D.Double myRect = getBoundingRectangle();
        Rectangle2D.Double playerRect = player.getBoundingRectangle();
        
        if (myRect.intersects(playerRect)) {
            System.out.println ("Collision with player!");
            return true;
        }
        else
            return false;
    }

}
