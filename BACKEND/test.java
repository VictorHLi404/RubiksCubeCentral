package BACKEND;

import java.util.Scanner;

public class test {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[][][] cubeNet = new String[6][3][3];

        // Reading the cube net input
        for (int face = 0; face < 6; face++) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    cubeNet[face][i][j] = scanner.next();
                }
            }
        }

        // Processing and displaying the cube net output
        System.out.println(CubeReformatter.finalCube(cubeNet));
        scanner.close();
    }
}
