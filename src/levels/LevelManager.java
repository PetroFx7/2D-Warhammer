package levels;

import main.Game;
import utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static main.Game.TILES_SIZE;

public class LevelManager {

    private Game game;
    private BufferedImage[] levelSprite;
    private Level levelOne;

    public LevelManager(Game game) {
        this.game = game;
        importOutsideSprites();
        levelOne = new Level(LoadSave.GetLevelData());
    }

    private void importOutsideSprites() {
        BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_ATLAS);
        levelSprite = new BufferedImage[48];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 12; j++) {
                int index = i * 12 + j;
                levelSprite[index] = img.getSubimage(j * 32, i * 32, 32, 32);
            }
        }
    }

    public void draw(Graphics g, int lvlOffset) {
        int tilesWide = levelOne.getLvlData()[0].length;

        int start = lvlOffset / TILES_SIZE;
        int end = start + Game.TILES_IN_WIDTH + 1;

        if (end > tilesWide) end = tilesWide;

        for (int i = 0; i < Game.TILES_IN_HEIGHT; i++) {
            for (int j = start; j < end; j++) {
                int index = levelOne.getSpriteIndex(j, i);
                g.drawImage(levelSprite[index], j * TILES_SIZE - lvlOffset, i * TILES_SIZE, TILES_SIZE, TILES_SIZE, null);
            }
        }
    }


    public void update() {
    }
    public Level getCurrentLevel() {
        return levelOne;
    }
}
