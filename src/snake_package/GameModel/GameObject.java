package snake_package.GameModel;

public abstract class GameObject {
    private Point position;

    public GameObject(Point position) {
        this.position = position;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }	
}