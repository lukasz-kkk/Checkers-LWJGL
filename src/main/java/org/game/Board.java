package org.game;

import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

public class Board {
    private final int VAO, VBO;
    private final ObjectLoader object;
    Shader shader;

    float[] vertices = {
            // Vertices       // Textures
            -0.53f,  0.9f,     0.0f, 1.0f,   // top left
             0.53f,  0.9f,     1.0f, 1.0f,   // top right
             0.53f, -0.9f,     1.0f, 0.0f,   // bottom right
            -0.53f, -0.9f,     0.0f, 0.0f,   // bottom left
    };

    public Board(Shader shader){
        this.shader = shader;
        object = new ObjectLoader();
        object.loadObject(vertices);
        VAO = object.getVAO();
        VBO = object.getVBO();
    }

    public void render(){
        shader.setObjectType(0);
        shader.setTexture("t_board", 0);
        object.render();
    }

    public void clean(){
        GL30.glDeleteVertexArrays(VAO);
        GL15.glDeleteBuffers(VBO);
    }


}
