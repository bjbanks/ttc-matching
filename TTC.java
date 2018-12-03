/***********************************
 * Copyright (c) 2018 Bryson J. Banks.
 * All rights reserved.
 **********************************/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;

public class TTC {
    
    private static int size;
    private static int[][] preferences;

    public static void main(String[] args) {
        // verify one argument: inputFile
        if (args.length != 1) {
            System.out.println("Error. Java program TTC expects 1 argument specifying an input file.");
            return;
        }
        String inputFile = args[0];
                
        // parse the input file - will initialize size and preferences
        if (!parseInput(inputFile)) {
            return;
        }
        
        long startTime=System.nanoTime();
        
        int[] matches = match(preferences);
        printMatch(matches);
        
        long endTime=System.nanoTime();
        long totalTime=endTime-startTime;
        System.out.println("Total time taken for TTC is " + totalTime);
    }
    
    private static void printMatch(int[] matches) {
        for (int i = 0; i < size; i++) {
            System.out.println("(" + (i+1) + "," + (matches[i]+1) + ")");
        }
    }
    
    private static int[] match(int[][] preferences) {
        int[] matches = new int[size];
        int[] prefIndexes = new int[size];
        HashSet<Integer> freeX = new HashSet<>();
        for (int i = 0; i < size; i++) {
            matches[i] = -1;
            prefIndexes[i] = 0;
            freeX.add(i);
        }
        while(!freeX.isEmpty()) {
            for(int x : freeX) {
                int topChoice = preferences[x][prefIndexes[x]];
                while (!freeX.contains(topChoice)) {
                    prefIndexes[x]++;
                    topChoice = preferences[x][prefIndexes[x]];
                }
                matches[x] = topChoice;
            }
            HashSet<Integer> topCycle = getTopCycle(matches, freeX);
            for (int x : topCycle) {
                freeX.remove(x);
            }
        }
        return matches;
    }
    
    private static HashSet<Integer> getTopCycle(int[] choices, HashSet<Integer> free) {
        HashSet<Integer> topCycle = new HashSet<>();
        for (int x : free) {
            HashSet<Integer> cycle = new HashSet<>();
            cycle.add(x);
            int choice = choices[x];
            while (!cycle.contains(choice) || !free.contains(choice)) {
                cycle.add(choice);
                choice = choices[choice];
            }
            if (choice == x) {
                if (cycle.size() > topCycle.size()) {
                    topCycle = cycle;
                }
            }
        }
        return topCycle;
    }
    
    // parse given input file
    // returns true if success, or false if parsing fails
    private static boolean parseInput(String inputFile) {
        // create file scanner object
        Scanner scanner;
        try {
            scanner = new Scanner(new File(inputFile));
        } catch (FileNotFoundException e) {
            System.out.println("Error. File not found: " + inputFile);
            return false;
        }
                    
        // parse the matrix size from first line
        size = parseSize(scanner);
        if (size < 0) {
            System.out.println("Error. Failed to parse matrix size. Input file is incorrectly formatted.");
            scanner.close();
            return false;
        }

        // parse the square matrix values
        preferences = parseMatrix(scanner, size);
        if (preferences == null) {
            System.out.println("Error. Failed to parse prefs matrix. Input file is incorrectly formatted.");
            scanner.close();
            return false;
        }
            
        // done parsing file, success
        scanner.close();
        return true;
    }

    // parse input file to retrieve matrix size
    // returns size, or -1 if parsing fails
    private static int parseSize(Scanner scanner) {
        if (scanner.hasNextInt()) {
            return scanner.nextInt();
        } else {
            return -1;
        }
    }       

    // parse input file to retrieve square matrix
    // returns matrix, or null if parsing fails
    private static int[][] parseMatrix(Scanner scanner, int size) {
        int[][] matrix = new int[size][size];
        for (int rowIndex = 0; rowIndex < size; rowIndex++) {
            for (int colIndex = 0; colIndex < size; colIndex++) {
                if (scanner.hasNextInt()) {
                    matrix[rowIndex][colIndex] = scanner.nextInt() - 1;
                } else {
                    return null;
                }
            }
        }
        return matrix;
    }

}
