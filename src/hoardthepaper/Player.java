package hoardthepaper;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Player extends GameObject {

    public static float speed;
    public static boolean findingItem;
    public static boolean payingCashier;
    public static boolean droppingItem;
    public static ArrayList<Paper> inventory; // stores paper
    public static final int inventoryLimit = 10; // purchaseable attribute

    public Player(float x, float y, int width, int height, ID id) {
        super(x, y, width, height, id);
        this.speed = 3;
        inventory = new ArrayList<Paper>();
        findingItem = false;
        payingCashier = false;
        droppingItem = false;
    }

    public static void addItem(Paper p) {
        if (Player.inventory.size() < inventoryLimit) {
            Player.inventory.add(p);
        }
    }

    public static void dropItem(Paper p) {
        Player.inventory.remove(p);
    }

    @Override
    public void tick() {

        // Move player
        x += velX;
        y += velY;
        x = Game.clamp(x, 0, Game.WIDTH - width - 5);
        y = Game.clamp(y, 0, Game.HEIGHT - height * 2 + 2);

        // Check for collisions with objects
        collision();

    }

    public void blockedMovement(GameObject obj) {
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

    private void collision() {
        for (int i = 0; i < Handler.objects.size(); i++) {
            GameObject obj = Handler.objects.get(i);
            if (obj.getID() == ID.Shelf || obj.getID() == ID.Cashier || obj.getID() == ID.Wall) {
                if (getBounds().intersects(obj.getBounds())) {
                    blockedMovement(obj);
                }
            } else if (obj.id == ID.Bot) {
                if (getBounds().intersects(obj.getBounds())) {
                    HUD.HEALTH -= 1;
                }
            } else if (obj.id == ID.Entrance) {
                if (getBounds().intersects(obj.getBounds()) && inventory.size() > 0) {
                    boolean illegal = true;
                    for (int j = 0; j < inventory.size(); j++) {
                        if (!inventory.get(j).isPaid()) {
                            illegal = false;
                            break;
                        }
                    }
                    if (!illegal) {
                        blockedMovement(obj);
                    }
                }
            }
        }
    }

    public static Paper getFirstPaidItem() {
        for (int i = 0; i < Player.inventory.size(); i++) {
            if (Player.inventory.get(i).isPaid()) {
                return Player.inventory.get(i);
            }
        }
        return null;
    }

    public static Paper getFirstUnpaidItem() {
        for (int i = 0; i < Player.inventory.size(); i++) {
            if (!Player.inventory.get(i).isPaid()) {
                return Player.inventory.get(i);
            }
        }
        return null;
    }
    
    public static void clearInventory() {
        inventory.clear();
    }

    @Override
    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLUE);
        g2d.fillRect((int) x, (int) y, width, height);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, width, height);
    }

}
