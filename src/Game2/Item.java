package Game2;

import java.awt.*;

public class Item {
    private int x, y, width, height, speed, score;
    private Color color;

    public Item(int x, int y, int width, int height, Color color, int score) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.speed = 5;
        this.score = score;
    }

    public void move() {
        y += speed;
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval(x, y, width, height);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public boolean isOutOfBounds(int panelHeight) {
        return y > panelHeight;
    }

    public int getScore() {
        return score;
    }
}
