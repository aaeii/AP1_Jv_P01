package s21.domain;

import java.util.List;

import static s21.domain.GameConstants.*;

public class Character {

    Position position;
    private int maxHealth;
    private int health;
    private int agility;
    private int strength;
    private int gold;
    private boolean sleep;
    private int eatenFoodCounter;
    private int drunkElixirCounter;
    private int attackCounter;
    private int enemiesAttackCounter;

    Character(Position pos) {
        this.position = pos;
        this.maxHealth = 20;
        this.health = maxHealth;
        this.agility = 2;
        this.strength = 2;
        this.gold = 0;
        this.sleep = false;
        this.eatenFoodCounter = 0;
        this.drunkElixirCounter = 0;
        this.attackCounter = 0;
    }


    public Position getPosition() {
        return position;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getHealth() {
        return health;
    }

    public int getAgility() {
        return agility;
    }

    public int getStrength() {
        return strength;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setMaxHealth(int value) {
        this.maxHealth = this.maxHealth + value;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setAgility(int value) {
        this.agility = value;
    }

    public void setStrength(int value) {
        this.strength = this.strength + value;
    }

    public int getGold() {
        return gold;
    }

    public void move(char[][] field, int direction, Level level) {
        int x = position.getX();
        int y = position.getY();
        switch (direction) {
            case (TOP):
                if (field[y - 1][x] != WALL_CHAR && field[y - 1][x] != OUTER_AREA_CHAR)
                    position.setNew(x, y - 1, true);
                break;
            case (RIGHT):
                if (field[y][x + 1] != WALL_CHAR && field[y][x + 1] != OUTER_AREA_CHAR)
                    position.setNew(x + 1, y, true);
                break;
            case (BOTTOM):
                if (field[y + 1][x] != WALL_CHAR && field[y + 1][x] != OUTER_AREA_CHAR)
                    position.setNew(x, y + 1, true);
                break;
            case (LEFT):
                if (field[y][x - 1] != WALL_CHAR && field[y][x - 1] != OUTER_AREA_CHAR)
                    position.setNew(x - 1, y, true);
                break;
        }
    }

}
