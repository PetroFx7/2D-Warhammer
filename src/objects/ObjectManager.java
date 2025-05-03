package objects;

import entities.Player;
import main.Game;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;


public class ObjectManager {
    private Player player;
    private Game game;
    private ArrayList<Projectile> projectiles = new ArrayList<>();
    private int[][] lvlData;


    public ObjectManager(Player player, Game game) {
        this.player = player;
        this.game = game;
    }

    public void loadLvlData(int[][] lvlData) {
        this.lvlData = lvlData;
    }

    public void mousePressed(MouseEvent e) {
        System.out.println("ObjectManager: mousePressed");
        if (e.getButton() == MouseEvent.BUTTON1) {
            float bulletX = player.getHitbox().x + player.getHitbox().width * 2;
            float bulletY = player.getHitbox().y + player.getHitbox().height / 5;
            int dir = 1;

            if (player.isLeft())
                dir = -1;
            else if (player.isRight())
                dir = 1;

            projectiles.add(new Projectile((int) bulletX, (int) bulletY, dir));
            System.out.println("Bullet created at: " + bulletX + ", " + bulletY);
        }
    }

    public void update() {
        for (Projectile p : projectiles) {
            if (p.isActive()) {
                p.updatePos();

                float x = p.getHitBox().x;
                float y = p.getHitBox().y;

                if (x < 0 || x > game.GAME_WIDTH)
                    p.setActive(false);

                if (!utilz.HelpMethods.CanMoveHere(
                        x, y,
                        p.getHitBox().width,
                        p.getHitBox().height,
                        lvlData))
                    p.setActive(false);
            }
        }

        // Видалити неактивні кулі
        projectiles.removeIf(p -> !p.isActive());
    }

    public void draw(Graphics g) {
        for (Projectile p : projectiles) {
            if (p.isActive()) {
                p.draw(g);
            }
        }

    }


}
