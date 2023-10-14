package inputs;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import graphics.SinglePlayerScene;

import java.util.Timer;
import java.util.TimerTask;

public class Keyboard implements KeyListener {
    private SinglePlayerScene cena;
    private float acceleration = 0.02f;
    private float maxVelocity = 0.1f;
    private float playerVelocityX = 0.0f;
    private float playerVelocityY = 0.0f;
    private Timer velocityResetTimer;

    public Keyboard(SinglePlayerScene cena) {
        this.cena = cena;
        setupVelocityResetTimer();
    }

    private void setupVelocityResetTimer() {
        velocityResetTimer = new Timer();
    }

    private void resetPlayerVelocity() {
        playerVelocityX = 0.0f;
        playerVelocityY = 0.0f;
        updatePlayerPosition();
    }

    private void scheduleVelocityReset() {
        velocityResetTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                resetPlayerVelocity();
            }
        }, 50);  // Adjust the delay (in milliseconds) as needed
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
            System.exit(0);

        if (e.getKeyCode() == KeyEvent.VK_UP) {
            accelerate(0, acceleration);
        }

        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            accelerate(0, -acceleration);
        }

        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            accelerate(-acceleration, 0);
        }

        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            accelerate(acceleration, 0);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Not used in this approach
    }

    private void accelerate(float dx, float dy) {
        if (Math.abs(playerVelocityX + dx) <= maxVelocity) {
            playerVelocityX += dx;
        }

        if (Math.abs(playerVelocityY + dy) <= maxVelocity) {
            playerVelocityY += dy;
        }

        updatePlayerPosition();

        // Cancel any previously scheduled velocity reset
        velocityResetTimer.cancel();
        // Schedule a new velocity reset
        setupVelocityResetTimer();
        scheduleVelocityReset();
    }

    private void updatePlayerPosition() {
        float dx = playerVelocityX;
        float dy = playerVelocityY;
        cena.transladar(dx, dy, 0.0f);
    }
}
