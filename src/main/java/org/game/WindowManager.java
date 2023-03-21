package org.game;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.system.MemoryUtil;


public class WindowManager {

    private String title;
    private int width, height;
    private long window;

    private boolean resize;

    public WindowManager(String title, int width, int height){
        this.title = title;
        this.width = width;
        this.height = height;
    }

    public void init(){
        GLFWErrorCallback.createPrint(System.err).set();
        if(!GLFW.glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");

        boolean maximised = false;
        if(width == 0 || height == 0){
            width = 100;
            height = 100;
            GLFW.glfwWindowHint(GLFW.GLFW_MAXIMIZED, GLFW.GLFW_TRUE);
            maximised = true;
        }

        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_STENCIL_BITS, 8);
        GLFW.glfwWindowHint(GLFW.GLFW_SAMPLES, 8);
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GL11.GL_FALSE);

        window = GLFW.glfwCreateWindow(width, height, title, 0, 0);
        if(window == MemoryUtil.NULL)
            throw new RuntimeException("Failed to create GLFW window");


        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE);
        GLFW.glfwShowWindow(window);
        GLFW.glfwMakeContextCurrent(window);
        GL.createCapabilities();
        GL11.glClearColor(0.4f, 0.4f, 0.4f, 0.0f);

        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glEnable(GL13.GL_MULTISAMPLE);
    }

    public void update(){
        GLFW.glfwSwapBuffers(window);
        GLFW.glfwPollEvents();
    }

    public void cleanup(){
        GLFW.glfwDestroyWindow(window);
    }

    public void setClearColor(float r, float g, float b, float a){
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
        GL11.glClearColor(r, g, b, a);
    }

    public boolean windowShouldClose(){
        return GLFW.glfwWindowShouldClose(window);
    }

    public boolean isResize() {
        return resize;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setResize(boolean resize) {
        this.resize = resize;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public long getWindow() {
        return window;
    }
}
