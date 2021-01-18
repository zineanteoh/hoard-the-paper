package hoardthepaper;

// Class for Head Up Display
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class HUD {

    public static int MONEY = 100; // Starting amount
    public static float HEALTH = 100; // 0 to 100
    public static int CAR_ITEM = 0; // Number of items stored in car
    private int level = 0; // AKA # of Days
    
    private BufferedImage openSlotImg; // To signify inventory slot is open
    private BufferedImage filledSlotImg; // To signify inventory is filled
    private BufferedImage paidImg;

    public HUD() {
        try {
            this.openSlotImg = ImageIO.read(new File("C:/Users/Zi Nean Teoh/Documents/NetBeansProjects/HoardThePaper/res/gray_paper.png"));
            this.filledSlotImg = ImageIO.read(new File("C:/Users/Zi Nean Teoh/Documents/NetBeansProjects/HoardThePaper/res/paper.png"));
            this.paidImg = ImageIO.read(new File("C:/Users/Zi Nean Teoh/Documents/NetBeansProjects/HoardThePaper/res/paid.png"));
        } catch (IOException ex) {
            Logger.getLogger(HUD.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.filledSlotImg = Game.resizeImage(this.filledSlotImg, BufferedImage.TRANSLUCENT, 40, 40);
        this.openSlotImg = Game.resizeImage(this.openSlotImg, BufferedImage.TRANSLUCENT, 40, 40);
        this.paidImg = Game.resizeImage(this.paidImg, BufferedImage.TRANSLUCENT, 40, 40);
    }

    public void tick() {
        HEALTH = Game.clamp(HEALTH, 0, 100);
    }

    public void render(Graphics g) {
        // Health Bar
        g.setColor(Color.gray);
        g.fillRect(15, 20, 200, 32);
        g.setColor(Color.getHSBColor(1f * HEALTH / 360, 1f, 1f));
        g.fillRect(15, 20, (int) HEALTH * 2, 32);

        // Health %
        Font fnt = new Font("arial", Font.BOLD, 15);
        g.setColor(Color.WHITE);
        g.drawRect(15, 20, 200, 32);
        g.setFont(fnt);
        g.drawString((int) HEALTH + "%", 180, 15);

        // Money
        g.setColor(Color.WHITE);
        g.setFont(fnt);
        g.drawString("Money: $" + MONEY, 15, 70);

        // Day (Level)
        g.drawString("Day: " + level, 15, 15);

        // Inventory Border
        g.setColor(new Color(209, 209, 209));
        g.fillRect(10, Game.HEIGHT - 80, 10 + Player.inventoryLimit * 30, 40);
        g.fillRect(10, Game.HEIGHT - 100, 90, 20);
        g.setColor(Color.WHITE);
        g.drawString("Inventory: ", 15, Game.HEIGHT - 85);

        // Inventory slots
        for (int i = 10; i <= Player.inventoryLimit * 30; i += 30) {
            g.drawImage(this.openSlotImg, i, Game.HEIGHT - 80, null);
        }
        if (Player.inventory != null) {
            for (int i = 10; i < Player.inventory.size() * 30; i += 30) {
                g.drawImage(this.filledSlotImg, i, Game.HEIGHT - 80, null);
                if (Player.inventory.get((i - 10) / 30).isPaid()) {
                    g.drawImage(this.paidImg, i, Game.HEIGHT - 80, null);
                }
            }
        }
    }

    public int getMoney() {
        return this.MONEY;
    }

    public int getLevel() {
        return this.level;
    }

    public void setMoney(int money) {
        this.MONEY = money;
    }

    public void setLevel(int level) {
        this.level = level;
    }

}
