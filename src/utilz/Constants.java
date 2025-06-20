package utilz;


import main.Game;

public class Constants {

    public static class EnemyConstants {
        public static final int THORNBACK = 0;

        public static final int IDLE = 0;
        public static final int RUNNING = 1;
        public static final int ATTACK = 2;
        public static final int DEAD = 3;

        public static final int THORNBACK_WIDTH_DEFAULT = 64;
        public static final int THORNBACK_HEIGHT_DEFAULT = 64;

        public static final int THORNBACK_WIDTH = (int) (THORNBACK_WIDTH_DEFAULT * Game.SCALE);
        public static final int THORNBACK_HEIGHT = (int) (THORNBACK_HEIGHT_DEFAULT * Game.SCALE);

        public static final int THORNBACK_DRAWOFFSET_X= (int) (10 * Game.SCALE);
        public static final int THORNBACK_DRAWOFFSET_Y= (int) (10 * Game.SCALE);

        public static int GetSpriteAmount(int enemy_type, int enemy_state) {
            switch (enemy_type) {
                case THORNBACK:
                    switch (enemy_state) {
                        case IDLE:
                            return 4;
                        case RUNNING:
                            return 4;
                        case ATTACK:
                            return 4;
                        case DEAD:
                            return 4;
                        default:
                            return 0;
                    }
                default:
                    return 0;
            }
        }
    }
    public static class Directions {
        public static final int LEFT = 0;
        public static final int RIGHT = 2;
    }



    public static class Environment {
        public static final int MOON_WIDTH_DEFAULT = 108;
        public static final int MOON_HEIGHT_DEFAULT = 108;

        public static final int MOON_WIDTH = (int) (MOON_WIDTH_DEFAULT * Game.SCALE);
        public static final int MOON_HEIGHT = (int) (MOON_HEIGHT_DEFAULT * Game.SCALE);

    }


    public static class UI{
        public static class Buttons{
            public static final int B_WIDTH_DEFAULT = 140;
            public static final int B_HEIGHT_DEFAULT = 56;
            public static final int B_WIDTH = (int) (B_WIDTH_DEFAULT * Game.SCALE);
            public static final int B_HEIGHT = (int) (B_HEIGHT_DEFAULT * Game.SCALE);
        }
        public static class PauseButtons{
            public static final int SOUND_SIZE_DEFAULT = 42;
            public static final int SOUND_SIZE = (int)(SOUND_SIZE_DEFAULT * Game.SCALE);
        }
        public static class URMButtons {
            public static final int URM_DEFAULT_SIZE = 56;
            public static final int URM_SIZE = (int) (URM_DEFAULT_SIZE * Game.SCALE);

        }

        public static class VolumeButtons {
            public static final int VOLUME_DEFAULT_WIDTH = 28;
            public static final int VOLUME_DEFAULT_HEIGHT = 44;
            public static final int SLIDER_DEFAULT_WIDTH = 215;

            public static final int VOLUME_WIDTH = (int) (VOLUME_DEFAULT_WIDTH * Game.SCALE);
            public static final int VOLUME_HEIGHT = (int) (VOLUME_DEFAULT_HEIGHT * Game.SCALE);
            public static final int SLIDER_WIDTH = (int) (SLIDER_DEFAULT_WIDTH * Game.SCALE);
        }

    }

    public static class Projectiles{
        public static final int BULLET_DEFAULT_WIDTH = 15;
        public static final int BULLET_DEFAULT_HEIGHT = 15;

        public static final int BULLET_WIDTH = (int)(Game.SCALE * BULLET_DEFAULT_WIDTH);
        public static final int BULLET_HEIGHT = (int)(Game.SCALE * BULLET_DEFAULT_HEIGHT);
        public static float SPEED = 2.2f * Game.SCALE;
    }

    public static class PlayerConstants {
        public static final int IDLE = 0;
        public static final int RUNNING = 1;
        public static final int ATTACK = 2;
        public static final int JUMP = 3;
        public static final int FALLING = 4;


        public static int GetSpriteAmount(int player_action) {
            switch (player_action) {
                case RUNNING:
                    return 5;
                case IDLE:
                    return 5;
                case ATTACK:
                    return 2;
                case JUMP:
                case FALLING:
                default:
                    return 1;
            }
        }
    }
}