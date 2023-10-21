package org.world;

import org.engine.GameLoop;
import org.entities.Ball;
import org.entities.Block;
import org.entities.InfoBoard;
import org.entities.Player;
import org.graphics.Color;
import org.graphics.EventListener;
import org.graphics.Screen;
import org.utils.Utils;

import java.util.concurrent.ConcurrentLinkedQueue;

public class World {
    private static final ConcurrentLinkedQueue<Block> blocks = new ConcurrentLinkedQueue<>();

    private static Player player;
    private static Ball ball;
    private static InfoBoard board;

    private static boolean lostGame = false;
    private static boolean wonGame = false;

    private static int blockStepX = 6;
    private static int blockStepY = 4;

    private static final int MAX_BLOCKSTEP_X = 6;
    private static final int MIN_BLOCKSTEP_X = 1;

    private static final int MAX_BLOCKSTEP_Y = 4;
    private static final int MIN_BLOCKSTEP_Y = 1;

    private static final int MAX_POINTS = 200;
    private static int pointMultiplier = 3;

    private static int levelCounter = 1;
    private final static int FINAL_LEVEL = 4;

    public static boolean ignoreNextThrow = false;

    static {
        initWorld();
    }

    public static void update() {
        try {
            if (player.getScore() >= MAX_POINTS) {
                levelCounter++;

                if (levelCounter > FINAL_LEVEL) {
                    wonGame = true;

                    System.out.println("You won! Ctrl + R to restart");

                    GameLoop.pauseGame();

                    return;
                }

                GameLoop.pauseGame();

                System.out.printf("Level %d finished! Press SPACE to continue\n", levelCounter - 1);
                ignoreNextThrow = true;

                decreaseBlockStep();
            }

            player.update();
            ball.update();
            board.update();

            if (player.getRemainingLives() == 0) {
                lostGame = !lostGame;

                System.out.println("You lost! Ctrl + R to restart");

                GameLoop.pauseGame();
            }

            if (checkCollision(ball, player)) {
                ball.flipYDirection();
            }

            for (Block block : blocks) {
                if (block.isDestroyed()) {
                    if (checkCollision(ball, block)) {
                        block.destroy();
                        ball.flipXDirection();
                        ball.flipYDirection();

                        player.addPoints(Block.BLOCK_POINT);
                    }
                }
                block.update();
            }
        } catch (NullPointerException ignored) {}
    }

    public static void render() {
        try {
            EventListener.turnOffTheLights();
            board.render(player, ball);
            EventListener.turnOnTheLights();

            player.render();
            ball.render();

            for (Block block: blocks) {
                if (block.isDestroyed()) {
                    block.render();
                }
            }
        } catch (NullPointerException ignored) {}
    }

    private static boolean checkCollision(Ball ball, Block block) {
        float circleDistanceX = Math.abs(ball.getX() - block.getX());
        float circleDistanceY = Math.abs(ball.getY() - block.getY());

        if (circleDistanceX > ((float) Block.BLOCK_WIDTH / 2 + ball.getRadius() - 2)) {
            return false;
        }

        if (circleDistanceY > ((float) Block.BLOCK_HEIGHT / 2 + ball.getRadius() - 2)) {
            return false;
        }

        if (circleDistanceX <= (float) Block.BLOCK_WIDTH / 2) {
            return true;
        }

        if (circleDistanceY <= (float) Block.BLOCK_HEIGHT / 2) {
            return true;
        }

        float cornerDistance = (float) (Math.pow((circleDistanceX - (float) Block.BLOCK_WIDTH  / 2), 2) + Math.pow((circleDistanceY - (float) Block.BLOCK_HEIGHT / 2), 2));

        return cornerDistance <= Math.pow(ball.getRadius(), 2);
    }
    private static boolean checkCollision(Ball ball, Player player) {
        float circleDistanceX = Math.abs(ball.getX() - player.getX());
        float circleDistanceY = Math.abs(ball.getY() - player.getY());

        if (circleDistanceX > ((float) Player.PLAYER_WIDTH / 2 + ball.getRadius() - 5)) {
            return false;
        }

        if (circleDistanceY > ((float) Player.PLAYER_HEIGHT / 2 + ball.getRadius() - 5)) {
            return false;
        }

        if (circleDistanceX <= (float) Player.PLAYER_WIDTH / 2) {
            return true;
        }

        if (circleDistanceY <= (float) Player.PLAYER_HEIGHT / 2) {
            return true;
        }

        float cornerDistance = (float) (Math.pow((circleDistanceX - (float) Player.PLAYER_WIDTH  / 2), 2) + Math.pow((circleDistanceY - (float) Player.PLAYER_HEIGHT / 2), 2));

        return cornerDistance <= Math.pow(ball.getRadius(), 2);
    }

    private static void addBlock(Block block) {
        blocks.offer(block);
    }

    public static void clearObjects() {
        blocks.clear();
        player = null;
        ball = null;
    }

    public static void initWorld() {
        board = new InfoBoard();
        player = new Player();
        ball = new Ball();

        createBlocks(blockStepX, blockStepY);
    }

    public static void resetWorld(boolean resetGame) {
        if (resetGame) {
            clearObjects();

            lostGame = false;
            wonGame = false;

            levelCounter = 1;

            ignoreNextThrow = false;

            blockStepX = MAX_BLOCKSTEP_X;
            blockStepY = MAX_BLOCKSTEP_Y;

            board = new InfoBoard();
            player = new Player();
            ball = new Ball();

            createBlocks(blockStepX, blockStepY);
        }

        ball = new Ball();
        player.resetPlayerX();
        player.resetScore();

        blocks.clear();
        createBlocks(blockStepX, blockStepY);
    }

    private static void createBlocks(int STEP_X, int STEP_Y) {
        int BLOCK_WIDTH = Block.BLOCK_WIDTH;
        int BLOCK_HEIGHT = Block.BLOCK_HEIGHT;

        int X_GAP = 10 * STEP_X;
        int Y_GAP = BLOCK_HEIGHT + 10 * STEP_Y;

        int SCREEN_WIDTH = Screen.getScreenWidth();
        int SCREEN_HEIGHT = Screen.getHalfScreenHeight();

        int HALF_SCREEN_WIDTH = Screen.getHalfScreenWidth();
        int HALF_SCREEN_HEIGHT = Screen.getHalfScreenHeight();

        int BLOCKS_PER_ROW = SCREEN_WIDTH / (BLOCK_WIDTH + X_GAP);
        int BLOCKS_PER_COLUMN = (int) Math.ceil(((SCREEN_HEIGHT - InfoBoard.BOARD_HEIGHT) / (BLOCK_HEIGHT + Y_GAP)));

        int POINT_BY_BLOCK = (int) Math.ceil((double) MAX_POINTS / (BLOCKS_PER_ROW * BLOCKS_PER_COLUMN)) * pointMultiplier;
        Block.setBlockPoint(POINT_BY_BLOCK);

        for (int row = 1; row <= BLOCKS_PER_COLUMN; row++) {
            for (int column = 0; column < BLOCKS_PER_ROW; column++) {

                float x = (-HALF_SCREEN_WIDTH + X_GAP + (float) BLOCK_WIDTH / 2) + column * (BLOCK_WIDTH + X_GAP);
                float y = HALF_SCREEN_HEIGHT - (Y_GAP * row) - InfoBoard.BOARD_HEIGHT;

                int red = 90 + (row * 15);
                int green = 49;
                int blue = 235;
                float alpha = 0.92f;

                Color blockColor = new Color(red, green, blue, alpha);

                addBlock(new Block(x, y, blockColor));
            }
        }
    }

    public static void decreasePlayerLife() {
        ball.resetBall();
        player.decreaseLive();
    }

    public static boolean isGameLost() {
        return lostGame;
    }

    public static boolean isGameWon() {
        return wonGame;
    }

    public static void resetPlayerPosition() {
        player.resetPlayerX();
    }

    public static void decreaseBlockStep() {
        blockStepX--;
        blockStepY--;

        pointMultiplier = Utils.clamp(pointMultiplier - 1, 1, 3);

        blockStepX = Utils.clamp(blockStepX, MIN_BLOCKSTEP_X, MAX_BLOCKSTEP_X);

        if (blockStepX <= MAX_BLOCKSTEP_Y) {
            blockStepY = Utils.clamp(blockStepY, MIN_BLOCKSTEP_Y, MAX_BLOCKSTEP_Y);
        }

        resetWorld(false);
    }

    public static float getPlayerX() {
        return player.getX();
    }

    public static float getPlayerY() {
        return player.getY();
    }

    public static float getBallX() {
        return ball.getX();
    }

    public static float getBallY() {
        return ball.getY();
    }

    public static void throwBall() {
        ball.throwBall();
    }
}
