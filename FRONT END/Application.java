import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;


public class Application implements ActionListener {
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

    public static Color standardBackgroundColor = Color.getHSBColor((float) 0.608, (float) 0.39, (float) 0.99);
    public static HashMap<String, Object> objectDatabase = new HashMap<String, Object>();

    public void runApplication() throws IOException {
        frame = new JFrame("Rubik's Cube App");
        initializeFrame(frame);

        windowList[0] = new Window("Title Window", displayHeight, displayWidth);
        windowList[0].getWindow().setBackground(standardBackgroundColor);

        windowList[0].add(new TextDisplay("titleText", 575, 150, 100, 725, 1, standardBackgroundColor, new String[] {"RUBIK'S CUBE CENTRAL"}, FontList.titleFont));
        windowList[0].add(new TextDisplay("subtitleText", 675, 240, 100, 525, 1, standardBackgroundColor, new String[] {"By Victor Li and Su Nguyen"}, FontList.subtitleFont));
        windowList[0].add(new WindowChangeButton("goToSolver", 675, 350, 100, 525, 1, Color.WHITE, this, new String[] {"CUBE SOLVER"}, FontList.subtitleFont, "Solver Window"));
        windowList[0].add(new WindowChangeButton("goToDatabase", 675, 460, 100, 525, 1, Color.WHITE, this, new String[] {"DATABASE"}, FontList.subtitleFont, "Solver Window"));
        windowList[0].add(new QuitButton("QuitApp", 675, 570, 100, 525, 1, Color.WHITE, this, new String[] {"QUIT"}, FontList.subtitleFont));
        //TextDisplay yaz = new TextDisplay("TextDisplay", "subTitleText", 675, 200, 50, 525, 2, standardBackgroundColor, new String[] {"By Victor Li and Su Nguyen"}, FontList.titleFont);
        //windowList[0].add(yaz);


        windowList[1] = new Window("Solver Window", displayHeight, displayWidth);

        windowList[1].getWindow().setBackground(Color.yellow);

        objectDatabase.put(windowList[0].getwindowID(), windowList[0]);
        objectDatabase.put(windowList[1].getwindowID(), windowList[1]);

        changeWindow("Title Window");
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
            if (windowList[i].getwindowID().equals(windowID)) {
                bufferWindow = windowList[i];
                break;
            }
        }

        if (bufferWindow == null) {
            System.out.println("NO WINDOW WITH THIS ID COULD BE FOUND");
            return;
        }
        refresh();
        frame.add(bufferWindow.getWindow());
        frame.revalidate();
        frame.repaint();
        currentWindow = bufferWindow;
    }

    public static void refresh() {
        frame.getContentPane().removeAll();
        frame.validate();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        String command = e.getActionCommand();
        if (command.contains("CHANGE WINDOW ")) {
            command = command.replace("CHANGE WINDOW ", "");
            changeWindow(command);
        }
        else if (command.contains("QUIT")) {
            // TODO implement saving protocol for databases n shit
            System.exit(0);
        }
    }



}
