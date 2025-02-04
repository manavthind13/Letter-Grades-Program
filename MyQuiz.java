import java.io.File;
import java.util.Scanner;
import java.io.*;
import java.io.PrintWriter;
public class MyQuiz {
    public static void main(String[] args) {
        Scanner myObj = new Scanner(System.in);
        int minScoreA, minScoreB, minScoreC, minScoreD;
        // This will read the input file
        System.out.print("Enter the path needed to access the input file: ");
        String inputFilePath = myObj.nextLine();
        try {
            File myInputFile = new File(inputFilePath);
            Scanner scanFile = new Scanner(myInputFile);
            // This will be the grading scheme from the instructor
            while (true) {
                System.out.print("Enter the minimum score needed for an A: ");
                minScoreA = myObj.nextInt();
                System.out.print("Enter the minimum score needed for a B: ");
                minScoreB = myObj.nextInt();
                System.out.print("Enter the minimum score needed for a C: ");
                minScoreC = myObj.nextInt();
                System.out.print("Enter the minimum score needed for a D: ");
                minScoreD = myObj.nextInt();
                // This will check for if the user entered duplicate values for the minimum grades
                if (GradeCalculator.DuplicateMinGrade(minScoreA, minScoreB, minScoreC, minScoreD)) {
                    System.out.println("\nError: Duplicate minimum grades detected! Please enter different minimum grades.");
                } else {
                    break; // Exit the loop if there are no duplicates
                }
            }
            // Calculate and display letter grades
            GradeCalculator gradeCalculator = new GradeCalculator(minScoreA, minScoreB, minScoreC, minScoreD);
            FileHandler fileHandler = new FileHandler(inputFilePath);
            fileHandler.calculateanddisplayGrades(scanFile, gradeCalculator);
            // Optionally save grades to a file
            System.out.print("\nDo you want to save the grades to a text file? (yes/no): ");
            String saveToFile = myObj.next().toLowerCase();
            if (saveToFile.equals("yes")) {
                fileHandler.saveGradesToFile(gradeCalculator);
            }
            scanFile.close();
        } catch (FileNotFoundException e) {
            System.out.println("Input file not found!");
        }
    }
}
class GradeCalculator {
    private final int minScoreA, minScoreB, minScoreC, minScoreD;
    public GradeCalculator(int minScoreA, int minScoreB, int minScoreC, int minScoreD) {
        // Initialize the minimum score thresholds for each grade
        this.minScoreA = minScoreA;
        this.minScoreB = minScoreB;
        this.minScoreC = minScoreC;
        this.minScoreD = minScoreD;
    }
    public char findLetterGrade(double averageScore) {
        // Determine the letter grade based on the average score and the minimum score thresholds
        if (averageScore >= minScoreA) {
            return 'A';
        } else if (averageScore >= minScoreB) {
            return 'B';
        } else if (averageScore >= minScoreC) {
            return 'C';
        } else if (averageScore >= minScoreD) {
            return 'D';
        } else {
            return 'F';
        }
    }
    // Check if there are any duplicate minimum grades entered by the user
    public static boolean DuplicateMinGrade(int minScoreA, int minScoreB, int minScoreC, int minScoreD) {
        return (minScoreA == minScoreB || minScoreA == minScoreC || minScoreA == minScoreD ||
                minScoreB == minScoreC || minScoreB == minScoreD || minScoreC == minScoreD);
    }
}
class FileHandler {
    private final String inputFilePath;
    public FileHandler(String inputFilePath) {
        // Initialize the input file path
        this.inputFilePath = inputFilePath;
    }
    public void calculateanddisplayGrades(Scanner scanFile, GradeCalculator gradeCalculator) {
        // Calculate and display letter grades for each student in the input file
        System.out.println("\nHere are the Quiz Grades for the class: ");
        while (scanFile.hasNextLine()) {
            String line = scanFile.nextLine();
            String[] myData = line.split(" ");
            String firstName = myData[0];
            int[] quizScores = new int[myData.length - 1];
            // Parse quiz scores
            for (int i = 1; i < myData.length; i++) {
                quizScores[i - 1] = Integer.parseInt(myData[i]);
            }
            // Calculate average score
            double averageScore = findAverage(quizScores);
            // Determine letter grade
            char letterGrade = gradeCalculator.findLetterGrade(averageScore);
            System.out.println(firstName + ": " + letterGrade);
        }
    }
    private double findAverage(int[] quizScores) {
        // Calculate the average of the quiz scores
        int sum = 0;
        for (int theScore : quizScores) {
            sum += theScore;
        }
        return (double) sum / quizScores.length;
    }
    public void saveGradesToFile(GradeCalculator gradeCalculator) {
        // Save the letter grades to a text file
        String outputFilePath = inputFilePath.replace(".txt", "_grades.txt");
        try (PrintWriter aWriter = new PrintWriter(outputFilePath);
             Scanner fileScanner = new Scanner(new File(inputFilePath))) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] data = line.split(" ");
                String firstName = data[0];
                int[] quizScores = new int[data.length - 1];
                for (int i = 1; i < data.length; i++) {
                    quizScores[i - 1] = Integer.parseInt(data[i]);
                }
                double averageScore = findAverage(quizScores);
                char letterGrade = gradeCalculator.findLetterGrade(averageScore);
                aWriter.println(firstName + ": " + letterGrade);
            }
            System.out.println("The grades are now saved to: " + outputFilePath);
        } catch (FileNotFoundException e) {
            System.out.println("There was an error saving the quiz grades to the file! Try this again!");
        }
    }
}