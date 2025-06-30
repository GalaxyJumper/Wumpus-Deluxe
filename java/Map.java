import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;
public class Map { 

    public static final int MAP_HEIGHT = 6;
    public static final int MAP_WIDTH = 6;

    private static Room[] rooms = new Room[30];
    public static void getAdjacents(int roomNum){

    }
    public static void getAdjacents(int x, int y){

    }
    public static void generateMap(){
        Random random = new Random();
        // Master list of each of the rooms. Index = room id, value = number of available doorways
        ArrayList<Integer> master = new ArrayList<Integer>(); 

        int[] servant; // Temp arrays - predefinition
        int[] servant2;

        // Each room starts with 3 available doorways
        for(int i = 0; i < MAP_WIDTH * MAP_HEIGHT; i++){
            master.add(3);
        }

        int numStartingNodes = random.nextInt(4, 9);
        servant = new int[numStartingNodes];

        boolean duplicatesExist = false;

        for(int i = 0; i < numStartingNodes; i++){
            servant[i] = random.nextInt(0, MAP_WIDTH * MAP_HEIGHT + 1);
            
        }

    }
    public static void loadMapFromFile(){
        try {

            File mapFile = new File("map1.map");
            Scanner reader = new Scanner(mapFile);
            int index = 0;

            int[] connectedRooms;

            while(reader.hasNextLine()){
                String[] splitLine = reader.nextLine().split(",");
                connectedRooms = new int[splitLine.length];

                for(int i = 0; i < splitLine.length; i ++){
                    connectedRooms[i] = Integer.parseInt(splitLine[i]);
                }

                rooms[index] = new Room(Room.RoomType.DEFAULT, index, connectedRooms);
                index ++;
            }
            reader.close();
        } 
        catch (FileNotFoundException e) {
            System.out.println("Where is the file???????????");
        }
    }
    public static Room getRoom(int roomNum){
        return rooms[roomNum];
    }
    public static Room getRoom(int x, int y){
        return rooms[GameManager.twoToOneD(x, y)];
    }
}
