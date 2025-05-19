public class Map { 
    private Room[] rooms = new Room[30];
    public Map(){
        
    }
    public void getAdjacents(int roomNum){

    }
    public void getAdjacents(int x, int y){

    }
    public void generateMap(){

    }
    public Room getRoom(int roomNum){
        return rooms[roomNum];
    }
    public Room getRoom(int x, int y){
        return rooms[GameManager.twoToOneD(x, y)];
    }
}
