package s21.domain;

import s21.domain.Entity;

import static s21.domain.GameConstants.*;
import static s21.domain.GameConstants.SNAKE_CHAR;

public class Item extends Entity {
    protected String name;

    public String getName() {
        return name;
    }

    public Item(int type) {
        super();
        this.type = type;
        switch (type) {
            case GOLD:
                break;
            case FOOD:
                break;
            case AGILITY:
                break;
            case STRENGTH:
                break;
            case MAX_HEALTH:
                break;
            case ELIXIR:
                break;
            default:
                break;
        }
    }

    public void move(Position player_pos, Position  top_left, Position bot_right){
        switch (type){
            case GOLD:
                break;
            default:
                break;

        }
    }
}
