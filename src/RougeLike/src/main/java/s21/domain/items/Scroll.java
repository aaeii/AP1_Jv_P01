package s21.domain.items;

import s21.domain.Character;
import s21.domain.Position;

import static s21.domain.GameConstants.*;

public class Scroll extends Item {
    private int agility;
    private int strength;
    private int health;


    public Scroll() {
        this.name = null;
        this.agility = 0;
        this.strength = 0;
        this.health = 0;
    }


    @Override
    public void action(Character player_pos, Position top_left, Position bot_right) {

    }

    @Override
    public String toString(){
        return  getName() + " " + getStrength() + " " + getStatus();
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
                this.health = 0;
                setSymbol(SCROLL_CHAR);
                break;
            case AGILITY_SCROLL:
                this.name = "Agility Scroll";
                this.agility = 5;
                this.strength = 0;
                this.health = 0;
                break;
            case MAX_HEALTH_SCROLL:
                this.name = "Max health Scroll";
                this.agility = 0;
                this.strength = 0;
                this.health = MAX_HEALTH;
                break;
            case CURSED_STRENGTH_SCROLL:
                this.name = "Cursed strength scroll";
                this.agility = 0;
                this.strength = -5;
                this.health = 0;
                break;
            case CURSED_AGILITY_SCROLL:
                this.name = "cursed agility scroll";
                this.agility = -5;
                this.strength = 0;
                this.health = 0;
                break;
            default:
                break;
        }
    }
    @Override
    public int getAgility() {
        return agility;
    }
    @Override
    public int getStrength() {
        return strength;
    }
    @Override
    public int getHealth() {
        return health;
    }

}
