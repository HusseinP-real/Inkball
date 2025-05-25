package inkball;

import processing.core.PGraphics;
import processing.core.PVector;

/**
    * Represents a hole in the Inkball game. The holes act as targets for the balls to fall into.
 */
public class Holes {
    private int x;
    private int y;
    private int color;

    /**
     * Gets the color of the hole.
     *
     * @return The color of the hole as an integer
     */
    public int getColor() {
        return color;
    }

    /**
     * Gets the y-coordinate of the hole.
     *
     * @return The y-coordinate of the hole
     */
    public int getY() {
        return y;
    }

    /**
     * Gets the x-coordinate of the hole.
     *
     * @return The x-coordinate of the hole
     */
    public int getX() {
        return x;
    }

    /**
     * Constructs a new Hole with the specified position and color.
     *
     * @param x The x-coordinate of the hole
     * @param y The y-coordinate of the hole
     * @param holeColor The color of the hole represented as an integer
     */
    public Holes(int x, int y, int holeColor) {
        this.x = x;
        this.y = y;
        this.color = holeColor;
    }

    //get hole pixel position
    /**
     * Gets the center pixel position of the hole.
     *
     * @return A PVector representing the center coordinates of the hole
     */
    public PVector getHoleCentrePosition() {
        float centerX = x + App.CELLSIZE;
        float centerY = y + App.CELLSIZE;
        return new PVector(centerX, centerY);
    }

    /**
     * Draws the hole at its current position using the provided app instance.
     * The image used to represent the hole depends on its color.
     *
     * @param app The app instance used for rendering the hole
     */
    public void drawHoles(App app) {
        String holePath = "inkball/hole" + getColor() + ".png";
        app.image(app.loadImage(holePath), x, y);
    }
}
