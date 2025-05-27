package main;

import gamestates.Gamestates;
import gamestates.Menu;
import gamestates.Playing;


import java.awt.*;

public class Game implements Runnable {

    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Thread gameThread;
    private final int FPS_SET = 120;
    private final int UPS_SET = 140;
    private Playing playing;
    private Menu menu;
    private static Gamestates state = Gamestates.PLAYING;



    public final static int TILES_DEFAULT_SIZE = 32;
    public final static int TILES_IN_WIDTH = 26;
    public final static int TILES_IN_HEIGHT = 14;
    public final static float SCALE = 1.7f;
    public final static int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);
    public final static int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
    public final static int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;


    public Game() {
        initClasses();
        gamePanel = new GamePanel(this);
        gameWindow = new GameWindow(gamePanel);

        gamePanel.requestFocus();
        startGameLoop();

    }

    private void initClasses() {
        menu = new Menu(this);
        playing = new Playing(this);

    }

    private void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void update() {
        switch (Gamestates.state) {
            case MENU:
                menu.update();
                break;
            case PLAYING:
                playing.update();
                break;
            case GAME_OVER:
                // гра зупинена, нічого не оновлюємо
                break;
            case OPTIONS:
            case QUIT:
            default:
                System.exit(0);
                break;
        }
    }


    public void render(Graphics g) {
        switch (Gamestates.state) {
            case MENU:
                menu.draw(g);
                break;
            case PLAYING:
                playing.draw(g);
                break;
            case GAME_OVER:
                drawGameOverScreen(g); // Малюємо екран "Game Over"
                break;
            default:
                break;
        }
    }


    @Override
    public void run() {

        double timePerFrame = 1000000000.0 / FPS_SET;
        double timePerUpdate = 1000000000.0 / UPS_SET;

        long previousTime = System.nanoTime();


        int frames = 0;
        int updates = 0;
        long lastCheck = System.currentTimeMillis();

        double deltaU = 0;
        double deltaF = 0;

        while (true) {

            long currentTime = System.nanoTime();
            deltaU += (currentTime - previousTime) / timePerUpdate;
            deltaF += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;
            if (deltaU > 1.0) {
                update();
                updates++;
                deltaU--;
            }
            if (deltaF > 1.0) {
                gamePanel.repaint();
                frames++;
                deltaF--;
            }
            if (System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS: " + frames + " | " + "UPS: " + updates);

                frames = 0;
                updates = 0;
            }
        }

    }
    public void drawGameOverScreen(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);

        g.setColor(Color.red);
        g.setFont(new Font("Arial", Font.BOLD, 60));
        g.drawString("Game Over", Game.GAME_WIDTH / 2 - 170, Game.GAME_HEIGHT / 2 - 40);
    }



    public void windowFocusLost() {
        if (Gamestates.state == Gamestates.PLAYING)
            playing.getPlayer().resetDirBooleans();
    }

    public Menu getMenu() {
        return menu;
    }

    public Playing getPlaying() {
        return playing;
    }
    public static Gamestates getGameState() {
        return state;
    }

    public static void setGameState(Gamestates newState) {
        state = newState;
    }


}