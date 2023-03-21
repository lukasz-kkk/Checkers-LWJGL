package org.game;

import org.lwjgl.glfw.GLFW;

import java.io.IOException;

public class Main {
    static boolean gameStopped = true;
    static WindowManager window;
    static Shader shader;
    static Background background;
    static Board board;
    static Pawn pawn;
    static MouseInput mouse;
    static TextRenderer textRenderer;
    static Game game;
    static Menu menu;

    static int gameState = 0;

    public static void main(String[] args) {
        int err = loadComponents();
        if(err != 0) return;

        // MAIN LOOP
        while (!window.windowShouldClose()) {
            shader.active();
            background.render();
            board.render();

            if(gameStopped){
                if(menu.processInput(mouse, gameState) == 1) {
                    game = new Game(shader, pawn, textRenderer);
                    gameStopped = false;
                }
                menu.render(gameState);
                window.update();
                continue;
            }

            game.processInput(mouse);
            gameState = game.processLogic();
            if(gameState != 0)
                gameStopped = true;
            game.render();
            window.update();
        }

        clean(background, board, textRenderer, pawn, menu);

        GLFW.glfwTerminate();
    }

    static void loadTextures() {
        Texture t_board       = new Texture("assets/board.png");
        Texture t_pawn_white  = new Texture("assets/pawn_white.png");
        Texture t_pawn_black  = new Texture("assets/pawn_black.png");
        Texture t_background  = new Texture("assets/background.png");
        Texture t_text        = new Texture("assets/text.png");
        Texture t_highlight   = new Texture("assets/highlight.png");
        Texture t_crown_white = new Texture("assets/crown_white.png");
        Texture t_crown_black = new Texture("assets/crown_black.png");


        t_board.bind(0);
        t_pawn_white.bind(1);
        t_pawn_black.bind(2);
        t_background.bind(3);
        t_text.bind(4);
        t_highlight.bind(5);
        t_crown_white.bind(6);
        t_crown_black.bind(7);
    }

    static int loadComponents() {
        try {
            window = new WindowManager("Checkers", 1600, 900);
            window.init();
            shader = new Shader("assets/shaders/vertex_core.glsl", "assets/shaders/fragment_core.glsl");
            background = new Background(shader);
            board = new Board(shader);
            pawn = new Pawn();
            mouse = new MouseInput(window.getWindow());
            textRenderer = new TextRenderer();
            menu = new Menu(shader, textRenderer);
            loadTextures();
        } catch (IOException e) {
            System.out.println("Failed to load component: ");
            e.printStackTrace();
        }
        return 0;
    }

    static void clean(Background background, Board board, TextRenderer textRenderer, Pawn pawn, Menu menu) {
        background.clean();
        board.clean();
        textRenderer.clean();
        pawn.clean();
    }
}