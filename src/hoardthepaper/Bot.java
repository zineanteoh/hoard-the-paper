package hoardthepaper;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class Bot extends GameObject {

    private static BufferedImage img;

    public Bot(float x, float y, int width, int height, ID id) {
        super(x, y, width, height, id);
        velX = 3;
        velY = 3;
        try {
            this.img = ImageIO.read(new File("C:/Users/Zi Nean Teoh/Documents/NetBeansProjects/HoardThePaper/res/virus.png"));
        } catch (IOException ex) {
            Logger.getLogger(Bot.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.img = resizeImage(this.img, BufferedImage.TRANSLUCENT, width, height);
    }
    
    private static BufferedImage resizeImage(BufferedImage originalImage, int type, Integer imgWidth, Integer imgHeight) {
        BufferedImage resizedImage = new BufferedImage(imgWidth, imgHeight, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, imgWidth, imgHeight, null);
        g.dispose();
        return resizedImage;
    }

    @Override
    public void tick() {
        x += velX;
        y += velY;
        if (y <= 0 || y >= Game.HEIGHT - this.height * 2 + 2) {
            velY *= -1;
        }
        if (x <= 0 || x >= Game.WIDTH - this.width - 5) {
            velX *= -1;
        }
//        collision();
    }

    private void collision() {
        for (int i = 0; i < Handler.objects.size(); i++) {
            GameObject obj = Handler.objects.get(i);
            if (obj.getID() != ID.Bot) {
                if (getBounds().intersects(obj.getBounds())) {
                    float newVal = 0;
                    // Blocked. Depends on velX and velY
                    // Moving right
                    if (velX > 0) {
                        newVal = Game.clamp(x, 0, obj.x - width - 1);
                        if (Math.abs(x - newVal) < 25) {
                            x = newVal;
                        }
                    }
                    // Moving left
                    if (velX < 0) {
                        newVal = Game.clamp(x, obj.x + obj.width + 2, Game.WIDTH - width - 5);
                        if (Math.abs(x - newVal) < 25) {
                            x = newVal;
                        }
                    }
                    // Moving up
                    if (velY < 0) {
                        newVal = Game.clamp(y, obj.y + obj.height + 2, Game.HEIGHT - height * 2 + 2);
                        if (Math.abs(y - newVal) < 25) {
                            y = newVal;
                        }
                    }
                    // Moving down
                    if (velY > 0) {
                        newVal = Game.clamp(y, 0, obj.y - height - 1);
                        if (Math.abs(y - newVal) < 25) {
                            y = newVal;
                        }
                    }
                }
            }
        }
    }

    @Override
    public void render(Graphics g) {
//        g.setColor(Color.GRAY);
//        g.fillRect((int) this.x, (int) this.y, this.width, this.height);
        g.drawImage(this.img, (int) this.x, (int) this.y, null);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) this.x, (int) this.y, this.width, this.height);
    }
}
