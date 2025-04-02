package s21.domain;

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
    private final Entity [] entities; //MAX_ENTITIES_PER_ROOM
    private int entities_cnt;

    public Room(){
        grid_i = 0;
        grid_j = 0;
        this.sector = -1;
        connections = new Room[4];
        doors = new Position[4];
        top_left = new Position();
        bot_right = new Position();
        entities = new Entity[MAX_ENTITIES_PER_ROOM + 5];
        entities_cnt = 0;
        visited = false;
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

        entities = new Entity[MAX_ENTITIES_PER_ROOM];
        for (int k = 0; k < MAX_ENTITIES_PER_ROOM; k++){
            entities[k] = null;
        }
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
        this.entities[entities_cnt] = entity;
        this.entities_cnt++;
    }

    public Entity getEntities(int i) {
        return entities[i];
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
                if (entities[i].getPosition().getX() == position.getX() &&
                        entities[i].getPosition().getY() == position.getY() &&
                        (entities[i].getType() <= ZOMBIE && entities[i].getType() >= SNAKE))
                    return true;
            }
        return false;
    }

    public boolean checkIsItExit(Position position){
        for (int i = 0; i < entities_cnt; i++) {
            if (entities[i].getPosition().getX() == position.getX() && entities[i].getPosition().getY() == position.getY() && entities[i].getType() == EXIT)
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
            if(entities[i].position.getX() == player.getPosition().getX()
                    && entities[i].position.getY() == player.getPosition().getY()
                    && entities[i].getType() > 7
                    && player.getItemsCount() <= MAX_COUNT_TYPE_ITEM)
                {
                    player.add(entities[i]);
                    entities[i].clear();
                }
        }
    }

    public void moveEnemiesInRoom(Position position){
        for (int i = 0; i < entities_cnt; i++) {
                entities[i].action(position, top_left, bot_right);
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
            entities[i].getPosition().setVisibility(true);
            if (entities[i].getType() == GHOST )
                entities[i].getPosition().setVisibility(Math.random() > 0.5);
        }
    }

    public void makeInvisible (){
        top_left.setVisibility(false);
        bot_right.setVisibility(false);
        for (int i = 0; i < entities_cnt; i++) {
            entities[i].getPosition().setVisibility(false);
        }
    }
}
