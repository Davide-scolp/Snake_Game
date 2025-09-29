package snake_package.Util;

import snake_package.GameModel.Board;
import snake_package.GameModel.Point;

public class CollisionManager{
	private Board board;
	
	public CollisionManager(Board board) {
		this.board = board;
	}
	
	public boolean checkCollisionWalls() {
        Point head = board.getSnake().getBody().get(0);
        return head.getX() < 0 || head.getX() >= board.getWidth() || head.getY() < 0 || head.getY() >= board.getHeight();
    }
	
	public boolean checkCollisionSelf() {
        Point head = board.getSnake().getBody().get(0);
        return board.getSnake().getBody().stream().skip(1).anyMatch(p -> p.equals(head));
    }
	
	public boolean checkCollisionApple() {
		Point head = board.getSnake().getBody().get(0);
        return head.equals(board.getApple().getPosition());
    }
	
	public boolean checkCollisionTrap() {
		return board.getTraps().stream().anyMatch(trap -> trap.getPosition().equals(board.getSnake().getBody().get(0)));
        }
	
	public boolean checkCollisionFireball() {
	    return board.getFireballs()
	    		.map(fireballs -> fireballs.stream().anyMatch(fireball -> 
	                board.getSnake().getBody().contains(fireball.getPosition())))
	    		.orElse(false);
	}
	
	public boolean checkCollisionObstacle() {
		return board.getObstacles().stream().anyMatch(obstacle -> obstacle.getPosition().equals(board.getSnake().getBody().get(0)));
	}
}
