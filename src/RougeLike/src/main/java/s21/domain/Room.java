package s21.domain;

import s21.domain.items.Gold;
import s21.domain.items.Inventory;

import java.util.ArrayList;
import java.util.List;

import static s21.domain.GameConstants.*;

public class Room {
    private  int sector;
    private  int grid_i;
    private  int grid_j;
    private Room[] connections; //4
    private Position[] doors; //4
    private Position topLeft;
    private Position botRight;
    private boolean visited;
    private final List  <Entity> entities; //MAX_ENTITIES_PER_ROOM
    private int entitiesCnt;
    private Position[][] roomPoints;

    public Room(){
        grid_i = 0;
        grid_j = 0;
        this.sector = -1;
        connections = new Room[4];
        doors = new Position[4];
        topLeft = new Position();
        botRight = new Position();
        entities = new ArrayList<>();
        entitiesCnt = 0;
        visited = false;
        roomPoints = new Position[SECTOR_HEIGHT][SECTOR_WIDTH];
    }

    public Room(int i, int j, int sector){
        grid_i = i;
        grid_j = j;
        this.sector = sector;
        connections = new Room[4];
        for (int k = 0; k < 4; k++){
            connections[i] = null;
        }
        doors = new Position[4];
        for (int k = 0; k < 4; k++){
            Position currentPosition = new Position();
            doors[k] = currentPosition;
        }
        roomPoints = new Position[SECTOR_HEIGHT][SECTOR_WIDTH];
        for(int k = 0; k<SECTOR_HEIGHT; k++){
            for(int m=0; m<SECTOR_WIDTH; m++){
                roomPoints[k][m]= new Position();
            }
        }
        entities = new ArrayList<>();
        topLeft = new Position();
        botRight = new Position();
        entitiesCnt = 0;
        visited = false;
    }

    public int getSector() {
        return sector;
    }

    public Room getConnections(int i) {
        return connections[i];
    }

    public int getEntitiesCnt(){
        return entitiesCnt;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public void setEntitiesCnt(int entitiesCnt) {
        this.entitiesCnt = entitiesCnt;
    }

    public void setConnections(Room room, int i) {
        this.connections[i] = room;
    }

    public void setDoors(Position pos, int i) {
        this.doors[i] = pos;
    }

    public void setConnections(Room[] connections) {
        this.connections = connections;
    }

    public void setEntities(Entity entity) {
        entities.add(entity);
        this.entitiesCnt++;
    }

    public Entity getEntities(int i) {
        return entities.get(i);
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public void setTopLeft(Position position) {
        this.topLeft = position;
    }

    public Position getTopLeft() {
        return topLeft;
    }

    public Position getDoors(int i) {
        return doors[i];
    }

    public void setBotRight(Position position) {
        this.botRight = position;
    }

    public Position getBotRight() {
        return botRight;
    }

    public boolean checkInRoomEntities(Position position){
            for (int i = 0; i < entitiesCnt; i++) {
                if (entities.get(i).getPosition().getX() == position.getX() &&
                        entities.get(i).getPosition().getY() == position.getY()
                )
                    return true;
            }
        return false;
    }


    public boolean checkPlayerInRoom(Position position){
            if (position.getX() >= topLeft.getX()
                    && position.getX() <= botRight.getX()
                    && position.getY() >= topLeft.getY()
                    && position.getY() <= botRight.getY())
                return true;
        return false;
    }

    public void ckeckIsItItem(Character player, Inventory inventory){
        for (int i = 0; i < entitiesCnt; i++) {
            if(entities.get(i).getPosition().getX() == player.getPosition().getX()
                    && entities.get(i).getPosition().getY() == player.getPosition().getY()
                    && entities.get(i).getStatus() == ON_FIELD
                    && entities.get(i).getType() > MIMIK)
            {
                    if (entities.get(i).getType() == GOLD) {
                        player.setGold(player.getGold() + 1);
                        entities.remove(i);
                        entitiesCnt--;
                    } else {
                        if (entities.get(i).getType() == FOOD) {
                            int newHealth = player.getHealth() + entities.get(i).getHealth();
                            if (newHealth < MAX_HEALTH) player.setHealth(newHealth);
                            else
                                player.setHealth(MAX_HEALTH);
                            player.setEatenFoodCounter();
                            entities.remove(i);
                            entitiesCnt--;
                        }
                        else {
//                            if (inventory.getSize() < MAX_COUNT_TYPE_ITEM)
//                                {
//                                entities.get(i).setStatus(IN_INVENTORY);
//                                inventory.addItem(entities.get(i));
//                                    entities.remove(i);
//                                    entities_cnt--;
//                                }
//                            }
                            if (inventory.addItem(entities.get(i)))
                            {
                                    entities.remove(i);
                                    entitiesCnt--;
                            }
                        }
                }
        }
        }
    }


    public boolean checkRoom(Position player_position){
            if (player_position.getX() >= topLeft.getX()
                    && player_position.getX() <= botRight.getX()
                    && player_position.getY() >= topLeft.getY()
                    && player_position.getY() <= botRight.getY())
                return true;
        return false;
    }

    public void throwGold(Entity enemy, Room room){
                int gold_count = (enemy.getAgility()+ enemy.getStrength()) / 10;
                if (gold_count < 1) gold_count++;
                for (int i=0; i < gold_count ;
//                        && room.getEntities_cnt() <= MAX_ENTITIES_PER_ROOM;
                     i++) {
                    Entity newGold = new Gold();
                    Position goldPos = new Position();
                    do goldPos = goldPos.generate_entity_coords(room);
                    while ((goldPos.check_unoccupied(room, goldPos) == OCCUPIED)
                            && (goldPos.check_walls(room, goldPos) == OCCUPIED));
                    newGold.setStatus(ON_FIELD);
                    newGold.setType(GOLD);
                    newGold.setSymbol(GOLD_CHAR);
                    newGold.setPosition(goldPos);
                    room.setEntities(newGold);
                }
    }

    public void makeVisible (){
        topLeft.setVisibility(true);
        botRight.setVisibility(true);
        for (int i = 0; i < entitiesCnt; i++) {
            entities.get(i).getPosition().setVisibility(true);
            if (entities.get(i).getType() == GHOST )
                entities.get(i).getPosition().setVisibility(Math.random() > 0.5);
        }
    }

    public void makeInvisible (){
        topLeft.setVisibility(false);
        botRight.setVisibility(false);
        for (int i = 0; i < entitiesCnt; i++) {
            entities.get(i).getPosition().setVisibility(false);
        }
    }

    public boolean isPlayerOnConnection(Position position, int moveDirection){
        boolean result = false;
        int corr_direction = -1;
        switch (moveDirection){
            case TOP:
                corr_direction = BOTTOM;
                break;
            case BOTTOM:
                corr_direction = TOP;
                break;
            case RIGHT:
                corr_direction = LEFT;
                break;
            case LEFT:
                corr_direction = RIGHT;
                break;
            default: break;
        }
        if (corr_direction != -1){
            if (connections[corr_direction] != null)
                result = isPlayerBetween(position, connections[corr_direction], corr_direction);
        }
        return result;
    }

    public boolean isPlayerBetween(Position position, Room room, int direction) {
        boolean result = false;
        int x = position.getX();
        int y = position.getY();
        if (direction == BOTTOM && y > topLeft.getY() && y < room.getBotRight().getY()
                && x > topLeft.getX() && x < botRight.getX() && !checkPlayerInRoom(position)) result = true;
        if (direction == TOP && y > room.getTopLeft().getY() && y < botRight.getY()
                && x > topLeft.getX() && x < botRight.getX()) result = true;
        if (direction == RIGHT && y > topLeft.getY() && y < botRight.getY()
                && x > botRight.getX() && x < room.getTopLeft().getX()) result = true;
        if (direction == LEFT && y > topLeft.getY() && y < botRight.getY()
                && x > room.getBotRight().getX() && x < topLeft.getX()) result = true;
        return  result;
    }

    public int distance(Position position, int direction){
        int distance = 0;
        int x = position.getX();
        int y = position.getY();
        distance = switch (direction) {
            case TOP -> y - botRight.getY();
            case BOTTOM -> topLeft.getY() - y;
            case LEFT -> x - botRight.getX();
            case RIGHT -> topLeft.getX() - x;
            default -> distance;
        };
        return distance;
    }

}
