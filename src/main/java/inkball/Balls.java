package inkball;

import processing.core.PVector;

import java.util.*;

/**
 * Represents a ball in the Inkball game. The ball moves around the board, interacts with walls, lines, holes, and accelerators.
 */
public class Balls {
    protected float x;
    protected float y;

    protected int color;
    protected float speed_x;
    protected float speed_y;
    protected int radius;
    protected boolean valid = true;

    /**
     * Constructs a new Ball with specified position and color.
     *
     * @param x The x-coordinate of the ball
     * @param y The y-coordinate of the ball
     * @param ballColor The color of the ball represented as an integer
     */
    public Balls(float x, float y, int ballColor) {
        this.x = x;
        this.y = y;
        this.color = ballColor;
        this.radius = 12;
        Random_V();
    }

    /**
     * Sets the color of the ball.
     *
     * @param color The new color of the ball
     */
    public void setColor(int color) {
        this.color = color;
    }

    /**
     * Sets the x-axis speed of the ball.
     *
     * @param speed_x The new speed along the x-axis
     */
    public void setSpeed_x(float speed_x) {
        this.speed_x = speed_x;
    }

    /**
     * Sets the y-axis speed of the ball.
     *
     * @param speed_y The new speed along the y-axis
     */
    public void setSpeed_y(float speed_y) {
        this.speed_y = speed_y;
    }

    /**
     * Gets the radius of the ball.
     *
     * @return The radius of the ball
     */
    public int getRadius() {
        return radius;
    }

    /**
     * Sets the radius of the ball.
     *
     * @param radius The new radius of the ball
     */
    public void setRadius(int radius) {
        this.radius = radius;
    }

    /**
     * Sets the x-coordinate of the ball.
     *
     * @param x The new x-coordinate
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Sets the y-coordinate of the ball.
     *
     * @param y The new y-coordinate
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Gets the speed along the x-axis.
     *
     * @return The speed along the x-axis
     */
    public float getSpeed_x() {
        return speed_x;
    }

    /**
     * Gets the speed along the y-axis.
     *
     * @return The speed along the y-axis
     */
    public float getSpeed_y() {
        return speed_y;
    }

    /**
     * Gets the x-coordinate of the ball.
     *
     * @return The x-coordinate
     */
    public float getX() {
        return x;
    }

    /**
     * Gets the color of the ball.
     *
     * @return The color of the ball
     */
    public int getColor() {
        return color;
    }

    /**
     * Gets the y-coordinate of the ball.
     *
     * @return The y-coordinate
     */
    public float getY() {
        return y;
    }

    /**
     * Assigns a random speed to the ball along both x and y axes.
     */
    public void Random_V() {
        Random rd = new Random();
        this.speed_x = getRandom_V(rd);
        this.speed_y = getRandom_V(rd);
    }

    /**
     * Generates a random velocity from a predefined set of values.
     *
     * @param rd Random object to generate values
     * @return The randomly selected velocity
     */
    private int getRandom_V(Random rd) {
        int[] possible_V = {-2, 2};
        int i = rd.nextInt(possible_V.length);
        return possible_V[i];
    }

    /**
     * Gets the center position of the ball.
     *
     * @return A PVector representing the center coordinates of the ball
     */
    public PVector getBallCenter() {
        float centerX = this.x + this.radius;
        float centerY = this.y + this.radius;
        return new PVector(centerX, centerY);
    }

    /**
     * Draws the ball and handles all the interactions, including collisions with lines, walls, holes, and accelerators.
     *
     * @param app The app instance used for rendering and game state management
     */
    public void drawBalls(App app) {
        // Ball interaction logic, including collisions with lines, walls, holes, and accelerators
        // (The full collision logic is included here for handling each type of collision and drawing the ball)
        boolean flag = false;
        int ballColor = this.getColor();
        PVector position = getBallCenter();

        PVector V = new PVector(this.speed_x, this.speed_y);
        int r = this.getRadius();

        // touch with lines
        for (int j = 0; j < app.bigPointList.size(); j++) {
            List<PVector> smallPointList = app.bigPointList.get(j);
            for (int k = 0; k < smallPointList.size() - 1; k++) {
                PVector p = smallPointList.get(k);
                PVector p1 = smallPointList.get(k + 1);

                // detect if the line touch the ball
                PVector nextPosition = PVector.add(position, V);
                float disToP = PVector.dist(nextPosition, p);
                float disToP1 = PVector.dist(nextPosition, p1);
                float disLine = PVector.dist(p, p1);
                PVector midPoint = PVector.add(p, p1).div(2);

                if (disToP + disToP1 < disLine + r) {
                    // get nearest normal
                    PVector n1 = new PVector(p.y - p1.y, p1.x - p.x);
                    PVector n2 = new PVector(p1.y-p.y, p.x-p1.x);
                    PVector nPoint1 = PVector.add(midPoint, n1);
                    PVector nPoint2 = PVector.add(midPoint, n2);

                    float dist1 = PVector.dist(position, nPoint1);
                    float dist2 = PVector.dist(position, nPoint2);
                    //PVector normal = dist1 < dist2 ? n1 : n2;
                    PVector normal;
                    if (dist1 < dist2) {
                        normal = n1.normalize();
                    } else {
                        normal = n2.normalize();
                    }

                    float dotProduct = V.dot(normal);
                    PVector newVelocity = PVector.sub(V, PVector.mult(normal, 2 * dotProduct));
                    this.setSpeed_x(newVelocity.x);
                    this.setSpeed_y(newVelocity.y);

                    app.bigPointList.remove(j);
                    break;
                }
            }
        }

        // touch the walls
        for (Walls wall: app.walls) {
            List<PVector> wallEdges = wall.getWallEdges();
            for (int k = 0; k < wallEdges.size() - 1; k++) {
                PVector p = wallEdges.get(k);
                PVector p1 = wallEdges.get(k + 1);

                // detect if the line touch the ball
                PVector nextPosition = PVector.add(position, V);
                float disToP = PVector.dist(nextPosition, p);
                float disToP1 = PVector.dist(nextPosition, p1);
                float disLine = PVector.dist(p, p1);
                PVector midPoint = PVector.add(p, p1).div(2);

                if (disToP + disToP1 < disLine + r) {
                    // get nearest normal
                    PVector n1 = new PVector(p.y - p1.y, p1.x - p.x);
                    PVector n2 = new PVector(p1.y-p.y, p.x-p1.x);
                    PVector nPoint1 = PVector.add(midPoint, n1);
                    PVector nPoint2 = PVector.add(midPoint, n2);

                    float dist1 = PVector.dist(position, nPoint1);
                    float dist2 = PVector.dist(position, nPoint2);
                    //PVector normal = dist1 < dist2 ? n1 : n2;
                    PVector normal;
                    if (dist1 < dist2) {
                        normal = n1.normalize();
                    } else {
                        normal = n2.normalize();
                    }

                    float dotProduct = V.dot(normal);
                    PVector newVelocity = PVector.sub(V, PVector.mult(normal, 2 * dotProduct));
                    this.setSpeed_x(newVelocity.x);
                    this.setSpeed_y(newVelocity.y);

                    int wallColor = wall.getColor();
                    if (wallColor != 0) this.color = wallColor;
                }
            }
        }

        // judge if ball is in the hole
        PVector ballCenter = this.getBallCenter();
        Iterator<Holes> iterator = app.holes.iterator();

        while (iterator.hasNext()) {
            Holes hole = iterator.next();
            PVector holeCenter = hole.getHoleCentrePosition();
            PVector distanceToHole = PVector.sub(holeCenter, ballCenter); // distance between ball and hole

            if (distanceToHole.mag() <= 40) {
                PVector newSpeed = PVector.add(new PVector(this.speed_x, this.speed_y), PVector.mult(distanceToHole, 0.1f));
                this.setSpeed_x(newSpeed.x);
                this.setSpeed_y(newSpeed.y);

                // Change the radius of the ball based on how close it is to the hole
                if (this.radius >= 4) {
                    this.radius -= 2;
                }

                if (distanceToHole.mag() <= 24) {
                    String ballColorStr = getColorNameByInt(this.color).toLowerCase();
                    String holeColorStr = getColorNameByInt(hole.getColor()).toLowerCase();

                    if (ballColorStr.equals(holeColorStr) || hole.getColor() == 0) {
                        // If the colors of the ball match the hole, increase the score
                        int scoreIncrease = (int) (app.scoreIncrease.get(ballColorStr)
                                * app.level.getFloat("score_increase_from_hole_capture_modifier"));
                        app.scoreList.set(app.levelNo, app.scoreList.get(app.levelNo) + scoreIncrease);

//                        app.balls.remove(this);  // Remove the ball from the game
                        this.valid = false;

                    } else {
                        // If the colors of the ball do not match the hole, decrease the score
                        int scoreDecrease = (int) (app.scoreDecrease.get(ballColorStr)
                                * app.level.getFloat("score_decrease_from_wrong_hole_modifier"));
                        app.scoreList.set(app.levelNo, app.scoreList.get(app.levelNo) - scoreDecrease);

                        // Respawn the ball at a random alive point
                        int index = App.random.nextInt(app.alivePoints.size());
                        alivePoint aP = app.alivePoints.get(index);
                        this.valid = false;
                        app.waitingBalls.add(new WaitingBalls(aP.getX(), aP.getY(), this.color));

                    }
                    return;  // Exit after processing the hole
                }

                break;  // Stop after processing the first hole the ball is close enough to
            }

            

            this.radius = 12;
        }

        // judge if ball is in the accelerator
        for (Tiles accelerator : app.accelerators) {
            PVector acceleratorCenter = new PVector(accelerator.getX() + 16, accelerator.getY() + 16);
            float distanceToAccelerator = PVector.dist(ballCenter, acceleratorCenter); //distance between ball and accelerator
            if (distanceToAccelerator <= 32) {
                switch (accelerator.getDirection()) {
                    case UP:
                        this.setSpeed_y(this.speed_y - 1.5f);
                        break;
                    case DOWN:
                        this.setSpeed_y(this.speed_y + 1.5f);
                        break;
                    case LEFT:
                        this.setSpeed_x(this.speed_x - 1.5f);
                        break;
                    case RIGHT:
                        this.setSpeed_x(this.speed_x + 1.5f);
                        break;
                    default:
                        break;
                }
            }
        }

        // get new position
        this.x += (int) this.speed_x;
        this.y += (int) this.speed_y;

        String ballPath = "inkball/ball" + ballColor + ".png";
        app.image(app.loadImage(ballPath), x, y, radius*2, radius*2);
    }

    /**
     * Converts a color integer to its corresponding color name as a string.
     *
     * @param colorInt The integer representation of the color
     * @return The corresponding color name as a string
     */
    public static String getColorNameByInt(int colorInt) {
        switch (colorInt) {
            case 0: return "grey";
            case 1: return "orange";
            case 2: return "blue";
            case 3: return "green";
            case 4: return "yellow";
            default: return "unknown";
        }
    }

}
