package inkball;

/**
 * Represents the possible directions for an accelerator tile in the Inkball game.
 */
enum Direction {
    /**
     * Represents an upward direction.
     */
    UP,
    /**
     * Represents a downward direction.
     */
    DOWN,
    /**
     * Represents a leftward direction.
     */
    LEFT,
    /**
     * Represents a rightward direction.
     */
    RIGHT
}

/**
 * Represents a tile on the Inkball game board. Tiles can be either regular tiles or accelerators that affect the ball's speed.
 */
public class Tiles {
    private int x;
    private int y;
    private boolean isAccelerator = false;
    private Direction direction;

    /**
     * Constructs a regular tile at the specified position.
     *
     * @param x The x-coordinate of the tile
     * @param y The y-coordinate of the tile
     */
    public Tiles(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Constructs a tile with specified properties, including whether it is an accelerator and its direction.
     *
     * @param x The x-coordinate of the tile
     * @param y The y-coordinate of the tile
     * @param isAccelerator Indicates if the tile is an accelerator
     * @param direction The direction of acceleration if the tile is an accelerator
     */
    public Tiles(int x, int y, boolean isAccelerator, Direction direction) {
        this.x = x;
        this.y = y;
        this.isAccelerator = isAccelerator;
        this.direction = direction;
    }

    /**
     * Gets the x-coordinate of the tile.
     *
     * @return The x-coordinate of the tile
     */
    public int getX() { return x; }

    /**
     * Gets the y-coordinate of the tile.
     *
     * @return The y-coordinate of the tile
     */
    public int getY() {
        return y;
    }

    /**
     * Checks if the tile is an accelerator.
     *
     * @return True if the tile is an accelerator, false otherwise
     */
    public boolean isAccelerator() { return isAccelerator; }

    /**
     * Gets the direction of the accelerator, if applicable.
     *
     * @return The direction of the accelerator, or null if the tile is not an accelerator
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * Draws the tile on the board.
     * If the tile is an accelerator, it will use a directional image based on its type.
     * Otherwise, a regular tile image will be drawn.
     *
     * @param app The app instance used for rendering the tile
     */
    public void drawTile(App app) {
        String tilePath = "";
        if (isAccelerator) {
            if (direction == Direction.UP) {
                tilePath = "inkball/up_acceleration.png";
            } else if (direction == Direction.DOWN) {
                tilePath = "inkball/down_acceleration.png";
            } else if (direction == Direction.LEFT) {
                tilePath = "inkball/left_acceleration.png";
            } else if (direction == Direction.RIGHT) {
                tilePath = "inkball/right_acceleration.png";
            }
        } else {
            tilePath = "inkball/tile.png";
        }
        app.image(app.loadImage(tilePath), x , y, 32, 32);
    }
}
