//-------------------------------------------------//
//                    Imports                      //
//-------------------------------------------------// 
import javax.swing.*;


import java.awt.*;
import java.util.ArrayList;
//-------------------------------------------------//
//                      Gui                        //
//-------------------------------------------------//
public class Gui extends JPanel{
    public static final int WIDTH = 1080;
    public static final int HEIGHT = 720;
    public static final int FOCAL_LENGTH = WIDTH / 2;
    public static final double CAMERA_ANGLE = -5.5 * Math.PI / 4;
    public static final double CAM_DIST = 1000;
    public static final int roomSize = 100;
    private static final double SQRT3 = Math.sqrt(3);
    public static double cameraX = 0;
    public static double cameraY = 0;
    public static double cameraZ = 100;
    Images images;
    // Queues up all the draw commands in a frame so that they can all be executed at the end of the frame at the correct time.
    ArrayList<GraphicsRunnable> drawQueue;
    // You need a frame to draw things on.
    JFrame frame = new JFrame("Hunt the Wumpus | DELUXE EDITION");
    
    //////////////
    //Constuctor
    //////////////
    public Gui(int width, int height, Input input) {
        // JFrame setup
        this.setSize(width, height);
        frame.setSize(width, height);

        frame.setVisible(true);
        this.setVisible(true);

        frame.addKeyListener(input);
        frame.setFocusable(true);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        this.setDoubleBuffered(true);
        // End JFrame setup



        drawQueue = new ArrayList<GraphicsRunnable>();
        images = new Images("img", Transparency.TRANSLUCENT);
    }

//-------------------------------------------------//
//                    Methods                      //
//-------------------------------------------------// 

    // Runs every frame and draws stuff to the screen.
    // Called internally by Swing.
    public void paintComponent(Graphics g){
        Graphics2D g2d = (Graphics2D)g;
        // Go through every item in the queue and draw it.
        for(int i = 0; i < drawQueue.size(); i++){
            drawQueue.get(i).draw(g2d);
        }
        // Prevent memory leaks (turns out adding 10-50 items to an ArrayList every frame gets slow fast)
        drawQueue.clear();
    }
    // Allows other classes to run custom draw code.
    public void addToQueue(GraphicsRunnable g){
        drawQueue.add(g);
    }

    /////////////////////////////////////////////////
    /// DRAW METHODS
    /////////////////////////////////////////////////
    public void background(int r, int g, int b){
        drawQueue.add(new GraphicsRunnable() {
            public void draw(Graphics2D g2d){
                g2d.setColor(new Color(r, g, b));
                g2d.fillRect(0, 0, Gui.WIDTH, Gui.HEIGHT);
            }
        });
    }
    // Draws a number to the screen, usually the FPS. Could really be anything.
    public void displayFPS(int fps){
        drawQueue.add(new GraphicsRunnable() {
            public void draw(Graphics2D g2d){
                g2d.setColor(new Color(0, 175, 255));
                g2d.drawString(String.valueOf(fps), 40, 60);  
                g2d.drawImage(images.getImage("vingetts"), 0, 0, Gui.WIDTH, Gui.HEIGHT, null);
            }
        });
    }

    public void drawRoom(double x, double y, int roomNum){
        drawQueue.add(new GraphicsRunnable() {
            public void draw(Graphics2D g2d){
                g2d.setColor(Color.WHITE);
                drawHexagon(x, y, roomSize, roomNum, g2d);

                g2d.drawString("" + roomNum, (int)GameManager.screenXTo3D(x, y), (int)GameManager.screenYTo3D(y) - 10);

            }
        });
    }
    public void drawMap(int offsetX, int offsetY){
        for(int x = 0; x < 6; x++){
            for(int y = 0; y < 5; y++){
                double roomX = (y % 2 == 1)?
                    x * SQRT3 * roomSize + (SQRT3 * roomSize * 0.5) :
                    x * SQRT3 * roomSize;
                double roomY = y * roomSize * 1.5;
                drawRoom(roomX + offsetX, roomY + offsetY, GameManager.twoToOneD(x, y) + 1); 
            }
        }
    }
    private void drawHexagon(double x, double y, double radius, int roomNum, Graphics2D g2d){
        int[] xPoints = new int[6];
        int[] yPoints = new int[6];
        
        g2d.setStroke(new BasicStroke(3f));
        for(int i = 0; i < 6; i++){
             xPoints[i] = (int)GameManager.screenXTo3D(x + radius * Math.cos(Math.toRadians(30 + 60 * i)), y + radius * Math.sin(Math.toRadians(30 + 60 * i)));
             yPoints[i] = (int)GameManager.screenYTo3D(y + radius * Math.sin(Math.toRadians(30 + 60 * i)));
        }
        
        g2d.setColor(new Color(200, 200, 200));
        for(int i = 0; i < 6; i += 2){
            g2d.drawLine(xPoints[i], yPoints[i], xPoints[(i + 1) % 6], yPoints[(i + 1) % 6]);
        }
    }
    /////////////////////////////////////////////////////////////////////
    /// Getters & Setters
    /////////////////////////////////////////////////////////////////////
        
    public void moveCamera(double xAmount, double yAmount){
        cameraX += xAmount;
        cameraY += yAmount;
    }
}