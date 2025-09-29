package snake_package.GameModel;

import java.util.List;
import java.util.Optional;

import javax.swing.Timer;
import snake_package.Core.GameManager;
import snake_package.Util.SoundManager;
import snake_package.Util.CollisionManager;
import snake_package.Util.LevelManager;

public class Board {
	private final int fireballCooldown = 3000;
	private final int trapCooldown = 10000;
	private final int BOARD_WIDTH = 30;
	private final int BOARD_HEIGHT = 20;
	private int score;
    private boolean checkWin;
    private Timer fireballTimer;
    private Timer trapTimer;
    private Snake snake;
    private Apple apple;
    private Optional<List<Fireball>> fireballs = Optional.empty();
	private List<Obstacle> obstacles;
    private List<Trap> traps;
    private ObjectGenerator objectGenerator;
    private SoundManager soundManager;
    private LevelManager currentLevel;	
    private CollisionManager collisionManager;
    private GameManager gameManager;

    public Board(GameManager gameManager) {
    	this.gameManager = gameManager;
        this.soundManager = SoundManager.getInstance();
        this.objectGenerator = new ObjectGenerator(this);
        this.currentLevel = new LevelManager(1, 5, 10, 3); // Imposta il primo livello (Livello 2, 5 mele, 10 ostacoli, 3 trappole
        this.collisionManager = new CollisionManager(this);
        initializeGame();
    }

    private void initializeGame() {
    	setScore(0);
    	this.snake = objectGenerator.generateSnake();
    	this.obstacles = objectGenerator.generateObstacles(currentLevel.getObstaclesCount());
    	this.traps = objectGenerator.generateTraps(currentLevel.getTrapsCount());
        this.apple = objectGenerator.generateApple();
        manageInitializeEvents();
    }

    private void checkCollisions() {
        manageCollisionSelfObstacleWall();
        manageCollisionApple();
        manageCollisionTrap();
        manageCollisionFireball();
        
    }
    
    private void advanceToNextLevel() {
    	LevelManager nextLevel = getNextLevel();
        if (nextLevel != null) {
            this.currentLevel = nextLevel;
            this.score = 0;
            initializeGame(); 
        } else {
        	manageEndGameEvents();
        	gameManager.endGame(checkWin());
        }
    }

    private LevelManager getNextLevel() {
        int nextLevelNumber = currentLevel.getLevelNumber() + 1;
        switch (nextLevelNumber) {
            case 2:
            	return new LevelManager(nextLevelNumber, 10, 15, 5);  // (Livello 2, 10 mele, 15 ostacoli, 5 trappole)
            case 3:
                return new LevelManager(nextLevelNumber, 15, 20, 10); // (Livello 3, 15 mele, 20 ostacoli, 10 trappole)
            default:
                return null; 
        }
    }
    
    private void manageCollisionApple() {
    	if (collisionManager.checkCollisionApple()) {
        	soundManager.playAppleEatenSound();
        	getSnake().grow();
            this.score++;
	        if (getScore() >= currentLevel.getApplesToCollect()) {
	            advanceToNextLevel();
	        } else {
	            this.apple = objectGenerator.generateApple(); 
	        }
        }
    }
    
    private void manageCollisionSelfObstacleWall() {
    	if (collisionManager.checkCollisionWalls() || collisionManager.checkCollisionSelf() || collisionManager.checkCollisionObstacle()) {
        	soundManager.playObstacleHitSound();
        	manageEndGameEvents();
        	gameManager.endGame(checkWin());
        }
    }
    
    private void manageCollisionFireball() {
        fireballs.ifPresent(list -> {
            if (collisionManager.checkCollisionFireball()) {
                for (Fireball fireball : list) {
                    if (getSnake().getBody().contains(fireball.getPosition())) {
                        soundManager.playFireballHitSound();
                        if (this.score > 0) {
                            this.score--;
                            getSnake().getBody().removeLast();
                        } else {
                            manageEndGameEvents();
                            gameManager.endGame(checkWin());
                        }
                    }
                }
            }
        });
    }
    
    private void manageCollisionTrap() {
    	if (collisionManager.checkCollisionTrap()) {
        	for (Trap trap : getTraps()) {
                if (trap.getPosition().equals(getSnake().getBody().get(0))) {
                    if (!trap.isActivated() && !trap.isCoolingDown()) {
                        trap.activate();
                        soundManager.playTrapSound();
                        if (this.score > 0) { 
                            this.score--;
                            getSnake().getBody().removeLast(); 
                        } else {
                        	manageEndGameEvents();
                        	gameManager.endGame(checkWin());
                        }
                        startTrapCooldown(trap);
                    }
                }
        	}
        }
    }
    
    private void startTrapCooldown(Trap trap) {
        trap.setCoolingDown(true);
        this.trapTimer = new Timer(this.trapCooldown, e -> trap.reset());
        this.trapTimer.setRepeats(false);
        this.trapTimer.start();
    }
    
    private void startFireballGeneration() {
        this.fireballTimer = new Timer(this.fireballCooldown, e -> this.fireballs = objectGenerator.generateFireball());
        this.fireballTimer.start();
    }
    
    private void stopFireballGeneration() {
    	this.fireballTimer.stop();
    }
    
    private void removeOffScreenFireballs() {
        fireballs.ifPresent(list -> list.removeIf(fireball -> fireball.isOutOfBounds(getWidth(), getHeight())));
    }

    
    private void manageInitializeEvents() {
    	if (this.currentLevel.getLevelNumber() == 3) {
            startFireballGeneration();
        }
    }
    
    private void manageInGameEvents() {
        if (this.currentLevel.getLevelNumber() == 3) {
            fireballs.ifPresent(list -> {
                list.forEach(Fireball::move);
                removeOffScreenFireballs();
            });
        }
    }
    
    private void manageEndGameEvents() {
    	if (this.currentLevel.getLevelNumber() == 3) {
    		stopFireballGeneration();
    	}
    }
    
    public void update() {
    	getSnake().move();
        checkCollisions();
        manageInGameEvents();
    }
    
    public boolean checkWin() {
    	if (this.getCurrentLevel().getLevelNumber() == 3 && this.score == 1) {
    		checkWin = true;
    	} else {
    		checkWin = false;
    	}
    	return checkWin;
    }
    
    public boolean isPositionOnObstacle(Point position) {
    	return getObstacles().stream().anyMatch(obstacle -> obstacle.getPosition().equals(position));
    }
    
    public boolean isPositionOnTrap(Point position) {
    	return getTraps().stream().anyMatch(trap -> trap.getPosition().equals(position));
    }
    
    public int getWidth() {
        return BOARD_WIDTH;
    }
    
    public int getHeight() {
        return BOARD_HEIGHT;
    }
    
    public int getScore() {
    	return score;
    }
    
    public void setScore(int x) {
    	this.score = x;
    }

    public LevelManager getCurrentLevel() {
        return currentLevel; 
    }
    
    public List<Obstacle> getObstacles(){
    	return obstacles;
    }
    
    public List<Trap> getTraps(){
    	return traps;
    }
    
    public Optional<List<Fireball>> getFireballs(){
    	return fireballs;
    }
    
    public Snake getSnake() {
        return snake;
    }

    public Apple getApple() {
        return apple;
    }
    
}