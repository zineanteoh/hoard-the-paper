package hoardthepaper;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {

    private boolean p[];

    private Game game;

    public KeyInput(Game game) {
        this.game = game;
        this.p = new boolean[4];
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        for (int i = 0; i < Handler.objects.size(); i++) {
            GameObject obj = Handler.objects.get(i);
            if (obj.getID() == ID.Player) {
                if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W) {
                    p[0] = true;
                    obj.setVelY(-Player.speed);
                } else if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) {
                    p[1] = true;
                    obj.setVelY(Player.speed);
                } else if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
                    p[2] = true;
                    obj.setVelX(-Player.speed);
                } else if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
                    p[3] = true;
                    obj.setVelX(Player.speed);
                } else if (key == KeyEvent.VK_J) {
                    Player.findingItem = true;
                } else if (key == KeyEvent.VK_K) {
                    Player.payingCashier = true;
                } else if (key == KeyEvent.VK_L) {
                    Player.droppingItem = true;
                }
            }
        }

        if (key == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        for (int i = 0; i < Handler.objects.size(); i++) {
            GameObject obj = Handler.objects.get(i);
            if (obj.getID() == ID.Player) {
                if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W) {
                    p[0] = false;
                    if (p[1]) {
                        obj.setVelY(Player.speed);
                    } else {
                        obj.setVelY(0);
                    }
                } else if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) {
                    p[1] = false;
                    if (p[0]) {
                        obj.setVelY(-Player.speed);
                    } else {
                        obj.setVelY(0);
                    }
                } else if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
                    p[2] = false;
                    if (p[3]) {
                        obj.setVelX(Player.speed);
                    } else {
                        obj.setVelX(0);
                    }
                } else if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
                    p[3] = false;
                    if (p[2]) {
                        obj.setVelX(-Player.speed);
                    } else {
                        obj.setVelX(0);
                    }
                } else if (key == KeyEvent.VK_J) {
                    Player.findingItem = false;
                } else if (key == KeyEvent.VK_K) {
                    Player.payingCashier = false;
                }else if (key == KeyEvent.VK_L) {
                    Player.droppingItem = false;
                }
            }
        }
    }

}
