
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.Timer;

public class GameManager implements ActionListener{
    Timer timer;
    Gui gui;
    Input input;
    double xVel, yVel;
    int framesLastSecond = 0;
    int fps = 0;
    int lastSecond = (int)System.currentTimeMillis();
    public GameManager(){
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
        gui.moveCamera(xVel, yVel);
        gui.background(40, 40, 40);
        gui.drawMap(0, 0);
        gui.displayFPS(fps);
        gui.repaint();
    }




    ////////////////////////////////////////////////////
    /// STATIC (UTILITY) METHODS
    ///////////////////////////////////////////////////

    public static int twoToOneD(int x, int y){
        return (y * 6) + x;
    }
    public static int[] oneToTwoD(int n){
        return new int[]{
            n / 6,
            n % 6
        };
    }
    public static double screenXTo3D(double xPlane, double yPlane){ 
        double x3d = xPlane - Gui.cameraX;
        double z3d = (yPlane - Gui.cameraY) * Math.cos(Gui.CAMERA_ANGLE) + Gui.cameraZ;
        return x3d * (Gui.FOCAL_LENGTH / z3d) + Gui.WIDTH / 2;
    }
    public static double screenYTo3D(double yPlane){
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
    public static int[] determineVisibleRooms(int playerX, int playerY){

    }
    public static int determineCurRoom(double x, double y){
        double tileWidth = 2 * Gui.roomSize * 3.0/4.0;
        double tileHeight = Gui.roomSize * Math.sqrt(3);

        double xTiles = x % tileWidth;
        double yTiles = y % tileHeight;

        double tileLeftCenterX = xTiles * tileWidth;
        double tileLeftCenterY = yTiles * tileHeight + (tileHeight / 2);
        // Bottom triangle
        if(y > 1.73205 * (x - tileLeftCenterX) + tileLeftCenterY){
            
        }
        // Top triangle
        else if(y < -1.73205 * (x - tileLeftCenterX) + tileLeftCenterY){

        } 
        // Rest of the rect
        else {

        }
    }
}
