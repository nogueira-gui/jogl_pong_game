package entities;

public class Block {
    private float x;
    private float y;
    private float width;
    private float height;
    private boolean isActive;

    public Block(float x, float y) {
        this.x = x;
        this.y = y;
        this.width = 0.4f;
        this.height = 0.10f;
        this.isActive = true;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
