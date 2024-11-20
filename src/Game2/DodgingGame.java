package Game2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class DodgingGame extends JPanel implements ActionListener, KeyListener, MouseListener {
    private Player player;
    private ArrayList<Obstacle> obstacles;
    private ArrayList<Item> greenItems;
    private ArrayList<Item> redItems;
    private Timer timer, lifeTimer, timeCounter;
    private int elapsedTime = 0, score = 0, greenItemCounter = 0, lastScoreMilestone = 0;
    private boolean gameOver = false;
    private int obstacleSpeed, obstacleFrequency;
    private JFrame parentFrame;
    private Random random;

    public DodgingGame(String difficulty, JFrame frame) {
        this.parentFrame = frame;
        setPreferredSize(new Dimension(600, 600));
        setBackground(Color.WHITE);
        initializeGame(difficulty);

        addKeyListener(this);
        addMouseListener(this);
        setFocusable(true);
    }

    private void initializeGame(String difficulty) {
        gameOver = false;
        score = 0;
        elapsedTime = 0;
        greenItemCounter = 0;
        lastScoreMilestone = 0;

        random = new Random();
        obstacles = new ArrayList<>();
        greenItems = new ArrayList<>();
        redItems = new ArrayList<>();

        int lifeIncreaseInterval;
        switch (difficulty.toLowerCase()) {
            case "어려움":
                obstacleSpeed = 4;
                obstacleFrequency = 20;
                player = new Player(250, 550, 50, 15, 20, 5);
                lifeIncreaseInterval = 60000;
                break;
            case "보통":
                obstacleSpeed = 6;
                obstacleFrequency = 15;
                player = new Player(250, 550, 40, 20, 20, 5);
                lifeIncreaseInterval = 45000;
                break;
            case "쉬움":
                obstacleSpeed = 8;
                obstacleFrequency = 10;
                player = new Player(250, 550, 30, 25, 20, 5);
                lifeIncreaseInterval = 30000;
                break;
            default:
                obstacleSpeed = 5;
                obstacleFrequency = 15;
                player = new Player(250, 550, 40, 20, 20, 5);
                lifeIncreaseInterval = 45000;
        }

        timer = new Timer(30, this);
        timer.start();

        lifeTimer = new Timer(lifeIncreaseInterval, e -> {
            if (!gameOver) {
                player.gainLife();
                repaint();
            }
        });
        lifeTimer.start();

        timeCounter = new Timer(1000, e -> {
            if (!gameOver) {
                elapsedTime++;
                repaint();
            }
        });
        timeCounter.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        player.draw(g);
        obstacles.forEach(obstacle -> obstacle.draw(g));
        greenItems.forEach(item -> item.draw(g));
        redItems.forEach(item -> item.draw(g));

        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.drawString("Lives: " + player.getLives() + "/" + player.getMaxLives(), 10, 20);
        g.drawString("Score: " + score, 10, 50);
        g.drawString(String.format("Time:%02d:%02d", elapsedTime / 60, elapsedTime % 60), getWidth() - 100, 20);

        if (gameOver) {
            g.setFont(new Font("Arial", Font.BOLD, 36));
            g.drawString("Game Over - Restart?", 120, 300);
        }
    }

    private void spawnEntities() {
        if (random.nextInt(100) < obstacleFrequency) {
            obstacles.add(new Obstacle(random.nextInt(getWidth() - 50), 0, 40, 40, obstacleSpeed));
            if (random.nextInt(100) < 15) {
                greenItems.add(new Item(random.nextInt(getWidth() - 30), 0, 20, 20, Color.GREEN, 10));
            }
            if (random.nextInt(100) < 10) {
                redItems.add(new Item(random.nextInt(getWidth() - 30), 0, 20, 20, Color.RED, 20));
            }
        }
    }

    private void checkCollisions() {
        ArrayList<Obstacle> obstaclesToRemove = new ArrayList<>();
        ArrayList<Item> greenItemsToRemove = new ArrayList<>();
        ArrayList<Item> redItemsToRemove = new ArrayList<>();

        for (Obstacle obstacle : obstacles) {
            obstacle.move();
            if (obstacle.getBounds().intersects(player.getBounds())) {
                player.loseLife();
                obstaclesToRemove.add(obstacle);
                if (player.getLives() <= 0) {
                    gameOver = true;
                    stopAllTimers();
                }
            } else if (obstacle.isOutOfBounds(getHeight())) {
                obstaclesToRemove.add(obstacle);
            }
        }

        for (Item item : greenItems) {
            item.move();
            if (item.getBounds().intersects(player.getBounds())) {
                score += item.getScore();
                greenItemsToRemove.add(item);
                greenItemCounter++;
                if (greenItemCounter >= 5) {
                    player.gainLife();
                    greenItemCounter = 0;
                }
                updateMaxLivesAndLives();
            } else if (item.isOutOfBounds(getHeight())) {
                greenItemsToRemove.add(item);
            }
        }

        for (Item item : redItems) {
            item.move();
            if (item.getBounds().intersects(player.getBounds())) {
                score += item.getScore();
                player.gainLife();
                redItemsToRemove.add(item);
            } else if (item.isOutOfBounds(getHeight())) {
                redItemsToRemove.add(item);
            }
        }

        obstacles.removeAll(obstaclesToRemove);
        greenItems.removeAll(greenItemsToRemove);
        redItems.removeAll(redItemsToRemove);
    }

    private void updateMaxLivesAndLives() {
        if (score >= lastScoreMilestone + 500) {
            player.gainLife();
            player.increaseMaxLives();
            lastScoreMilestone += 500;
        }
    }

    private void stopAllTimers() {
        timer.stop();
        lifeTimer.stop();
        timeCounter.stop();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver) {
            spawnEntities();
            checkCollisions();
            repaint();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!gameOver) {
            if (e.getKeyCode() == KeyEvent.VK_LEFT) player.moveLeft();
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) player.moveRight(getWidth());
            repaint();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void mouseClicked(MouseEvent e) {
        if (gameOver && SwingUtilities.isLeftMouseButton(e)) {
            ((MainEx) parentFrame).resetGame();
        }
    }
    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
}
