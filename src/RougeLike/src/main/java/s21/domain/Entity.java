package s21.domain;

import static s21.domain.GameConstants.*;

public abstract class Entity {
    private int type; // enemy, item, player, exit
    private int symbol; // выбрать из возможного пула
    private Position position;
    private int status;
    private int duration;
    private int health;
    private int countHit;

    public Entity() {
        type = UNINITIALIZED;
        symbol = UNINITIALIZED;
        Position position = new Position();
        status = ON_FIELD;
        duration = UNINITIALIZED;
        health = 0;
        countHit = 0;

    }

    public Entity(int type, int symbol, Position position) {
        this.type = type;
        this.symbol = symbol;
        this.position = new Position(position.getX(), position.getY(), false);
        this.status = ON_FIELD;
        this.duration = 0;
        this.health = 0;
        this.countHit = 0;
    }

    public abstract void action(Character player, Room room);

    public abstract String toString();

    public abstract int getStrength();

    public abstract int getAgility();

    public abstract int getHealth();

    public abstract void setHealth(int health);

    public int getType() {
        return type;
    }


    public void setType(int type) {
        this.type = type;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public int getSymbol() {
        return symbol;
    }

    public void setSymbol(int symbol) {
        this.symbol = symbol;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


    public int getDuration() {
        return duration;
    }

    public void setDuration(int i) {
        duration = i;
    }

    public void reduceDuration() {
        if (duration >= 1) duration--;
    }

    public int getCountHit() {
        return countHit;
    }

    public void setCountHit(int countHit) {
        this.countHit = countHit;
    }

    public void clear() {
        type = UNINITIALIZED;
        symbol = UNINITIALIZED;
        position.setX(-1);
        position.setY(-1);
        position.setVisibility(false);
        duration = UNINITIALIZED;
    }
}
