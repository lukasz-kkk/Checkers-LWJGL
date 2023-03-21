package org.game;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL20;

public class Shader {
    private int shaderProgram;

    public Shader(String vertexShaderPath, String fragmentShaderPath) throws IOException {
        String vertexShaderSource = readFile(vertexShaderPath);
        String fragmentShaderSource = readFile(fragmentShaderPath);

        int vertexShader = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
        GL20.glShaderSource(vertexShader, vertexShaderSource);
        GL20.glCompileShader(vertexShader);

        int fragmentShader = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);
        GL20.glShaderSource(fragmentShader, fragmentShaderSource);
        GL20.glCompileShader(fragmentShader);

        shaderProgram = GL20.glCreateProgram();
        GL20.glAttachShader(shaderProgram, vertexShader);
        GL20.glAttachShader(shaderProgram, fragmentShader);
        GL20.glLinkProgram(shaderProgram);
    }

    public void active(){
        GL20.glUseProgram(shaderProgram);
    }

    public static String readFile(String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        StringBuilder stringBuilder = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line).append("\n");
        }
        reader.close();
        return stringBuilder.toString();
    }

    public void setObjectType(int type) {
        GL20.glUniform1i(GL20.glGetUniformLocation(shaderProgram, "OBJECT"), type);
    }

    public void setTexture(String texName, int texID) {
        int textureUniformLoc = GL20.glGetUniformLocation(shaderProgram, texName);
        GL20.glUniform1i(textureUniformLoc, texID);
    }
    public void setPosition(int positionX, int positionY) {
        GL20.glUniform1i(GL20.glGetUniformLocation(shaderProgram, "POSITION_X"), positionX);
        GL20.glUniform1i(GL20.glGetUniformLocation(shaderProgram, "POSITION_Y"), positionY);
    }

    public void setLetterPosition(int position){
        GL20.glUniform1f(GL20.glGetUniformLocation(shaderProgram, "LETTER_POSITION"), (0.057f * position));
    }
    public void setLetter(int letter){
        GL20. glUniform1i(GL20.glGetUniformLocation(shaderProgram, "LETTER"), letter);
    }
}
