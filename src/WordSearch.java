import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class WordSearch {

    public static int[] find(final char[][] crossword, final String target) {
        int dim = crossword[0].length;
        //generate strings for each basic direction
        String[] vertical = getVertical(crossword);
        String[] horizontal = getHorizontal(crossword);
        String[] aDiagonal = getAscendingDiagonal(crossword);
        String[] dDiagonal = getDescendingDiagonal(crossword);

        //make sets of arrays for reverse order
        String[] rvertical = new String[vertical.length];
        String[] rhorizontal = new String[horizontal.length];
        String[] raDiagonal = new String[aDiagonal.length];
        String[] rdDiagonal = new String[dDiagonal.length];
        for (int i = 0; i < vertical.length; i++) {
            StringBuilder sb = new StringBuilder(vertical[i]);
            rvertical[i] = sb.reverse().toString();
            sb = new StringBuilder(horizontal[i]);
            rhorizontal[i] = sb.reverse().toString();
        }
        for (int i = 0; i < aDiagonal.length; i++) {
            StringBuilder sb = new StringBuilder(aDiagonal[i]);
            raDiagonal[i] = sb.reverse().toString();
            sb = new StringBuilder(dDiagonal[i]);
            rdDiagonal[i] = sb.reverse().toString();
        }

        //put these in directions
        String[][] directions = new String[8][];
        directions[0] = vertical;
        directions[1] = dDiagonal;
        directions[2] = horizontal;
        directions[3] = aDiagonal;
        directions[4] = rvertical;
        directions[5] = rdDiagonal;
        directions[6] = rhorizontal;
        directions[7] = raDiagonal;

        //Search using BoyerMoore
        int dir = 0;
        int row = -1;
        int col = -1;
        int find = 0;
        int x = 0;
        search:
        for (dir = 0; dir <= directions.length; dir++) {
            if (dir == 8) {
                dir = -1;
                break search;
            }
            String[] direction = directions[dir];
            for (x = 0; x < direction.length; x++) {
                find = BoyerMoore.find(direction[x], target);
                if (find != -1) break search;
            }
        }

        //Translate (x,find) to (row,col)
        if (dir == 0) {
            row = find;
            col = x;
        }
        else if (dir == 1) {
            if (x < dim) {
                row = find;
                col = dim - 1 - x + find;
            } else {
                row = x - dim + 1 + find;
                col = find;
            }
        }
        else if (dir == 2) {
            row = x;
            col = find;
        }
        else if (dir == 3) {
            if (x < dim) {
                row = x - find;
                col = find;
            } else {
                row = dim - 1 - find;
                col = x - dim + 1 + find;
            }
        }
        else if (dir == 4) {
            row = dim - 1 - find;
            col = x;
        }
        else if (dir == 5) {
            find = directions[dir][x].length() - find - 1;
            if (x < dim) {
                row = find;
                col = dim - 1 - x + find;
            } else {
                row = x - dim + 1 + find;
                col = find;
            }
        }
        else if (dir == 6) {
            row = x;
            col = dim - 1 - find;
        }
        else if (dir == 7) {
            find = directions[dir][x].length() - find - 1;
            if (x < dim) {
                row = x - find;
                col = find;
            } else {
                row = dim - 1 - find;
                col = x - dim + 1 + find;
            }
        }
        return new int[] {row, col, dir};
    }

    private static String[] getVertical(final char[][] crossword) {
        int dim = crossword[0].length;
        String[] vertical = new String[dim];
        //Get vertical Strings from the crossword
        //iterate over cols
        for (int n = 0; n < dim; n++) {
            vertical[n] = "";
            //iterate over rows
            for (int m = 0; m < dim; m++) {
                vertical[n] += crossword[m][n];
            }
        }
        return vertical;
    }

    private static String[] getHorizontal(final char[][] crossword) {
        int dim = crossword[0].length;
        String[] horizontal = new String[dim];
        //Get Horizontal Strings from the crossword
        //iterate over rows
        for (int m = 0; m < dim; m++) {
            horizontal[m] = "";
            //iterate over cols
            for (int n = 0; n < dim; n++) {
                horizontal[m] += crossword[m][n];
            }
        }
        return horizontal;
    }

    private static String[] getAscendingDiagonal(final char[][] crossword) {
        int dim = crossword[0].length;
        String[] ascendingDiagonal = new String[dim*2 - 1];
        for (int i = 0; i < dim; i++) {
            ascendingDiagonal[i] = "";
            for (int j = 0; j <= i; j++) {
                ascendingDiagonal[i] += crossword[i - j][j];
            }
        }
        for (int i = 1; i < dim; i++) {
            ascendingDiagonal[dim + i - 1] = "";
            for (int j = 0; j < dim - i; j++) {
                ascendingDiagonal[dim + i - 1] += crossword[dim - 1  - j][i + j];
            }
        }

        return ascendingDiagonal;
    }

    private static String[] getDescendingDiagonal(final char[][] crossword) {
        int dim = crossword[0].length;
        String[] descendingDiagonal = new String[dim*2 - 1];
        for (int i = 0; i < dim; i++) {
            descendingDiagonal[i] = "";
            for (int j = 0; j <= i; j++) {
                descendingDiagonal[i] += crossword[j][dim - 1 - i + j];
            }
        }
        for (int i = 1; i < dim; i++) {
            descendingDiagonal[dim + i - 1] = "";
            for (int j = 0; j < dim - i; j++) {
                descendingDiagonal[dim + i - 1] += crossword[i + j][j];
            }
        }

        return descendingDiagonal;
    }

    private static char[][] readDataMatrixIn(String filename) {
        File file = new File("./" + filename);
        try {
            Scanner sc = new Scanner(file);
            int n;
            String temp = sc.nextLine();
            n = Integer.parseInt(temp);

            char[][] matrix = new char[n][n];

            for (int i = 0; i < n; i++) {
                temp = sc.nextLine();

                for (int j = 0; j < n; j++)
                    matrix[i][j] = temp.charAt(j);
            }
            sc.close();
            return matrix;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String[] readLinesIn(String filename) {
        File file = new File("./" + filename);
        try {
            Scanner sc = new Scanner(file);
            int n;
            String temp = sc.nextLine();
            n = Integer.parseInt(temp);

            String[] words = new String[n];

            for (int i = 0; i < n; i++) {
                words[i] = sc.nextLine();
            }
            sc.close();
            return words;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        long startTime = System.nanoTime();

        String noun = "Artists";
        char[][] matrix = readDataMatrixIn("testFiles/Famous" + noun + "_puzzle.txt");
        System.out.println("Successful read in matrix!");
        String[] words = readLinesIn("testFiles/Famous" + noun + "_words.txt");
        System.out.println("Successful read in find data!");
        String[] solutions = readLinesIn("testFiles/Famous" + noun + "_solution.txt");
        System.out.println("Successful read in solution data!");
        System.out.println();

        String[] outputs = new String[solutions.length];
        for (int i = 0; i < solutions.length; i++) {
            String word = words[i];
            String target = word.replaceAll("\\s", "");
            int[] solution = find(matrix, target);
            String output = "";
            if (solution[2] == -1) output = "!!!Could not find: " + word + "!!!";
            else output = word + " " + (solution[0]+1) + ":" + (solution[1]+1);
            System.out.println(output);
            outputs[i] = output;
        }
        System.out.println();

        int mismatches = 0;
        for (int i = 0; i < solutions.length; i++) {
            if (!outputs[i].equals(solutions[i])) {
                System.out.println("Mismatch at output line: " + i);
                mismatches++;
            }
        }
        if (mismatches != 0) System.out.println("Total Mismatches: " + mismatches);
        else System.out.println("Output matches solution!");

        long endTime = System.nanoTime();
        long duration = (endTime - startTime)/1000000;
        System.out.println("Program took " + duration + " milliseconds to run.");
    }
}
