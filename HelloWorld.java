import java.util.Scanner;
public class HelloWorld {
    public static void main(String args[]) {

        int[] numbers = new int[10];
        Scanner input = new Scanner(System.in);


        for (int i = 0; i < 10; i++) {
            System.out.print("Enter the number " + (i + 1) + ": ");
            numbers[i] = input.nextInt();
        }

        // Calculate the sum of the numbers
        int sum = 0;
        for (int number : numbers) {
            sum += number;
        }

        // Calculate the average of the numbers
        double average = (double) sum / numbers.length;

        // Print the sum and average
        System.out.println("Sum: " + sum);
        System.out.println("Average: " + average);

        // Close the scanner
        input.close();
    }
}