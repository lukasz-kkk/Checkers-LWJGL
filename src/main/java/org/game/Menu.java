package org.game;

import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

public class Menu {
    private final int VAO, VBO;
    private final ObjectLoader object;
    Shader shader;
    TextRenderer textRenderer;
    Texture t_menu_start = new Texture("assets/menu_start.png");
    Texture t_menu_reset = new Texture("assets/menu_reset.png");

    float[] vertices = {
            // Vertices       // Textures
            -0.40f,  0.4f,     0.0f, 1.0f,   // top left
             0.40f,  0.4f,     1.0f, 1.0f,   // top right
             0.40f, -0.4f,     1.0f, 0.0f,   // bottom right
            -0.40f, -0.4f,     0.0f, 0.0f,   // bottom left
    };

    public Menu(Shader shader, TextRenderer textRenderer){
        this.textRenderer = textRenderer;
        this.shader = shader;
        object = new ObjectLoader();
        object.loadObject(vertices);
        VAO = object.getVAO();
        VBO = object.getVBO();
    }

    public int processInput(MouseInput mouse, int gameState){
        if(!mouse.isLeftButtonPressed()) return 0;
        double posY = mouse.getMouseY();
        double posX = mouse.getMouseX();
        System.out.println("Y: " + mouse.getMouseY());
        System.out.println("X: " + mouse.getMouseX());

        // START BUTTON
        if(gameState == 0)
            if(posX >= 0.44 && posX <= 0.56 && posY >= 0.46 && posY <= 0.53)
                return 1;
        if(gameState != 0)
            if(posX >= 0.44 && posX <= 0.56 && posY >= 0.48 && posY <= 0.55)
                return 1;

        return 0;
    }

    public void render(int winner){
        shader.setObjectType(5);
        shader.setTexture("t_menu", 8);
        if(winner == 0){
            renderStart();
        }else {
            renderWinner(winner);
        }
    }

    public void renderStart(){
        t_menu_start.bind(8);
        object.render();
    }

    public void renderWinner(int winner){
        t_menu_reset.bind(8);
        object.render();
        textRenderer.render(shader, -1, 6, String.format("PLAYER %d WON ", winner));
    }


    public void clean(){
        GL30.glDeleteVertexArrays(VAO);
        GL15.glDeleteBuffers(VBO);
    }


}
