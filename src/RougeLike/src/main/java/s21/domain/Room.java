package s21.domain;

import java.util.ArrayList;
import java.util.List;

import static s21.domain.GameConstants.*;

public class Room {
    private  int sector;
    private  int grid_i;
    private  int grid_j;
    private Room[] connections; //4
    private Position[] doors; //4
    private Position top_left;
    private Position bot_right;
    private boolean visited;
//    private boolean playerExit;
//    private boolean playerSpawn;
    private final List  <Entity> entities; //MAX_ENTITIES_PER_ROOM
    private int entities_cnt;
    private Position[][] room_points;

    public Room(){
        grid_i = 0;
        grid_j = 0;
        this.sector = -1;
        connections = new Room[4];
        doors = new Position[4];
        top_left = new Position();
        bot_right = new Position();
        entities = new ArrayList<>();
        entities_cnt = 0;
        visited = false;
        room_points = new Position[SECTOR_HEIGHT][SECTOR_WIDTH];
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
        room_points = new Position[SECTOR_HEIGHT][SECTOR_WIDTH];
        for(int k = 0; k<SECTOR_HEIGHT; k++){
            for(int m=0; m<SECTOR_WIDTH; m++){
                room_points[k][m]= new Position();
            }
        }
        entities = new ArrayList<>();
        top_left = new Position();
        bot_right = new Position();
        entities_cnt = 0;
        visited = false;
    }

    public int getSector() {
        return sector;
    }

    public Room getConnections(int i) {
        return connections[i];
    }

    public int getEntities_cnt(){
        return entities_cnt;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public void setEntities_cnt(int entities_cnt) {
        this.entities_cnt = entities_cnt;
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
        this.entities_cnt++;
    }

    public Entity getEntities(int i) {
        return entities.get(i);
    }

    public void setTop_left(Position position) {
        this.top_left = position;
    }

    public Position getTop_left() {
        return  top_left;
    }

    public Position getDoors(int i) {
        return doors[i];
    }

    public void setBot_right (Position position) {
        this.bot_right = position;
    }

    public Position getBot_right() {
        return bot_right;
    }

    public boolean checkInRoomEntities(Position position){
            for (int i = 0; i < entities_cnt; i++) {
                if (entities.get(i).getPosition().getX() == position.getX() &&
                        entities.get(i).getPosition().getY() == position.getY() &&
                        (entities.get(i).getType() <= ZOMBIE && entities.get(i).getType() >= SNAKE))
                    return true;
            }
        return false;
    }

    public boolean checkIsItExit(Position position){
        for (int i = 0; i < entities_cnt; i++) {
            if (entities.get(i).getPosition().getX() == position.getX() && entities.get(i).getPosition().getY() == position.getY() && entities.get(i).getType() == EXIT)
                return true;
        }
        return false;
    }

    public boolean checkPlayerInRoom(Position position){
            if (position.getX() >= top_left.getX()
                    && position.getX() <= bot_right.getX()
                    && position.getY() >= top_left.getY()
                    && position.getY() <= bot_right.getY())
                return true;
        return false;
    }

    public void ckeckIsItItem(Character player){
        for (int i = 0; i < entities_cnt; i++) {
            if(entities.get(i).position.getX() == player.getPosition().getX()
                    && entities.get(i).position.getY() == player.getPosition().getY()
                    && entities.get(i).getStatus() == ON_FIELD
                    && entities.get(i).getType() > 7)
            {
                    if (entities.get(i).getType() == GOLD) {
                        player.setGold(player.getGold() + 1);
                        entities.remove(i);
                        entities_cnt--;
                    } else {
                        if (entities.get(i).getType() == FOOD) {
                            int newHealth = player.getHealth() + entities.get(i).getHealth();
                            if (newHealth < MAX_HEALTH) player.setHealth(newHealth);
                            else
                                player.setHealth(MAX_HEALTH);
                            player.setEatenFoodCounter();
                            entities.remove(i);
                            entities_cnt--;
                        }
                        else {
                            if (player.getItemsCount() < MAX_COUNT_TYPE_ITEM)
                                {
                                entities.get(i).setStatus(IN_INVENTORY);
                                player.addItem(entities.get(i));
                                    entities.remove(i);
                                    entities_cnt--;
                                }
                            }
                    player.printInventary();
                }
        }
        }
    }

    public void moveEnemiesInRoom(Position position){
        for (int i = 0; i < entities_cnt; i++) {
            entities.get(i).action(position, top_left, bot_right);
            }
    }

    public boolean checkRoom(Position player_position){
            if (player_position.getX() >= top_left.getX()
                    && player_position.getX() <= bot_right.getX()
                    && player_position.getY() >= top_left.getY()
                    && player_position.getY() <= bot_right.getY())
                return true;
        return false;
    }

    public void makeVisible (){
        top_left.setVisibility(true);
        bot_right.setVisibility(true);
        for (int i = 0; i < entities_cnt; i++) {
            entities.get(i).getPosition().setVisibility(true);
            if (entities.get(i).getType() == GHOST )
                entities.get(i).getPosition().setVisibility(Math.random() > 0.5);
        }
    }

    public void makeInvisible (){
        top_left.setVisibility(false);
        bot_right.setVisibility(false);
        for (int i = 0; i < entities_cnt; i++) {
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
        if (direction == BOTTOM && y > top_left.getY() && y < room.getBot_right().getY()
                && x > top_left.getX() && x < bot_right.getX() && !checkPlayerInRoom(position)) result = true;
        if (direction == TOP && y > room.getTop_left().getY() && y < bot_right.getY()
                && x > top_left.getX() && x < bot_right.getX()) result = true;
        if (direction == RIGHT && y > top_left.getY() && y < bot_right.getY()
                && x > bot_right.getX() && x < room.getTop_left().getX()) result = true;
        if (direction == LEFT && y > top_left.getY() && y < bot_right.getY()
                && x > room.getBot_right().getX() && x < top_left.getX()) result = true;
        return  result;
    }

    public int distance(Position position, int direction){
        int distance = 0;
        int x = position.getX();
        int y = position.getY();
        distance = switch (direction) {
            case TOP -> y - bot_right.getY();
            case BOTTOM -> top_left.getY() - y;
            case LEFT -> x - bot_right.getX();
            case RIGHT -> top_left.getX() - x;
            default -> distance;
        };
        return distance;
    }

//    public void render()

}
