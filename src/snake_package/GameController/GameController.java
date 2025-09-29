package snake_package.GameController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import snake_package.GameModel.Board;
import snake_package.GameModel.Direction;

import javax.swing.Timer;

import snake_package.Core.GameManager;

public class GameController implements KeyListener, ActionListener{
	
	private final int DELAY = 150; // millisecondi
	private GameManager gameManager;
	private Board board;
	private Timer timer;
	
	public GameController(Board board, GameManager gameManager) {
		this.board = board;
		this.gameManager = gameManager;
		this.timer = new Timer(DELAY, this);
        timer.start();
	}
	
	 public void stopGame() {
	        if (this.timer != null) {
	            timer.stop();  // Ferma il timer
	        }
	    }
	 
	 public Board getBoard() {
		 return board;
	 }
	
	@Override
    public void actionPerformed(ActionEvent e) {
    	if (gameManager.isGameActive()) {
            board.update();
            gameManager.getGamePanel().repaint();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        switch (key) {
            case KeyEvent.VK_UP -> board.getSnake().setDirection(Direction.UP);
            case KeyEvent.VK_DOWN -> board.getSnake().setDirection(Direction.DOWN);
            case KeyEvent.VK_LEFT -> board.getSnake().setDirection(Direction.LEFT);
            case KeyEvent.VK_RIGHT -> board.getSnake().setDirection(Direction.RIGHT);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    	
    }

    @Override
    public void keyTyped(KeyEvent e) {
    	
    }
}
