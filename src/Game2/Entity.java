package Game2;

import java.awt.*;

public abstract class Entity {
    protected int x, y, width, height, speed;

    public Entity(int x, int y, int width, int height, int speed) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
    }

    public void move() {
        y += speed;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public abstract void draw(Graphics g);

    // Add isOutOfBounds method here
    public boolean isOutOfBounds(int panelHeight) {
        return y > panelHeight;
    }
}
