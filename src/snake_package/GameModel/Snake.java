package snake_package.GameModel;

import java.util.LinkedList;

public class Snake extends GameObject {
    private LinkedList<Point> body;
    private Direction direction;
    private Direction nextDirection; // Aggiunto per il buffer degli input

    public Snake(Point startPosition) {
        super(startPosition);
        body = new LinkedList<>();
        body.add(startPosition);
        direction = Direction.RIGHT; // Direzione iniziale
        nextDirection = direction; // Inizializza la prossima direzione
    }

    public void move() {
        // Applica la prossima direzione dal buffer
        direction = nextDirection;

        Point head = body.get(0);
        Point newHead = null;

        switch (direction) {
            case UP -> newHead = new Point(head.getX(), head.getY() - 1);
            case DOWN -> newHead = new Point(head.getX(), head.getY() + 1);
            case LEFT -> newHead = new Point(head.getX() - 1, head.getY());
            case RIGHT -> newHead = new Point(head.getX() + 1, head.getY());
        }

        body.add(0, newHead); // Aggiungi la nuova testa all'inizio
        body.remove(body.size() - 1); // Rimuovi l'ultima parte (coda)
    }

    public void grow() {
        Point tail = body.get(body.size() - 1);
        body.add(new Point(tail.getX(), tail.getY()));
    }

    public void setDirection(Direction newDirection) {
        // Verifica che la nuova direzione sia valida
        if ((this.direction == Direction.UP && newDirection != Direction.DOWN) ||
            (this.direction == Direction.DOWN && newDirection != Direction.UP) ||
            (this.direction == Direction.LEFT && newDirection != Direction.RIGHT) ||
            (this.direction == Direction.RIGHT && newDirection != Direction.LEFT)) {
            // Se valida, aggiorna il buffer degli input con la nuova direzione
            this.nextDirection = newDirection;
        }
    }

    public LinkedList<Point> getBody() {
        return body;
    }

    public Direction getDirection() {
        return direction;
    }
}
