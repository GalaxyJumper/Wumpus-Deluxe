import java.awt.Graphics2D;

public class Room implements Drawable{
    public static enum RoomType {
        BAT_SPAWN,
        PIT,
        WUMPUS_SPAWN,
        DEFAULT
    }

    private int roomNum;
    private int[] connectedRooms;
    private RoomType type;
    private int xRooms;
    private int yRooms;

    public Room(RoomType type, int num, int[] connectedRooms){
        this.type = type;
        this.roomNum = num;
        this.connectedRooms = connectedRooms;
        xRooms = GameManager.oneToTwoD(num)[0];
        yRooms = GameManager.oneToTwoD(num)[1];
    }

    public int[] getConnectedRooms(){
        return connectedRooms;
    }
    public RoomType getType(){
        return type;
    }
    public int getNum(){
        return roomNum;
    }
    public int getX(){
        return xRooms;
    }
    public int getY(){
        return yRooms;
    }

    @Override
    public void draw(Graphics2D g2d) {
        // TODO: Soon...
    }
    public int[] getAdjacents(){
        int[] result = new int[6];
        int[][] offsets;
        if(this.yRooms % 2 == 1)
            offsets = new int[][]{
                {0, -1},
                {1, -1},

                {-1, 0},
                {1, 0},

                {0, 1},
                {1, 1}
            };
        else // if yRooms is even
            offsets = new int[][]{
                {-1, -1},
                {0, -1},
                {-1, 0},
                {1, 0},
                {-1, 1},
                {0, 1}
            };
        int x, y;
        for(int i = 0; i < 6; i++){
                                              // For negatives  // For values > map width
            x = ((this.xRooms + offsets[i][0]) + Map.MAP_WIDTH) % Map.MAP_WIDTH;

            y = ((this.yRooms + offsets[i][1]) + Map.MAP_HEIGHT) % Map.MAP_HEIGHT;
            result[i] = GameManager.twoToOneD(x, y);
        }
        return result;
    }

}
