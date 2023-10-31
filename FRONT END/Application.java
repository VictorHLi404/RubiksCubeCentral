import javax.swing.*;
import java.awt.*;


public class Application {
    /*
     * STUFF TO DO
     * TODO automatic element resizing for different monitor sizes with dimension
     * 
     */
    public static int windowCount = 2;

    public static JFrame frame;
    public static Window currentWindow;
    public static Window[] windowList = new Window[windowCount];
    public static void main(String[] args) {
        frame = new JFrame("Rubik's Cube App");
        initializeFrame(frame);

        windowList[0] = new Window("Window1", 400, 200);
        windowList[1] = new Window("Window2", 400, 200);
        windowList[0].getWindow().setBackground(Color.blue);
        windowList[1].getWindow().setBackground(Color.yellow);

        
        changeWindow("Window1");
        
    } 

    public static void initializeFrame(JFrame frame) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(screenSize.width, screenSize.height);
        frame.setVisible(true);//making the frame visible  
    }

    public static void changeWindow(String windowID) {
        
        Window bufferWindow = null;

        for (int i = 0; i < windowList.length; i++) {
            if (windowList[i].getwindowID() == windowID) {
                bufferWindow = windowList[i];
                break;
            }
        }

        frame.getContentPane().removeAll();
        frame.add(bufferWindow.getWindow());
        frame.validate();
    }



}
