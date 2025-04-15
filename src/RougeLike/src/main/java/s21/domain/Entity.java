package s21.domain;

import static s21.domain.GameConstants.*;

public abstract class Entity {
    int type; // enemy, item, player, exit
    int symbol; // выбрать из возможного пула
    Position position;
    int status;
    private int duration;

    public Entity() {
        type = UNINITIALIZED;
        symbol = UNINITIALIZED;
        Position position = new Position();
        status = ON_FIELD;
        duration = UNINITIALIZED;

    }

    public Entity(int type, int symbol, Position position) {
        this.type = type;
        this.symbol = symbol;
        this.position = new Position(position.getX(), position.getY(), false);
        this.status = ON_FIELD;
        this.duration = 0;
    }

    public abstract void action(Position player_pos, Position top_left, Position bot_right);
    public abstract String toString();
    public abstract int getStrength();
    public abstract int getAgility();
    public abstract int getHealth();

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
        if (duration>=1) duration--;
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
