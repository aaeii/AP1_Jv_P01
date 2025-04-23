package s21.domain;

import s21.domain.Entity;
import s21.domain.Position;
import s21.domain.items.*;

import java.util.List;
import java.util.Random;

import static s21.domain.GameConstants.*;

public class Enemy extends Entity {
    public static final int VERY_HIGH_LVL = 40;
    public static final int HIGH_LVL = 30;
    public static final int MEDIUM_LVL = 20;
    public static final int LOW_LVL = 10;
    private int health;
    private int agility;
    private int strength;
    private int hostility;

    public Enemy() {
        super();
        setType(UNINITIALIZED);
                this.health = 0;
                this.agility = 0;
                this.strength = 0;
                this.hostility = 0;
                setSymbol(' ');
        }

    public Enemy(int type, int level) {
        super();
        this.setType(type);
        switch (type) {
            case ZOMBIE:
                this.health = HIGH_LVL * (level + 1)/2;
                this.agility = LOW_LVL;
                this.strength = MEDIUM_LVL * (level + 1)/2;
                this.hostility = MEDIUM_LVL;
                setSymbol(ZOMBIE_CHAR);
                break;
            case VAMPIRE:
                this.health = HIGH_LVL * (level + 1)/2;
                this.agility = HIGH_LVL;
                this.strength = MEDIUM_LVL * (level + 1)/2;
                this.hostility = HIGH_LVL;
                setSymbol(VAMPIRE_CHAR);
                break;
            case GHOST:
                this.health = LOW_LVL * (level + 1)/2;
                this.agility = HIGH_LVL;
                this.strength = LOW_LVL * (level + 1)/2;
                this.hostility = LOW_LVL;
                setSymbol(GHOST_CHAR);
                break;
            case OGRE:
                this.health = VERY_HIGH_LVL * (level + 1)/2;
                this.agility = LOW_LVL;
                this.strength = VERY_HIGH_LVL * (level + 1)/2 - 10;
                this.hostility = MEDIUM_LVL;
                setSymbol(OGRE_CHAR);
                break;
            case SNAKE:
                this.health = LOW_LVL * (level + 1)/2;
                this.agility = VERY_HIGH_LVL;
                this.strength = LOW_LVL * (level + 1)/2;
                this.hostility = HIGH_LVL;
                setSymbol(SNAKE_CHAR);
                break;
            case MIMIK:
                this.health = HIGH_LVL * (level + 1)/2;
                this.agility = HIGH_LVL;
                this.strength = LOW_LVL * (level + 1)/2;
                this.hostility = LOW_LVL;
                setSymbol(mimikType());
                break;
            default:
                break;
        }
    }

    public int mimikType() {
        int item_type = (int) (Math.random() * (double) (ELIXIR - GOLD + 1) + GOLD);
        int mimik_symbol = ' ';
        switch (item_type) {
            case GOLD -> mimik_symbol = GOLD_CHAR;
            case FOOD -> mimik_symbol = FOOD_CHAR;
            case WEAPON -> mimik_symbol = WEAPON_CHAR;
            case SCROLL -> mimik_symbol = SCROLL_CHAR;
            case ELIXIR -> mimik_symbol = ELIXIR_CHAR;
        };
        return mimik_symbol;
    }

    public int getHealth() {
        return health;
    }

 @Override
 public void setHealth(int health) {
     this.health = Math.max(health, 0);
 }

    public int getAgility() {
        return agility;
    }

    @Override
    public int getStrength() {
        return strength;
    }

    public int getHostility() {
        return hostility;
    }

    private boolean isValidMove(int x, int y, Room room) {
        return x > room.getTop_left().getX() && x < room.getBot_right().getX()
                && y > room.getTop_left().getY() && y < room.getBot_right().getY()
                && Position.check_unoccupied(room, new Position(x, y, false)) == UNOCCUPIED;
    }

    private int initialDirection = TOP;

    private int getDistanceForPursuit(int hostilityLevel) {
        return hostilityLevel / 10;
    }

    /**
     * Движение по диагонали
     */
    public void moveSnake(Position playerPosition, Room room, int hostilityLevel) {
        int x = getPosition().getX();
        int y = getPosition().getY();
        int newX = x, newY = y;
        int distanceToPlayerX = Math.abs(x - playerPosition.getX());
        int distanceToPlayerY = Math.abs(y - playerPosition.getY());
        int distanceForPursuit = getDistanceForPursuit(hostilityLevel);
        Random random = new Random();
        int randomNumber = 0;
        if (distanceToPlayerX <= distanceForPursuit && distanceToPlayerY <= distanceForPursuit) {
            moveToPlayer(playerPosition, room, x, y, 3,false);
        } else {
            for (int i = 0; i < 4; i++) {
                switch (initialDirection) {
                    case TOP:
                        randomNumber = random.nextInt(1 + 1);
                        y = y - 1;
                        x = randomNumber == 0 ? x + 1 : x - 1;
                        break;
                    case RIGHT:
                        x = x + 1;
                        randomNumber = random.nextInt(1 + 1);
                        y = randomNumber == 0 ? y + 1 : y - 1;
                        break;
                    case BOTTOM:
                        y = y + 1;
                        randomNumber = random.nextInt(1 + 1);
                        x = randomNumber == 0 ? x + 1 : x - 1;
                        break;
                    case LEFT:
                        randomNumber = random.nextInt(1 + 1);
                        x = x - 1;
                        y = randomNumber == 0 ? y + 1 : y - 1;
                        break;
                    default:
                        break;
                }
                if (isValidMove(x, y, room)) {
                    getPosition().setNew(x, y, false);
                } else {
                    initialDirection = (initialDirection + 1) % 4;
                }
            }
        }
    }


    /**
     * Движение по вверх-вниз
     */
    public void moveZombie(Position playerPosition, Room room, int hostilityLevel) {
        int x = getPosition().getX();
        int y = getPosition().getY();
        int newX = x, newY = y;
        int distanceToPlayerX = Math.abs(x - playerPosition.getX());
        int distanceToPlayerY = Math.abs(y - playerPosition.getY());
        int distanceForPursuit = getDistanceForPursuit(hostilityLevel);
        if (distanceToPlayerX <= distanceForPursuit && distanceToPlayerY <= distanceForPursuit) {
            moveToPlayer(playerPosition,room, newX, newY, 2,false);
        } else {
            for (int i = 0; i < 4; i++) {
                switch (initialDirection) {
                    case TOP:
                        newY = y - 1;
                        break;
                    case BOTTOM:
                        newY = y + 1;
                        break;
                    default:
                        break;
                }
                if (isValidMove(newX, newY, room)) {
                    getPosition().setNew(newX, newY, false);
                } else {
                    initialDirection = (initialDirection + 2) % 4;
                }
            }
        }
    }

    /**
     * Движение по кругу
     */
    public void moveOgre(Position playerPosition, Room room, int hostilityLevel) {
        int x = getPosition().getX();
        int y = getPosition().getY();
        int distanceToPlayerX = Math.abs(x - playerPosition.getX());
        int distanceToPlayerY = Math.abs(y - playerPosition.getY());
        int distanceForPursuit = getDistanceForPursuit(hostilityLevel);
        if (distanceToPlayerX <= distanceForPursuit && distanceToPlayerY <= distanceForPursuit) {
            moveToPlayer(playerPosition, room, x, y, 2,false);
        } else {
            int newX = UNINITIALIZED, newY = UNINITIALIZED;
            for (int i = 0; i < 4; i++) {
                switch (initialDirection) {
                    case TOP:
                        newX = x;
                        newY = y - 2;
                        break;
                    case RIGHT:
                        newX = x + 2;
                        newY = y;
                        break;
                    case BOTTOM:
                        newX = x;
                        newY = y + 2;
                        break;
                    case LEFT:
                        newX = x - 2;
                        newY = y;
                        break;
                    default:
                        break;
                }
                if (isValidMove(newX, newY, room)) {
                    getPosition().setNew(newX, newY, false);
                } else {
                    initialDirection = (initialDirection + 1) % 4;
                }
            }
        }
    }

    /**
     * Рандомное передвижение
     */
    public void moveGhost(Position playerPosition, Room room, int hostilityLevel) {
        int x = getPosition().getX();
        int y = getPosition().getY();
        int newX = x, newY = y;
        Random random = new Random();
        boolean visible = Math.random() > 0.6;
        int distanceToPlayer = Math.abs(x - playerPosition.getX()) + Math.abs(y - playerPosition.getY());
        int distanceForPursuit = getDistanceForPursuit(hostilityLevel);
        if (distanceToPlayer <= distanceForPursuit) {
            visible = true;
            moveToPlayer(playerPosition,room, x, y, 2,visible);
        } else {
        for (int i = 0; i < 2; i++) {
                int direction = random.nextInt(4);
                switch (direction) {
                    case TOP:
                        newY = y - 1;
                        newX = x;
                        break;
                    case RIGHT:
                        newX = x + 1;
                        newY = y;
                        break;
                    case BOTTOM:
                        newY = y + 1;
                        newX = x;
                        break;
                    case LEFT:
                        newX = x - 1;
                        newY = y;
                        break;
                    default:
                        break;
                }
            if (isValidMove(newX, newY, room)) {
                getPosition().setNew(newX, newY, visible);
                }
            }
        }
    }

    public void moveVampire(Position playerPosition, Room room, int hostilityLevel) {
        int x = getPosition().getX();
        int y = getPosition().getY();
        int distanceToPlayerX = Math.abs(x - playerPosition.getX());
        int distanceToPlayerY = Math.abs(y - playerPosition.getY());
        int distanceForPursuit = getDistanceForPursuit(hostilityLevel);
        if (distanceToPlayerX <= distanceForPursuit && distanceToPlayerY <= distanceForPursuit)
        {
            moveToPlayer(playerPosition, room, x, y, 2,true);
        }
    }


    @Override
    public void action(Character player, Room room){
        switch (getType()){
            case ZOMBIE -> moveZombie( player.getPosition(), room, getHostility());
            case GHOST -> moveGhost( player.getPosition(), room, getHostility());
            case OGRE -> moveOgre( player.getPosition(), room, getHostility());
            case VAMPIRE -> moveVampire( player.getPosition(), room, getHostility());
            case SNAKE -> moveSnake( player.getPosition(), room, getHostility());
        }
    }

    @Override
    public String toString(){
        return  "enemy" + getType() + " " + getStrength() + "HP" + getHealth();
    }


    private void moveToPlayer(Position player, Room room, int x, int y, int step, boolean visible) {
            if (x < player.getX()) {
                x = x + step;
            } else if (x > player.getX()) {
                x = x - step;
            } else if (y < player.getY()) {
                y = y + step;
            } else if (y > player.getY()) {
                y = y - step;
            }
        if (isValidMove(x, y, room)) {
            getPosition().setX(x);
            getPosition().setY(y);
        }

    }

}



