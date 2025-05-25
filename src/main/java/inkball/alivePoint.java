package inkball;

/**
    * Represents an alive point on the board, which serves as a possible starting point for balls.
 */
public class alivePoint {
    public int x;
    public int y;

    /**
     * Gets the x-coordinate of the alive point.
     *
     * @return The x-coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the y-coordinate of the alive point.
     *
     * @return The y-coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Constructor to create an alivePoint with specified x and y coordinates.
     *
     * @param x The x-coordinate of the alive point
     * @param y The y-coordinate of the alive point
     */
    public alivePoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Draws the alive point on the board.
     * This method uses the provided app instance to draw an image of the entry point at the alive point's coordinates.
     *
     * @param app The App instance used for drawing the alive point
     */
    public void drawAlivePoint(App app) {
        String aPPath = "inkball/entrypoint.png";
        app.image(app.loadImage(aPPath), x, y);
    }


}
