import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;

public class Application implements ActionListener {
    /*
     * STUFF TO DO
     * 
     */
    public static final int windowCount = 7;

    public static JFrame frame;
    public static Window currentWindow;
    public static Window[] windowList = new Window[windowCount];
    public static int displayHeight;
    public static int displayWidth;

    public static Color standardBackgroundColor = Color.getHSBColor((float) 0.608, (float) 0.39, (float) 0.99);
    
    public static HashMap<String, Object> objectDatabase = new HashMap<String, Object>();

    public static String[] blankString = {""};

    public static String currentColor = null;
    
    public static RubiksCubeNet currentSolveScrambleBuild = null;
    public static RubiksCubeNet currentGenerateScrambleView = null;
    public static RubiksCubeNet currentDatabaseScrambleView = null;

    public static DatabaseView solveDatabase;
    public static HashMap<String, String> errorMessageDatabase;

    // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    public void runApplication() throws IOException {
        frame = new JFrame("Rubik's Cube App");
        initializeFrame(frame);

        // -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
        windowList[0] = new Window("Title Window", displayHeight, displayWidth);

        windowList[0].add(new TextDisplay("titleText", 450, 125, 100, 1025, 1, standardBackgroundColor, new String[] {"RUBIK'S CUBE CENTRAL"}, FontList.titleFont));
        windowList[0].add(new TextDisplay("subtitleText", 675, 240, 100, 525, 1, standardBackgroundColor, new String[] {"By Victor Li and Su Nguyen"}, FontList.subtitleFont));
        windowList[0].add(new WindowChangeButton("titleGoToSolver", 675, 350, 100, 525, 1, Color.WHITE, this, new String[] {"CUBE SOLVER"}, FontList.subtitleFont, "Solver Window"));
        windowList[0].add(new WindowChangeButton("titleGoToDatabase", 675, 460, 100, 525, 1, Color.WHITE, this, new String[] {"DATABASE"}, FontList.subtitleFont, "Database Window"));
        windowList[0].add(new WindowChangeButton("titleGoToScrambler", 675, 570, 100, 525, 1, Color.WHITE, this, new String[] {"SCRAMBLER"}, FontList.subtitleFont, "Scramble Generate Window"));
        windowList[0].add(new WindowChangeButton("titleGoToTimer", 675, 680, 100, 525, 1, Color.WHITE, this, new String[] {"TIMER"}, FontList.subtitleFont, "Timer Window"));
        windowList[0].add(new QuitButton("titleQuitApp", 675, 790, 100, 525, 1, Color.WHITE, this, new String[] {"QUIT"}, FontList.subtitleFont));

        // -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

        windowList[1] = new Window("Solver Window", displayHeight, displayWidth);

        windowList[1].add(new TextDisplay("titleText", 50, 50, 100, 1025, 1, standardBackgroundColor, new String[] {"RUBIKS CUBE SOLVER"}, FontList.titleFont));
        windowList[1].add(new TextDisplay("explanationText", 50, 160, 100, 1500, 1, standardBackgroundColor, new String[] {"Construct your current scramble by clicking on a color, and then clicking on the respective square where it is on the cube.\nWhen looking at the cube, make sure the white center piece is on top, the yellow on the bottom,\nthe green facing you, and the orange facing the left.\nEnsure that the cube you construct is in a valid state, otherwise the program will not work."}, FontList.standardFont));
        
        windowList[1].add(new ColorSwatch("redColorSwatch", 50, 270, 75, 275, 1, standardBackgroundColor, this, blankString, FontList.titleFont, "RED"));
        windowList[1].add(new ColorSwatch("greenColorSwatch", 360, 270, 75, 275, 1, standardBackgroundColor, this, blankString, FontList.titleFont, "GREEN"));
        windowList[1].add(new ColorSwatch("yellowColorSwatch", 670, 270, 75, 275, 1, standardBackgroundColor, this, blankString, FontList.titleFont, "YELLOW"));
        windowList[1].add(new ColorSwatch("whiteColorSwatch", 980, 270, 75, 275, 1, standardBackgroundColor, this, blankString, FontList.titleFont, "WHITE"));
        windowList[1].add(new ColorSwatch("orangeColorSwatch", 1290, 270, 75, 275, 1, standardBackgroundColor, this, blankString, FontList.titleFont, "ORANGE"));
        windowList[1].add(new ColorSwatch("blueColorSwatch", 1600, 270, 75, 275, 1, standardBackgroundColor, this, blankString, FontList.titleFont, "BLUE"));
        
        currentSolveScrambleBuild = new RubiksCubeNet(this, "Solver Window", windowList[1], true, 900, 650, 60, 60);

        windowList[1].add(new WindowChangeButton("solverGoToMain", 1475, 50, 100, 400, 1, Color.WHITE, this, new String[] {"BACK TO MAIN"}, FontList.subtitleFont, "Title Window"));
        windowList[1].add(new UploadNetButton("uploadRubiksNetButton", 1475, 900, 75, 400, 1, Color.WHITE, this, new String[] {"UPLOAD"}, FontList.subtitleFont));
//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

        windowList[2] = new Window("Timer Window", displayHeight, displayWidth);

        windowList[2].add(new TextDisplay("titleText", 50, 50, 100, 1025, 1, standardBackgroundColor, new String[] {"RUBIKS CUBE TIMER"}, FontList.titleFont));
        windowList[2].add(new TextDisplay("subtitleText", 50, 160, 100, 1500, 1, standardBackgroundColor, new String[] {"Place both hands on the mouse or trackpad that you will click the timer with, and then click the time on screen to start.\nClick the time on screen to stop when you have finished the solve.\nAfter finishing a run, you can upload it to the database or reset the timer for another run."}, FontList.standardFont));
        
        windowList[2].add(new WindowChangeButton("timerGoToMain", 1475, 50, 100, 400, 1, Color.WHITE, this, new String[] {"BACK TO MAIN"}, FontList.subtitleFont, "Title Window"));
        windowList[2].add(new TimerButton("timerButton", 190, 300, 400, 1500, 1, Color.WHITE, this, new String[] {"00:00:000"}, FontList.massiveTimerFont));
        windowList[2].add(new ResetTimerButton("resetTimerButton", 190, 750, 200, 700, 1, Color.WHITE, this, new String[] {"RESET TIMER"}, FontList.titleFont));
        //TODO AUTO UPLOAD RUN BUTTON
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

        windowList[3] = new Window("Database Window", displayHeight, displayWidth) ;

        windowList[3].add(new TextDisplay("titleText", 50, 50, 100, 1300, 1, standardBackgroundColor, new String[] {"RUBIKS CUBE RUN DATABASE"}, FontList.titleFont)); 
        windowList[3].add(new TextDisplay("subtitleText", 50, 160, 100, 1400, 1, standardBackgroundColor, new String[] {"View your previous runs sorted by most recent or fastest time. Your top 5 runs in terms of time are shown as well.\nUpload new runs to the database with the button below."}, FontList.standardFont));
        windowList[3].add(new WindowChangeButton("databaseGoToMain", 1475, 50, 100, 400, 1, Color.WHITE, this, new String[] {"BACK TO MAIN"}, FontList.subtitleFont, "Title Window"));


        windowList[3].add(new TextDisplay("#Heading", 125, 250, 50, 30, 1, standardBackgroundColor, new String[] {"#"}, FontList.subtitleFont)); 
        windowList[3].add(new TextDisplay("timeHeading", 275, 250, 50, 100, 1, standardBackgroundColor, new String[] {"TIME"}, FontList.subtitleFont)); 
        windowList[3].add(new TextDisplay("dateRecordedHeading", 475, 250, 50, 350, 1, standardBackgroundColor, new String[] {"DATE RECORDED"}, FontList.subtitleFont));
        windowList[3].add(new TextDisplay("scrambleHeading", 895, 250, 50, 400, 1, standardBackgroundColor, new String[] {"GIVEN SCRAMBLE"}, FontList.subtitleFont));
        
        windowList[3].add(new TextDisplay("slot1Heading", 125, 360, 60, 100, 1, standardBackgroundColor, new String[] {"AAAAAAAAAAAAAAAAAAAAAAAAA"}, FontList.subtitleFont));
        windowList[3].add(new TextDisplay("slot2Heading", 125, 470, 60, 100, 1, standardBackgroundColor, new String[] {"AAAAAAAAAAAAAAAAAAAAAAAAA"}, FontList.subtitleFont)); 
        windowList[3].add(new TextDisplay("slot3Heading", 125, 580, 60, 100, 1, standardBackgroundColor, new String[] {"AAAAAAAAAAAAAAAAAAAAAAAAA"}, FontList.subtitleFont)); 
        windowList[3].add(new TextDisplay("slot4Heading", 125, 690, 60, 100, 1, standardBackgroundColor, new String[] {"AAAAAAAAAAAAAAAAAAAAAAAAA"}, FontList.subtitleFont)); 
        windowList[3].add(new TextDisplay("slot5Heading", 125, 800, 60, 100, 1, standardBackgroundColor, new String[] {"AAAAAAAAAAAAAAAAAAAAAAAAA"}, FontList.subtitleFont));  

        windowList[3].add(new TextDisplay("slot1DataDisplay", 245, 360, 60, 500, 1, standardBackgroundColor, new String[] {"AAAAAAAAAAAAAAAAAAAAAAAAA"}, FontList.subtitleFont));
        windowList[3].add(new TextDisplay("slot2DataDisplay", 245, 470, 60, 500, 1, standardBackgroundColor, new String[] {"AAAAAAAAAAAAAAAAAAAAAAAAA"}, FontList.subtitleFont)); 
        windowList[3].add(new TextDisplay("slot3DataDisplay", 245, 580, 60, 500, 1, standardBackgroundColor, new String[] {"AAAAAAAAAAAAAAAAAAAAAAAAA"}, FontList.subtitleFont)); 
        windowList[3].add(new TextDisplay("slot4DataDisplay", 245, 690, 60, 500, 1, standardBackgroundColor, new String[] {"AAAAAAAAAAAAAAAAAAAAAAAAA"}, FontList.subtitleFont)); 
        windowList[3].add(new TextDisplay("slot5DataDisplay", 245, 800, 60, 500, 1, standardBackgroundColor, new String[] {"AAAAAAAAAAAAAAAAAAAAAAAAA"}, FontList.subtitleFont));  

        windowList[3].add(new ScrambleViewButton("slot1ScrambleView", 895, 360, 60, 360, 1, Color.WHITE, this, new String[] {"VIEW SCRAMBLE"}, FontList.standardFont, "Scramble View Window", null));
        windowList[3].add(new ScrambleViewButton("slot1ScrambleView", 895, 470, 60, 360, 1, Color.WHITE, this, new String[] {"VIEW SCRAMBLE"}, FontList.standardFont, "Scramble View Window", null));
        windowList[3].add(new ScrambleViewButton("slot1ScrambleView", 895, 580, 60, 360, 1, Color.WHITE, this, new String[] {"VIEW SCRAMBLE"}, FontList.standardFont, "Scramble View Window", null));
        windowList[3].add(new ScrambleViewButton("slot1ScrambleView", 895, 690, 60, 360, 1, Color.WHITE, this, new String[] {"VIEW SCRAMBLE"}, FontList.standardFont, "Scramble View Window", null));
        windowList[3].add(new ScrambleViewButton("slot1ScrambleView", 895, 800, 60, 360, 1, Color.WHITE, this, new String[] {"VIEW SCRAMBLE"}, FontList.standardFont, "Scramble View Window", null));

        windowList[3].add(new TextDisplay("averageHeading", 1350, 500, 100, 500, 1, standardBackgroundColor, new String[] {"TIME AVERAGE"}, FontList.headerFont));
        windowList[3].add(new TextDisplay("averageTimeDisplay", 1425, 600, 100, 300, 1, standardBackgroundColor, new String[] {"00:00:000"}, FontList.headerFont)); 

        windowList[3].add(new ChangeDatabasePageButton("databaseScrollBackwardsButton", 125, 880, 75, 200, 1, Color.WHITE, this, new String[] {"<-"}, FontList.titleFont, false));
        windowList[3].add(new ChangeDatabasePageButton("databaseScrollForwardsButton", 1055, 880, 75, 200, 1, Color.WHITE, this, new String[] {"->"}, FontList.titleFont, true));
        windowList[3].add(new ChangeDatabaseSortTypeButton("sortByTimeButton", 350, 880, 75, 300, 1, Color.WHITE, this, new String[] {"SORT BY TIME"}, FontList.standardFont, "TIME"));
        windowList[3].add(new ChangeDatabaseSortTypeButton("sortByDateButton", 670, 880, 75, 300, 1, Color.WHITE, this, new String[] {"SORT BY DATE"}, FontList.standardFont, "DATE"));
        windowList[3].add(new WindowChangeButton("databaseGoToUpload", 1350, 800, 100, 450, 1, Color.WHITE, this, new String[] {"UPLOAD NEW RUN"}, FontList.subtitleFont, "Database Upload Window"));
        //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
        
        windowList[4] = new Window("Scramble View Window", displayHeight, displayWidth);
        windowList[4].getWindow().setBackground(standardBackgroundColor);

//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

        windowList[5] = new Window("Database Upload Window", displayHeight, displayWidth);

        windowList[5].add(new TextDisplay("titleText", 50, 50, 100, 1300, 1, standardBackgroundColor, new String[] {"UPLOAD NEW RUN"}, FontList.titleFont)); 
        windowList[5].add(new TextDisplay("subtitleText", 50, 160, 100, 1400, 1, standardBackgroundColor, new String[] {"Fill out the run below with the time, date, and given scramble.\nIf you have a time and/or scramble currently generated, it will autofill into the fields."}, FontList.standardFont));

        windowList[5].add(new TextDisplay("timeHeading", 150, 300, 100, 200, 1, standardBackgroundColor, new String[] {"TIME"}, FontList.titleFont));
        windowList[5].add(new EditableTextDisplay("minuteInput", 600, 300, 80, 200, 1, Color.WHITE, new String[] {"00"}, FontList.titleFont, 1, 2));
        windowList[5].add(new TextDisplay("Colon", 825, 300, 100, 25, 1, standardBackgroundColor, new String[] {":"}, FontList.titleFont));
        windowList[5].add(new EditableTextDisplay("secondInput", 875, 300, 80, 200, 1, Color.WHITE, new String[] {"00"}, FontList.titleFont, 1, 2));
        windowList[5].add(new TextDisplay("Colon", 1100, 300, 100, 25, 1, standardBackgroundColor, new String[] {":"}, FontList.titleFont));
        windowList[5].add(new EditableTextDisplay("millisecondInput", 1150, 300, 80, 250, 1, Color.WHITE, new String[] {"000"}, FontList.titleFont, 1, 3));

        windowList[5].add(new TextDisplay("dateHeading", 150, 450, 100, 400, 1, standardBackgroundColor, new String[] {"DATE"}, FontList.titleFont));
        windowList[5].add(new EditableTextDisplay("dayInput", 600, 450, 80, 200, 1, Color.WHITE, new String[] {"00"}, FontList.titleFont, 1, 2));
        windowList[5].add(new TextDisplay("Slash", 825, 450, 80, 25, 1, standardBackgroundColor, new String[] {"/"}, FontList.titleFont));
        windowList[5].add(new EditableTextDisplay("monthInput", 875, 450, 80, 200, 1, Color.WHITE, new String[] {"00"}, FontList.titleFont, 1, 2));
        windowList[5].add(new TextDisplay("Slash", 1100, 450, 80, 25, 1, standardBackgroundColor, new String[] {"/"}, FontList.titleFont));
        windowList[5].add(new EditableTextDisplay("yearInput", 1150, 450, 80, 250, 1, Color.WHITE, new String[] {"0000"}, FontList.titleFont, 1, 4));

        windowList[5].add(new TextDisplay("scrambleHeading", 150, 600, 100, 500, 1, standardBackgroundColor, new String[] {"SCRAMBLE"}, FontList.titleFont));
        windowList[5].add(new EditableTextDisplay("moveInput", 800, 600, 80, 600, 1, Color.WHITE, new String[] {"Format like L, R\', B2"}, FontList.standardFont, 3, 30));

        windowList[5].add(new UploadRunButton("runUploadButton", 425, 750, 150, 700, 1, Color.WHITE, this, new String[] {"UPLOAD"}, FontList.subtitleFont));
// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

        windowList[6] = new Window("Scramble Generate Window", displayHeight, displayWidth);
        windowList[6].add(new TextDisplay("titleText", 50, 50, 100, 1300, 1, standardBackgroundColor, new String[] {"SCRAMBLER"}, FontList.titleFont)); 
        windowList[6].add(new TextDisplay("subtitleText", 50, 160, 100, 1400, 1, standardBackgroundColor, new String[] {"Click the button to generate a random scramble for your runs.\nThe app will provide you with the move notation and animage of the final cube state.\nPress the Go To Timer Button to automatically record your time for this scramble."}, FontList.standardFont));
        windowList[6].add(new WindowChangeButton("scrambleGenerateGoToMain", 1475, 50, 100, 400, 1, Color.WHITE, this, new String[] {"BACK TO MAIN"}, FontList.subtitleFont, "Title Window"));

        windowList[6].add(new TextDisplay("scrambleHeader", 1325, 300, 50, 500, 1, standardBackgroundColor, new String[] {"CURRENT SCRAMBLE"}, FontList.subtitleFont));
        windowList[6].add(new TextDisplay("scrambleTextDisplay", 1250, 360, 300, 600, 1, Color.WHITE, new String[] {"SCRAMBLE GENERATED HERE"}, FontList.standardFont));

        currentGenerateScrambleView = new RubiksCubeNet(this, "Scramble Generate Window", windowList[6], false, 450, 600, 70, 70);
        windowList[6].add(new ScrambleGeneratorButton("generateScrambleButton", 1250, 700, 125, 600, 1, Color.WHITE, this, new String[] {"GENERATE SCRAMBLE"}, FontList.subtitleFont));

//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
        solveDatabase = new DatabaseView(DatabaseIO.loadSolves());
        loadObjectDatabase();

        currentSolveScrambleBuild.loadCublets(objectDatabase);
        currentGenerateScrambleView.loadCublets(objectDatabase);
        loadDatabasePage();

        errorMessageDatabase = DatabaseIO.loadErrorMessages();
        changeWindow("Title Window");
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

    public static void loadDatabasePage() {
        String[] pageData = solveDatabase.getCurrentPage();
        int databaseIndex = solveDatabase.getCurrentIndex();
        for (int i = 1; i <= 5; i++) {
            String slotId = "slot" + String.valueOf(i) + "DataDisplay";
            TextDisplay slotDisplay = (TextDisplay) objectDatabase.get(slotId);
            slotDisplay.updateTextDisplay(pageData[i-1]);
            String headerId = "slot" + String.valueOf(i) + "Heading";
            TextDisplay headerDisplay = (TextDisplay) objectDatabase.get(headerId);
            headerDisplay.updateTextDisplay(String.valueOf(i+databaseIndex));
        }
        //TODO IMPLEMENT SCRAMBLE VIEW LOADER
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
            try {
                DatabaseIO.saveSolves(solveDatabase);
            } catch (IOException e1) {
                System.out.println("SAVING NOT SUCCESSFUL");
                e1.printStackTrace();
            }
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

        else if (command.contains("RESET TIMER")) {
            TimerButton button = (TimerButton) objectDatabase.get("timerButton");
            button.resetTimer();
        }

        else if (command.contains("UPLOAD NET")) {
            //TODO INTEGRATE WITH BACKEND AND VALIDATION
            currentSolveScrambleBuild.toDataString();
            currentSolveScrambleBuild.printOutCube();
        }

        else if (command.contains("UPLOAD RUN")) {
            EditableTextDisplay minutes = (EditableTextDisplay) objectDatabase.get("minuteInput");
            EditableTextDisplay seconds = (EditableTextDisplay) objectDatabase.get("secondInput");
            EditableTextDisplay milliseconds = (EditableTextDisplay) objectDatabase.get("millisecondInput");

            if (!InputValidation.timeIsValid(minutes.getText(), seconds.getText(), milliseconds.getText())) {
                displayErrorMessage("ERROR CODE 20");
                return;
            }

            String time = minutes.getText() + ":" + seconds.getText() + ":" + milliseconds.getText();
            System.out.println("TIME: " + time);

            EditableTextDisplay day = (EditableTextDisplay) objectDatabase.get("dayInput");
            EditableTextDisplay month = (EditableTextDisplay) objectDatabase.get("monthInput");
            EditableTextDisplay year = (EditableTextDisplay) objectDatabase.get("yearInput");
            
            String date = day.getText() + "/" + month.getText() + "/" + year.getText();

            if (!InputValidation.dateIsValid(day.getText() + month.getText() + year.getText())) {
                displayErrorMessage("ERROR CODE 21");
                return;
            }
            System.out.println("DATE: " + date);

            EditableTextDisplay scrambleInput = (EditableTextDisplay) objectDatabase.get("moveInput");

            String scrambleString = scrambleInput.getText();
            //TODO SCRAMBLE VALIDATION

            if (solveDatabase.isFull()) {
                displayErrorMessage("ERROR CODE 22");
                return;
            }
            solveDatabase.addToList(time, date, scrambleString);
            loadDatabasePage();
            changeWindow("Database Window");
        }

        else if (command.contains("SCROLL DATABASE PAGE FORWARD")) {
            solveDatabase.updateCurrentIndex(true);
            loadDatabasePage();
        }

        else if (command.contains("SCROLL DATABASE PAGE BACKWARDS")) {
            solveDatabase.updateCurrentIndex(false);
            loadDatabasePage();
        }
        else if (command.contains("CHANGE SORT TO ")) {
            command = command.replace("CHANGE SORT TO ", "");
            solveDatabase.updateSortType(command);
            loadDatabasePage();
        }
        else if (command.contains("GENERATE SCRAMBLE")) { // WORKS
            //TODO BACKEND IMPLEMENTATION, NEED TO PASS: SCRAMBLE MOVE SEQUENCE + NET OF COMPLETED SCRAMBLE
            String newScramble = "123411111222222222333333333444444444555555555666666666";
            currentGenerateScrambleView.overwriteCube(newScramble);
        }
        
    }

    public void displayErrorMessage (String errorCode) {
        errorCode = errorCode.replace("ERROR CODE ", "");
        JOptionPane.showMessageDialog(frame, errorMessageDatabase.get(errorCode), "ERROR", JOptionPane.ERROR_MESSAGE);
    }

}


