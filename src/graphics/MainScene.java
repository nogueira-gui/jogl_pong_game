package graphics;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;

public class MainScene implements GLEventListener {
    private float xMin, xMax, yMin, yMax, zMin, zMax;

    private float dir_x, dir_y, dir_z, rot_x, rot_y, rot_z = 0.0f;

    private int lastMouseX;

    private int lastMouseY;

    private boolean showCube, showStar, showTriangle, showLines = false;


    GLU glu;

    @Override
    public void init(GLAutoDrawable drawable) {
        //dados iniciais da cena
        glu = new GLU();
        //Estabelece as coordenadas do SRU (Sistema de Referencia do Universo)
        xMin = yMin = zMin = -1;
        xMax = yMax = zMax = 1;
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        //obtem o contexto Opengl
        GL2 gl = drawable.getGL().getGL2();
        //define a cor da janela (R, G, G, alpha)
        gl.glClearColor(0, 0, 0, 1);
        //limpa a janela com a cor especificada
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
        gl.glLoadIdentity(); //lê a matriz identidade

        /*
            desenho da cena
        *
        */
        //draw lines XYZ perspective
        if(showLines){
            drawLinesXYZ(gl);
        }
        if(showTriangle){
            drawTriangle(gl, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f);
        }
        if(showCube){
            drawCube(gl, 0.50F, this.dir_x, this.dir_y, this.dir_z, this.rot_x, this.rot_y, this.rot_z);
        }
        if(showStar){
            drawStar(gl, 0.5f, this.dir_x, this.dir_y, this.dir_z, this.rot_x, this.rot_y, this.rot_z);
        }

        gl.glFlush();
    }

    private void drawStar(GL2 gl, float tamanho, float tx, float ty, float tz, float rx, float ry, float rz) {
        float[][] vertices = {
                {-0.15f, 0.3f, 0f}, // Ponto superior esquerdo
                {-0.5f, 0.3f, 0f},  // Ponto esquerdo superior
                {-0.2f, 0.0f, 0f},  // Ponto médio esquerdo
                {-0.35f, -0.4f, 0f},// Ponto inferior esquerdo
                {0.0f, -0.2f, 0f},  // Ponto central
                {0.35f, -0.4f, 0f}, // Ponto inferior direito
                {0.2f, 0.0f, 0f},   // Ponto médio direito
                {0.5f, 0.3f, 0f},   // Ponto superior direito
                {0.15f, 0.3f, 0f},   // Ponto superior direito
                {0.0f, 0.9f, 0f}    // Ponto superior
        };

        aplicarTransformacoes(tx,ty,tz,rx,ry,rz,vertices);

        gl.glColor3f(1.0f, 1.0f, 0.0f);
        gl.glBegin(GL2.GL_POLYGON);
        for (float[] vertice : vertices) {
            gl.glVertex3f(vertice[0] * tamanho, vertice[1] * tamanho, vertice[2] * tamanho);
        }
        gl.glEnd();

    }

    private void drawTriangle(GL2 gl, float dx, float dy, float rz, float rx, float ry) {
        float[][] vertices = {
                {0.0f + dx, 0.0f + dy},
                {0.4f + dx, 0.0f + dy},
                {0.4f + dx, 0.4f + dy}
        };
        gl.glPushMatrix();
        gl.glBegin(GL2.GL_TRIANGLES);
        gl.glColor3f(1.0f, 1.0f, 1.0f);
        for (var vertice : vertices) {
            gl.glVertex2f(vertice[0], vertice[1]);
        }
        gl.glEnd();
        gl.glPopMatrix();
    }

    private void drawLinesXYZ(GL2 gl) {
        gl.glBegin(GL2.GL_LINES);
        gl.glColor3f(1, 0, 0); //red
        gl.glVertex3f(0, 0, 0);
        gl.glVertex3f(xMax + 1, 0, 0);
        gl.glColor3f(0, 1, 0); //green
        gl.glVertex3f(0, 0, 0);
        gl.glVertex3f(0, yMax, 0);
        gl.glColor3f(0, 0, 1); //blue
        gl.glVertex3f(0, 0, zMin);
        gl.glVertex3f(-1, -1, zMax);
        gl.glEnd();
    }

    public void showAction(char key) {
        if (key == 'l'){
            this.showLines = !showLines;
        }
        if (key == 'c'){
            this.showCube = !showCube;
        }
        if (key == 's'){
            this.showStar = !showStar;
        }
        if (key == 't'){
            this.showTriangle = !showTriangle;
        }
    }

    public void rotacionarX() {
        if (rot_x >= 360.0F)
            this.rot_x = 0.0F;
        this.rot_x = 5.0f + this.rot_x;
    }

    public void rotacionarY() {
        if (rot_y >= 360.0F)
            this.rot_y = 0.0F;
        this.rot_y = 5.0f + this.rot_y;
    }

    public void rotacionarZ() {
        if (rot_z >= 360.0F)
            this.rot_z = 0.0F;
        this.rot_z = 5.0f + this.rot_z;
    }

    public void transladar(float dx, float dy, float dz) {
        this.dir_x += dx;
        this.dir_y += dy;
        this.dir_z += dz;
    }

    public void objectIsPressed(int currentMouseX, int currentMouseY) {

        // Calcula a diferença entre as posições atual e anterior do mouse
        int dx = currentMouseX - lastMouseX;
        int dy = currentMouseY - lastMouseY;

        // Define sensibilidades de rotação (ajuste conforme necessário)
        float sensitivityX = 0.5f;
        float sensitivityY = 0.5f;

        // Atualiza as rotações em torno dos eixos Y e X
        rot_y += dx * sensitivityX;
        rot_x += dy * sensitivityY;

        // Mantém as rotações dentro de 0-360 graus
        if (rot_y < 0) {
            rot_y += 360;
        } else if (rot_y >= 360) {
            rot_y -= 360;
        }

        if (rot_x < -90) {
            rot_x = -90;
        } else if (rot_x > 90) {
            rot_x = 90;
        }

        lastMouseX = currentMouseX;
        lastMouseY = currentMouseY;

    }

    private static void drawCube(GL2 gl, float tamanho, float dx, float dy, float dz, float rot_x, float rot_y, float rot_z) {
        float d = tamanho / 2.0f;

        float[][] vertices = {
                {-d, -d, -d},
                {d, -d, -d},
                {d, d, -d},
                {-d, d, -d},
                {-d, -d, d},
                {d, -d, d},
                {d, d, d},
                {-d, d, d}
        };

        aplicarTransformacoes(dx, dy, dz, rot_x, rot_y, rot_z, vertices);

        int[][] edges = {
                {0, 1}, {1, 2}, {2, 3}, {3, 0},
                {4, 5}, {5, 6}, {6, 7}, {7, 4},
                {0, 4}, {1, 5}, {2, 6}, {3, 7}
        };

        gl.glBegin(GL2.GL_LINES);
        gl.glColor3f(1, 1, 1); // Branco

        for (int[] edge : edges) {
            int v1 = edge[0];
            int v2 = edge[1];
            float[] vertex1 = vertices[v1];
            float[] vertex2 = vertices[v2];
            gl.glVertex3fv(vertex1, 0);
            gl.glVertex3fv(vertex2, 0);
        }

        gl.glEnd();
    }

    private static void aplicarTransformacoes(float dx, float dy, float dz, float rot_x, float rot_y, float rot_z, float[][] vertices) {
        // Aplicar rotações
        float cosY = (float) Math.cos(Math.toRadians(rot_y));
        float sinY = (float) Math.sin(Math.toRadians(rot_y));
        float cosX = (float) Math.cos(Math.toRadians(rot_x));
        float sinX = (float) Math.sin(Math.toRadians(rot_x));
        float cosZ = (float) Math.cos(Math.toRadians(rot_z));
        float sinZ = (float) Math.sin(Math.toRadians(rot_z));

        for (int i = 0; i < vertices.length; i++) {
            float[] vertex = vertices[i];
            float x = vertex[0];
            float y = vertex[1];
            float z = vertex[2];

            // Rotação em torno do eixo Y
            float new_x = x * cosY - z * sinY;
            float new_z = x * sinY + z * cosY;
            x = new_x;
            z = new_z;

            // Rotação em torno do eixo X
            float new_y = y * cosX - z * sinX;
            z = y * sinX + z * cosX;
            y = new_y;

            // Rotação em torno do eixo Z
            new_x = x * cosZ - y * sinZ;
            float new_y2 = x * sinZ + y * cosZ;
            x = new_x;
            y = new_y2;

            // Atualiza as coordenadas do vértice após as rotações
            // soma dx dy e dz para translação
            vertex[0] = x + dx; //atualiza eixo x e soma a translação com dx
            vertex[1] = y + dy; //atualiza eixo y e soma a translação com dy
            vertex[2] = z + dz; //atualiza eixo z e soma a translação com dz
        }
    }


    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        //obtem o contexto grafico Opengl
        GL2 gl = drawable.getGL().getGL2();

        //evita a divisão por zero
        if (height == 0) height = 1;
        //calcula a proporção da janela (aspect ratio) da nova janela
        float aspect = (float) width / height;

        //seta o viewport para abranger a janela inteira
        gl.glViewport(0, 0, width, height);

        //ativa a matriz de projeção
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity(); //lê a matriz identidade

        //Projeção ortogonal
        //true:   aspect >= 1 configura a altura de -1 para 1 : com largura maior
        //false:  aspect < 1 configura a largura de -1 para 1 : com altura maior
        if (width >= height)
            gl.glOrtho(xMin * aspect, xMax * aspect, yMin, yMax, zMin, zMax);
        else
            gl.glOrtho(xMin, xMax, yMin / aspect, yMax / aspect, zMin, zMax);

        //ativa a matriz de modelagem
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity(); //lê a matriz identidade
        System.out.println("Reshape: " + width + ", " + height);
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
    }
}





