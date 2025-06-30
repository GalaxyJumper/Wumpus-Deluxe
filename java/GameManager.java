
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.Timer;

public class GameManager implements ActionListener{
    private static final double SQRT3 = Math.sqrt(3);


    double currentOriginX = 0;
    double currentOriginY = 0;
    Room currentRoom;

    Timer timer;
    Gui gui;
    Input input;
    double xVel, yVel;
    int framesLastSecond = 0;
    int fps = 0;
    int lastSecond = (int)System.currentTimeMillis();
    public GameManager(){
        Map.loadMapFromFile();
        Map.generateMap();
        System.exit(fps);
        currentRoom = Map.getRoom(0, 0);
        input = new Input();
        gui = new Gui(1080, 720, input);
        xVel = 0;
        yVel = 0;
        timer = new Timer(5, this);
        timer.start();
    
    }


    ///////////// MAIN LOOP //////////////
    // Only method calls should happen here!!!
    public void actionPerformed(ActionEvent e) {
        if((int)System.currentTimeMillis() - lastSecond > 1000){
            lastSecond = (int)System.currentTimeMillis();
            fps = framesLastSecond;
            framesLastSecond = 0;
        } else {
            framesLastSecond ++;
        }
        if(Input.getKey(KeyEvent.VK_W)){
            yVel -= 0.05;
        }
        if(Input.getKey(KeyEvent.VK_A)){
            xVel -= 0.05;
        }
        if(Input.getKey(KeyEvent.VK_S)){
            yVel += 0.05;
        }
        if(Input.getKey(KeyEvent.VK_D)){
            xVel += 0.05;
        }
        xVel *= 0.95;
        yVel *= 0.95;
        gui.background(40, 40, 40);

        // TODO: Move to its own method i just need this working now please :)
        if(Math.hypot(Gui.cameraX - currentOriginX, Gui.cameraY - currentOriginY) > Gui.roomSize){
            double deltaX = Gui.cameraX - currentOriginX;
            double deltaY = Gui.cameraY - currentOriginY;
            // Find newX, newY
            // Update origin x,y
            double angle = Math.atan2(deltaY, deltaX) + (double)(Math.PI/6);
            double direction = Math.floor(angle / ((double)(Math.PI/3.0)));
            currentOriginX += (double)(Gui.roomSize * SQRT3)*Math.cos(direction * Math.PI/3.0);
            currentOriginY += (double)(Gui.roomSize * SQRT3)*Math.sin(direction * Math.PI/3.0);
        }

        gui.moveCamera(xVel, yVel);
        //gui.drawMap(0, 0);
        
        gui.drawRoom(currentOriginX, currentOriginY, fps);
        for(int i = 0; i < currentRoom.getConnectedRooms().length; i++){
            Room connectedRoom = Map.getRoom(currentRoom.getConnectedRooms()[i]);
            double x = roomXtoAbs(connectedRoom.getX(), connectedRoom.getY());
            double y = roomYtoAbs(connectedRoom.getY());
            gui.drawRoom(x, y, i);
            System.out.println(currentRoom.getConnectedRooms().length);
        }

        gui.displayFPS(fps);
        gui.repaint();
    }




    ////////////////////////////////////////////////////
    /// STATIC (UTILITY) METHODS
    ///////////////////////////////////////////////////

    

    public static int twoToOneD(int x, int y){
        return y * Map.MAP_WIDTH + x;
    }
    public static int[] oneToTwoD(int n){
        return new int[]{
            n % Map.MAP_WIDTH,
            n / Map.MAP_WIDTH
        };
    }
    public static double absXTo3D(double xPlane, double yPlane){ 
        double x3d = xPlane - Gui.cameraX;
        double z3d = (yPlane - Gui.cameraY) * Math.cos(Gui.CAMERA_ANGLE) + Gui.cameraZ;
        return x3d * (Gui.FOCAL_LENGTH / z3d) + Gui.WIDTH / 2;
    }
    public static double absYTo3D(double yPlane){
        double y3d = (yPlane - Gui.cameraY) * Math.sin(Gui.CAMERA_ANGLE);
        double z3d = (yPlane - Gui.cameraY) * Math.cos(Gui.CAMERA_ANGLE) + Gui.cameraZ;
        return y3d * (Gui.FOCAL_LENGTH / z3d) + Gui.HEIGHT / 2;
    }
    public static double[] absXYTo3D(double xPlane, double yPlane){
        double x3d = xPlane - Gui.cameraX;
        double y3d = (yPlane - Gui.cameraY) * Math.sin(Gui.CAMERA_ANGLE);
        double z3d = (yPlane - Gui.cameraY) * Math.cos(Gui.CAMERA_ANGLE) + Gui.cameraZ;
        return new double[] {
            x3d * (Gui.FOCAL_LENGTH / z3d) + Gui.WIDTH / 2,
            y3d * (Gui.FOCAL_LENGTH / z3d) + Gui.HEIGHT / 2
        }; 
    }
    public static double roomXtoAbs(int x, int y){
        return (y % 2 == 1)?
            x * SQRT3 * Gui.roomSize + (SQRT3 * Gui.roomSize * 0.5) :
            x * SQRT3 * Gui.roomSize;            
    }
    public static double roomYtoAbs(int y){
        return y * Gui.roomSize * 1.5;
    }
    public static double[] roomXYtoAbs(int x, int y){
        return new double[] { 
            (y % 2 == 1)?
                x * SQRT3 * Gui.roomSize + (SQRT3 * Gui.roomSize * 0.5) :
                x * SQRT3 * Gui.roomSize,

            y * Gui.roomSize * 1.5
        };

    }
    
}
