import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class DatabaseIO {

    private static final String solveFile = "lib/solves.csv";
    private static final String errorFile = "lib/errorCodes.csv";
    private static BufferedReader csvReader;
    private static FileWriter csvWriter;
    private static final int databaseMaxSize = 100;

    public static CubeSolve[] loadSolves() throws IOException {
        csvReader = new BufferedReader(new FileReader(solveFile));
        CubeSolve[] solveList = new CubeSolve[databaseMaxSize];
        String rowData = "";
        rowData = csvReader.readLine();
        rowData = csvReader.readLine(); // buffer the first line which only contains the names of the variables
        int i = 0;
        while (rowData != null) {
            String dataArray[] = rowData.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
            solveList[i] = new CubeSolve(dataArray[0], dataArray[1], dataArray[2].replace("\"", ""));
            rowData = csvReader.readLine();
            i++;
        }
        csvReader = null;
        return solveList;
    }

    public static HashMap<String, String> loadErrorMessages() throws IOException {
        csvReader = new BufferedReader(new FileReader(errorFile));
        HashMap<String, String> errorMessageDatabase = new HashMap<String, String>();
        String rowData = "";
        rowData = csvReader.readLine();
        rowData = csvReader.readLine(); // buffer the first line which only contains the names of the variables
        while (rowData != null) {
            String dataArray[] = rowData.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
            errorMessageDatabase.put(dataArray[0], dataArray[1]);
            System.out.println(errorMessageDatabase);
            rowData = csvReader.readLine();
        }
        return errorMessageDatabase;
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


