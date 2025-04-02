package s21.domain;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;
import java.util.List;

import static s21.domain.GameConstants.*;
import s21.domain.*;

public class Level {

    private List<Room> roomsSequence;
    private List<Corridor> corridors;
    private Room[][] rooms;
    private int room_cnt;
    private int  corridors_cnt;
    private Position exit_position;

    public Level() {
        roomsSequence = new ArrayList<>();
        corridors = new ArrayList<>();
        rooms = new Room[ROOMS_PER_SIDE][ROOMS_PER_SIDE];
        room_cnt = 0;
        corridors_cnt = 0;
        exit_position = new Position();
        for (int i = 0; i < ROOMS_PER_SIDE; i++)
            for (int j = 0; j < ROOMS_PER_SIDE; j++)
            { Room currentRoom = new Room();
                rooms[i][j] = currentRoom;
                for (int k = 0; k < 4; k++)
                {
                    rooms[i][j].setConnections(null, k);
                    Position pos = new Position(UNINITIALIZED, UNINITIALIZED, false);
                    rooms[i][j].setDoors(pos, k);
                }
                rooms[i][j].setEntities_cnt(0);
            }
        roomsSequence = new ArrayList<>();
        for (int i = 0; i < MAX_ROOMS_NUMBER; i++){
            Room room = new Room();
            roomsSequence.add(room);
            }
        corridors = new ArrayList<>();
        for (int i = 0; i < MAX_CORRIDORS_NUMBER; i++){
            Corridor corr = new Corridor();
            corridors.add(corr);
        }
}
    public void refreshLevel() {
        roomsSequence = new ArrayList<>();
        corridors = new ArrayList<>();
        rooms = new Room[ROOMS_PER_SIDE][ROOMS_PER_SIDE];
        room_cnt = 0;
        corridors_cnt = 0;
        exit_position = new Position();
        for (int i = 0; i < ROOMS_PER_SIDE; i++)
            for (int j = 0; j < ROOMS_PER_SIDE; j++)
            { Room currentRoom = new Room();
                rooms[i][j] = currentRoom;
                for (int k = 0; k < 4; k++)
                {
                    rooms[i][j].setConnections(null, k);
                    Position pos = new Position(UNINITIALIZED, UNINITIALIZED, false);
                    rooms[i][j].setDoors(pos, k);
                }
                rooms[i][j].setEntities_cnt(0);
            }
        roomsSequence = new ArrayList<>();
        for (int i = 0; i < MAX_ROOMS_NUMBER; i++){
            Room room = new Room();
            roomsSequence.add(room);
        }
        corridors = new ArrayList<>();
        for (int i = 0; i < MAX_CORRIDORS_NUMBER; i++){
            Corridor corr = new Corridor();
            corridors.add(corr);
        }
    }

    public Room getRoomsSequence(int i) {
        return roomsSequence.get(i);
    }

    public void setRoomsSequence(int i, Room room) {
        this.roomsSequence.set(i, room);
    }

    public Corridor getCorridors(int i) {
        return corridors.get(i);
    }

    public Position getExit_position(){
        return exit_position;
    }


    public int getRoom_cnt() {
        return room_cnt;
    }

    public int getCorridors_cnt() {
        return corridors_cnt;
    }

    public void generate_level(){
        int connection = 1;
        while (connection != CONNECTED){
            refreshLevel();
            generate_sectors();
            generate_connections();
            generate_rooms_geometry();
            generate_corridors_geometry();
            generate_exit();
            connection = check_connectivity();
        }
    }

    public void generate_sectors()
    {
        while (room_cnt < 6)
        {
            int sector = 0;
            for (int i = 0; i < ROOMS_PER_SIDE ; i++){
                for (int j = 0; j < ROOMS_PER_SIDE; j++, sector++){
                    if (Math.random() < ROOM_CHANCE &&
                            rooms[i][j].getSector() == -1 ){
                            rooms[i][j] = new Room(i, j, sector);
                            setRoomsSequence(room_cnt, rooms[i][j]);
                            room_cnt++;
                    }
                }
            }
        }
        roomsSequence.sort(new RoomsComparator());
    }

    public class RoomsComparator implements Comparator<Room> {
        @Override
        public int compare(Room o1, Room o2) {
            return o1.getSector() - o2.getSector();
        }
    }


    public void generate_rooms_geometry(){
        int count = 0;
        for (int i = 0; i < ROOMS_PER_SIDE ; i++)
            for (int j = 0; j < ROOMS_PER_SIDE; j++)
                if (rooms[i][j].getSector() != -1)
                {
                    generate_corners(rooms[i][j], (i) * SECTOR_HEIGHT, (j) * SECTOR_WIDTH);
                    generate_doors(rooms[i][j]);
                    for (int k = 0; k < roomsSequence.size(); k++){
                        if (roomsSequence.get(k).getSector() == rooms[i][j].getSector()){
                            roomsSequence.get(k).setBot_right(rooms[i][j].getBot_right());
                            roomsSequence.get(k).setTop_left(rooms[i][j].getTop_left());
                        }
                    }
                }
    }

    public void generate_corners(Room room, int offset_y, int offset_x)
    {
        int top_leftY = (int) ((Math.random() * (double) (SECTOR_HEIGHT - 6) / 2) + offset_y + 1);
        int top_leftX = (int) ((Math.random() * (double) (SECTOR_WIDTH - 6) / 2) + offset_x + 1);
        Position Top_left = new Position(top_leftX, top_leftY, false);
        room.setTop_left(Top_left);
        int bot_rightY =  top_leftY + (int) ((Math.random() * (double) (SECTOR_HEIGHT - SECTOR_HEIGHT/2)) + 3);
        int bot_rightX = top_leftX + (int) ((Math.random() * (double) (SECTOR_WIDTH - SECTOR_WIDTH/2)) + 3 );
        Position Bot_right = new Position(bot_rightX, bot_rightY, false);
        room.setBot_right(Bot_right);
    }

    public void generate_doors(Room room){
        if (room.getConnections(TOP) != null)
        {
            int xDoor = (int) (Math.random() * (double) ((room.getBot_right().getX()-room.getTop_left().getX() - 1)) + room.getTop_left().getX()+1);
            Position doorPos = new Position(xDoor, room.getTop_left().getY(), false);
            room.setDoors(doorPos, TOP);
        }

        if (room.getConnections(RIGHT) != null)
        {
            int yDoor = (int) (Math.random() * (double) ((room.getBot_right().getY()-room.getTop_left().getY() - 1)) + room.getTop_left().getY()+1);
            Position doorPos = new Position(room.getBot_right().getX(), yDoor, false);
            room.setDoors(doorPos, RIGHT);
        }

        if (room.getConnections(BOTTOM) != null)
        {
            int xDoor = (int) (Math.random() * (double) ((room.getBot_right().getX()-room.getTop_left().getX() - 1)) + room.getTop_left().getX()+1);
            Position doorPos = new Position(xDoor, room.getBot_right().getY(), false);
            room.setDoors(doorPos, BOTTOM);
        }
        if (room.getConnections(LEFT) != null)
        {
            int yDoor = (int) (Math.random() * (double) ((room.getBot_right().getY()-room.getTop_left().getY() - 1)) + room.getTop_left().getY()+1);
            Position doorPos = new Position(room.getTop_left().getX(), yDoor, false);
            room.setDoors(doorPos, LEFT);
        }
    }

    void generate_connections()
    {
        generate_primary_connections();
    }

    void generate_primary_connections()
    {
        for (int i = 0; i < ROOMS_PER_SIDE; i++)
            for (int j = 0; j < ROOMS_PER_SIDE; j++)
                if ((rooms[i][j].getSector() != UNINITIALIZED) && (rooms[i][j] != null))
                {
                    if ((i > 0))
                    {
                        if ((rooms[i - 1][j].getSector() != UNINITIALIZED) && (rooms[i - 1][j] != null))
                        rooms[i][j].setConnections( rooms[i - 1][j], TOP );
                    }
                    if (j < (ROOMS_PER_SIDE - 1) )
                    {
                        if ((rooms[i][j + 1].getSector() != UNINITIALIZED) && (rooms[i][j + 1] != null))
                        {
                            rooms[i][j].setConnections(rooms[i][j + 1], RIGHT);
                        }
                    }
                    if (i < ROOMS_PER_SIDE - 1)
                    {
                        if ((rooms[i + 1][j].getSector() != UNINITIALIZED) && (rooms[i + 1][j] != null))
                            rooms[i][j].setConnections(rooms[i + 1][j], BOTTOM);
                    }
                    if (j > 0) {
                    if ((rooms[i][j - 1].getSector() != UNINITIALIZED) && (rooms[i][j - 1]!= null))
                        rooms[i][j].setConnections(rooms[i][j - 1], LEFT);
                    }
                }

    }


    private void generate_corridors_geometry(){
        for (int i = 0; i < ROOMS_PER_SIDE; i++){
            for (int j = 0; j < ROOMS_PER_SIDE; j++)
            {
                if (rooms[i][j].getConnections(RIGHT) != null && rooms[i][j].getConnections(RIGHT).getConnections(LEFT) == rooms[i][j])
                    generate_left_to_right_corridor(rooms[i][j], rooms[i][j].getConnections(RIGHT), corridors_cnt);
                if (rooms[i][j].getConnections(BOTTOM) != null)
                    generate_top_to_bottom_corridor(rooms[i][j], rooms[i][j].getConnections(BOTTOM), corridors_cnt);
            }
        }
    }

    private void generate_left_to_right_corridor(Room left_room, Room right_room, int corr_cnt) {
        corridors.get(corr_cnt).setType(LEFT_TO_RIGHT_CORRIDOR);
        corridors.get(corr_cnt).setPoints(left_room.getDoors(RIGHT), 0);
        int x_min = left_room.getDoors(RIGHT).getX();
        int x_max = right_room.getDoors(LEFT).getX();
        int random_center_x = (int) (Math.random() * (double)(x_max - x_min - 2) + x_min + 1);
        Position second_point = new Position(random_center_x, left_room.getDoors(RIGHT).getY(), false);
        Position third_point  = new Position(random_center_x, right_room.getDoors(LEFT).getY(), false);
        corridors.get(corr_cnt).setPoints(second_point, 1);
        corridors.get(corr_cnt).setPoints(third_point, 2);
        corridors.get(corr_cnt).setPoints(right_room.getDoors(LEFT), 3);
        corridors_cnt++;
    }

    private void generate_top_to_bottom_corridor(Room top_room, Room bottom_room, int corr_cnt){
        corridors.get(corr_cnt).setType(TOP_TO_BOTTOM_CORRIDOR);
        corridors.get(corr_cnt).setPoints(top_room.getDoors(BOTTOM), 0);
        int y_min = top_room.getDoors(BOTTOM).getY();
        int y_max = bottom_room.getDoors(TOP).getY();
        int random_center_y = (int) (Math.random() * (double)(y_max - y_min - 2) + y_min + 1);
        Position second_point = new Position(top_room.getDoors(BOTTOM).getX(), random_center_y, false);
        Position third_point  = new Position(bottom_room.getDoors(TOP).getX(), random_center_y, false);
        corridors.get(corr_cnt).setPoints(second_point, 1);
        corridors.get(corr_cnt).setPoints(third_point, 2);
        corridors.get(corr_cnt).setPoints(bottom_room.getDoors(TOP), 3);
        corridors_cnt++;
    }

    public int check_connectivity()
    {
        int rc = CONNECTED;
        int [] visited = new int[9];
        int j = 0;
        while (getRoomsSequence(j).getSector() == -1)
            ++j;
        int visited_count = depth_first_search(getRoomsSequence(j), visited);
        if (visited_count != room_cnt)
            rc = NOT_CONNECTED;
        return rc;
    }

    private int depth_first_search(Room room, int [] visited)
    {
        int visited_count = 1;
        visited[room.getSector()] = 1;
        for (int i = 0; i < 4; i++)
        {
            if (room.getConnections(i) != null && visited[room.getConnections(i).getSector()] == 0)
            visited_count += depth_first_search(room.getConnections(i), visited);}
        return visited_count;
    }

    public void moveEnemies(Position position){
        int offset = 0 ;
        while (roomsSequence.get(offset).getSector() == -1)
            ++offset;
        for (int i = offset; i < roomsSequence.size(); i++) {
                if (roomsSequence.get(i).checkPlayerInRoom(position)){
                    roomsSequence.get(i).moveEnemiesInRoom(position);
                }
        }
    }

    public void generate_exit(){
        int room_index = (int) (Math.random() * (double) (getRoom_cnt())+1);
        int offset = 0 ;
        while (getRoomsSequence(offset).getSector() == -1)
            ++offset;
        Room exit_room = new Room();

            exit_room = getRoomsSequence(offset + room_index - 1);

        Position exit_coordinate = new Position();
        exit_coordinate = exit_coordinate.generate_entity_coords(exit_room);
        exit_position.setNew(exit_coordinate.getX(), exit_coordinate.getY(), false);
    }


    public boolean checkExitInRoom(int number){
        return roomsSequence.get(number).checkRoom(exit_position);
    }

    public void takeItem(Character player){
        int offset = 0 ;
        while (roomsSequence.get(offset).getSector() == -1)
            ++offset;
        for (int i = offset; i < roomsSequence.size(); i++){
            roomsSequence.get(i).ckeckIsItItem(player);
        }
    }

    public void changeVisibility(Position player_position){
        int offset = 0 ;
        while (roomsSequence.get(offset).getSector() == -1)
            ++offset;
        for (int i = offset; i < roomsSequence.size(); i++)
        {
            if (roomsSequence.get(i).checkRoom(player_position)) {
                roomsSequence.get(i).makeVisible();
            } else {
                roomsSequence.get(i).makeInvisible();
            }
            if (roomsSequence.get(i).checkRoom(player_position) && roomsSequence.get(i).checkRoom(exit_position))
                exit_position.setVisibility(true);
        }
        for (int i = 0; i < getCorridors_cnt(); i++){
            if (corridors.get(i).checkCorridor(player_position))
                for (int k=0; k < 4; k++){
                    corridors.get(i).getPoints(k).setVisibility(true);;
                }
        }

    }

    public void RoomVisit(Position player_position){
        int offset = 0 ;
        while (roomsSequence.get(offset).getSector() == -1)
            ++offset;
        for (int i = offset; i < roomsSequence.size(); i++){
            if (roomsSequence.get(i).checkPlayerInRoom(player_position))
                roomsSequence.get(i).setVisited(true);
        }
    }
}

