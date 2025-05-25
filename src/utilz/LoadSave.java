package utilz;

import entities.Thornback;
import main.Game;
import utilz.Constants.EnemyConstants;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class LoadSave {
    public static final String PLAYER_ATLAS = "animation-export.png";
    public static final String LEVEL_ATLAS = "Terrain (32x32)-export.png";
   // public static final String LEVEL_ONE_DATA = "level_one_data.png";
    public static final String LEVEL_ONE_DATA = "level_one_data_long.png";
    public static final String BULLET = "Bullet.png";
    public static final String MENU_BUTTONS = "button_atlas_sexx.png";
    public static final String MENU_BACKGROUND = "menu_backgroundBlue.png";
    public static final String PAUSE_BACKGROUND = "pause_menu.png";
    public static final String SOUND_BUTTONS = "sound_button.png";
    public static final String URM_BUTTONS = "urm_buttons.png";
    public static final String VOLUME_BUTTONS = "volume_buttons.png";
    public static final String PLAYING_BACKGROUND = "play_background.png";
    public static final String MOON_BACKGROUND = "red_moon.png";
    public static final String FOUR_LEGS_ENEMY = "four_legs_enemy.png";




    public static BufferedImage GetSpriteAtlas(String fileName) {
        BufferedImage img = null;
        InputStream is = LoadSave.class.getResourceAsStream("/res/" + fileName);
        try {
            img = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return img;
    }

    public static ArrayList<Thornback> GetThornB() {
        BufferedImage img = GetSpriteAtlas(LEVEL_ONE_DATA);
        ArrayList<Thornback> list = new ArrayList<>();

        for (int j = 0; j < img.getHeight(); j++) {
            for (int i = 0; i < img.getWidth(); i++) {
                Color color = new Color(img.getRGB(i, j));
                int value = color.getGreen();
                if (value == Constants.EnemyConstants.THORNBACK) {
                    int x = i * Game.TILES_SIZE;
                    int y = j * Game.TILES_SIZE - (Constants.EnemyConstants.THORNBACK_HEIGHT - Game.TILES_SIZE);
                    list.add(new Thornback(x, y));
                }
            }
        }

        return list;
    }


    public static int[][] GetLevelData() {

        BufferedImage img = GetSpriteAtlas(LEVEL_ONE_DATA);
        int[][] lvlData = new int[img.getHeight()][img.getWidth()];
        for (int i = 0; i < img.getHeight(); i++)
            for (int j = 0; j < img.getWidth(); j++) {
                Color color = new Color(img.getRGB(j, i));
                int value = color.getRed();
                if (value >= 48)
                    value = 0;
                lvlData[i][j] = value;
            }
        return lvlData;
    }
}
