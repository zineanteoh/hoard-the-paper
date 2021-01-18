package hoardthepaper;

// Abstract class for all game objects
import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class GameObject {

    // Coordinate of game objects
    protected float x, y;

    // Dimension of object
    protected int width, height;

    // ID to distinguish between players and bots
    protected ID id;

    // Movement of object
    protected float velX, velY;
    

    public GameObject(float x, float y, int width, int height, ID id) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.id = id;
        Handler.addObject(this);
    }

    // Abstract methods for subclasses of GameObject
    public abstract void tick();

    public abstract void render(Graphics g);

    // Use Rectangles as bounds and collision check
    public abstract Rectangle getBounds();

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setID(ID id) {
        this.id = id;
    }

    public void setVelX(float velX) {
        this.velX = velX;
    }

    public void setVelY(float velY) {
        this.velY = velY;
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public ID getID() {
        return this.id;
    }

    public float getVelX() {
        return this.velX;
    }

    public float getVelY() {
        return this.velY;
    }
}
