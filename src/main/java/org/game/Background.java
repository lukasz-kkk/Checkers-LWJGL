package org.game;

import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;


public class Background {
    private final int VAO, VBO;
    private final ObjectLoader object;
    Shader shader;

    float[] vertices = {
            // Vertices       // Textures
            -1f,  1f,     0.0f, 1.0f,   // top left
              1,  1f,     1.0f, 1.0f,   // top right
             1f, -1f,     1.0f, 0.0f,   // bottom right
            -1f, -1f,     0.0f, 0.0f,   // bottom left
    };

    public Background(Shader shader){
        this.shader = shader;
        object = new ObjectLoader();
        object.loadObject(vertices);
        VAO = object.getVAO();
        VBO = object.getVBO();
    }

    public void render(){
        shader.setObjectType(2);
        shader.setTexture("t_background", 3);
        object.render();
    }

    public void clean(){
        GL30.glDeleteVertexArrays(VAO);
        GL15.glDeleteBuffers(VBO);
    }

    public int getVAO() {
        return VAO;
    }

}
