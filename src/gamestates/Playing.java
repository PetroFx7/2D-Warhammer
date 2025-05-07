package gamestates;

import entities.Player;
import levels.LevelManager;
import main.Game;
import objects.ObjectManager;
import ui.PauseOverlay;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;


public class Playing extends State implements Statemethods {

    private Player player;
    private LevelManager levelManager;
    private ObjectManager objectManager;
    private PauseOverlay pauseOverlay;
    private boolean paused = false;

    public Playing(Game game) {
        super(game);
        initClasses();
    }


    private void initClasses() {
        levelManager = new LevelManager(game);
        player = new Player(200, 200, (int) (64 * Game.SCALE), (int) (50 * Game.SCALE));
        player.loadLvlData(levelManager.getCurrentLevel().getLvlData());
        objectManager = new ObjectManager(player,game);
        objectManager.loadLvlData(levelManager.getCurrentLevel().getLvlData());
        pauseOverlay = new PauseOverlay(this);

    }

    public void windowFocusLost() {
        player.resetDirBooleans();
    }

    public Player getPlayer() {
        return player;
    }

    public ObjectManager getObjectManager() {
        return objectManager;
    }

    @Override
    public void update() {
        if (!paused) {
        levelManager.update();
        player.update();
        objectManager.update();
        } else {
            pauseOverlay.update();
        }
    }

    @Override
    public void draw(Graphics g) {
        levelManager.draw(g);
        player.render(g);
        objectManager.draw(g);

        if (paused)
            pauseOverlay.draw(g);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("Mouse Clicked");
        if (e.getButton() == MouseEvent.BUTTON1)
            player.setAttacking(true);
    }

    public void mouseDragged(MouseEvent e){
        if(paused){
            pauseOverlay.mouseDragged(e);
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {
        objectManager.mousePressed(e);
        if (paused)
            pauseOverlay.mousePressed(e);

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (paused)
            pauseOverlay.mouseReleased(e);

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (paused)
            pauseOverlay.mouseMoved(e);
    }

    public void unpauseGame() {
        paused = false;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A:
                player.setLeft(true);
                System.out.println("A Clicked");
                break;
            case KeyEvent.VK_D:
                player.setRight(true);
                System.out.println("D Clicked");
                break;
            case KeyEvent.VK_SPACE:
                player.setJump(true);
                break;
            case KeyEvent.VK_ESCAPE:
                paused = !paused;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A:
                player.setLeft(false);
            case KeyEvent.VK_D:
                player.setRight(false);
                break;
            case KeyEvent.VK_SPACE:
                player.setJump(false);
                break;
        }
    }
}
