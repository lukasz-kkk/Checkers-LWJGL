package org.game;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class ObjectLoader {
    private int VAO, VBO;

    public void loadObject(float[] vertices){
        VAO = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(VAO);

        VBO = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, VBO);

        //FloatBuffer verticesBuffer = MemoryUtil.memAllocFloat(vertices.length);
        //verticesBuffer.put(vertices).flip();
        //GL15.glBufferData(GL15.GL_ARRAY_BUFFER, verticesBuffer, GL15.GL_STATIC_DRAW);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertices, GL15.GL_STATIC_DRAW);

        GL20.glVertexAttribPointer(0, 2, GL11.GL_FLOAT, false, 4 * Float.BYTES, 0);
        GL30.glEnableVertexAttribArray(0);

        GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, 4 * Float.BYTES, 2 * Float.BYTES);
        GL30.glEnableVertexAttribArray(1);

        GL30.glBindVertexArray(0);
    }

    public void render(){
        GL30.glBindVertexArray(VAO);
        GL20.glEnableVertexAttribArray(0);
        GL11.glDrawArrays(GL11.GL_QUADS, 0, 4);
        GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
    }

    public int getVAO() {
        return VAO;
    }

    public int getVBO() {
        return VBO;
    }
}
