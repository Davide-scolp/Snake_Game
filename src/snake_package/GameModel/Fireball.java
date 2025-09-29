package snake_package.GameModel;

public class Fireball extends GameObject {
    private Direction direction;
    private int speed;

    public Fireball(Point position, Direction direction, int speed) {
        super(position);
        this.direction = direction;
        this.speed = speed;
    }

    public void move() {
        Point position = getPosition();
        switch (direction) {
            case UP -> position.setY(position.getY() - speed);
            case DOWN -> position.setY(position.getY() + speed);
            case LEFT -> position.setX(position.getX() - speed);
            case RIGHT -> position.setX(position.getX() + speed);
        }
        setPosition(position);
    }

    public boolean isOutOfBounds(int width, int height) {
        Point position = getPosition();
        return position.getX() < 0 || position.getX() >= width || position.getY() < 0 || position.getY() >= height;
    }
}