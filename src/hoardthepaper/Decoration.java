package hoardthepaper;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

// This class is responsible for the layout of the shopping mall
public class Decoration extends GameObject {

    public static BufferedImage carImg;

    // Array of all the walls
    public Decoration(float x, float y, int width, int height, ID id) {
        super(x, y, width, height, id);
    }

    @Override
    public void tick() {
    }

    @Override
    public void render(Graphics g) {
        if (this.id == ID.Wall) {
            g.setColor(Color.BLACK);
        } else if (this.id == ID.Cashier) {
            g.setColor(new Color(2, 195, 194));
        } else if (this.id == ID.Road) {
            g.setColor(Color.GRAY);
        } else if (this.id == ID.Entrance) {
            g.setColor(Color.WHITE);
        } else if (this.id == ID.Car) {
            g.drawImage(carImg, Spawner.car.x, Spawner.car.y, null);
            return;
        }
        g.drawRect((int) x, (int) y, width, height);
        g.fillRect((int) x, (int) y, width, height);
    }

    // This method should return null. Collision detection will be checked separately
    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, width, height);
    }

}
