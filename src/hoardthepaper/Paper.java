package hoardthepaper;

import static hoardthepaper.Player.dropItem;
import static hoardthepaper.Player.droppingItem;
import static hoardthepaper.Player.getFirstPaidItem;
import static hoardthepaper.Player.inventory;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class Paper extends GameObject {

    private BufferedImage paperImg;
    private boolean paid = false;  // true when user is holding it
    private boolean heldByPlayer = false;
    public static int costToBuy = 10;
    public static int sellingPrice = 20;

    public Paper(float x, float y, int width, int height, ID id, BufferedImage img) {
        super(x, y, width, height, id);
        this.paperImg = img;
    }

    @Override
    public void tick() {
        // Check for proximity with player 
        // Allow player to PICK UP ITEM
        if (Player.findingItem && Player.inventory.size() < Player.inventoryLimit && !this.heldByPlayer) {
            for (int i = 0; i < Handler.objects.size(); i++) {
                GameObject obj = Handler.objects.get(i);
                if (obj.id == ID.Player) {
                    if (this.closeTo(getBounds(), obj.getBounds())) {
                        Player.addItem(this);
                        this.heldByPlayer = true;
                        Player.findingItem = false;
                    }
                }
            }
        } // Allow player to BUY ITEM
        else if (Player.payingCashier && Player.inventory.size() > 0 && !this.paid && this.heldByPlayer) {
            // Allow player to pay for paper at the cashier
            // Check if this item is the first unpaid item
            Paper tempPaper = Player.getFirstUnpaidItem();
            if (tempPaper != null && tempPaper.equals(this)) {
                for (int i = 0; i < Handler.objects.size(); i++) {
                    GameObject obj = Handler.objects.get(i);
                    if (obj.id == ID.Player) {
                        if (this.closeTo(Spawner.cashier.getBounds(), obj.getBounds())) {
                            HUD.MONEY -= Paper.costToBuy;
                            this.paid = true;
                            Player.payingCashier = false;
                        }
                    }
                }
            }
        } // Allow player to DROP ITEMS
        else if (Player.droppingItem && this.heldByPlayer && !this.paid && Player.droppingItem && Player.inventory.size() > 0) {
            // only dropping unpaid items when in store
            Player.dropItem(this);
            Player.droppingItem = false;
            this.heldByPlayer = false;
        } // Allow player to STORE ITEMS in car
        else if (droppingItem && inventory.size() > 0) {
            // Check if player's bound intersects with car bound
            for (int i = 0; i < Handler.objects.size(); i++) {
                if (Handler.objects.get(i).id == ID.Player) {
                    if (Handler.objects.get(i).getBounds().intersects(Spawner.car.getBounds())) {
                        System.out.println("DROPPED");
                        dropItem(getFirstPaidItem());
                        HUD.CAR_ITEM++;
                        droppingItem = false;
                    }
                }
            }

        }
    }

    @Override
    public void render(Graphics g) {
        if (!this.heldByPlayer && this.paperImg != null) {
            g.drawImage(this.paperImg, (int) this.x, (int) this.y, null);
        }
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, width, height);
    }

    public boolean isPaid() {
        return this.paid;
    }

    public boolean closeTo(Rectangle r1, Rectangle r2) {
        // Calculate center points
        int cx1 = r1.x + r1.width / 2;
        int cy1 = r1.y + r1.height / 2;
        int cx2 = r2.x + r2.width / 2;
        int cy2 = r2.y + r2.height / 2;

        double a = Math.pow(cx1 - cx2, 2);
        double b = Math.pow(cy1 - cy2, 2);
        return (Math.sqrt(a + b) < 50);
    }

}
