package hoardthepaper;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Game extends Canvas implements Runnable {

    // Game Window's Dimension (16:9 ratio)
    public static final int WIDTH = 960, HEIGHT = WIDTH / 16 * 9;

    // Thread controls
    private Thread thread;
    private boolean running = false;

    // Class variables
    private Menu menu;
    private Spawner spawner;
    private HUD hud;

    // Background Image
    private BufferedImage bgImage;

    // State of the game
    public enum STATE {
        Menu, // Menu screen. Choose to play, help or quit
        Game, // Game screen. Playing
        Help, // Help screen. tutorial
        End   // End screen. Try again button
    };

    // Set initial state to Menu screen
    public static STATE gameState = STATE.Menu;

    public Game() throws IOException {
        this.hud = new HUD();
        this.spawner = new Spawner(hud, this);
        this.menu = new Menu(this, spawner);
        new Window(WIDTH, HEIGHT, "Hoard The Paper", this);
        // Get background image
        bgImage = ImageIO.read(new File("C:/Users/Zi Nean Teoh/Documents/NetBeansProjects/HoardThePaper/res/essential_items.png"));

        // -------------------Start messing around------------------
        if (gameState == STATE.Menu) {
            // Add falling tissue rolles
        }

        // --------------Start of Event Listeners--------------
        this.addMouseListener(menu);
        this.addKeyListener(new KeyInput(this));
        // --------------End of Event Listeners--------------
    }

    public synchronized void start() {
        // Run the thread, set running to true
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    public synchronized void stop() {
        try {
            // Kill of Thread, set running to false
            thread.join();
            running = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Frame control
    @Override
    public void run() {
        // Allow this class to listen to events
        this.requestFocus();
        // A popular code template copied from online
        // ... controls how frequently the frames are being updated
        // ... allow sufficient time to pass to achieve ~60fps
        double amountOfTicks = 60.0;
        long lastTime = System.nanoTime();
        double ns = 1e9 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while (running) {
            long now = System.nanoTime();
            // Calculate how many ticks have passed 
            delta += (now - lastTime) / ns;
            lastTime = now;
            // Allow a steady pause in game loop
            while (delta >= 1) {
                // Tick() runs the game mechanism
                tick();
                delta--;
            }
            // Pause is complete. Proceed to rendering next frame
            if (running) {
                render();
            }
            // A frame has passed
            frames++;

            // Print FPS frames every second by using timer to keep track of last printed time
            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                System.out.println("FPS: " + frames);
                frames = 0;
            }
        }
        stop();

    }

    // Method to tick other objects
    private void tick() {
        if (gameState == STATE.Menu) {
            this.menu.tick();
            Handler.tick();
        } else if (gameState == STATE.Game) {
            Handler.tick();
            this.hud.tick();
            if (HUD.HEALTH <= 0) {
                HUD.HEALTH = 100;
                gameState = STATE.End;
                Handler.clearObjects();
            }

            // Tick game objects
        }
    }

    // Method to render graphics 
    private void render() {
        // Uses BufferStrategy to render and store graphics
        // ... the graphics will then be sent to screen to display

        BufferStrategy bs = this.getBufferStrategy();
        // When rendering for the first time, bs is null
        if (bs == null) {
            // Create 3 buffers for triple-buffering 
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        // Window color
        g.setColor(new Color(220, 142, 68));
        g.fillRect(0, 0, WIDTH, HEIGHT);

        // --------------Start of Add Code--------------
        
        if (gameState == STATE.Menu && this.bgImage != null) {
            this.menu.render(g);
            g.drawImage(bgImage, 375, 130, null);
        } else if (gameState == STATE.Game) {
            Handler.render(g);
            this.hud.render(g);
        }
        
        // --------------End of Add Code--------------
        g.dispose();
        bs.show();
    }

    // Method for keeping a variable between min and max 
    public static float clamp(float var, float min, float max) {
        if (var >= max) {
            return max;
        } else if (var <= min) {
            return min;
        } else {
            return var;
        }
    }

    public static BufferedImage resizeImage(BufferedImage originalImage, int type, Integer imgWidth, Integer imgHeight) {
        BufferedImage resizedImage = new BufferedImage(imgWidth, imgHeight, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, imgWidth, imgHeight, null);
        g.dispose();
        return resizedImage;
    }

    // -----------Main Class--------------
    public static void main(String[] args) throws IOException {
        // Start game
        Game game = new Game();
    }

}
