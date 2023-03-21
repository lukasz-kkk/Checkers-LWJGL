package org.game;

import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

public class Pawn {
    private final int VAO, VBO;
    private final ObjectLoader object;
    float[] vertices = {
            // Vertices       // Textures
            -0.052f,  0.09f,     0.0f, 1.0f,   // top left
             0.052f,  0.09f,     1.0f, 1.0f,   // top right
             0.052f, -0.09f,     1.0f, 0.0f,   // bottom right
            -0.052f, -0.09f,     0.0f, 0.0f,   // bottom left
    };

    public Pawn(){
        object = new ObjectLoader();
        object.loadObject(vertices);
        VAO = object.getVAO();
        VBO = object.getVBO();
    }

    public void render(Shader shader, int positionX, int positionY, int color){
        shader.setObjectType(1);
        shader.setTexture("t_pawn", color);
        shader.setPosition(positionX, positionY);
        object.render();
    }


    public void clean(){
        GL30.glDeleteVertexArrays(VAO);
        GL15.glDeleteBuffers(VBO);
    }
}
