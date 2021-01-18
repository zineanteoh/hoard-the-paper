package hoardthepaper;

import static hoardthepaper.Game.HEIGHT;
import hoardthepaper.Game.STATE;
import static hoardthepaper.Game.WIDTH;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

// Menu screen (Wait for user to choose Play, Help, Quit...)
public class Menu extends MouseAdapter {

    // Private variables for Menu
    private Game game;
    private Spawner spawner;

//    private BufferedImage bgImage;
    public Menu(Game game, Spawner spawner) {
        this.game = game;
        this.spawner = spawner; 
//        System.out.println("received");
    }

    public void mousePressed(MouseEvent e) {
        int mx = e.getX(), my = e.getY();
        if (game.gameState == STATE.Menu) {
            // Play button
            if (mouseOver(mx, my, 50, 180, 275, 75)) {
                game.gameState = STATE.Game;
                spawner.start();
                new Player(50, Game.HEIGHT - 100, 30, 30, ID.Player);

                System.out.println("State Changed: from Menu to Game");
            } // Help Button
            else if (mouseOver(mx, my, 50, 280, 275, 75)) {
                game.gameState = STATE.Help;
                System.out.println("State Changed: from Menu to Help");
            } // Quit button
            else if (mouseOver(mx, my, 50, 380, 275, 75)) {
                System.out.println("State Changed: from Menu to Quit");
                System.exit(0);
            }
        }
    }

    public void mouseReleased(MouseEvent e) {
    }

    private boolean mouseOver(int mx, int my, int x, int y, int width, int height) {
        if (mx > x && mx < x + width) {
            if (my > y && my < y + height) {
                return true;
            }
        }
        return false;
    }

    public void tick() {

    }

    public void render(Graphics g) {
        Font fntBig = new Font("arial", Font.BOLD, 90);
        Font fntMedium = new Font("arial", Font.PLAIN, 40);
        if (game.gameState == STATE.Menu) {

            // Title 
            g.setFont(fntBig);
            g.setColor(Color.WHITE);
            g.drawString("Hoard The Paper", 50, 100);

            // Play button text
            g.setFont(fntMedium);
            g.setColor(Color.WHITE);
            g.drawRect(50, 180, 275, 75);
            g.drawString("Play", 150, 230);

            // Help button text
            g.drawRect(50, 280, 275, 75);
            g.drawString("Help", 150, 330);

            // Quit button text
            g.drawRect(50, 380, 275, 75);
            g.drawString("Quit", 150, 430);

        }
    }

}
