package hoardthepaper;

// Handler class handles all the rendering for every objects
import hoardthepaper.Game.STATE;
import java.awt.Graphics;
import java.util.LinkedList;

public class Handler {

    public static LinkedList<GameObject> objects = new LinkedList<GameObject>();

    // Loop through all objects and tick()
    public static void tick() {
        for (int i = 0; i < objects.size(); i++) {
            objects.get(i).tick();
        }
        if (HUD.MONEY < Paper.costToBuy && Player.inventory.size() == 0) {
            System.out.println("NEXT DAY");
            HUD.MONEY = Paper.sellingPrice * HUD.CAR_ITEM;
            HUD.CAR_ITEM = 0;
            Player.clearInventory();
            Spawner.nextLevel();
        }
    }

    // Loop through all objects and render(g)
    public static void render(Graphics g) {
        if (Game.gameState == STATE.Game) {
            if (Game.gameState == STATE.Game) {
                // Render road decorations
                Spawner.render(g);
            }
            for (int i = 0; i < objects.size(); i++) {
                objects.get(i).render(g);
            }
        }
    }

    public static void addObject(GameObject object) {
        objects.add(object);
    }

    // Remove objects permenantly
    public static void removeObject(GameObject object) {
        objects.remove(object);
    }

    public static synchronized void clearObjects() {
        for (int i = 0; i < objects.size(); i++) {
            GameObject obj = objects.get(i);
            if (obj.getID() == ID.Player) {
                objects.clear();
                if (Game.gameState != STATE.End) {
                    new Player((int) obj.x, (int) obj.y, obj.width, obj.height, ID.Player);
                }
                break;
            }
        }
    }

}
