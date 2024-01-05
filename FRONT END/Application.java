import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Application implements ActionListener {
    /*
     * STUFF TO DO
     * TODO automatic element resizing for different monitor sizes with dimension
     * 
     */
    public static int windowCount = 4;

    public static JFrame frame;
    public static Window currentWindow;
    public static Window[] windowList = new Window[windowCount];
    public static int displayHeight;
    public static int displayWidth;

    public static Color standardBackgroundColor = Color.getHSBColor((float) 0.608, (float) 0.39, (float) 0.99);
    public static HashMap<String, Object> objectDatabase = new HashMap<String, Object>();

    public static String[] blankString = {""};

    public static String currentColor = null;

    public static String currentScrambleView = null;

    public static DatabaseView solveDatabase;
    // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    public void runApplication() throws IOException {
        frame = new JFrame("Rubik's Cube App");
        initializeFrame(frame);

        // -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
        windowList[0] = new Window("Title Window", displayHeight, displayWidth);
        windowList[0].getWindow().setBackground(standardBackgroundColor);

        windowList[0].add(new TextDisplay("titleText", 450, 125, 100, 1025, 1, standardBackgroundColor, new String[] {"RUBIK'S CUBE CENTRAL"}, FontList.titleFont));
        windowList[0].add(new TextDisplay("subtitleText", 675, 240, 100, 525, 1, standardBackgroundColor, new String[] {"By Victor Li and Su Nguyen"}, FontList.subtitleFont));
        windowList[0].add(new WindowChangeButton("titleGoToSolver", 675, 350, 100, 525, 1, Color.WHITE, this, new String[] {"CUBE SOLVER"}, FontList.subtitleFont, "Solver Window"));
        windowList[0].add(new WindowChangeButton("titleGoToDatabase", 675, 460, 100, 525, 1, Color.WHITE, this, new String[] {"DATABASE"}, FontList.subtitleFont, "Database Window"));
        windowList[0].add(new WindowChangeButton("titleGoToTimer", 675, 570, 100, 525, 1, Color.WHITE, this, new String[] {"TIMER"}, FontList.subtitleFont, "Timer Window"));
        windowList[0].add(new QuitButton("titleQuitApp", 675, 680, 100, 525, 1, Color.WHITE, this, new String[] {"QUIT"}, FontList.subtitleFont));

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
        
        buildCubeFace(windowList[1], "ORANGE", new String[] {"OCenter", "OBEdge", "OGEdge", "OWEdge", "OYEdge",
        "OBWCorner", "OGWCorner", "OBYCorner", "OGYCorner"},  720, 650, 60, 60);
        buildCubeFace(windowList[1], "GREEN", new String[] {"GCenter", "GOEdge", "GREdge", "GWEdge", "GYEdge",
        "GOWCorner", "GRWCorner", "GOYCorner", "GRYCorner"},900, 650, 60, 60);
        buildCubeFace(windowList[1], "RED", new String[] {"RCenter", "RGEdge", "RBEdge", "RWEdge", "RYEdge",
        "RGWCorner", "RBWCorner", "RGYCorner", "RBYCorner"}, 1080, 650, 60, 60);
        buildCubeFace(windowList[1],"BLUE", new String[] {"BCenter", "BREdge", "BOEdge", "BWEdge", "BYEdge",
        "BRWCorner", "BOWCorner", "BRYCorner", "BOYCorner"},  1260, 650, 60, 60);
        buildCubeFace(windowList[1],"WHITE", new String[] {"WCenter", "WOEdge", "WREdge", "WBEdge", "WGEdge",
        "WOBCorner", "WRBCorner", "WOGCorner", "WRGCorner"},  900, 470, 60, 60);
        buildCubeFace(windowList[1], "YELLOW", new String[] {"YCenter", "YOEdge", "YREdge", "YGEdge", "YBEdge",
        "YOGCorner", "YRGCorner", "YOBCorner", "YRBCorner"},  900, 830, 60, 60);
        
        windowList[1].add(new WindowChangeButton("solverGoToMain", 1475, 50, 100, 400, 1, Color.WHITE, this, new String[] {"BACK TO MAIN"}, FontList.subtitleFont, "Title Window"));

//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

        windowList[2] = new Window("Timer Window", displayHeight, displayWidth);
        windowList[2].getWindow().setBackground(standardBackgroundColor);
        windowList[2].add(new TextDisplay("titleText", 50, 50, 100, 1025, 1, standardBackgroundColor, new String[] {"RUBIKS CUBE TIMER"}, FontList.titleFont));
        windowList[2].add(new TextDisplay("subtitleText", 50, 160, 100, 1500, 1, standardBackgroundColor, new String[] {"Place both hands on the mouse or trackpad that you will click the timer with, and then click the time on screen to start.\nClick the time on screen to stop when you have finished the solve.\nAfter finishing a run, you can upload it to the database or reset the timer for another run."}, FontList.standardFont));
        
        windowList[2].add(new WindowChangeButton("timerGoToMain", 1475, 50, 100, 400, 1, Color.WHITE, this, new String[] {"BACK TO MAIN"}, FontList.subtitleFont, "Title Window"));
        windowList[2].add(new TimerButton("timerButton", 190, 300, 400, 1500, 1, Color.WHITE, this, new String[] {"00:00:000"}, FontList.massiveTimerFont));
        windowList[2].add(new ResetTimerButton("resetTimerButton", 190, 750, 200, 700, 1, Color.WHITE, this, new String[] {"RESET TIMER"}, FontList.titleFont));
        //TODO AUTO UPLOAD RUN BUTTON
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

        windowList[3] = new Window("Database Window", displayHeight, displayWidth) ;
        windowList[3].getWindow().setBackground(standardBackgroundColor);
        windowList[3].add(new TextDisplay("titleText", 50, 50, 100, 1300, 1, standardBackgroundColor, new String[] {"RUBIKS CUBE RUN DATABASE"}, FontList.titleFont)); 
        windowList[3].add(new TextDisplay("subtitleText", 50, 160, 100, 1400, 1, standardBackgroundColor, new String[] {"View your previous runs sorted by most recent or fastest time.\nUpload new runs to the database with the button below."}, FontList.standardFont));
        windowList[3].add(new WindowChangeButton("databaseGoToMain", 1475, 50, 100, 400, 1, Color.WHITE, this, new String[] {"BACK TO MAIN"}, FontList.subtitleFont, "Title Window"));

        windowList[3].add(new TextDisplay("#Heading", 50, 300, 50, 30, 1, standardBackgroundColor, new String[] {"#"}, FontList.subtitleFont)); 
        windowList[3].add(new TextDisplay("timeHeading", 170, 300, 50, 100, 1, standardBackgroundColor, new String[] {"TIME"}, FontList.subtitleFont)); 
        windowList[3].add(new TextDisplay("dateRecordedHeading", 370, 300, 50, 350, 1, standardBackgroundColor, new String[] {"DATE RECORDED"}, FontList.subtitleFont));
        windowList[3].add(new TextDisplay("scrambleHeading", 820, 300, 50, 360, 1, standardBackgroundColor, new String[] {"GIVEN SCRAMBLE"}, FontList.subtitleFont));

        windowList[3].add(new TextDisplay("slot1DataDisplay", 50, 410, 100, 720, 1, standardBackgroundColor, new String[] {"AAAAAAAAAAAAAAAAAAAAAAAAA"}, FontList.subtitleFont));
        windowList[3].add(new TextDisplay("slot2DataDisplay", 50, 520, 100, 720, 1, standardBackgroundColor, new String[] {"AAAAAAAAAAAAAAAAAAAAAAAAA"}, FontList.subtitleFont)); 
        windowList[3].add(new TextDisplay("slot3DataDisplay", 50, 630, 100, 720, 1, standardBackgroundColor, new String[] {"AAAAAAAAAAAAAAAAAAAAAAAAA"}, FontList.subtitleFont)); 
        windowList[3].add(new TextDisplay("slot4DataDisplay", 50, 740, 100, 720, 1, standardBackgroundColor, new String[] {"AAAAAAAAAAAAAAAAAAAAAAAAA"}, FontList.subtitleFont)); 
        windowList[3].add(new TextDisplay("slot5DataDisplay", 50, 850, 100, 720, 1, standardBackgroundColor, new String[] {"AAAAAAAAAAAAAAAAAAAAAAAAA"}, FontList.subtitleFont));  

        windowList[3].add(new ScrambleViewButton("slot1ScrambleView", 820, 410, 60, 360, 1, Color.WHITE, this, new String[] {"VIEW SCRAMBLE"}, FontList.standardFont, "Scramble View Window", null));
        windowList[3].add(new ScrambleViewButton("slot1ScrambleView", 820, 520, 60, 360, 1, Color.WHITE, this, new String[] {"VIEW SCRAMBLE"}, FontList.standardFont, "Scramble View Window", null));
        windowList[3].add(new ScrambleViewButton("slot1ScrambleView", 820, 630, 60, 360, 1, Color.WHITE, this, new String[] {"VIEW SCRAMBLE"}, FontList.standardFont, "Scramble View Window", null));
        windowList[3].add(new ScrambleViewButton("slot1ScrambleView", 820, 740, 60, 360, 1, Color.WHITE, this, new String[] {"VIEW SCRAMBLE"}, FontList.standardFont, "Scramble View Window", null));
        windowList[3].add(new ScrambleViewButton("slot1ScrambleView", 820, 850, 60, 360, 1, Color.WHITE, this, new String[] {"VIEW SCRAMBLE"}, FontList.standardFont, "Scramble View Window", null));

        windowList[3].add(new TextDisplay("averageHeading", 1250, 500, 100, 700, 1, standardBackgroundColor, new String[] {"TIME AVERAGE"}, FontList.titleFont));
        windowList[3].add(new TextDisplay("averageTimeDisplay", 1350, 600, 100, 500, 1, standardBackgroundColor, new String[] {"00:00:000"}, FontList.titleFont)); 
//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
        solveDatabase = new DatabaseView(DatabaseIO.loadSolves());
        loadObjectDatabase();
        loadPage();
        changeWindow("Title Window");
    } 

    public void buildCubeFace(Window window, String initialColor, String[] id, int centerXCoord, int centerYCoord, int height, int width) {
        window.add(new NonEditableBlockFace(id[0], centerXCoord, centerYCoord, height, width, 1, standardBackgroundColor, this, blankString, FontList.titleFont, initialColor));
        
        window.add(new BlockFace(id[1], centerXCoord-width, centerYCoord, height, width, 1, standardBackgroundColor, this, blankString, FontList.titleFont)); // LEFT
        window.add(new BlockFace(id[2], centerXCoord+width, centerYCoord, height, width, 1, standardBackgroundColor, this, blankString, FontList.titleFont)); // RIGHT
        window.add(new BlockFace(id[3], centerXCoord, centerYCoord-height, height, width, 1, standardBackgroundColor, this, blankString, FontList.titleFont)); // UP
        window.add(new BlockFace(id[4], centerXCoord, centerYCoord+height, height, width, 1, standardBackgroundColor, this, blankString, FontList.titleFont)); // DOWN

        window.add(new BlockFace(id[5], centerXCoord-width, centerYCoord-height, height, width, 1, standardBackgroundColor, this, blankString, FontList.titleFont)); // LEFT UP
        window.add(new BlockFace(id[6], centerXCoord+width, centerYCoord-height, height, width, 1, standardBackgroundColor, this, blankString, FontList.titleFont)); // RIGHT UP
        window.add(new BlockFace(id[7], centerXCoord-width, centerYCoord+height, height, width, 1, standardBackgroundColor, this, blankString, FontList.titleFont)); // LEFT DOWN
        window.add(new BlockFace(id[8], centerXCoord+width, centerYCoord+height, height, width, 1, standardBackgroundColor, this, blankString, FontList.titleFont)); // RIGHT DOWN
    }
    
    public static void initializeFrame(JFrame frame) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        displayHeight = screenSize.height;
        displayWidth = screenSize.width;
        frame.setSize(displayWidth, displayHeight);
        frame.setVisible(true);//making the frame visible  
    }

    public static void refresh() {
        frame.getContentPane().removeAll();
        frame.validate();
    }
    
    public static void loadObjectDatabase() {
        for (int i = 0; i < windowCount; i++) {
            DisplayElement[] elementList = windowList[i].getElementList();
            for (int j = 0; j < windowList[i].getElementListSize(); j++) {
                objectDatabase.put(elementList[j].getId(), elementList[j]);
            }
        }
    }

    public static void loadPage() {
        String[] pageData = solveDatabase.getCurrentPage();
        for (int i = 1; i <= 5; i++) {
            String id = "slot" + String.valueOf(i) + "DataDisplay";
            TextDisplay textDisplay = (TextDisplay) objectDatabase.get(id);
            textDisplay.updateTextDisplay(pageData[i-1]);
        }
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
                        System.out.println(currentBlockFace.getId());
                        break;
                    }
                }
            }
        }
        else if (command.contains("DO NOT CHANGE CURRENT COLOR OF BLOCKFACE")) {
            return;
        }
        else if (command.contains("STOP TIMER")) {
            TimerButton button = (TimerButton) objectDatabase.get("timerButton");
            button.resetTimer();
        }
    }
}
