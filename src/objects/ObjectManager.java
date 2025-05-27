package objects;

import entities.EnemyManager;
import entities.Player;
import main.Game;
import static utilz.Constants.Projectiles.*;


import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;


public class ObjectManager {
    private EnemyManager enemyManager;
    private Player player;
    private Game game;
    private ArrayList<Projectile> projectiles = new ArrayList<>();
    private int[][] lvlData;


    public ObjectManager(Player player, Game game,EnemyManager enemyManager) {
        this.player = player;
        this.game = game;
        this.enemyManager = enemyManager;
    }

    public void loadLvlData(int[][] lvlData) {
        this.lvlData = lvlData;
    }

    public void mousePressed(MouseEvent e) {

        if (e.getButton() == MouseEvent.BUTTON1) {
            float bulletX;
            float bulletY = player.getHitbox().y + player.getHitbox().height / 3.43f;
            int dir = player.isLeft() ? -1 : 1;

            if (dir == 1) {
                bulletX = player.getHitbox().x + player.getHitbox().width * 2;
            } else {
                bulletX = player.getHitbox().x - BULLET_WIDTH;
            }

            projectiles.add(new Projectile((int) bulletX, (int) bulletY, dir));
        }
    }


    public void update() {
        for (Projectile p : projectiles) {
            if (p.isActive()) {
                p.updatePos();

                float x = p.getHitBox().x;
                float y = p.getHitBox().y;

                int levelWidth = lvlData[0].length * Game.TILES_SIZE;
                if (x < 0 || x > levelWidth)
                    p.setActive(false);


                if (!utilz.HelpMethods.CanMoveHere(
                        x, y,
                        p.getHitBox().width,
                        p.getHitBox().height,
                        lvlData))
                    p.setActive(false);
            }
        }
        enemyManager.checkProjectileHit(projectiles);

        // Видалити неактивні кулі
        projectiles.removeIf(p -> !p.isActive());
    }

    public void draw(Graphics g, int xLvlOffset) {
        for (Projectile p : projectiles) {
            if (p.isActive()) {
                p.draw(g, xLvlOffset);
            }
        }
    }
    public void drawHealthBar(Graphics g) {
        int barWidth = 100;
        int barHeight = 10;
        int healthBarX = 20;
        int healthBarY = 20;

        int currentHealth = player.getCurrentHealth();
        int maxHealth = player.getMaxHealth();

        g.setColor(Color.red);
        g.fillRect(healthBarX, healthBarY, barWidth, barHeight);

        g.setColor(Color.green);
        int greenWidth = (int) ((currentHealth / (float) maxHealth) * barWidth);
        g.fillRect(healthBarX, healthBarY, greenWidth, barHeight);

        g.setColor(Color.black);
        g.drawRect(healthBarX, healthBarY, barWidth, barHeight);
    }


}