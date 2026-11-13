import java.util.Scanner;

public class StudentGradeTracker {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        String[] names;
        int[] marks;

        int n;
        int highest, lowest;
        double total = 0, average;

        System.out.println("Message");
        System.out.println("     STUDENT GRADE TRACKER");
        System.out.println("Message");

        System.out.print("Enter number of students: ");
        n = sc.nextInt();
        sc.nextLine();

        if (n <= 0) {
            System.out.println("Invalid number of students!");
            sc.close();
            return;
        }

        names = new String[n];
        marks = new int[n];

        // Input student details
        for (int i = 0; i < n; i++) {

            System.out.println("\nEnter details of Student " + (i + 1));

            System.out.print("Enter student name: ");
            names[i] = sc.nextLine();

            System.out.print("Enter marks (0-100): ");
            marks[i] = sc.nextInt();
            sc.nextLine();

            if (marks[i] < 0 || marks[i] > 100) {
                System.out.println("Invalid marks! Enter marks between 0 and 100.");
                i--;
                continue;
            }

            total = total + marks[i];
        }

        // Calculate average
        average = total / n;

        // Find highest and lowest marks
        highest = marks[0];
        lowest = marks[0];

        for (int i = 1; i < n; i++) {

            if (marks[i] > highest) {
                highest = marks[i];
            }

            if (marks[i] < lowest) {
                lowest = marks[i];
            }
        }

        // Display student report
        System.out.println("\n");
        System.out.println("       STUDENT SUMMARY REPORT");
        System.out.println("Message");

        System.out.printf("%-20s %s%n", "Student Name", "Marks");
        System.out.println("Message");

        for (int i = 0; i < n; i++) {
            System.out.printf("%-20s %d%n", names[i], marks[i]);
        }

        System.out.println("Message");
        System.out.printf("Average Marks : %.2f%n", average);
        System.out.println("Highest Marks : " + highest);
        System.out.println("Lowest Marks  : " + lowest);

        System.out.println("Message");

        sc.close();
    }
}