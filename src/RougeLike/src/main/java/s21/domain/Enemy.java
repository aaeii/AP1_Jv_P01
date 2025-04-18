package s21.domain;

import s21.domain.Entity;
import s21.domain.Position;

import java.util.List;
import java.util.Random;

import static s21.domain.GameConstants.*;

public class Enemy extends Entity {
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

    public Enemy(int type) {
        super();
        this.setType(type);
        switch (type) {
            case ZOMBIE:
                this.health = HIGH_LVL;
                this.agility = LOW_LVL;
                this.strength = MEDIUM_LVL;
                this.hostility = MEDIUM_LVL;
                setSymbol(ZOMBIE_CHAR);
                break;
            case VAMPIRE:
                this.health = HIGH_LVL;
                this.agility = HIGH_LVL;
                this.strength = MEDIUM_LVL;
                this.hostility = HIGH_LVL;
                setSymbol(VAMPIRE_CHAR);
                break;
            case GHOST:
                this.health = LOW_LVL;
                this.agility = HIGH_LVL;
                this.strength = LOW_LVL;
                this.hostility = LOW_LVL;
                setSymbol(GHOST_CHAR);
                break;
            case OGRE:
                this.health = VERY_HIGH_LVL;
                this.agility = LOW_LVL;
                this.strength = VERY_HIGH_LVL;
                this.hostility = MEDIUM_LVL;
                setSymbol(OGRE_CHAR);
                break;
            case SNAKE:
                this.health = LOW_LVL;
                this.agility = VERY_HIGH_LVL;
                this.strength = LOW_LVL;
                this.hostility = HIGH_LVL;
                setSymbol(SNAKE_CHAR);
                break;
            default:
                break;
        }
    }

    public int getHealth() {
        return health;
    }

    @Override
    public void setHealth(int health) {
        this.health = health;
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
        boolean result = false;
//        if (x > room.getTop_left().getX() && x < room.getBot_right().getX()
//                && y > room.getTop_left().getY() && y < room.getBot_right().getY()
//                && Position.check_unoccupied(room, new Position(x, y, false)) == UNOCCUPIED) {
//            result = true;
//        }
        if (x > room.getTop_left().getX() && x < room.getBot_right().getX()
                && y > room.getTop_left().getY() && y < room.getBot_right().getY()) {
            result = true;
        }
        return result;
    }

    private int initialDirection = TOP;

    private int getDistanceForPursuit(int hostilityLevel) {
        return switch (hostilityLevel) {
            case HIGH_LVL -> 3;
            case MEDIUM_LVL -> 2;
            case LOW_LVL -> 1;
            default -> 0;
        };
    }


    public void moveSnake(Position playerPosition, Room room, int hostilityLevel) {
        int x = getPosition().getX();
        int y = getPosition().getY();
        int distanceToPlayerX = Math.abs(x - playerPosition.getX());
        int distanceToPlayerY = Math.abs(y - playerPosition.getY());
        int distanceForPursuit = getDistanceForPursuit(hostilityLevel);
        Random random = new Random();
        int randomNumber = 0;
        if (distanceToPlayerX <= distanceForPursuit && distanceToPlayerY <= distanceForPursuit) {
            moveToPlayer(playerPosition, room, x, y, 3, false);
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
                        return;
                }
                if (isValidMove(x, y, room)) {
                    getPosition().setNew(x, y, false);
//                    System.out.println("snake2: (" + x + ", " + y + ")");
                } else {
                    initialDirection = (initialDirection + 1) % 4;
                }
            }
        }
    }

    public void moveZombie(Position playerPosition, Room room, int hostilityLevel) {
        int x = getPosition().getX();
        int y = getPosition().getY();
        int distanceToPlayerX = Math.abs(x - playerPosition.getX());
        int distanceToPlayerY = Math.abs(y - playerPosition.getY());
        int distanceForPursuit = getDistanceForPursuit(hostilityLevel);
        if (distanceToPlayerX <= distanceForPursuit && distanceToPlayerY <= distanceForPursuit) {
            moveToPlayer(playerPosition, room, x, y, 1, false);
        } else {
            for (int i = 0; i < 1; i++) {
                if (room.room_points.length > room.room_points[0].length) {
                    switch (initialDirection) {
                        case TOP:
                            y--;
                            break;
                        case BOTTOM:
                            y++;
                            break;
                        default:
                            return;
                    }
                } else {
                    switch (initialDirection) {
                        case TOP:
                            x--;
                            break;
                        case BOTTOM:
                            x++;
                            break;
                        default:
                            return;
                    }
                }
                if (isValidMove(x, y, room)) {
                    getPosition().setNew(x, y, false);
//                    System.out.println("x= " + x + " y= " + y);
                } else {
                    initialDirection = (initialDirection + 2) % 4;
                }
            }
        }
    }

    public void moveOgre(Position playerPosition, Room room, int hostilityLevel) {
        int x = getPosition().getX();
        int y = getPosition().getY();
        int distanceToPlayerX = Math.abs(x - playerPosition.getX());
        int distanceToPlayerY = Math.abs(y - playerPosition.getY());
        int distanceForPursuit = getDistanceForPursuit(hostilityLevel);
        if (distanceToPlayerX <= distanceForPursuit && distanceToPlayerY <= distanceForPursuit) {
            moveToPlayer(playerPosition, room, x, y, 2, false);
        } else {
            for (int i = 0; i < 2; i++) {
                switch (initialDirection) {
                    case TOP:
                        y = y - 2;
                        break;
                    case RIGHT:
                        x = x + 2;
                        break;
                    case BOTTOM:
                        y = y + 2;
                        break;
                    case LEFT:
                        x = x - 2;
                        break;
                    default:
                        return;
                }
                if (isValidMove(x, y, room)) {
                    getPosition().setNew(x, y, false);

                } else {
                    initialDirection = (initialDirection + 1) % 4;
                }
            }
        }
    }

    public void moveGhost(Position playerPosition, Room room, int hostilityLevel) {
        int x = getPosition().getX();
        int y = getPosition().getY();
        Random random = new Random();
        boolean visible = Math.random() > 0.6;
        int distanceToPlayer = Math.abs(x - playerPosition.getX()) + Math.abs(y - playerPosition.getY());
        int distanceForPursuit = getDistanceForPursuit(hostilityLevel);
        if (distanceToPlayer <= distanceForPursuit) {
            visible = false;//призрак виден при приближении
            moveToPlayer(playerPosition, room, x, y, 1, visible);
        } else {
            visible = Math.random() > 0.6;
            for (int i = 0; i < 4; i++) {
                int direction = random.nextInt(4);
                switch (direction) {
                    case TOP:
                        y = y - 1;
                        break;
                    case RIGHT:
                        x = x + 1;
                        break;
                    case BOTTOM:
                        y = y + 1;
                        break;
                    case LEFT:
                        x = x - 1;
                        break;
                    default:
                        return;
                }
                if (isValidMove(x, y, room)) {
                    getPosition().setNew(x, y, visible);
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
        if (distanceToPlayerX <= distanceForPursuit && distanceToPlayerY <= distanceForPursuit) {
            moveToPlayer(playerPosition, room, x, y, 1, false);
        }
    }


    @Override
    public void action(Character player, Room room) {
        switch (getType()) {
            case ZOMBIE -> moveZombie(player.getPosition(), room, getHostility());
            case GHOST -> moveGhost(player.getPosition(), room, getHostility());
            case OGRE -> moveOgre(player.getPosition(), room, getHostility());
            case VAMPIRE -> moveVampire(player.getPosition(), room, getHostility());
            case SNAKE -> moveSnake(player.getPosition(), room, getHostility());

        }
    }

    @Override
    public String toString() {
        return "enemy" + getType() + " " + getStrength() + "HP" + getHealth();
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
            getPosition().setNew(x, y, visible);
        }
    }

}



