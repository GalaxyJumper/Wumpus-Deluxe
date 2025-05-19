
import java.awt.MouseInfo;
import java.awt.event.*;
public class Input implements MouseListener, KeyListener, MouseMotionListener{
    static double mouseX, mouseY;
    static boolean[] keys = new boolean[90];
    
    public static double mouseX(){
        return mouseX;
    }
    public static double mouseY(){
        return mouseY;
    }
    public static void updateMouse(){
        mouseX = MouseInfo.getPointerInfo().getLocation().getX();
        mouseY = MouseInfo.getPointerInfo().getLocation().getY();
    
    }
    public static boolean getKey(int keyCode){
        return keys[keyCode];
    }
    @Override
    public void mouseMoved(MouseEvent e){

    }
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // Empty
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // Empty
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() < keys.length){
            keys[e.getKeyCode()] = true;
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() < keys.length){
            keys[e.getKeyCode()] = false;
        }
    }
    @Override
    public void mouseDragged(MouseEvent e){

    }
    
}
