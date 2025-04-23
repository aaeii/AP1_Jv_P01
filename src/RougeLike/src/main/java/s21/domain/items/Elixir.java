package s21.domain.items;


import s21.domain.Character;
import s21.domain.Position;
import s21.domain.Room;

import java.sql.Date;

import static s21.domain.GameConstants.*;

public class Elixir extends Item {

    private Date dateStart;
    private int agility;
    private int strength;
    private int health;

    public Elixir(){
        super();
        this.dateStart = null;
        this.agility = 0;
        this.strength = 0;
        this.health = 0;

    }

    public Elixir( int elixir_type) {
        super();
        setSymbol(ELIXIR_CHAR);
        setType(ELIXIR);
        switch (elixir_type) {
            case HEALTH_ELIXIR: //( "Health Elixir", 0, 0, 50, 60000)
                this.name = "Health Elixir";
                this.agility = 0;
                this.strength = 0;
                this.health = 5;
                break;
            case STRENGTH_ELIXIR: //("Strength Elixir", 5, 10, 0, 60000)
                this.name = "Strength Elixir";
                this.agility = 5;
                this.strength = 10;
                this.health = 0;
                break;
            case AGILITY_ELIXIR:
                this.name = "Agility Elixir";
                this.agility = 10;
                this.strength = 0;
                this.health = 0;
                break;
            default:
                break;
        }
    }

    @Override
    public void action(Character player, Room room){
    }
    @Override
    public void setHealth(int health) {
        this.health = health;
    }

    @Override
    public String toString(){
        return  getName() + " " + getStatus();
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


