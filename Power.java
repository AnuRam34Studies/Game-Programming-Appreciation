import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.Image;
import java.awt.Point;
import javax.swing.JPanel;


public class Power {
    private Player player;

    private int x; // set x position
    private int y;

    // for the intention of functionality
    Image health1, health2, health3, health4, health5, health6, health7,health8,health9,health10;

    public Power(JPanel panel){
        this.player = player;
        
        health1 = ImageManager.loadImage("images/health1.png");
        health2 = ImageManager.loadImage("images/health2.png");
        health3 = ImageManager.loadImage("images/health3.png");
        health4 = ImageManager.loadImage("images/health4.png");
        health5 = ImageManager.loadImage("images/health5.png");
        health6 = ImageManager.loadImage("images/health6.png");
        health7 = ImageManager.loadImage("images/health7.png");
        health8 = ImageManager.loadImage("images/health8.png");
        health9 = ImageManager.loadImage("images/health9.png");
        health10 = ImageManager.loadImage("images/health10.png");

    }

    public Image showPower(int power){
        if(power == 10){
            return health10;
        }
        else if(power == 9){
            return health9;
        }
        else if(power == 8){
            return health8;
        }
        else if(power == 7){
            return health7;
        }
        else if(power == 6){
            return health6;
        }
        else if(power == 5){
            return health5;
        }
        else if(power == 4){
            return health4;
        }
        else if(power == 3){
            return health3;
        }
        else if(power == 2){
            return health2;
        }
        else 
            return health1;
    }
}
