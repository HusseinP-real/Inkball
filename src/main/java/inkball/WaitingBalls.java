package inkball;

/**
 * Represents a waiting ball in the Inkball game.
 * Waiting balls are balls that are ready to be launched into the game but are not yet active.
 */
public class WaitingBalls extends Balls{

    /**
     * Constructs a new WaitingBall with specified position and color.
     *
     * @param x The x-coordinate of the waiting ball
     * @param y The y-coordinate of the waiting ball
     * @param ballColor The color of the waiting ball
     */
    public WaitingBalls(int x, int y, int ballColor) {
        super(x, y, ballColor);
    }

    /**
     * Draws the waiting ball on the game board.
     * Uses the same drawing logic as active balls by calling the superclass's draw method.
     *
     * @param app The app instance used for rendering the waiting ball
     */
    @Override
    public void drawBalls(App app) {
        super.drawBalls(app);
    }
}
