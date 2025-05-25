package inkball;

import com.google.common.base.MoreObjects;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import processing.data.JSONArray;
import processing.data.JSONObject;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import java.io.*;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
    * main class of the game
 */
public class App extends PApplet {
    public static final int CELLSIZE = 32; //8;
    public static final int CELLHEIGHT = 32;
    public static final int CELLAVG = 32;
    public static final int TOPBAR = 64;
    public static int WIDTH = 32 * 18; //CELLSIZE*BOARD_WIDTH;
    public static int HEIGHT = 32 * 18 + 64; //BOARD_HEIGHT*CELLSIZE+TOPBAR;
    public static final int BOARD_WIDTH = WIDTH / CELLSIZE;
    public static final int BOARD_HEIGHT = 20;
    public boolean isPaused;
    public static final int FPS = 30;
    public static Random random = new Random();
    public String configPath = "config.json";

    public List<Walls> walls = new ArrayList<>();
    public List<Holes> holes = new ArrayList<>();
    public List<alivePoint> alivePoints = new ArrayList<>();
    public List<Balls> balls = new CopyOnWriteArrayList<>();
    public List<WaitingBalls> waitingBalls = new CopyOnWriteArrayList<>();
    public List<Tiles> tiles = new ArrayList<>();
    public List<Tiles> accelerators = new ArrayList<>();
    public List<PVector> smallPointList = new ArrayList<>();
    public List<List<PVector>> bigPointList = new ArrayList<>();
    public List<Integer> scoreList = Arrays.asList(0, 0, 0);
    public Map<String, Integer> scoreIncrease = new HashMap<>();
    public Map<String, Integer> scoreDecrease = new HashMap<>();
    private int frameCount;
    private int countdown;
    private int intervalFrameCount;
    public int levelNo = 0;
    public JSONObject level;
    public boolean finalScored = false;


    public App() {
        this.configPath = "config.json";
    }

    /**
     * Initialise the setting of the window size.
     */
    @Override
    public void settings() {
        size(WIDTH, HEIGHT);
    }

    /**
     * Load all resources such as images. Initialise the elements such as the player and map elements.
     */
    @Override
    public void setup() {
        frameRate(FPS);
        /*try {
            result = loadImage(URLDecoder.decode(this.getClass().getResource(filename+".png").getPath(), StandardCharsets.UTF_8.name()));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }*/

        JSONObject jb = loadJSONObject(configPath);
        JSONArray ja = jb.getJSONArray("levels");
        level = ja.getJSONObject(levelNo);

        String lo = level.getString("layout");
        String[] linesInJson = loadStrings(lo);
        for (int i = 0; i < linesInJson.length; i++) { // y
            String lineInJson = linesInJson[i];
            for (int j = 0; j < lineInJson.length(); j++) { // x
                char c = lineInJson.charAt(j);
                if (c == 'X') {
                    int x = j * 32;
                    int y = i * 32 + 64;
                    List<PVector> wallEdges = new ArrayList<>();
                    wallEdges.add(new PVector(x, y));
                    wallEdges.add(new PVector(x, y + 32));
                    wallEdges.add(new PVector(x + 32, y + 32));
                    wallEdges.add(new PVector(x + 32, y));
                    wallEdges.add(new PVector(x, y));
                    Walls wall = new Walls(x, y , 0, wallEdges);
                    walls.add(wall);
                }
                else if (c == 'H') {
                    int x = j;
                    int y = i;
                    // see if the int is the holes color
                    if ((j + 1) < lineInJson.length() && Character.isDigit(lineInJson.charAt(j + 1))) {
                        int holeColor = lineInJson.charAt(j + 1) - '0';
                        Holes hole = new Holes(x * 32, (y * 32) + TOPBAR, holeColor);
                        holes.add(hole);
                        j++;
                    }
                }
                else if (c == 'S') {
                    int x = j;
                    int y = i;
                    alivePoint aP = new alivePoint(x * 32, (y * 32) + TOPBAR);
                    alivePoints.add(aP);
                }
                else if (c == 'B') {
                    int x = j;
                    int y = i;
                    // if the color is the ball color
                    if ((j + 1) < lineInJson.length() && Character.isDigit(lineInJson.charAt(j + 1))) {
                        int ballColor = lineInJson.charAt(j + 1) - '0';
                        Balls ball = new Balls(x * 32, (y * 32) + TOPBAR, ballColor);
                        balls.add(ball);
                        Tiles tile = new Tiles(x * 32, (y * 32) + TOPBAR);
                        Tiles tile1 = new Tiles((x+1) * 32, (y * 32) + TOPBAR);
                        tiles.add(tile);
                        tiles.add(tile1);
                        j++;
                    }
                }
                else if (c == ' ') {
                    int x = j;
                    int y = i;
                    Tiles tile = new Tiles(x * 32, (y * 32) + TOPBAR);
                    tiles.add(tile);
                }
                else if (c == '0' || c == '1' || c == '2' || c == '3' || c == '4') {
                    if (j > 0) {
                        char prevChar = lineInJson.charAt(j - 1);
                        if (prevChar == 'H' || prevChar == 'B') {
                            continue;
                        }
                    }
                    int x = j * 32;
                    int y = i * 32 + 64;
                    int wallColor = c - '0';
                    List<PVector> wallEdges = new ArrayList<>();
                    // TODO: add more edges
                    wallEdges.add(new PVector(x, y));
                    wallEdges.add(new PVector(x, y + 32));
                    wallEdges.add(new PVector(x + 32, y + 32));
                    wallEdges.add(new PVector(x + 32, y));
                    wallEdges.add(new PVector(x, y));
                    Walls cwall = new Walls(x, y, wallColor, wallEdges);
                    walls.add(cwall);
                }
                else if (c == 'U' || c == 'D' || c == 'L' || c == 'R') {
                    int x = j;
                    int y = i;
                    String direction = "";
                    if (c == 'U') {
                        direction = "UP";
                    } else if (c == 'D') {
                        direction = "DOWN";
                    } else if (c == 'L') {
                        direction = "LEFT";
                    } else if (c == 'R') {
                        direction = "RIGHT";
                    }
                    Direction dir = Direction.valueOf(direction);
                    Tiles tile = new Tiles(x * 32, (y * 32) + TOPBAR, true, dir);
                    tiles.add(tile);
                    accelerators.add(tile);
                }
            }
        }

        JSONArray ballsStrings = level.getJSONArray("balls");
        waitingBalls = new ArrayList<>();
        for (int i = 0; i < ballsStrings.size(); i++) {
            int color = getColorIntByString(ballsStrings.getString(i));
            if (!alivePoints.isEmpty()) {
                int index = random.nextInt(alivePoints.size());
                alivePoint aP = alivePoints.get(index);
                waitingBalls.add(new WaitingBalls(aP.x, aP.y, color));
            }

        }

        JSONObject scoreConfigJson = loadJSONObject(configPath);
        JSONObject scoreIncreaseJson = scoreConfigJson.getJSONObject("score_increase_from_hole_capture");
        JSONObject scoreDecreaseJson = scoreConfigJson.getJSONObject("score_decrease_from_wrong_hole");

        for (Object key : scoreIncreaseJson.keys()) {
            String K = (String)key;
            int value = scoreIncreaseJson.getInt(K);
            scoreIncrease.put(K.toLowerCase(), value);
        }

        // score_decrease_from_wrong_hole
        for (Object key : scoreDecreaseJson.keys()) {
            String K = (String)key;
            int value = scoreDecreaseJson.getInt(K);
            scoreDecrease.put(K.toLowerCase(), value);
        }
    }

    /**
     * Receive key pressed signal from the keyboard.
     */
    @Override
    public void keyPressed(KeyEvent event) {
        if (event.getKey() == ' ') {
            isPaused = !isPaused;
        } else if (event.getKey() == 'r' || event.getKey() == 'R') {
            // if the game is ended, restart the game
            if (allKindBallsInHoles() && levelNo == 2) {
                levelNo = 0;
                scoreList.set(0, 0);
                scoreList.set(1, 0);
                scoreList.set(2, 0);
                level = loadJSONObject(configPath).getJSONArray("levels").getJSONObject(levelNo);
            }
            balls = new ArrayList<>();
            walls = new ArrayList<>();
            tiles = new ArrayList<>();
            holes = new ArrayList<>();
            alivePoints = new ArrayList<>();
            smallPointList = new ArrayList<>();
            bigPointList = new ArrayList<>();
            frameCount = 0;
            intervalFrameCount = 0;
            isPaused = false;
            scoreList.set(levelNo, 0);

            String lo = level.getString("layout");
            String[] linesInJson = loadStrings(lo);
            for (int i = 0; i < linesInJson.length; i++) { // y
                String lineInJson = linesInJson[i];
                for (int j = 0; j < lineInJson.length(); j++) { // x
                    char c = lineInJson.charAt(j);
                    if (c == 'X') {
                        int x = j * 32;
                        int y = i * 32 + 64;
                        List<PVector> wallEdges = new ArrayList<>();
                        wallEdges.add(new PVector(x, y));
                        wallEdges.add(new PVector(x, y + 32));
                        wallEdges.add(new PVector(x + 32, y + 32));
                        wallEdges.add(new PVector(x + 32, y));
                        wallEdges.add(new PVector(x, y));
                        Walls wall = new Walls(x, y , 0, wallEdges);
                        walls.add(wall);
                    }
                    else if (c == 'H') {
                        int x = j;
                        int y = i;
                        // see if the int is the holes color
                        if ((j + 1) < lineInJson.length() && Character.isDigit(lineInJson.charAt(j + 1))) {
                            int holeColor = lineInJson.charAt(j + 1) - '0';
                            Holes hole = new Holes(x * 32, (y * 32) + TOPBAR, holeColor);
                            holes.add(hole);
                            j++;
                        }
                    }
                    else if (c == 'S') {
                        int x = j;
                        int y = i;
                        alivePoint aP = new alivePoint(x * 32, (y * 32) + TOPBAR);
                        alivePoints.add(aP);
                    }
                    else if (c == 'B') {
                        int x = j;
                        int y = i;
                        // if the color is the ball color
                        if ((j + 1) < lineInJson.length() && Character.isDigit(lineInJson.charAt(j + 1))) {
                            int ballColor = lineInJson.charAt(j + 1) - '0';
                            Balls ball = new Balls(x * 32, (y * 32) + TOPBAR, ballColor);
                            balls.add(ball);
                            Tiles tile = new Tiles(x * 32, (y * 32) + TOPBAR);
                            Tiles tile1 = new Tiles((x+1) * 32, (y * 32) + TOPBAR);
                            tiles.add(tile);
                            tiles.add(tile1);
                            j++;
                        }
                    }
                    else if (c == ' ') {
                        int x = j;
                        int y = i;
                        Tiles tile = new Tiles(x * 32, (y * 32) + TOPBAR);
                        tiles.add(tile);
                    }
                    else if (c == '0' || c == '1' || c == '2' || c == '3' || c == '4') {
                        if (j > 0) {
                            char prevChar = lineInJson.charAt(j - 1);
                            if (prevChar != 'H' || prevChar != 'B') {
                                int x = j * 32;
                                int y = i * 32 + 64;
                                int wallColor = c - '0';
                                List<PVector> wallEdges = new ArrayList<>();
                                wallEdges.add(new PVector(x, y));
                                wallEdges.add(new PVector(x, y + 32));
                                wallEdges.add(new PVector(x + 32, y + 32));
                                wallEdges.add(new PVector(x + 32, y));
                                wallEdges.add(new PVector(x, y));
                                Walls cwall = new Walls(x, y, wallColor, wallEdges);
                                walls.add(cwall);
                            }
                        }
                    }
                    else if (c == 'U' || c == 'D' || c == 'L' || c == 'R') {
                        int x = j;
                        int y = i;
                        String direction = "";
                        if (c == 'U') {
                            direction = "UP";
                        } else if (c == 'D') {
                            direction = "DOWN";
                        } else if (c == 'L') {
                            direction = "LEFT";
                        } else if (c == 'R') {
                            direction = "RIGHT";
                        }
                        Direction dir = Direction.valueOf(direction);
                        Tiles tile = new Tiles(x * 32, (y * 32) + TOPBAR, true, dir);
                        tiles.add(tile);
                        accelerators.add(tile);
                    }
                }
            }

            JSONArray ballsStrings = level.getJSONArray("balls");
            waitingBalls = new ArrayList<>();
            Random rd = new Random();
            for (int i = 0; i < ballsStrings.size(); i++) {
                int color = getColorIntByString(ballsStrings.getString(i));
                if (!alivePoints.isEmpty()) {
                    int index = rd.nextInt(alivePoints.size());
                    alivePoint aP = alivePoints.get(index);
                    waitingBalls.add(new WaitingBalls(aP.x, aP.y, color));
                }


            }
        }
    }

    /**
     * Receive key released signal from the keyboard.
     */
    @Override
    public void keyReleased() {

    }

    /**
     * Called when the mouse is pressed.
     * If the right mouse button is pressed, the bigPointList is cleared.
     *
     * @param e MouseEvent object that contains details about the mouse press event
     */
    @Override
    public void mousePressed(MouseEvent e) {
        // create a new player-drawn line object
        if (e.getButton() == RIGHT) {
            bigPointList.clear();
        }
    }

    /**
     * Called when the mouse is dragged.
     * Adds the current mouse position to the smallPointList.
     *
     * @param e MouseEvent object that contains details about the mouse drag event
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        PVector pv = new PVector(e.getX(), e.getY());
        smallPointList.add(pv);

    }

    /**
     * Called when the mouse is released.
     * Adds the current smallPointList to the bigPointList and then clears the smallPointList
     * to prepare for the next drawing.
     *
     * @param e MouseEvent object that contains details about the mouse release event
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        bigPointList.add(new ArrayList<>(smallPointList));
        // clear the smallPointList and get ready for the next time
        smallPointList.clear();


    }

    /**
     * Draw all elements in the game by current frame.
     */
    @Override
    public void draw() {
        // if not paused, or the game is not ended, increase the frame count
        if (!isPaused && !(allKindBallsInHoles() && levelNo == 2)) {
            frameCount++;
            intervalFrameCount++;
        }

        int secondCount = frameCount / FPS;
        int time = level.getInt("time");
        countdown = time - secondCount;

        if (countdown <= 0) {
            // game over
            text("=== TIME's UP ===", 200, 30);
            return;
        }

        if (isPaused){
            text("*** Paused ***", 230, 30);
            return;
        }


        //draw tiles
        for (Tiles tile : tiles) {
            tile.drawTile(this);
        }

        if (levelNo == 0) {
            for (Tiles accelerator : accelerators) {
                accelerator.drawTile(this);
            }
        }


        //draw walls
        for (Walls wall : walls) {
            wall.drawWalls(this);
        }

        //draw holes
        for (Holes hole : holes) {
            hole.drawHoles(this);
        }

        //draw alive points
        for (alivePoint aP : alivePoints) {
            aP.drawAlivePoint(this);
        }

        //draw balls
        for (Balls ball : balls) {
           if (ball.valid) ball.drawBalls(this);
        }

        drawTopBar();

        drawMouse();

        // check if all balls are in holes, if so, go to next level
        if (allKindBallsInHoles()) {

            if (levelNo < 2) {
                levelNo++;
                int secondsLeft = level.getInt("time") - (frameCount / FPS);
                scoreList.set(levelNo, (int)((float)secondsLeft / 0.067f) + scoreList.get(levelNo));
            } else if (levelNo == 2) {
                textSize(20);
                text("=== ENDED ===", 220, 30);
                text("PRESS 'R' TO RESTART", 220, 60);
                if (!finalScored) {
                    int secondsLeft = level.getInt("time") - (frameCount / FPS);
                    scoreList.set(levelNo, (int)((float)secondsLeft / 0.067f) + scoreList.get(levelNo));
                    finalScored = true;
                }

                pause();
                return;
            }
            level = loadJSONObject(configPath).getJSONArray("levels").getJSONObject(levelNo);
            balls = new ArrayList<>();
            walls = new ArrayList<>();
            tiles = new ArrayList<>();
            holes = new ArrayList<>();
            alivePoints = new ArrayList<>();
            smallPointList = new ArrayList<>();
            bigPointList = new ArrayList<>();
            frameCount = 0;
            accelerators.clear();

            String lo = level.getString("layout");
            String[] linesInJson = loadStrings(lo);
            for (int i = 0; i < linesInJson.length; i++) { // y
                String lineInJson = linesInJson[i];
                for (int j = 0; j < lineInJson.length(); j++) { // x
                    char c = lineInJson.charAt(j);
                    if (c == 'X') {
                        int x = j * 32;
                        int y = i * 32 + 64;
                        List<PVector> wallEdges = new ArrayList<>();
                        wallEdges.add(new PVector(x, y));
                        wallEdges.add(new PVector(x, y + 32));
                        wallEdges.add(new PVector(x + 32, y + 32));
                        wallEdges.add(new PVector(x + 32, y));
                        wallEdges.add(new PVector(x, y));
                        Walls wall = new Walls(x, y , 0, wallEdges);
                        walls.add(wall);
                    }
                    if (c == 'H') {
                        int x = j;
                        int y = i;
                        // see if the int is the holes color
                        if ((j + 1) < lineInJson.length() && Character.isDigit(lineInJson.charAt(j + 1))) {
                            int holeColor = lineInJson.charAt(j + 1) - '0';
                            Holes hole = new Holes(x * 32, (y * 32) + TOPBAR, holeColor);
                            holes.add(hole);
                            j++;
                        }
                    }
                    if (c == 'S') {
                        int x = j;
                        int y = i;
                        alivePoint aP = new alivePoint(x * 32, (y * 32) + TOPBAR);
                        alivePoints.add(aP);
                    }
                    if (c == 'B') {
                        int x = j;
                        int y = i;
                        // if the color is the ball color
                        if ((j + 1) < lineInJson.length() && Character.isDigit(lineInJson.charAt(j + 1))) {
                            int ballColor = lineInJson.charAt(j + 1) - '0';
                            Balls ball = new Balls(x * 32, (y * 32) + TOPBAR, ballColor);
                            balls.add(ball);
                            Tiles tile = new Tiles(x * 32, (y * 32) + TOPBAR);
                            Tiles tile1 = new Tiles((x+1) * 32, (y * 32) + TOPBAR);
                            tiles.add(tile);
                            tiles.add(tile1);
                            j++;
                        }
                    }
                    if (c == ' ') {
                        int x = j;
                        int y = i;
                        Tiles tile = new Tiles(x * 32, (y * 32) + TOPBAR);
                        tiles.add(tile);
                    }
                    if (c == '0' || c == '1' || c == '2' || c == '3' || c == '4') {
                        if (j > 0) {
                            char prevChar = lineInJson.charAt(j - 1);
                            if (prevChar != 'H' || prevChar != 'B') {
                                int x = j * 32;
                                int y = i * 32 + 64;
                                int wallColor = c - '0';
                                List<PVector> wallEdges = new ArrayList<>();
                                wallEdges.add(new PVector(x, y));
                                wallEdges.add(new PVector(x, y + 32));
                                wallEdges.add(new PVector(x + 32, y + 32));
                                wallEdges.add(new PVector(x + 32, y));
                                wallEdges.add(new PVector(x, y));
                                Walls cwall = new Walls(x, y, wallColor, wallEdges);
                                walls.add(cwall);
                            }
                        }
                    }
                }
            }

            JSONArray ballsStrings = level.getJSONArray("balls");
            waitingBalls = new ArrayList<>();
            Random rd = new Random();
            for (int i = 0; i < ballsStrings.size(); i++) {
                int color = getColorIntByString(ballsStrings.getString(i));
                if (!alivePoints.isEmpty()) {
                    int index = rd.nextInt(alivePoints.size());
                    alivePoint aP = alivePoints.get(index);
                    waitingBalls.add(new WaitingBalls(aP.x, aP.y, color));
                }
            }
        }
    }

    /**
     * the main method to draw top bar
     */
    public void drawTopBar() {

        // draw rect
        noStroke();
        fill(185);
        rect(0,0, WIDTH, TOPBAR);

        // score
        fill(0);
        textSize(20);
        text("Score: " + getTotalScore(), App.WIDTH - 120, 30);

        // time
        text("Time: " + countdown, App.WIDTH - 120, 55);

        // draw spawn balls
        fill(0,0,0);
        rect(8,8, 160, 32);
        for (int i = 0; i < waitingBalls.size() && i < 5; i++) {
            WaitingBalls waitingBall = waitingBalls.get(i);
            image(loadImage("inkball/ball" + waitingBall.getColor() + ".png"), 12 + i * 32, 12, 25, 25);
        }


        // draw interval countdown
        int intervalSecondTime = level.getInt("spawn_interval");
        int intervalFrameTime = intervalSecondTime * FPS;
        int remainingFrameToSpawnWaitingBall = intervalFrameTime - (intervalFrameCount % intervalFrameTime);
        float remainingSecondToSpawnWaitingBall = (float)remainingFrameToSpawnWaitingBall / FPS;

        if (remainingSecondToSpawnWaitingBall <= 0.05) {
            if (!waitingBalls.isEmpty()) {
                WaitingBalls waitingBall = waitingBalls.get(0);
                Balls ball = new Balls(waitingBall.getX(), waitingBall.getY(), waitingBall.getColor());
                balls.add(ball);
                waitingBalls.remove(0);
            }
        }

        textSize(20);
        fill(0);
        // format string
        text(String.format("%.1f", remainingSecondToSpawnWaitingBall) + "s", 180, 30);
    }

    /**
     * the main method to draw lines
     */
    public void drawMouse() {
        for (int i = 0; i < smallPointList.size() - 1; i++) {
            PVector p = smallPointList.get(i);
            PVector p1 = smallPointList.get(i + 1);
            stroke(0);
            strokeWeight(10);
            line(p.x, p.y, p1.x, p1.y);
        }
        for (int j = 0; j < bigPointList.size(); j++) {
            List<PVector> smallPointList = bigPointList.get(j);
            for (int i = 0; i < smallPointList.size() - 1; i++) {
                PVector p = smallPointList.get(i);
                PVector p1 = smallPointList.get(i + 1);
                stroke(0);
                strokeWeight(10);
                line(p.x, p.y, p1.x, p1.y);
            }
        }
    }

    /**
     * Converts a color string to its corresponding integer value.
     * The mapping is as follows:
     * "grey" -> 0
     * "orange" -> 1
     * "blue" -> 2
     * "green" -> 3
     * "red" -> 4
     *
     * @param color The color as a string
     * @return The corresponding integer value for the given color string
     */
    public int getColorIntByString(String color) {
        switch (color) {
            case "grey":
                return 0;
            case "orange":
                return 1;
            case "blue":
                return 2;
            case "green":
                return 3;
            case "red":
                return 4;
        }
        return 0;
    }

    /**
     * Checks if all kinds of balls have entered the holes.
     * This method returns true if both the balls list and the waitingBalls list are empty.
     *
     * @return true if all balls are in holes, false otherwise
     */
    public boolean allKindBallsInHoles() {
        // if balls is empty or invalid and if waitingBalls is empty, return true
        boolean allInvalid = true;
        for (Balls ball : balls) {
            if (ball.valid) {
                allInvalid = false;
                break;
            }
        }
        return allInvalid && waitingBalls.isEmpty();
    }

    /**
     * Calculates the total score based on the scores stored in the scoreList.
     *
     * @return The total score as an integer
     */
    public int getTotalScore() {
        int totalScore = 0;
        for (int i = 0; i < scoreList.size(); i++) {
            totalScore += scoreList.get(i);
        }
        return totalScore;
    }

    /**
     * The main method to start the Inkball application.
     * This method starts the Processing PApplet which represents the main application window.
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        PApplet.main("inkball.App");
    }

}
