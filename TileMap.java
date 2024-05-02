import java.awt.Image;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.Iterator;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.geom.Rectangle2D;

/**
    The TileMap class contains the data for a tile-based
    map, including Sprites. Each tile is a reference to an
    Image. Images are used multiple times in the tile map.
    map.
*/

public class TileMap {

    private static final int TILE_SIZE = 64;
    private static final int TILE_SIZE_BITS = 6;

    private Image[][] tiles;
    private int screenWidth, screenHeight;
    private int mapWidth, mapHeight;
    private int offsetY;

    private LinkedList sprites;
    private Player player;
    private Heart heart;
    private Jade jade;
    private Lantern[]lanterns;
    private Attacker2 attacker2;
    private Mei mei;
    private ShadowFang sf;
    private Yunjin yunjin;
    private Attacker1 attacker1;
    BackgroundManager bgManager;
    SoundManager soundManager;

    private GamePanel panel;
    private Dimension dimension;

    /**
        Creates a new TileMap with the specified width and
        height (in number of tiles) of the map.
    */
    public TileMap(GamePanel panel, int width, int height) 
    {

        this.panel = panel;
        dimension = panel.getSize();

        screenWidth = dimension.width;
        screenHeight = dimension.height;

        System.out.println ("Width: " + screenWidth);
        System.out.println ("Height: " + screenHeight);

        mapWidth = width;
        mapHeight = height;

        // get the y offset to draw all sprites and tiles
        offsetY = (screenHeight - tilesToPixels(mapHeight))/2;
        //offsetY = screenHeight - tilesToPixels(mapHeight);
        System.out.println("offsetY: " + offsetY);

        bgManager = new BackgroundManager (panel,panel, 12);
        soundManager = SoundManager.getInstance();
        tiles = new Image[mapWidth][mapHeight];
        player = new Player (panel, this, bgManager);
        heart = new Heart (panel, player);
        sprites = new LinkedList();
        lanterns= new Lantern[2];
        mei = new Mei(panel, player);
        yunjin = new Yunjin (panel, this, bgManager);
        attacker1=new Attacker1(panel, this, bgManager, yunjin);
        lanterns[0]=new Lantern (panel, player);
        lanterns[1]=new Lantern (panel, player);
        jade= new Jade(panel, player);
        attacker2 = new Attacker2(panel, this, bgManager, player);
        sf = new ShadowFang(panel, this, bgManager, yunjin);

        Image playerImage = player.getImage();
        int playerHeight = playerImage.getHeight(null);

        int x, y;
        x = 50;//(dimension.width / 2) + TILE_SIZE;        // position player in middle of screen

        //x = 1000;                    // position player in 'random' location
        //y = dimension.width - (TILE_SIZE + playerHeight);
        y = (screenHeight / 2) - (TILE_SIZE / 2); // Center vertically

        player.setX(x);
        player.setY(y);

        System.out.println("Player coordinates: " + x + "," + y);

    }


    /**
        Gets the width of this TileMap (number of pixels across).
    */
    public int getWidthPixels() {
        return tilesToPixels(mapWidth);
    }

    public int getHeightPixels() {
        return tilesToPixels(mapHeight);
    }

    /**
        Gets the width of this TileMap (number of tiles across).
    */
    public int getWidth() {
        return mapWidth;
    }


    /**
        Gets the height of this TileMap (number of tiles down).
    */
    public int getHeight() {
        return mapHeight;
    }


    public int getOffsetY() {
        return offsetY;
    }

    /**
        Gets the tile at the specified location. Returns null if
        no tile is at the location or if the location is out of
        bounds.
    */
    public Image getTile(int x, int y) {
        if (x < 0 || x >= mapWidth ||
            y < 0 || y >= mapHeight)
        {
            return null;
        }
        else {
            return tiles[x][y];
        }
    }


    /**
        Sets the tile at the specified location.
    */
    public void setTile(int x, int y, Image tile) {
        tiles[x][y] = tile;
    }


    /**
        Gets an Iterator of all the Sprites in this map,
        excluding the player Sprite.
    */

    public Iterator getSprites() {
        return sprites.iterator();
    }

    /**
        Class method to convert a pixel position to a tile position.
    */

    public static int pixelsToTiles(float pixels) {
        return pixelsToTiles(Math.round(pixels));
    }


    /**
        Class method to convert a pixel position to a tile position.
    */

    public static int pixelsToTiles(int pixels) {
        return (int)Math.floor((float)pixels / TILE_SIZE);
    }


    /**
        Class method to convert a tile position to a pixel position.
    */

    public static int tilesToPixels(int numTiles) {
        return numTiles * TILE_SIZE;
    }

    /**
        Draws the specified TileMap.
    */
    public void draw(Graphics2D g2)
    {
        playSound();
        int mapWidthPixels = tilesToPixels(mapWidth);

        // get the scrolling position of the map
        // based on player's position

        int offsetX = screenWidth / 2 -
        Math.round(player.getX()) - TILE_SIZE;
        offsetX = Math.min(offsetX, 0);
        offsetX = Math.max(offsetX, screenWidth - mapWidthPixels);

        /*
        // draw black background, if needed
        if (background == null ||
            screenHeight > background.getHeight(null))
        {
            g.setColor(Color.black);
            g.fillRect(0, 0, screenWidth, screenHeight);
        }
        */
        // draw the background first

        bgManager.draw (g2);

        //Draw white background (for screen capture)
        /*
        g2.setColor (Color.WHITE);
        g2.fill (new Rectangle2D.Double (0, 0, 600, 500));
        */
        // draw the visible tiles

        int firstTileX = pixelsToTiles(-offsetX);
        int lastTileX = firstTileX + pixelsToTiles(screenWidth) + 1;
        for (int y=0; y<mapHeight; y++) {
            for (int x=firstTileX; x <= lastTileX; x++) {
                Image image = getTile(x, y);
                if (image != null) {
                    g2.drawImage(image,
                        tilesToPixels(x) + offsetX,
                        tilesToPixels(y) + offsetY,
                        null);
                }
            }
        }


        /*for (int y=0; y<mapHeight; y++) {
            for (int x=firstTileX; x <= lastTileX; x++) {
                Image image = getTile(x, y);
                if (image != null) {
                    g2.drawImage(image,
                        tilesToPixels(x) + offsetX,
                        tilesToPixels(y) + offsetY,
                        null);
                }
            }
        }*/


            // draw player

            g2.drawImage(player.getImage(),
            Math.round(player.getX()) + offsetX,
            Math.round(player.getY()), 270, 152, //+ offsetY,
            null);

            g2.drawImage(attacker2.getImage(),
            Math.round(attacker2.getX()) + offsetX,
            Math.round(attacker2.getY()), //+ offsetY,
            null);

            

            // draw Heart sprite
            if(panel.getlevel()==1)
            {
            g2.drawImage(heart.getImage(),
            Math.round(heart.getX()) + offsetX,
            Math.round(heart.getY()), 40, 40, //+ offsetY, 50, 50,
            null);
        
            g2.drawImage(lanterns[0].getImage(),
            Math.round(lanterns[0].getX()) + offsetX,
            Math.round(lanterns[0].getY()), 40, 40, //+ offsetY, 50, 50,
            null);

            g2.drawImage(lanterns[1].getImage(),
            Math.round(lanterns[1].getX()) + offsetX,
            Math.round(lanterns[1].getY()), 40, 40, //+ offsetY, 50, 50,
            null);

            g2.drawImage(jade.getImage(),
            Math.round(jade.getX()) + offsetX,
            Math.round(jade.getY()), 40, 40, //+ offsetY, 50, 50,
            null);
            }
            if(panel.getlevel()!=1)
            {
            g2.drawImage(mei.getImage(),
            Math.round(mei.getX()) + offsetX,
            Math.round(mei.getY()), 40, 40, //+ offsetY, 50, 50,
            null);
            g2.drawImage(yunjin.getImage(),
            Math.round(yunjin.getX()) + offsetX,
            Math.round(yunjin.getY()), 270, 152, //+ offsetY,
            null);
            g2.drawImage(attacker1.getImage(),
            Math.round(attacker1.getX()) + offsetX,
            Math.round(attacker1.getY()), //+ offsetY,
            null);
            g2.drawImage(sf.getImage(),
            Math.round(sf.getX()) + offsetX,
            Math.round(sf.getY()), 270, 152, //+ offsetY,
            null);
            }

            //g2.drawImage(healthBar.showHealth(health), 10, 10, 80, 20, null);
            //g2.drawImage(powerBar.showPower(power), 100, 10, 80, 20, null);

    }


    public void moveUp() {
        int x, y;
        x = player.getX();
        y = player.getY();

        String mess = "Going up. x = " + x + " y = " + y;
        System.out.println(mess);

        player.move(1);

    }


    public void moveRight() {
        int x, y;
        x = player.getX();
        y = player.getY();

        String mess = "Going right. x = " + x + " y = " + y;
        System.out.println(mess);

        player.move(2);

    }
    
    public void right() {

        yunjin.move(2);

    }
    public void left() {

        yunjin.move(1);

    }
    public void jump2() {

        yunjin.move(3);

    }
    
    
    public void moveDown() {
        int x, y;
        x = player.getX();
        y = player.getY();

        String mess = "Going down. x = " + x + " y = " + y;
        System.out.println(mess);

        player.move(4);
        

    }


    public void jump() {
        int x, y;
        x = player.getX();
        y = player.getY();

        String mess = "Jumping. x = " + x + " y = " + y;
        System.out.println(mess);

        player.move(3);

    }
    
    public void playSound() {
        soundManager.playSound("theme", true);
        //soundManager.playSound("fireworks", true);
        }


    public void stopSound() {
        soundManager.stopSound("theme");
        //soundManager.playSound("fireworks", true);
    }


 public void update() 
    {
        if(panel.getlevel()==1)
        {
        player.update();
        lanterns[0].update();
        lanterns[1].update();
        attacker2.update();
        jade.update();
    

        
        if (heart.collidesWithPlayer()) {
            panel.endLevel();
            return;
        }
       /* if(mei.collidesWithPlayer())
        {
            panel.endGame();
        }*/
        heart.update();

        if (heart.collidesWithPlayer()) {
            panel.endLevel();
        }
        }
        else{
            yunjin.update();
            sf.update();
            attacker1.update();
            mei.update();
        }
        mei.update();
       /* if(mei.collidesWithPlayer())
        {
            jump();
        }*/

    }

}
