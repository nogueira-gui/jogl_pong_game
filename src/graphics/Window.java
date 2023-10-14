package graphics;

import com.jogamp.newt.event.WindowAdapter;
import com.jogamp.newt.event.WindowEvent;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.FPSAnimator;
import inputs.Keyboard;

public class Window {
    private static GLWindow glWindow = null;
    public static int screenWidth = 640;  //1280
    public static int screenHeight = 480; //960

    //Cria a janela de rendeziração do JOGL
    public void init(){
        GLProfile.initSingleton();
        GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities caps = new GLCapabilities(profile);
        glWindow = GLWindow.create(caps);
        glWindow.setSize(screenWidth, screenHeight);
        //window.setResizable(false);

        SinglePlayerScene singlePlayerScene = new SinglePlayerScene();

        glWindow.addGLEventListener(singlePlayerScene); //adiciona a Cena a Janela

        glWindow.addKeyListener(new Keyboard(singlePlayerScene));
//        glWindow.addMouseListener(new Mouse(singlePlayerScene));

        //window.requestFocus();
        FPSAnimator animator = new FPSAnimator(glWindow, 60);
        animator.start(); //inicia o loop de animação

        //encerrar a aplicacao adequadamente
        glWindow.addWindowListener(new WindowAdapter() {
            @Override
            public void windowDestroyNotify(WindowEvent e) {
                animator.stop();
                System.exit(0);
            }
        });
//        glWindow.setFullscreen(true);
        glWindow.setVisible(true);
    }

}




