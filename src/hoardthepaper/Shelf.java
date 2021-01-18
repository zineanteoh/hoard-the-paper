package hoardthepaper;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Shelf extends GameObject {

    public Shelf(float x, float y, int width, int height, ID id) {
        super(x, y, width, height, id);
    }

    @Override
    public void tick() {
        if (Player.payingCashier && this.id == ID.Cashier && Player.inventory != null) {
            if (Player.inventory.size() > 0) {
                // Check if money sufficient
                // Change item to GREEN (meaning purchased!)
//                Player.dropItem();
            }
        }
    }

    @Override
    public void render(Graphics g) {
        if (this.id == ID.Cashier) {
            g.setColor(new Color(2, 195, 194));
        } else {
            g.setColor(Color.ORANGE);
        }
        g.fillRect((int) x, (int) y, width, height);
        g.setColor(Color.WHITE);
        g.drawRect((int) x, (int) y, width, height);
        if (this.id == ID.Cashier) {
            Font fnt = new Font("arial", Font.BOLD, 30);
            g.setColor(Color.WHITE);
            g.setFont(fnt);
            g.drawString("Cashier", (int) x + 15, (int) y + 35);
        }

    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, width, height);
    }

}
