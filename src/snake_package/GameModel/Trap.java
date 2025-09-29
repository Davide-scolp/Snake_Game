package snake_package.GameModel;

public class Trap extends GameObject {
    private boolean activated;
    private boolean coolingDown;

    public Trap(Point position) {
        super(position);
        this.activated = false;
        this.coolingDown = false;
    }

    public boolean isActivated() {
        return activated;
    }

    public void activate() {
        this.activated = true;
    }

    public void deactivate() {
        this.activated = false;
    }

    public boolean isCoolingDown() {
        return coolingDown;
    }

    public void setCoolingDown(boolean coolingDown) {
        this.coolingDown = coolingDown;
    }

    public void reset() {
        this.activated = false;
        this.coolingDown = false;
    }
}
