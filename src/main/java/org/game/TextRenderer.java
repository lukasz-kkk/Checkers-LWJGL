package org.game;

import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

public class TextRenderer {
    private final int VAO, VBO;
    private final ObjectLoader object;
    float[] vertices = {
            // Vertices       // Textures
            0.0f, 0.15f,     0.0f, 1.0f,   // top left
            0.11f, 0.15f,   1.0f, 1.0f,   // top right
            0.11f, 0.0f,    1.0f, 0.0f,   // bottom right
            0.0f, 0.0f,     0.0f, 0.0f,   // bottom left
    };

    public TextRenderer() {
        object = new ObjectLoader();
        object.loadObject(vertices);
        VAO = object.getVAO();
        VBO = object.getVBO();
    }

    public void render(Shader shader, int posY, int posX, String toPrint) {
        shader.setObjectType(4);
        shader.setTexture("t_text", 4);
        shader.setPosition(posX, posY);

        for (int i = 0; i < toPrint.length(); i++) {
            shader.setLetterPosition(i);
            if (toPrint.charAt(i) == ':')
                shader.setLetter(36);
            else if (toPrint.charAt(i) >= '0' && toPrint.charAt(i) <= '9')
                shader.setLetter(toPrint.charAt(i) - 48);
            else
                shader.setLetter(toPrint.charAt(i) - 55);

            object.render();
        }
    }

    public void clean() {
        GL30.glDeleteVertexArrays(VAO);
        GL15.glDeleteBuffers(VBO);
    }
}
