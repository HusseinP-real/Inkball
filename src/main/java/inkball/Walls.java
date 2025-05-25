package inkball;

import processing.core.PVector;

import java.util.List;

/**
 * Represents a wall in the Inkball game.
 * Walls are obstacles that balls can collide with, and each wall is defined by its position, color, and edges.
 */
public class Walls {
    private int x;
    private int y;
    private int color;
    private List<PVector> wallEdges;

    /**
     * Constructs a new Wall with specified position, color, and edges.
     *
     * @param x The x-coordinate of the wall
     * @param y The y-coordinate of the wall
     * @param wallColor The color of the wall represented as an integer
     * @param wallEdges A list of PVector objects representing the edges of the wall
     */
    public Walls(int x, int y, int wallColor, List<PVector> wallEdges) {
        this.x = x;
        this.y = y;
        this.color = wallColor;
        this.wallEdges = wallEdges;
    }

    /**
     * Gets the x-coordinate of the wall.
     *
     * @return The x-coordinate of the wall
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the y-coordinate of the wall.
     *
     * @return The y-coordinate of the wall
     */
    public int getY() {
        return y;
    }

    /**
     * Gets the color of the wall.
     *
     * @return The color of the wall as an integer
     */
    public int getColor() {
        return color;
    }

    /**
     * Gets the list of PVector objects representing the edges of the wall.
     *
     * @return A list of PVector objects representing the wall edges
     */
    public List<PVector> getWallEdges() {
        return wallEdges;
    }

    /**
     * Draws the wall at its current position using the provided app instance.
     * The image used to represent the wall is determined by the wall's color.
     *
     * @param app The app instance used for rendering the wall
     */
    public void drawWalls(App app) {
        String wallPath = "inkball/wall" + getColor() + ".png";
        app.image(app.loadImage(wallPath), x, y);
    }


}
