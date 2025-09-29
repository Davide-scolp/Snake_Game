package snake_package.Core;

import javax.swing.JFrame;
import snake_package.GameView.GamePanel;
import snake_package.Util.SoundManager;
import snake_package.MenuUI.*;
import snake_package.GameModel.Board;
import snake_package.GameController.GameController;
	
public class GameManager {
	
	private JFrame gameFrame;
    private JFrame menuFrame;
    private GamePanel gamePanel;
    private SoundManager soundManager;
    private GameMenu gameMenu;
    private GameOverPanel gameOverPanel;
    private GameController gameController;
    private Board board;
    private boolean isGameActive;

    public GameManager() {
    	this.soundManager = SoundManager.getInstance();
        startMenu();
    }
    
    private void startMenu() {
    	this.menuFrame = new JFrame("Game Menu");
    	this.gameMenu = new GameMenu(this);
    	menuFrame.add(gameMenu);
    	menuFrame.setSize(600, 600);
    	menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	menuFrame.setLocationRelativeTo(null);
    	menuFrame.setVisible(true);
        soundManager.playMusic();
    }

    public void startGame() {
        if (this.menuFrame != null) {
            this.menuFrame.setVisible(false);
        }

        this.board = new Board(this);
        this.gameController = new GameController(board, this);
        this.gamePanel = new GamePanel(gameController);
        
        gamePanel.addKeyListener(gameController); 
        
        this.gameFrame = new JFrame("Snake Game");
        gameFrame.add(gamePanel);
        gameFrame.pack();
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setLocationRelativeTo(null);
        gameFrame.setVisible(true);
        
        this.isGameActive = true;
        soundManager.playMusic();
    }

    public void endGame(boolean checkWin) {
    	gameController.stopGame();
    	this.isGameActive = false;
        soundManager.stopMusic();
        
        if (checkWin) {
            soundManager.playYouWinSound();
        } else {
            soundManager.playGameOverSound();
        }
        
        this.gameOverPanel = new GameOverPanel(this, checkWin);
        
        gameFrame.getContentPane().removeAll(	);
        gameFrame.add(gameOverPanel);
        gameFrame.revalidate();
        gameFrame.repaint();
    }

    public void restartGame() {
    	if (this.gameFrame != null) {
            gameFrame.dispose();
        }
        startGame();
    }
    
    public void returnToMenu() {
        if (this.gameFrame != null) {
            gameFrame.dispose();
        }
        menuFrame.setVisible(true);
        soundManager.playMusic();
    }
    
    public boolean isGameActive() {
    	return isGameActive;
    }
    
    public GamePanel getGamePanel() {
    	return this.gamePanel;
    }
    
    public void exitGame() {
        System.exit(0);
    }
   
}