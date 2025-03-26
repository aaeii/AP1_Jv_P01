package s21.domain;

import static s21.domain.GameConstants.*;

public class Enemy {
    public static final int VERY_HIGH_LVL = 100;
    public static final int HIGH_LVL = 75;
    public static final int MEDIUM_LVL = 50;
    public static final int LOW_LVL = 25;
    private int health;
    private int agility;
    private int strength;
    private int hostility;
    private int type;
    private int symbol;
    Position position;

    public Enemy(int type) {
        this.type = type;
        switch (type) {
            case ZOMBIE:
                this.health = HIGH_LVL;
                this.agility = LOW_LVL;
                this.strength = MEDIUM_LVL;
                this.hostility = MEDIUM_LVL;
                break;
            case VAMPIRE:
                this.health = HIGH_LVL;
                this.agility = HIGH_LVL;
                this.strength = MEDIUM_LVL;
                this.hostility = HIGH_LVL;
                break;
            case GHOST:
                this.health = LOW_LVL;
                this.agility = HIGH_LVL;
                this.strength = LOW_LVL;
                this.hostility = LOW_LVL;
                break;
            case OGRE:
            this.health = VERY_HIGH_LVL;
                this.agility = LOW_LVL;
                this.strength = VERY_HIGH_LVL;
                this.hostility = MEDIUM_LVL;
                break;
            case SNAKE:
                this.health = LOW_LVL;//?
                this.agility = VERY_HIGH_LVL;
                this.strength = LOW_LVL;//?
                this.hostility = HIGH_LVL;
                break;
            default:
                break;
        }
    }

    public char getDisplayChar() {
        switch (type) {
            case ZOMBIE:
                return ZOMBIE_CHAR;
            case VAMPIRE:
                return VAMPIRE_CHAR;
            case GHOST:
                return GHOST_CHAR;
            case OGRE:
                return OGRE_CHAR;
            case SNAKE:
                return SNAKE_CHAR;
            default:
                return '?';
        }
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setSymbol(int symbol) {
        this.symbol = symbol;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public int getType() {
        return type;
    }

    public Position getPosition() {
        return position;
    }

    public int getSymbol() {
        return symbol;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getAgility() {
        return agility;
    }


    public int getStrength() {

        return strength;
    }

    public int getHostility() {
        return hostility;
    }

    public void attack(Character character) {
        switch (type) {
            case VAMPIRE:
                return;
            case GHOST:
                break;
            case OGRE:
                break;
            case SNAKE:
                break;
            default:
                break;
        }
    }

    public Position generate_entity_coords(Room room){
        Position pos  = new Position();
        do {
            int x = (int) ((Math.random() * (room.getBot_right().getX() - room.getTop_left().getX() - 1)) + room.getTop_left().getX() + 1);
            int y = (int) ((Math.random() * (room.getBot_right().getY() - room.getTop_left().getY() - 1)) + room.getTop_left().getY() + 1);
            pos.setNew(x,y);
        }
        while (check_unoccupied(room, pos) == OCCUPIED);
        return pos;
    }

    private int check_unoccupied(Room room, Position pos)
    {
        int status = UNOCCUPIED;

        for (int i = 0; i < room.getEnemies_cnt() && status == UNOCCUPIED; i++)
            if (room.getEnemies(i).position.getX() == pos.getX() && room.getEnemies(i).position.getY() == pos.getY())
                status = OCCUPIED;

        return status;
    }

}


