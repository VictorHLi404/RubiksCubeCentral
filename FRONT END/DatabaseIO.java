import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class DatabaseIO {

    private static final String solveFile = "lib/solves.csv";
    private static BufferedReader csvReader;
    private static FileWriter csvWriter;
    private static int databaseMaxSize = 100;

    public static CubeSolve[] loadSolves() throws IOException {
        csvReader = new BufferedReader(new FileReader(solveFile));
        CubeSolve[] solveList = new CubeSolve[databaseMaxSize];
        String rowData = "";
        rowData = csvReader.readLine();
        rowData = csvReader.readLine(); // buffer the first line which only contains the names of the variables
        int i = 0;
        while (rowData != null) {
            String dataArray[] = rowData.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
            solveList[i] = new CubeSolve(dataArray[0], dataArray[1], dataArray[2]);
            rowData = csvReader.readLine();
            i++;
        }
        csvReader = null;
        return solveList;
    }

    public static void saveSolves(DatabaseView database) throws IOException {
        CubeSolve[] solveList = database.getSolveList();
        csvWriter = new FileWriter(solveFile);
        csvWriter.write("time,date,scramble\n");
        for (int i = 0; i < database.solveListSize; i++) {
            csvWriter.write(solveList[i].convertToCSV() + "\n");
        }
        csvWriter.close();
        System.out.println("Runs have been saved successfully!");

    }
}


/*
 *     public static void saveBookList(HashMap<Integer, Book> masterList) throws IOException {
        csvWriter = new FileWriter(bookFile);
        csvWriter.write("bookID,title,author,availableCopies\n");
        masterList.forEach((key,Book) -> {
            try {
                csvWriter.write(Book.convertToCSV());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        csvWriter.close();
        System.out.println("Book database has been saved!");
    }
 */
class DatabaseView {
    protected CubeSolve[] solveList;

    protected int solveListSize;

    protected String currentSolveSort;

    protected final int displayPageLength = 5;
    protected CubeSolve[] currentPage;

    protected String averageTime;
    protected int currentIndex = 0;
    
    public DatabaseView(CubeSolve[] solveList) {
        this.solveList = solveList;
        int index = 0;
        while (solveList[index] != null) {
            index++;
        }
        solveListSize = index;
        // default sort by time
        this.currentSolveSort = "TIME";
        sortByTime();
        currentPage = new CubeSolve[displayPageLength];
        for (int i = 0; i < displayPageLength; i++) {
            currentPage[i] = solveList[i];
        }
    }

    public void loadPage(int currentIndex) {
        for (int i = 0; i < displayPageLength; i++) {
            currentPage[i] = solveList[currentIndex+i];
        }
    }

    public String[] getCurrentPage() {
        String[] currentPageInfo = new String[displayPageLength];
        for (int i = 0; i < displayPageLength; i++) {
            String index = String.valueOf(currentIndex+1+i);
            currentPageInfo[i] = index + " " + currentPage[i];
        }
        return currentPageInfo;
    }

    public void sortByDate() {
        //TODO SHUFFLE LIST TO SORT BY DATE
    }

    public void sortByTime() {
        //TODO SHUFFLE LIST TO SORT BY TIME
    }

    public void addToList(String solvetime, String date, String scramble) {
        CubeSolve newEntry = new CubeSolve(solvetime, date, scramble);
        solveList[solveListSize] = newEntry;
        solveListSize++;
        if (currentSolveSort.equals("TIME")) {
            sortByTime();
        }
        else if (currentSolveSort.equals("DATE")) {
            sortByDate();
        }
        loadPage(currentIndex);
    }
    public CubeSolve[] getSolveList() {
        return solveList;
    }
}

class CubeSolve {
    protected String solveTime;
    protected String date;
    protected String scramble;

    public CubeSolve(String solveTime, String date, String scramble) {
        this.solveTime = solveTime;
        this.date = date;
        this.scramble = scramble;
    }

    public String getSolveTime() {
        return solveTime;
    }
    public void setSolve(String solve) {
        this.solveTime = solve;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getScramble() {
        return scramble;
    }
    public void setScramble(String scramble) {
        this.scramble = scramble;
    }
    @Override
    public String toString() {
        return solveTime + "  " + date;
    }

    public String convertToCSV() {
        return solveTime + "," + date + "," + scramble;
    }
}