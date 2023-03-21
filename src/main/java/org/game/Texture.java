package org.game;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.stb.STBImage;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class Texture {
    private final int id;

    public Texture(String fileName) {
        IntBuffer width = BufferUtils.createIntBuffer(1);
        IntBuffer height = BufferUtils.createIntBuffer(1);
        IntBuffer bpp = BufferUtils.createIntBuffer(1);

        STBImage.stbi_set_flip_vertically_on_load(true);

        ByteBuffer localBuffer = STBImage.stbi_load(fileName, width, height, bpp, 4);

        id = GL11.glGenTextures();
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);

        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL13.GL_CLAMP_TO_EDGE);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL13.GL_CLAMP_TO_EDGE);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);

        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width.get(0), height.get(0), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, localBuffer);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
        if(localBuffer != null)
            STBImage.stbi_image_free(localBuffer);
    }

    public void bind(int slot){
        GL13.glActiveTexture((GL13.GL_TEXTURE0 + slot));
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
    }

}
