import javax.swing.*;
import java.awt.*;
import java.io.IOException;


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
    public static int displayHeight;
    public static int displayWidth;

    public static void main(String[] args) throws IOException {
        frame = new JFrame("Rubik's Cube App");
        initializeFrame(frame);

        windowList[0] = new Window("Window1", displayHeight, displayWidth);
        windowList[1] = new Window("Window2", displayHeight, displayWidth);
        windowList[0].getWindow().setBackground(Color.blue);
        windowList[1].getWindow().setBackground(Color.yellow);


        
        String[] textWindowText = new String[] {"hello world"};
        String[] textWindowText2 = new String[] {"goodbye world"};
        TextDisplay textWindow = new TextDisplay("TextDisplay", "mainTextDisplay", 600, 600, 100, 100, 1, textWindowText, FontList.getStandardFont());
        TextDisplay textWindow2 = new TextDisplay("TextDisplay", "mainTextDisplay", 250, 250, 100, 100, 2, textWindowText2, FontList.getStandardFont());
        
        ImageContainer image = new ImageContainer("strawberry", "strawberry", 200, 200, 100, 100, 1, "strawberry.jpg");
        changeWindow("Window2");
        windowList[1].add(textWindow);
        windowList[1].add(textWindow2);
        windowList[1].add(image);
        
    } 

    public static void initializeFrame(JFrame frame) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        displayHeight = screenSize.height;
        displayWidth = screenSize.width;
        frame.setSize(displayWidth, displayHeight);
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

        refresh();
        frame.add(bufferWindow.getWindow());
        frame.validate();
        currentWindow = bufferWindow;
    }

    public static void refresh() {
        frame.getContentPane().removeAll();
        frame.validate();
    }



}
