package snake_package.GameView;

import javax.swing.*;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import snake_package.Util.TexturesManager;
import snake_package.GameController.GameController;
import snake_package.GameModel.*;

public class GamePanel extends JPanel{

	private final int TILE_SIZE = 20;
    private int lastLevelNumber;
    private GameController gameController;
    private Image backgroundImage;
    private TexturesManager texturesManager;

    public GamePanel(GameController gameController) {
    	this.gameController = gameController;
    	this.texturesManager = TexturesManager.getInstance();
    	this.lastLevelNumber = -1;
        setPreferredSize(new Dimension(TILE_SIZE * gameController.getBoard().getWidth(), TILE_SIZE * gameController.getBoard().getHeight()));
        setFocusable(true);
    }
    private void setBackgroundForCurrentLevel(Graphics g) {
        int currentLevelNumber = gameController.getBoard().getCurrentLevel().getLevelNumber();
        if (currentLevelNumber != lastLevelNumber) {
            backgroundImage = texturesManager.getLevelBackgrounds().get(currentLevelNumber);
            lastLevelNumber = currentLevelNumber; // Aggiorna l'ultimo livello
        }
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }

    private void drawLevelInfo(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Level: " + gameController.getBoard().getCurrentLevel().getLevelNumber(), 10, 40);
        g.drawString("Apples to collect: " + (gameController.getBoard().getCurrentLevel().getApplesToCollect() - gameController.getBoard().getScore()), 10, 60);
        g.drawString("Score: " + gameController.getBoard().getScore(), 10, 20);
    }
    
    private void drawApple(Graphics g) {
        Image appleImage = texturesManager.getTextures().get("apple");
        Point applePosition = gameController.getBoard().getApple().getPosition();
        g.drawImage(appleImage, applePosition.getX() * TILE_SIZE, applePosition.getY() * TILE_SIZE, TILE_SIZE, TILE_SIZE, this);
    }
    
    private void drawFireballs(Graphics g) {
        if (gameController.getBoard().getCurrentLevel().getLevelNumber() == 3) {
            gameController.getBoard().getFireballs().ifPresent(fireballs -> {
                Image fireballImage = texturesManager.getTextures().get("fireball");
                for (Fireball fireball : fireballs) {
                    Point fireballPosition = fireball.getPosition();
                    g.drawImage(fireballImage, fireballPosition.getX() * TILE_SIZE, fireballPosition.getY() * TILE_SIZE, TILE_SIZE, TILE_SIZE, this);
                }
            });
        }
    }
    private void drawSnake(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        Point head = gameController.getBoard().getSnake().getBody().get(0);
        Image headImage = texturesManager.getTextures().get("snake_head");

        // Calcola l'angolo di rotazione basato sulla direzione
        double rotationAngle = 0;
        Direction direction = gameController.getBoard().getSnake().getDirection();  // Assumi che la direzione sia accessibile

        switch (direction) {
            case UP:
                rotationAngle = Math.toRadians(180);
                break;
            case DOWN:
                rotationAngle = 0;
                break;
            case LEFT:
                rotationAngle = Math.toRadians(90);
                break;
            case RIGHT:
                rotationAngle = Math.toRadians(270);
                break;
        }

        // Esegue la rotazione della testa
        AffineTransform originalTransform = g2d.getTransform();
        AffineTransform transform = new AffineTransform();

        // Posiziona il punto di rotazione al centro della testa del serpente
        transform.translate(head.getX() * TILE_SIZE + TILE_SIZE / 2, head.getY() * TILE_SIZE + TILE_SIZE / 2);
        transform.rotate(rotationAngle);
        transform.translate(-TILE_SIZE / 2, -TILE_SIZE / 2);

        g2d.setTransform(transform);
        g2d.drawImage(headImage, 0, 0, TILE_SIZE, TILE_SIZE, this);

        // Ripristina la trasformazione originale
        g2d.setTransform(originalTransform);

        // Disegna il corpo del serpente
        g.setColor(Color.GREEN);
        for (int i = 1; i < gameController.getBoard().getSnake().getBody().size(); i++) {
            Point point = gameController.getBoard().getSnake().getBody().get(i);
            g.fillRect(point.getX() * TILE_SIZE, point.getY() * TILE_SIZE, TILE_SIZE, TILE_SIZE);
        }
    }

    private void drawObstacles(Graphics g) {
    	Image obstacleImage = texturesManager.getTextures().get("obstacle");
        for (Obstacle obstacle : gameController.getBoard().getObstacles()) {
        	Point obstaclePosition = obstacle.getPosition();
            g.drawImage(obstacleImage, obstaclePosition.getX() * TILE_SIZE, obstaclePosition.getY() * TILE_SIZE, TILE_SIZE, TILE_SIZE, this);
        }
    }

    private void drawTraps(Graphics g) {
        Image trapImage = texturesManager.getTextures().get("trap");
        Image trapActivatedImage = texturesManager.getTextures().get("trapActivated");
        for (Trap trap : gameController.getBoard().getTraps()) {
            Point trapPosition = trap.getPosition();
            g.drawImage(trapImage, trapPosition.getX() * TILE_SIZE, trapPosition.getY() * TILE_SIZE, TILE_SIZE, TILE_SIZE, this);

            if (trap.isActivated()) {
                g.drawImage(trapActivatedImage, trapPosition.getX() * TILE_SIZE, trapPosition.getY() * TILE_SIZE, TILE_SIZE, TILE_SIZE, this);
            }
        }
    }
    
    private void drawGame(Graphics g) {
        drawSnake(g);
        drawApple(g);
        drawObstacles(g);
        drawTraps(g);
        drawLevelInfo(g);
        drawFireballs(g);		
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackgroundForCurrentLevel(g);
        drawGame(g);
    }
}