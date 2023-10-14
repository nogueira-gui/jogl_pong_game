package graphics;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;
import entities.Block;

import java.util.ArrayList;
import java.util.List;

public class SinglePlayerScene implements GLEventListener {
    private float xMin, xMax, yMin, yMax, zMin, zMax;

    private float dir_x, dir_y, dir_z, rot_x, rot_y, rot_z = 0.0f;

    private float playerX, playerY = 0.0f;
    private float playerWidth = 0.4f;
    private float ballX = 0.0f;
    private float ballY = 0.0f;
    private float ballSpeedX = 0.02f;
    private float ballSpeedY = 0.02f;

    GLU glu;

    private List<Block> blocks;

    @Override
    public void init(GLAutoDrawable drawable) {
        //dados iniciais da cena
        glu = new GLU();
        //Estabelece as coordenadas do SRU (Sistema de Referencia do Universo)
        xMin = yMin = zMin = -1;
        xMax = yMax = zMax = 1;
        blocks = new ArrayList<>();

        for (float y = 0; y < 3; y++) {
            for (float i = -5; i < 0; i++) {
                float gap = 1.5f + i * 0.50f;
                float height = 0.8f - y * 0.15f;
                Block block = new Block(gap, height);
                blocks.add(block);
            }
        }
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        //obtem o contexto Opengl
        GL2 gl = drawable.getGL().getGL2();
        //define a cor da janela (R, G, G, alpha)
        gl.glClearColor(0, 0, 0, 1);
        //limpa a janela com a cor especificada
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity(); //lê a matriz identidade

        /*
            desenho da cena
        *
        */
        drawPlayer(gl, dir_x);

        GLUT glut = new GLUT();
        updateBallPosition();
        gl.glColor3f(1.0f, 1.0f, 1.0f);
        drawBall(gl, glut);

        for (Block block : blocks) {
            gl.glColor3f(0.3f * block.getY() + 0.2f, 0.15f * block.getY() + 0.2f, 0.2f * block.getY() + 0.2f);
            drawBlock(gl, block);
        }
        gl.glFlush();
    }

    public void transladar(float dx, float dy, float dz) {
        final float halfPlayerWidth = playerWidth / 2;
        //Verifica se o player não saiu dos limites da tela
        if (this.dir_x + dx - halfPlayerWidth > xMin && this.dir_x + dx +halfPlayerWidth < xMax) {
            this.dir_x += dx;
        }
        this.dir_y += dy;
        this.dir_z += dz;
    }

    private void drawPlayer(GL2 gl, float dx) {
        float width = playerWidth;
        float height = 0.05f;

        // Coordenadas do centro do retângulo
        float centerX = this.xMin + this.xMax;
        float centerY = this.yMin + height + 0.10f;

        // Calcula as coordenadas dos
        float x1 = centerX - width / 2f + dx;
        float x2 = centerX + width / 2f + dx;
        float y1 = centerY - height / 2f;
        float y2 = centerY + height / 2f;
        this.playerX = x1;
        this.playerY = y1;
        // Desenha o retângulo
        gl.glBegin(GL2.GL_QUADS);
        gl.glColor3f(1, 1, 1); // Branco
        gl.glVertex2f(x1, y1);
        gl.glVertex2f(x2, y1);
        gl.glVertex2f(x2, y2);
        gl.glVertex2f(x1, y2);
        gl.glEnd();
    }

    private void drawBlock(GL2 gl, Block block) {
        if (block.isActive()) {
            float x = block.getX();
            float y = block.getY();
            float width = block.getWidth();
            float height = block.getHeight();

            // Desenha o retângulo
            gl.glBegin(GL2.GL_QUADS);
            gl.glVertex2f(x - width / 2f, y - height / 2f);
            gl.glVertex2f(x + width / 2f, y - height / 2f);
            gl.glVertex2f(x + width / 2f, y + height / 2f);
            gl.glVertex2f(x - width / 2f, y + height / 2f);
            gl.glEnd();
        }

    }

    private void drawBall(GL2 gl,GLUT glut) {
        gl.glPushMatrix();
        gl.glTranslatef(ballX, ballY, 0.0f);
        glut.glutSolidSphere(0.05, 15, 15);
        gl.glPopMatrix();
    }


    private void updateBallPosition() {
        // Atualiza a posição da bola com base na velocidade
        ballX += ballSpeedX;
        ballY += ballSpeedY;
        float ballRadius = 0.05f;
        float playerY = this.yMin + 0.15f;

        // Verifica se a bola atingiu as bordas da tela em xMin e xMax
        if (ballX - ballRadius < xMin || ballX + ballRadius > xMax) {
            ballSpeedX = -ballSpeedX;  // Inverte a direção no eixo x
        }

        // Verifica se a bola atingiu as bordas da tela em yMin e yMax
        if (ballY + ballRadius > yMax) {
            // Inverte a direção no eixo y
            ballSpeedY = -ballSpeedY;
        }

        if (ballY - ballRadius < playerY &&
                (ballX - ballRadius > playerX && ballX + ballRadius < (playerX + playerWidth))) {
            ballSpeedY = -ballSpeedY;
        }
        if (ballY - ballRadius < yMin) {
            // Inverte a direção no eixo y
            ballSpeedY = -ballSpeedY;
        }

        // Verifica colisão com os blocos
        for (Block block : blocks) {
            if (block.isActive() &&
                    ballY + ballRadius > block.getY() - block.getHeight() / 2f &&
                    ballY - ballRadius < block.getY() + block.getHeight() / 2f &&
                    ballX + ballRadius > block.getX() - block.getWidth() / 2f &&
                    ballX - ballRadius < block.getX() + block.getWidth() / 2f) {
                // Remove a colisão com o bloco (pode ser marcando como inativo ou removendo o bloco)
                block.setActive(false);

                // Inverte a direção no eixo y
                ballSpeedY = -ballSpeedY;
            }
        }
    }


    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        // Obtem o contexto gráfico OpenGL
        GL2 gl = drawable.getGL().getGL2();

        // Evita a divisão por zero
        if (height == 0) height = 1;

        // Calcula a proporção da janela (aspect ratio) da nova janela
        float aspect = (float) width / height;

        // Calcula os novos limites da tela
        float halfWidth = 1.0f * aspect;
        float halfHeight = 1.0f;

        xMin = -halfWidth;
        xMax = halfWidth;
        yMin = -halfHeight;
        yMax = halfHeight;

        // Seta o viewport para abranger a janela inteira
        gl.glViewport(0, 0, width, height);

        // Atualiza a matriz de projeção
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity(); // Lê a matriz identidade

        // Projeção ortogonal
        gl.glOrtho(xMin, xMax, yMin, yMax, zMin, zMax);

        // Atualiza a matriz de modelagem
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity(); // Lê a matriz identidade

        System.out.println("Reshape: " + width + ", " + height);
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
    }
}





