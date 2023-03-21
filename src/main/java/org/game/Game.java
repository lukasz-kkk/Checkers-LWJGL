package org.game;

import org.lwjgl.glfw.GLFW;

public class Game {
    private final int[][] stateMap = new int[8][8];

    private int fieldSelectedY;
    private int fieldSelectedX;
    boolean fieldSelected = false;

    private int pawnSelectedY;
    private int pawnSelectedX;
    private boolean pawnSelected = false;

    boolean whiteMove = true;
    boolean blackMove = false;

    boolean shouldCapture = false;
    boolean enemyCapture = false;

    private static final int WHITE = 1;
    private static final int BLACK = 2;
    private static final int CROWN_WHITE = 6;
    private static final int CROWN_BLACK = 7;

    private int numOfBlacksCanMove = 8, numOfWhitesCanMove = 8;


    private int whiteTime = 0, blackTime = 0;
    private int prevTime = 0;

    Shader shader;
    Pawn pawn;
    TextRenderer textRenderer;

    public Game(Shader shader, Pawn pawn, TextRenderer textRenderer) {
        this.shader = shader;
        this.pawn = pawn;
        this.textRenderer = textRenderer;
        init();
    }

    private void init() {
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                stateMap[y][x] = 0;
                if ((y % 2 == 0 && x % 2 == 1)
                        || (y % 2 == 1 && x % 2 == 0)) {
                    if (y < 3) stateMap[y][x] = WHITE;
                    if (y > 4) stateMap[y][x] = BLACK;

                }
            }
        }
    }

    public void render() {
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                if (stateMap[y][x] != 0)
                    pawn.render(shader, x, y, stateMap[y][x]);
            }
        }
        pawn.render(shader, -5, 4, WHITE);
        pawn.render(shader, -4, 4, CROWN_WHITE);
        pawn.render(shader, 11, 4, BLACK);
        pawn.render(shader, 12, 4, CROWN_BLACK);
        printTime();
    }

    private void printTime() {
        textRenderer.render(shader, 6, 29, "PLAYER 1");
        textRenderer.render(shader, 8, 29, "GAME TIME:");
        textRenderer.render(shader, 10, 29, getWhiteTime());

        textRenderer.render(shader, 6, -17, "PLAYER 2");
        textRenderer.render(shader, 8, -17, "GAME TIME:");
        textRenderer.render(shader, 10, -17, getBlackTime());
    }

    public int processLogic() {
        mapStateUpdate();
        shouldCapture = true;
        double time = GLFW.glfwGetTime();
        if ((int) time != prevTime) {
            prevTime = (int) time;
            addSecond();
        }

        if (!fieldSelected || !pawnSelected) return 0; // nothing to do

        int pawnOnMove = stateMap[pawnSelectedY][pawnSelectedX];
        if (!canMoveValidation(pawnSelectedY, pawnSelectedX, fieldSelectedY, fieldSelectedX, pawnOnMove))
            fieldSelected = false;

        if (!fieldSelected || !pawnSelected) return 0; // nothing to do
        changePosition(pawnSelectedY, pawnSelectedX, fieldSelectedY, fieldSelectedX);
        mapStateUpdate();

        System.out.println(numOfWhitesCanMove);
        if (numOfWhitesCanMove == 0) return 2;
        if (numOfBlacksCanMove == 0) return 1;

        return 0;
    }

    private void changePosition(int prevY, int prevX, int newY, int newX) {
        stateMap[newY][newX] = stateMap[prevY][prevX];
        stateMap[prevY][prevX] = 0;
        fieldSelected = false;
        shouldCapture = false;

        if (enemyCapture) {
            enemyCapture = false;
            pawnSelectedY = newY;
            pawnSelectedX = newX;
            return;
        }

        pawnSelected = false;

        whiteMove = !whiteMove;
        blackMove = !blackMove;
    }


    private boolean canMoveValidation(int prevY, int prevX, int newY, int newX, int color) {
        if (newY < 0 || newY > 7 || newX < 0 || newX > 7) return false;
        if (color == WHITE) {
            if (newY - prevY == 1 && Math.abs(newX - prevX) == 1 && stateMap[newY][newX] == 0)
                return true;
            if ((newY - prevY == 2 || newY - prevY == -2) && Math.abs(newX - prevX) == 2 && stateMap[newY][newX] == 0 && enemyBetween(prevY, prevX, newY, newX, color)) {
                return true;
            }
        }
        if (color == BLACK) {
            if (newY - prevY == -1 && Math.abs(newX - prevX) == 1 && stateMap[newY][newX] == 0)
                return true;
            if ((newY - prevY == 2 || newY - prevY == -2) && Math.abs(newX - prevX) == 2 && stateMap[newY][newX] == 0 && enemyBetween(prevY, prevX, newY, newX, color))
                return true;
        }
        if (color == CROWN_WHITE || color == CROWN_BLACK) {
            if (Math.abs(newY - prevY) == 1 && Math.abs(newX - prevX) == 1 && stateMap[newY][newX] == 0)
                return true;
            if ((newY - prevY == 2 || newY - prevY == -2) && Math.abs(newX - prevX) == 2 && stateMap[newY][newX] == 0 && enemyBetween(prevY, prevX, newY, newX, color))
                return true;
        }
        return false;
    }

    private boolean enemyBetween(int playerY, int playerX, int newY, int newX, int color) {
        int target_1, target_2;
        if (color == BLACK || color == CROWN_BLACK) {
            target_1 = WHITE;
            target_2 = CROWN_WHITE;
        } else {
            target_1 = BLACK;
            target_2 = CROWN_BLACK;
        }
        if (stateMap[playerY - (playerY - newY) / 2][playerX - (playerX - newX) / 2] == target_1
                || stateMap[playerY - (playerY - newY) / 2][playerX - (playerX - newX) / 2] == target_2) {
            // capture enemy pawn
            if (shouldCapture) {
                stateMap[newY][newX] = stateMap[playerY][playerX];
                stateMap[playerY - (playerY - newY) / 2][playerX - (playerX - newX) / 2] = 0;
                if (pawnCanCapture(newY, newX)) enemyCapture = true;
            }
            return true;
        }

        return false;
    }

    private void mapStateUpdate() {
        shouldCapture = false;
        numOfBlacksCanMove = 0;
        numOfWhitesCanMove = 0;
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                if (stateMap[y][x] == WHITE && y == 7)
                    stateMap[y][x] = CROWN_WHITE;
                if (stateMap[y][x] == BLACK && y == 0)
                    stateMap[y][x] = CROWN_BLACK;
                if (stateMap[y][x] != 0)
                    pawnCanMove(y, x);
            }
        }
        shouldCapture = true;
    }

    private void pawnCanMove(int y, int x) {
        boolean canMove = false;
        for (int i = y - 2; i <= y + 2; i++) {
            for (int j = x - 2; j <= x + 2; j++) {
                if (canMoveValidation(y, x, i, j, stateMap[y][x])) {
                    if (stateMap[y][x] == WHITE || stateMap[y][x] == CROWN_WHITE) {
                        numOfWhitesCanMove++;
                        if (whiteMove)
                            pawn.render(shader, x, y, 5); // Marking
                    }
                    if (stateMap[y][x] == BLACK || stateMap[y][x] == CROWN_BLACK) {
                        numOfBlacksCanMove++;
                        if (blackMove)
                            pawn.render(shader, x, y, 5); // Marking
                    }
                    canMove = true;
                    break;
                }
                if (canMove) break;
            }
        }
    }

    private boolean pawnCanCapture(int y, int x) {
        shouldCapture = false;
        for (int i = y - 2; i <= y + 2; i += 4) {
            for (int j = x - 2; j <= x + 2; j += 4) {
                System.out.println("y: " + y + " x: " + x + " i: " + i + " j: " + j);
                if (canMoveValidation(y, x, i, j, stateMap[y][x])) {
                    return true;
                }
            }
        }
        shouldCapture = true;
        return false;
    }

    private void addSecond() {
        if (whiteMove) whiteTime++;
        if (blackMove) blackTime++;
    }

    private String getWhiteTime() {
        int minutes = whiteTime / 60;
        int seconds = whiteTime % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    private String getBlackTime() {
        int minutes = blackTime / 60;
        int seconds = blackTime % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    public void processInput(MouseInput mouse) {
        if (!mouse.isLeftButtonPressed()) return;
        double mouseY = mouse.getMouseY();
        double mouseX = mouse.getMouseX();
        double offsetX = 0.053;
        double offsetY = 0.09;
        double baseY = 0.14;
        for (int y = 0; y < 8; y++) {
            double baseX = 0.288;
            for (int x = 0; x < 8; x++) {
                if ((mouseX >= baseX && mouseX <= baseX + offsetX)
                        && (mouseY >= baseY && mouseY <= baseY + offsetY)) {
                    if (stateMap[y][x] != 0) {
                        if ((whiteMove && (stateMap[y][x] == WHITE || stateMap[y][x] == CROWN_WHITE))
                                || blackMove && (stateMap[y][x] == BLACK || stateMap[y][x] == CROWN_BLACK)) {
                            pawnSelected = true;
                            pawnSelectedY = y;
                            pawnSelectedX = x;
                        }
                    } else {
                        if (pawnSelected) {
                            fieldSelectedY = y;
                            fieldSelectedX = x;
                            fieldSelected = true;
                        }
                    }
                    return;
                }
                baseX += offsetX;
            }
            baseY += offsetY;
        }
    }
}


