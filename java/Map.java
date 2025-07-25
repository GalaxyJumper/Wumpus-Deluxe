import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
public class Map { 

    public static final int MAP_HEIGHT = 6;
    public static final int MAP_WIDTH = 6;

    private static Room[] rooms = new Room[30];
    public static int[] roomData;
    public static void getAdjacents(int roomNum){

    }
    public static void getAdjacents(int x, int y){

    }
    public static void generateMap(){
        Random random = new Random();

        int numTotalRooms = Map.MAP_WIDTH * Map.MAP_HEIGHT;

        // You know what, fuck you. Hardcoded values: if your map is larger there will be longer tunnels!
        // TODO: Rewrite this comment before microsoft shows up.
        int numStartingNodes = random.nextInt(4, 9);

        // Master list of each of the rooms. Index = room id, value = number of available doorways
        int[] numDoorsLeftPerRoom = new int[numTotalRooms];

        // Each room starts with 3 available doorways
        for(int i = 0; i < numTotalRooms; i++) numDoorsLeftPerRoom[i] = 3;

        // List of which structure each room is a part of.
        int[] structureIdPerRoom = new int[numTotalRooms];
        
        // List of all the rooms in each structure (UNORGANIZED: NOT A TREE)
        // Unchecked cast because Java is stupid and doesn't let you instantiate an array of ArrayLists.
        ArrayList<Integer>[] structureLists = (ArrayList<Integer>[]) new ArrayList[numStartingNodes];

        // Keeps track of if each room is available for picking
        boolean[] availableRooms = new boolean[numTotalRooms];
        for(int i = 0; i < availableRooms.length; i++) availableRooms[i] = true;
        
        // Find 4-8 starting nodes and add them to the structures list.
        for(int i = 0; i < numStartingNodes; i++){
            // Pick a random room and guarantee that it is not taken
            int randomRoom = random.nextInt(0, numTotalRooms);
            while(availableRooms[randomRoom] == false){randomRoom = random.nextInt(0, numTotalRooms);}

            // Create a new structure and add the first node to its list.
            structureLists[i] = new ArrayList<Integer>();
            structureLists[i].add(randomRoom);

            // This room is now taken.
            availableRooms[randomRoom] = false;
            
            structureIdPerRoom[randomRoom] = i;
        } 
        
        ArrayList<Integer> currentIterationBranches = new ArrayList<Integer>();
        ArrayList<Integer> nextIterationBranches = new ArrayList<Integer>();
        ArrayList<Integer> tempAdjacents = new ArrayList<Integer>();
        ArrayList<Integer> connections = new ArrayList<Integer>();

        for(int i = 0; i < structureLists.length; i++) nextIterationBranches.add(structureLists[i].get(0));

        while(nextIterationBranches.size() > 0){

            currentIterationBranches.clear();
            currentIterationBranches.addAll(nextIterationBranches);
            nextIterationBranches.clear();
            System.out.println(currentIterationBranches.size());

            // For each branch...
            for(int i = 0; i < currentIterationBranches.size(); i++){

                // Get adjacents of said room
                int[] adjacents = new Room(Room.RoomType.DEFAULT, currentIterationBranches.get(i)).getAdjacents();
                int thisRoomStructID = structureIdPerRoom[currentIterationBranches.get(i)];

                // Put only the valid ones into a list
                for(int k = 0; k < adjacents.length; k++){
                    if(availableRooms[adjacents[k]]) 
                        tempAdjacents.add(adjacents[k]);
                }

                // Pick random ones
                int[] newBranches = GameManager.getUniqueIntsFromArrayList(random.nextInt(1, 4), tempAdjacents);


                for(int k : newBranches){
                    //TODO: Define connections

                    structureLists[thisRoomStructID].add(k);
                    availableRooms[k] = false;
                    structureIdPerRoom[k] = thisRoomStructID;
                    nextIterationBranches.add(k);
                }
            
            }
        }
        for(int i : connections){

        }
        roomData = structureIdPerRoom;
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

                //rooms[index] = new Room(Room.RoomType.DEFAULT, index, connectedRooms);
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
