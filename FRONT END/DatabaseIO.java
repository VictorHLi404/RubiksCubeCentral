import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DatabaseIO {

    private static final String solveFile = "lib/solves.csv";
    private static BufferedReader csvReader;

    public static CubeSolve[] loadSolves() throws IOException {
        csvReader = new BufferedReader(new FileReader(solveFile));
        CubeSolve[] solveList = new CubeSolve[9999];
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
}

class DatabaseView {
    protected CubeSolve[] solveList;
    protected final int displayPageLength = 5;
    protected CubeSolve[] currentPage;

    protected String averageTime;
    protected int currentIndex = 0;
    
    public DatabaseView(CubeSolve[] solveList) {
        this.solveList = solveList;
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
            System.out.println(currentPageInfo[i]);
        }
        return currentPageInfo;
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
}