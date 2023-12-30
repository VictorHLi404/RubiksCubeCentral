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

    public static String[] blankString = {""};

    public static String currentColor = null;
    // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    public void runApplication() throws IOException {
        frame = new JFrame("Rubik's Cube App");
        initializeFrame(frame);

        // -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
        windowList[0] = new Window("Title Window", displayHeight, displayWidth);
        windowList[0].getWindow().setBackground(standardBackgroundColor);

        windowList[0].add(new TextDisplay("titleText", 450, 125, 100, 1025, 1, standardBackgroundColor, new String[] {"RUBIK'S CUBE CENTRAL"}, FontList.titleFont));
        windowList[0].add(new TextDisplay("subtitleText", 675, 240, 100, 525, 1, standardBackgroundColor, new String[] {"By Victor Li and Su Nguyen"}, FontList.subtitleFont));
        windowList[0].add(new WindowChangeButton("goToSolver", 675, 350, 100, 525, 1, Color.WHITE, this, new String[] {"CUBE SOLVER"}, FontList.subtitleFont, "Solver Window"));
        windowList[0].add(new WindowChangeButton("goToDatabase", 675, 460, 100, 525, 1, Color.WHITE, this, new String[] {"DATABASE"}, FontList.subtitleFont, "Database Window"));
        windowList[0].add(new WindowChangeButton("goToTimer", 675, 570, 100, 525, 1, Color.WHITE, this, new String[] {"TIMER"}, FontList.subtitleFont, "Timer Window"));
        windowList[0].add(new QuitButton("QuitApp", 675, 680, 100, 525, 1, Color.WHITE, this, new String[] {"QUIT"}, FontList.subtitleFont));

        // -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

        windowList[1] = new Window("Solver Window", displayHeight, displayWidth);
        windowList[1].getWindow().setBackground(standardBackgroundColor);

        windowList[1].add(new TextDisplay("titleText", 50, 50, 100, 1025, 1, standardBackgroundColor, new String[] {"RUBIKS CUBE SOLVER"}, FontList.titleFont));
        windowList[1].add(new TextDisplay("explanationText", 50, 160, 100, 1500, 1, standardBackgroundColor, new String[] {"Construct your current scramble by clicking on a color, and then clicking on the respective square where it is on the cube.\nEnsure that the cube you construct is in a valid state, otherwise the program will not work."}, FontList.standardFont));

        windowList[1].add(new ColorSwatch("redColorSwatch", 50, 270, 75, 275, 1, standardBackgroundColor, this, blankString, FontList.titleFont, "RED"));
        windowList[1].add(new ColorSwatch("greenColorSwatch", 360, 270, 75, 275, 1, standardBackgroundColor, this, blankString, FontList.titleFont, "GREEN"));
        windowList[1].add(new ColorSwatch("yellowColorSwatch", 670, 270, 75, 275, 1, standardBackgroundColor, this, blankString, FontList.titleFont, "YELLOW"));
        windowList[1].add(new ColorSwatch("whiteColorSwatch", 980, 270, 75, 275, 1, standardBackgroundColor, this, blankString, FontList.titleFont, "WHITE"));
        windowList[1].add(new ColorSwatch("orangeColorSwatch", 1290, 270, 75, 275, 1, standardBackgroundColor, this, blankString, FontList.titleFont, "ORANGE"));
        windowList[1].add(new ColorSwatch("blueColorSwatch", 1600, 270, 75, 275, 1, standardBackgroundColor, this, blankString, FontList.titleFont, "BLUE"));

        windowList[1].add(new BlockFace("centerTest", 50, 500, 60, 60, 1, standardBackgroundColor, this, blankString, FontList.titleFont));
        windowList[1].add(new WindowChangeButton("goToMain", 1475, 50, 100, 400, 1, Color.WHITE, this, new String[] {"BACK TO MAIN"}, FontList.subtitleFont, "Title Window"));

        changeWindow("Title Window");
        loadObjectDatabase();
    } 

    public static void loadObjectDatabase() {
        for (int i = 0; i < windowCount; i++) {
            DisplayElement[] elementList = windowList[i].getElementList();
            for (int j = 0; j < windowList[i].getElementListSize(); j++) {
                objectDatabase.put(elementList[j].getId(), elementList[j]);
            }
        }
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
        String command = e.getActionCommand();
        if (command.contains("CHANGE WINDOW ")) {
            command = command.replace("CHANGE WINDOW ", "");
            changeWindow(command);
        }
        else if (command.contains("QUIT")) {
            // TODO implement saving protocol for databases n shit
            System.exit(0);
        }
        else if (command.contains("SWITCH CURRENT COLOR TO ")) {
            command = command.replace("SWITCH CURRENT COLOR TO ", "");
            currentColor = command;
            System.out.println("CURRENT COLOR IS " + currentColor);
        }
        else if (command.contains("CHANGE CURRENT COLOR OF BLOCKFACE")) { // assuming currently on Solver Window
            DisplayElement[] elementList = currentWindow.getElementList();
            Object sourceObject = e.getSource();

            for (int i = 0; i < currentWindow.getElementListSize(); i++) {
                DisplayElement currentElement = elementList[i];

                if (currentElement.getType().equals("BlockFace")) {
                    BlockFace currentBlockFace = (BlockFace) currentElement;
                    if (currentBlockFace.getButton().equals(sourceObject)) {
                        currentBlockFace.updateColor(currentColor);
                        break;
                    }
                }
            }
        }
    }
}
