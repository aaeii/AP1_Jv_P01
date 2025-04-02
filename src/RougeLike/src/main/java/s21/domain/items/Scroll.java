package s21.domain.items;

import s21.domain.Position;

import java.sql.Date;

import static s21.domain.GameConstants.*;

public class Scroll extends Item {
    private int agility;
    private int strength;
    private int maxHealth;


    public Scroll() {
        this.name = null;
        this.agility = 0;
        this.strength = 0;
        this.maxHealth = 0;
    }


    public Scroll(int scroll_type) {
        super();
        setSymbol(SCROLL_CHAR);
        setType(SCROLL);
        switch (scroll_type) {
            case STRENGTH_SCROLL:
                this.name = "Strength Scroll";
                this.agility = 0;
                this.strength = 5;
                this.maxHealth = 0;
                setSymbol(SCROLL_CHAR);
                break;
            case AGILITY_SCROLL:
                this.name = "Agility Scroll";
                this.agility = 5;
                this.strength = 0;
                this.maxHealth = 0;
                break;
            case MAX_HEAL_SCROLL:
                this.name = "Max heal Scroll";
                this.agility = 0;
                this.strength = 0;
                this.maxHealth = 5;
                break;
            case CURSED_STRENGTH_SCROLL:
                this.name = "Cursed strength scroll";
                this.agility = 0;
                this.strength = -5;
                this.maxHealth = 0;
                break;
            case CURSED_AGILITY_SCROLL:
                this.name = "cursed agility scroll";
                this.agility = -5;
                this.strength = 0;
                this.maxHealth = 0;
                break;
            default:
                break;
        }
    }

    public int getAgility() {
        return agility;
    }

    public int getStrength() {
        return strength;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    @Override
    public void action(Position player_pos, Position top_left, Position bot_right) {
    }
}
