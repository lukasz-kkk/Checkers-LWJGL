package org.game;

import org.lwjgl.glfw.GLFW;

public class MouseInput {
    private long window;
    private boolean leftButtonPressed = false;
    private boolean prevMousePressed = false;
    private double mouseX = -1, mouseY = -1;

    public MouseInput(long window) {
        this.window = window;

        GLFW.glfwSetMouseButtonCallback(window, (windowHandle, button, action, mods) -> {
            if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
                if (action == GLFW.GLFW_PRESS) {
                    leftButtonPressed = true;
                } else if (action == GLFW.GLFW_RELEASE) {
                    leftButtonPressed = false;
                }
            }
        });

        GLFW.glfwSetCursorPosCallback(window, (windowHandle, xpos, ypos) -> {
            int[] windowWidth = new int[1];
            int[] windowHeight = new int[1];
            GLFW.glfwGetWindowSize(windowHandle, windowWidth, windowHeight);
            double relativeX = xpos / windowWidth[0];
            double relativeY = ypos / windowHeight[0];

            mouseX = relativeX;
            mouseY = relativeY;
        });

        GLFW.glfwSetWindowSizeCallback(window, (windowHandle, width, height) -> {
            double relativeX = mouseX * width;
            double relativeY = mouseY * height;

            mouseX = relativeX;
            mouseY = relativeY;
        });
    }

    public boolean isLeftButtonPressed() {
        boolean isPressed = leftButtonPressed && !prevMousePressed;
        prevMousePressed = leftButtonPressed;
        return isPressed;
    }

    public double getMouseX() {
        return mouseX;
    }

    public double getMouseY() {
        return mouseY;
    }
}
