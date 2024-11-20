package Game2;

import java.awt.*;

public class Player {
    private int x, y, width, height, speed, lives, maxLives;

    public Player(int x, int y, int width, int height, int speed, int lives) {
        this.x = x;
        this.y = y;
        this.width = width;	
        this.height = height;
        this.speed = speed;
        this.lives = lives;
        this.maxLives = 5;
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect(x, y, width, height);
    }

    public void moveLeft() {
        x -= speed;
        if (x < 0) x = 0;
    }

    public void moveRight(int panelWidth) {
        x += speed;
        if (x + width > panelWidth) x = panelWidth - width;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public void loseLife() {
        lives--;
    }

    public void gainLife() {
        if (lives < maxLives) lives++;
    }

    public int getLives() {
        return lives;
    }

    public int getMaxLives() {
        return maxLives;
    }

    public void increaseMaxLives() {
        if (maxLives < 10) maxLives++;
    }
}
