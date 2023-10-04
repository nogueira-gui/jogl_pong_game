package entities;

public class Ball {
    private float x;
    private float y;
    private float dx;  // Velocidade em x
    private float dy;  // Velocidade em y

    public Ball(float x, float y) {
        this.x = x;
        this.y = y;
        this.dx = 0.02f;  // Velocidade inicial
        this.dy = 0.01f;  // Velocidade inicial
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void move() {
        // Movimenta a bola
        x += dx;
        y += dy;

        // Restringe a movimentação dentro dos limites
        if (x > 1.0f || x < -1.0f) {
            dx = -dx;  // Inverte a direção no eixo x
        }

        if (y > 1.0f || y < -1.0f) {
            dy = -dy;  // Inverte a direção no eixo y
        }
    }
}
