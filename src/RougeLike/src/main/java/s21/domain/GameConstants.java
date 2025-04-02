package s21.domain;

public class GameConstants {

    public static final int  UNINITIALIZED  = -1;

    public static final int CONNECTED = 0;
    public static final int NOT_CONNECTED = 1;
    public static final int MAP_HEIGHT = 24;
    public static final int MAP_WIDTH  = 60;
    public static final int MAX_LEVEL_NUMBER  = 21;
    public static final int ROOMS_PER_SIDE  = 3;
    public static final int SECTOR_WIDTH = MAP_WIDTH / ROOMS_PER_SIDE;
    public static final int SECTOR_HEIGHT = MAP_HEIGHT / ROOMS_PER_SIDE;
    public static final int MAX_ROOMS_NUMBER = ROOMS_PER_SIDE * ROOMS_PER_SIDE;
    public static final int MAX_CORRIDORS_NUMBER  = 12;
    public static final int CORNER_VERT_RANGE = (SECTOR_HEIGHT - 6) / 2; // для 3д графики
    public static final int CORNER_HOR_RANGE = (SECTOR_WIDTH - 6) / 2; // для 3д графики
    public static final double ROOM_CHANCE = 0.5;
    public static final double SPAWN_SET_CHANCE = 0.5;
    public static final int MAX_ENEMIES_PER_ROOM = 3;
    public static final int MAX_ITEMS_PER_ROOM = 2;
    public static final int MAX_ENTITIES_PER_ROOM = MAX_ENEMIES_PER_ROOM + MAX_ITEMS_PER_ROOM + 2;
    public static final int MAX_ENEMIES_TOTAL = MAX_ENEMIES_PER_ROOM * ROOMS_PER_SIDE * ROOMS_PER_SIDE;
    public static final int MAX_ITEMS_TOTAL = MAX_ITEMS_PER_ROOM * ROOMS_PER_SIDE * ROOMS_PER_SIDE;
    public static final int MAX_ENTITIES_TOTAL = MAX_ENEMIES_TOTAL + MAX_ITEMS_TOTAL + 2;
    public static final int ENEMY_POOL_LEN  = 26;
    public static final int ITEM_POOL_LEN  = 5;
    public static final int MAX_HEALTH  = 20;
    public static final long MAX_COUNT_TYPE_ITEM = 9;

    public static final int TOP = 0;
    public static final int RIGHT = 1;
    public static final int BOTTOM = 2;
    public static final int LEFT = 3;

    public static final int LEFT_TO_RIGHT_CORRIDOR = 0;
    public static final int LEFT_TURN_CORRIDOR = 1;
    public static final int TOP_TO_BOTTOM_CORRIDOR = 3;

    public static final int UNOCCUPIED = 0;
    public static final int OCCUPIED = 1;

    public static final int  PLAYER = 0;
    public static final int PLAYER_CHAR = '@';
    public static final int EXIT = 1;
    public static final int EXIT_CHAR  = '|';

    public static final int  WALL_CHAR = '#';
    public static final int CORRIDOR_CHAR = '+';


    public static final int  OUTER_AREA_CHAR = '.';
    public static final int  INNER_AREA_CHAR = ' ';


    //TYPE OF ENEMIES
    public static final int ZOMBIE = 3;
    public static final int ZOMBIE_CHAR = 'Z';
    public static final int VAMPIRE = 4;
    public static final int VAMPIRE_CHAR = 'V';
    public static final int GHOST = 5;
    public static final int GHOST_CHAR = 'G';
    public static final int OGRE = 6;
    public static final int OGRE_CHAR = 'O';
    public static final int SNAKE = 7;
    public static final int SNAKE_CHAR = 'S';

    //ITEMS
    public static final int GOLD = 8;
    public static final int GOLD_CHAR = '$';
    public static final int FOOD = 9;
    public static final int FOOD_CHAR = 'F';
    public static final int SCROLL = 10;
    public static final int SCROLL_CHAR = '>';
    public static final int WEAPON = 11;
    public static final int WEAPON_CHAR = '%';
    public static final int ELIXIR = 12;
    public static final int ELIXIR_CHAR = '^';

    //TYPE OF ELIXIR
    public static final int HEALTH_ELIXIR = 0;
    public static final int STRENGTH_ELIXIR = 1;
    public static final int AGILITY_ELIXIR = 2;

    //TYPE OF SCROLL
    public static final int STRENGTH_SCROLL = 0;
    public static final int AGILITY_SCROLL = 1;
    public static final int MAX_HEAL_SCROLL = 2;
    public static final int CURSED_STRENGTH_SCROLL = 3;
    public static final int CURSED_AGILITY_SCROLL = 4;

//TYPE OF WEAPON
    public static final int MACE = 0;
    public static final int LONG_SWORD = 1;
    public static final int SHORT_BOW = 2;
    public static final int  DAGGER = 3;
    public static final int TWO_HANDED_SWORD = 4;

    //PLAYER CONST
    public static final float DEFAULT_VIEW_DISTANCE = 30.0f;
    public static final float DEFAULT_FOV = 3.14159f / 3.5f;
    public static final float DEFAULT_ANGLE = 0.0f;
    public static final float MOVEMENT_STEP = 0.2f;
    public static final float TURN_ANGLE_STEP = 0.03f;



//            #define IS_OUTER    0
//            #define IS_INNER    1
//            #define IS_WALL     2

}
