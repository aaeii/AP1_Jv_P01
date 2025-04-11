
package s21.domain.items;


import s21.domain.Position;

import static s21.domain.GameConstants.*;
import static s21.domain.GameConstants.SCROLL_CHAR;

public class Weapon extends Item {
    private int strength;
    private int agility;
    private int health;

    public Weapon(String name, int strength) {
        this.name = name;
        this.strength = strength;
        this.agility = 0;
        this.health = 0;
    }
    public Weapon() {
        this.name = null;
        this.strength = 0;
        this.agility = 0;
        this.health = 0;
    }

    @Override
    public String toString(){
        return  getName() + " " + getStrength() + " " + getStatus();
    }

    public Weapon(int weapon_type) {
        super();
        setType(WEAPON);
        setSymbol(WEAPON_CHAR);
        switch (weapon_type) {
            case MACE:
                this.name = "Mace ";
                this.strength = 10;
                break;
            case LONG_SWORD:
                this.name = "Long sword";
                this.strength = 13;
                break;
            case SHORT_BOW:
                this.name = "Short bow";
                this.strength = 7;
                break;
            case DAGGER:
                this.name = "Dagger";
                this.strength = 8;
                break;
            case TWO_HANDED_SWORD:
                this.name = "Two handed sword";
                this.strength = 16;
                break;
            default:
                break;
        }
        this.agility = 0;
        this.health = 0;
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

    @Override
    public void action(Position player_pos, Position top_left, Position bot_right) {
    }
}