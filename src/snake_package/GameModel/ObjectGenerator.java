package snake_package.GameModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class ObjectGenerator {
	private final int centralAreaWidth = 10;     // Coordinate utilizzate per evitare la generazione
	private final int centralAreaHeight = 10;	 // di ostacoli/trappole al centro della griglia di gioco
	
	private Random random;
	private Board board;
	private List<Fireball> fireballs;
	private List<Obstacle> obstacles;
    private List<Trap> traps;
	
	public ObjectGenerator(Board board) {
		this.random = new Random();
		this.board = board;
		this.traps = new ArrayList<>();
		this.obstacles = new ArrayList<>();
		this.fireballs = new ArrayList<>();
	}
	
	public Apple generateApple() {
        Point applePosition;
        do {
         int x = random.nextInt(board.getWidth());
   	     int y = random.nextInt(board.getHeight());
   	     applePosition = new Point(x, y);
        }while (board.getSnake().getBody().contains(applePosition) || board.isPositionOnObstacle(applePosition));

        return new Apple(applePosition);
    }
	
	public Snake generateSnake() {
		return new Snake(new Point(board.getWidth() / 2, board.getHeight() / 2));
	}
	
	public List<Trap> generateTraps(int count) {
        for (int i = 0; i < count; i++) {
        	Point position;
            do {
                int x = random.nextInt(board.getWidth());
                int y = random.nextInt(board.getHeight());
                position = new Point(x, y);
            } while (board.getSnake().getBody().contains(position) ||
                     board.isPositionOnObstacle(position) ||
                     isInsideCentralArea(position)); // Evita l'area centrale
            traps.add(new Trap(position));
        }
        return traps;
    }
	
	public List<Obstacle> generateObstacles(int count) {
        for (int i = 0; i < count; i++) {
            Point position;
            do {
                int x = random.nextInt(board.getWidth());
                int y = random.nextInt(board.getHeight());
                position = new Point(x, y);
            } while (board.getSnake().getBody().contains(position) ||
                     isInsideCentralArea(position)); // Evita l'area centrale
            obstacles.add(new Obstacle(position));
        }
        return obstacles;
    }
	
	public Optional<List<Fireball>> generateFireball() {
	    Direction direction = Direction.values()[random.nextInt(Direction.values().length)];
	    Point position = switch (direction) {
	        case UP -> new Point(random.nextInt(board.getWidth()), board.getHeight() - 1);
	        case DOWN -> new Point(random.nextInt(board.getWidth()), 0);
	        case LEFT -> new Point(board.getWidth() - 1, random.nextInt(board.getHeight()));
	        case RIGHT -> new Point(0, random.nextInt(board.getHeight()));
	    };
	    fireballs.add(new Fireball(position, direction, 1)); // Velocità impostata a 1
	    return Optional.of(fireballs);
	}
	
	private boolean isInsideCentralArea(Point position) {
        int centralXStart = (board.getWidth() - centralAreaWidth) / 2;
        int centralYStart = (board.getHeight() - centralAreaHeight) / 2;

        return position.getX() >= centralXStart && position.getX() < centralXStart + centralAreaWidth &&
               position.getY() >= centralYStart && position.getY() < centralYStart + centralAreaHeight;
    }
	
}
