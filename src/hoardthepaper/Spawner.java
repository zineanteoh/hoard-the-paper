package hoardthepaper;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class Spawner {

    private static HUD hud;
    private static Game game;
    // Bot Spawner
    private static ArrayList<Bot> bots;
    private static Random r;
    private static int spawnBotRate = 3; // increases by 3 every level
    // Map Spawner
    private static ArrayList<Decoration> decorations; // (Decorations include wall, roads, welcome signs, etc.)
    private static ArrayList<Shelf> shelves;
    public static Shelf cashier;
    public static Rectangle car;
    private static Rectangle roadFloor;
    // Item Spawner
    private static ArrayList<Paper> papers;
    private static double spawnPaperChance = 0.6; // Decreases by 10% every level
    private static BufferedImage paperImg;
    private static final int PAPER_WIDTH = 30, PAPER_HEIGHT = 30;

    public Spawner() {

    }

    public Spawner(HUD hud, Game game) {
        this.hud = hud;
        this.game = game;
        decorations = new ArrayList<Decoration>();
        car = new Rectangle(30, 321, 70, 120);
        shelves = new ArrayList<Shelf>();
        papers = new ArrayList<Paper>();
        bots = new ArrayList<Bot>();
        r = new Random();
        roadFloor = new Rectangle(0, 306, 349, 235);
        try {
            this.paperImg = ImageIO.read(new File("C:/Users/Zi Nean Teoh/Documents/NetBeansProjects/HoardThePaper/res/paper.png"));
            Decoration.carImg = ImageIO.read(new File("C:/Users/Zi Nean Teoh/Documents/NetBeansProjects/HoardThePaper/res/car.png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        this.paperImg = Game.resizeImage(this.paperImg, BufferedImage.TRANSLUCENT, PAPER_WIDTH, PAPER_HEIGHT);
        Decoration.carImg = Game.resizeImage(Decoration.carImg, BufferedImage.TRANSLUCENT, 70, 120);

    }

    public static void start() {
        configureLevel();
        nextLevel();
    }

    public static void configureLevel() {
        decorations.clear();

        // Walls of the shop
        decorations.add(new Decoration(0, 300, 250, 5, ID.Wall));
        decorations.add(new Decoration(325, 300, 25, 5, ID.Wall));
        decorations.add(new Decoration(350, 300, 5, 260, ID.Wall));
        decorations.add(new Decoration(250, 300, 75, 5, ID.Entrance));
        decorations.add(new Decoration(30, 321, 70, 120, ID.Car));

        // Road
        cashier = new Shelf(30, 250, 150, 50, ID.Cashier);
    }

    public static void nextLevel() {
        shelves.clear();
        papers.clear();
        bots.clear();
        Handler.clearObjects();

        // Map Spawner
        shelves.add(new Shelf(400, 35, 50, 200, ID.Shelf));
        shelves.add(new Shelf(400, 275, 50, 200, ID.Shelf));

        shelves.add(new Shelf(500, 35, 50, 200, ID.Shelf));
        shelves.add(new Shelf(500, 275, 50, 200, ID.Shelf));

        shelves.add(new Shelf(600, 35, 50, 440, ID.Shelf));

        shelves.add(new Shelf(700, 35, 50, 200, ID.Shelf));
        shelves.add(new Shelf(700, 275, 50, 200, ID.Shelf));

        shelves.add(new Shelf(800, 35, 50, 200, ID.Shelf));
        shelves.add(new Shelf(800, 275, 50, 200, ID.Shelf));

        shelves.add(new Shelf(905, 35, 50, 440, ID.Shelf));
        
        configureLevel();
        
        // Item spawner 
        if (paperImg != null) {
            for (int i = 0; i < shelves.size(); i++) {
                // Get each shelf
                Shelf s = shelves.get(i);
                // Fill paper throughout shelf
                if (i == shelves.size() - 1) {
                    for (int j = 0; j <= (s.height - 50) / PAPER_HEIGHT; j++) {
                        papers.add(new Paper((int) s.x + 5, (int) s.y + j * PAPER_HEIGHT, PAPER_WIDTH, PAPER_HEIGHT, ID.Paper, paperImg));
                    }
                    break;
                }
                for (int j = 0; j <= (s.height - 50) / PAPER_HEIGHT; j++) {
                    if (r.nextDouble() < spawnPaperChance) {
                        papers.add(new Paper((int) s.x - 5, (int) s.y + j * PAPER_HEIGHT, PAPER_WIDTH, PAPER_HEIGHT, ID.Paper, paperImg));
                    }
                    if (r.nextDouble() < spawnPaperChance) {
                        papers.add(new Paper((int) s.x + 24, (int) s.y + j * PAPER_HEIGHT, PAPER_WIDTH, PAPER_HEIGHT, ID.Paper, paperImg));
                    }
                }
            }
        }
        spawnPaperChance *= 0.9;

        // Bot Spawner
        for (int i = 0; i < spawnBotRate; i++) {
            bots.add(new Bot(r.nextInt(Game.WIDTH), r.nextInt(Game.HEIGHT), 30, 30, ID.Bot));
        }
        spawnBotRate += 2;

        // Level up
        hud.setLevel(hud.getLevel() + 1);
    }

    public static void restart() {
        decorations.clear();
        shelves.clear();
        hud.setLevel(0);
        start();
    }

    public static void tick() {
        for (int i = 0; i < bots.size(); i++) {
            bots.get(i).tick();
        }
    }

    public static void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        // Road Floor
        g2d.setColor(Color.GRAY);
        g2d.drawRect(roadFloor.x, roadFloor.y, roadFloor.width, roadFloor.height);
        g2d.fillRect(roadFloor.x, roadFloor.y, roadFloor.width, roadFloor.height);

        // Road Decorations (yellow lines)
        g2d.setColor(new Color(255, 210, 63));
        g2d.drawRect(roadFloor.x + 10, roadFloor.y, 5, 150);
        g2d.fillRect(roadFloor.x + 10, roadFloor.y, 5, 150);

        g2d.drawRect(roadFloor.x + 110, roadFloor.y, 5, 150);
        g2d.fillRect(roadFloor.x + 110, roadFloor.y, 5, 150);

        g2d.drawRect(roadFloor.x + 210, roadFloor.y, 5, 150);
        g2d.fillRect(roadFloor.x + 210, roadFloor.y, 5, 150);

        g2d.drawRect(0, roadFloor.y + 150, 25, 5);
        g2d.fillRect(0, roadFloor.y + 150, 25, 5);

        g2d.drawRect(100, roadFloor.y + 150, 25, 5);
        g2d.fillRect(100, roadFloor.y + 150, 25, 5);

        g2d.drawRect(200, roadFloor.y + 150, 25, 5);
        g2d.fillRect(200, roadFloor.y + 150, 25, 5);

        // Disabled Parking
        g2d.setColor(new Color(120, 195, 241));
        g2d.fillRect(133, roadFloor.y + 50, 60, 60);
        g2d.setColor(Color.WHITE);
        g2d.fillOval(150, roadFloor.y + 55, 15, 15);
        g2d.setStroke(new BasicStroke(7));
        g2d.drawLine(155, roadFloor.y + 70, 157, roadFloor.y + 90);
        g2d.drawLine(157, roadFloor.y + 90, 167, roadFloor.y + 90);
        g2d.drawLine(169, roadFloor.y + 91, 175, roadFloor.y + 99);
        g2d.drawArc(143, roadFloor.y + 80, 25, 25, 130, 190);
        g2d.setStroke(new BasicStroke(1));

        // # of items
        g2d.setColor(Color.WHITE);
        g2d.drawString("#Items: " + HUD.CAR_ITEM, roadFloor.x + 30, roadFloor.y + 15);

    }
}
