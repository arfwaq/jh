package Game2;

import java.awt.*;

public class Obstacle extends Entity {

    public Obstacle(int x, int y, int width, int height, int speed) {
        super(x, y, width, height, speed);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(new Color(139, 69, 19)); // Brown color for the poop

        int adjustedWidth = (int)(width * 0.8);
        int adjustedHeight = (int)(height * 0.8);

        g.fillOval(x + width / 10, y, adjustedWidth, adjustedHeight);                   // Bottom layer
        g.fillOval(x + width / 5, y - adjustedHeight / 2, adjustedWidth - 10, adjustedHeight - 10); // Middle layer
        g.fillOval(x + width / 3, y - adjustedHeight, adjustedWidth - 20, adjustedHeight - 20);    // Top layer
    }
}
