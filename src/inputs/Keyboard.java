package inputs;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import graphics.MainScene;

public class Keyboard implements KeyListener {
    private MainScene cena;

    public Keyboard(MainScene cena){
        this.cena = cena;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        float dx = 0.0f;
        float dy = 0.0f;
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
            System.exit(0);
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            dy += 0.05f; // Mover para cima
        }

        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            dy -= 0.05f; // Mover para baixo
        }

        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            dx -= 0.05f; // Mover para a esquerda
        }

        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            dx += 0.05f; // Mover para a direita
        }

        cena.transladar(dx, dy, 0.0f);

    }
    @Override
    public void keyReleased(KeyEvent e) { }

}