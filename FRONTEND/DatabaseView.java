package FRONTEND;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

class DatabaseView {
    protected CubeSolve[] solveList;

    protected final int databaseMaxSize = 100;

    protected int solveListSize;

    protected String currentSortType;

    protected final int displayPageLength = 5;
    protected CubeSolve[] currentPage;

    protected String averageTime;
    protected int currentIndex = 0;

    public DatabaseView(CubeSolve[] solveList) {
        this.solveList = solveList;
        int index = 0;
        while (index < databaseMaxSize) {
            if (solveList[index] == null) {
                break;
            }
            index++;
        }
        solveListSize = index;
        // default sort by time
        this.currentSortType = "TIME";
        sort();
        currentPage = new CubeSolve[displayPageLength];
        for (int i = 0; i < displayPageLength; i++) {
            currentPage[i] = solveList[i];
        }
    }

    public void loadPage() {
        for (int i = 0; i < displayPageLength; i++) {
            currentPage[i] = solveList[currentIndex+i];
        }
    }

    public String[] getCurrentPage() {
        String[] currentPageInfo = new String[displayPageLength];
        for (int i = 0; i < displayPageLength; i++) {
            if (currentPage[i] != null) {
                currentPageInfo[i] = currentPage[i].toDisplayString();
            }
            else {
                currentPageInfo[i] = "ENTRY DOES NOT EXIST";
            }
        }
        return currentPageInfo;
    }

    public void updateCurrentIndex(boolean moveForward) {
        if (moveForward) {
            if (currentIndex + displayPageLength < databaseMaxSize) {
                currentIndex += displayPageLength;
            }
        }
        else {
            if (currentIndex != 0) {
                currentIndex -= displayPageLength;
            }
        }
        loadPage();
    }

    private void sort() {
        CubeSolve[] sortedSolveList = new CubeSolve[solveListSize];
        for (int i = 0; i < solveListSize; i++) {
            sortedSolveList[i] = solveList[i];
        }
        MergeSort.mergeSort(sortedSolveList, currentSortType);
        for (int i = 0; i < solveListSize; i++) {
            solveList[i] = sortedSolveList[i];
        }
    }

    public void updateSortType(String sortType) {
        currentSortType = sortType;
        sort();
        loadPage();
    }

    public void addToList(String solvetime, String date, String scramble) {
        CubeSolve newEntry = new CubeSolve(solvetime, date, scramble);
        solveList[solveListSize] = newEntry;
        solveListSize++;
        sort();
        loadPage();
    }

    public void deleteFromList(int entryIndex) {
        int deleteIndex = entryIndex + currentIndex - 1;
        System.out.println(deleteIndex);
        solveList[deleteIndex] = null;
        for (int i = deleteIndex; i < solveListSize; i++) { // shuffle elements down
            if (i < databaseMaxSize-1) {
                solveList[i] = solveList[i+1];
            }
            else {
                solveList[i] = null;
            }
        }
        solveListSize--;
        sort();
        loadPage();
    }

    public CubeSolve[] getSolveList() {
        return solveList;
    }

    public boolean isFull() {
        if (solveListSize == databaseMaxSize) {
            return true;
        }
        else {
            return false;
        }
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public int getDatabaseMaxSize() {
        return databaseMaxSize;
    }

    public int getDisplayPageLength() {
        return displayPageLength;
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
    
    public long solveTimeToInt() {
        long minutes = Long.valueOf(solveTime.substring(0,2));
        long seconds = Long.valueOf(solveTime.substring(3, 5));
        long milliseconds = Long.valueOf(solveTime.substring(6, 9));

        return minutes*60000 + seconds*1000 + milliseconds;
    }

    public LocalDate dateToLocalDate() {
        String loadDate = date.replace("/", "");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("ddMMuuuu").withResolverStyle(ResolverStyle.STRICT);
        return LocalDate.parse(loadDate, dateTimeFormatter);
    }

    @Override
    public String toString() {
        return solveTime + "  " + date;
    }

    public String toDisplayString() {
        return solveTime + "        " + date;
    }

    public String convertToCSV() {
        return solveTime + "," + date + ",\"" + scramble + "\"";
    }
}