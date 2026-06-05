import java.util.Scanner;

public class StudentGradeCalculator {

    static final int MAX_MARKS = 100;
    static final int MIN_MARKS = 0;
    static final int PASS_PERCENT = 40;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        printBanner();

        System.out.print("Enter Student Name : ");
        String studentName = sc.nextLine();

        System.out.print("Enter Number of Subjects (1-20) : ");
        int numSubjects = readPositiveInt(sc, 1, 20);

        String[] subjectNames = new String[numSubjects];
        int[]    marks        = new int[numSubjects];

        System.out.println();
        System.out.println("  Enter marks for each subject (0-100):");
        System.out.println("  ----------------------------------------");

        for (int i = 0; i < numSubjects; i++) {
            System.out.printf("  Subject %d Name  : ", (i + 1));
            subjectNames[i] = sc.nextLine();
            System.out.printf("  Subject %d Marks : ", (i + 1));
            marks[i] = readValidMarks(sc);
            System.out.println();
        }

        int    totalMarks  = calculateTotal(marks, numSubjects);
        double average     = calculateAverage(totalMarks, numSubjects);
        String grade       = assignGrade(average);
        String status      = getPassFailStatus(marks, numSubjects, average);
        int    highestMark = findHighest(marks, numSubjects);
        int    lowestMark  = findLowest(marks, numSubjects);
        String remark      = getPerformanceRemark(average);

        printReportCard(studentName, numSubjects, subjectNames,
                marks, totalMarks, average, grade,
                status, highestMark, lowestMark, remark);

        sc.close();
    }

    static void printBanner() {
        System.out.println("╔══════════════════════════════════════════════════════╗");
        System.out.println("║        STUDENT GRADE CALCULATOR  v2.0               ║");
        System.out.println("║        Powered by DecodeLabs | Batch 2026           ║");
        System.out.println("╚══════════════════════════════════════════════════════╝");
        System.out.println();
    }

    static int readValidMarks(Scanner sc) {
        while (true) {
            try {
                int mark = Integer.parseInt(sc.nextLine().trim());
                if (mark < MIN_MARKS || mark > MAX_MARKS) {
                    System.out.printf("  Invalid! Marks must be 0-100. Re-enter: ");
                } else {
                    return mark;
                }
            } catch (NumberFormatException e) {
                System.out.print("  Please enter a number. Re-enter: ");
            }
        }
    }

    static int readPositiveInt(Scanner sc, int min, int max) {
        while (true) {
            try {
                int value = Integer.parseInt(sc.nextLine().trim());
                if (value >= min && value <= max) {
                    return value;
                }
                System.out.printf("  Enter a value between %d and %d. Re-enter: ", min, max);
            } catch (NumberFormatException e) {
                System.out.print("  Please enter a whole number. Re-enter: ");
            }
        }
    }

    static int calculateTotal(int[] marks, int n) {
        int total = 0;
        for (int i = 0; i < n; i++) {
            total += marks[i];
        }
        return total;
    }

    static double calculateAverage(int total, int n) {
        return (double) total / n;
    }

    static String assignGrade(double average) {
        if      (average >= 90) return "A+";
        else if (average >= 80) return "A";
        else if (average >= 70) return "B";
        else if (average >= 60) return "C";
        else if (average >= 50) return "D";
        else                    return "F";
    }

    static String getPassFailStatus(int[] marks, int n, double average) {
        if (average < PASS_PERCENT) return "FAIL ✗";
        for (int i = 0; i < n; i++) {
            if (marks[i] < PASS_PERCENT) return "FAIL ✗";
        }
        return "PASS ✓";
    }

    static int findHighest(int[] marks, int n) {
        int highest = marks[0];
        for (int i = 1; i < n; i++) {
            if (marks[i] > highest) highest = marks[i];
        }
        return highest;
    }

    static int findLowest(int[] marks, int n) {
        int lowest = marks[0];
        for (int i = 1; i < n; i++) {
            if (marks[i] < lowest) lowest = marks[i];
        }
        return lowest;
    }

    static String getPerformanceRemark(double average) {
        if (average >= 90) return "Outstanding";
        if (average >= 80) return "Excellent";
        if (average >= 70) return "Very Good";
        if (average >= 60) return "Good";
        if (average >= 50) return "Average";
        return "Needs Improvement";
    }

    static void printReportCard(String name, int numSubjects,
            String[] subjectNames, int[] marks, int total,
            double average, String grade, String status,
            int highest, int lowest, String remark) {

        System.out.println();
        System.out.println("╔══════════════════════════════════════════════════════╗");
        System.out.println("║               STUDENT REPORT CARD                   ║");
        System.out.println("║           DecodeLabs | Batch 2026                   ║");
        System.out.println("╠══════════════════════════════════════════════════════╣");
        System.out.printf( "║  Student Name  : %-36s║%n", name);
        System.out.printf( "║  Total Subjects: %-36d║%n", numSubjects);
        System.out.println("╠══════════════════════════════════════════════════════╣");
        System.out.println("║          SUBJECT-WISE MARKS BREAKDOWN                ║");
        System.out.println("╠══════════════════╦═════════╦════════════════════════╣");
        System.out.println("║ Subject          ║  Marks  ║  Out Of                ║");
        System.out.println("╠══════════════════╬═════════╬════════════════════════╣");

        for (int i = 0; i < numSubjects; i++) {
            System.out.printf("║ %-16s ║   %3d   ║  100                   ║%n",
                    subjectNames[i], marks[i]);
        }

        System.out.println("╠══════════════════╩═════════╩════════════════════════╣");
        System.out.printf( "║  Total Marks   : %-36d║%n", total);
        System.out.printf( "║  Max Possible  : %-36d║%n", numSubjects * 100);
        System.out.printf( "║  Average (%%)   : %-36s║%n", String.format("%.2f%%", average));
        System.out.println("╠══════════════════════════════════════════════════════╣");
        System.out.printf( "║  Grade         : %-36s║%n", grade);
        System.out.printf( "║  Status        : %-36s║%n", status);
        System.out.printf( "║  Highest Mark  : %-36d║%n", highest);
        System.out.printf( "║  Lowest Mark   : %-36d║%n", lowest);
        System.out.printf( "║  Remark        : %-36s║%n", remark);
        System.out.println("╚══════════════════════════════════════════════════════╝");
        System.out.println();
        System.out.println("  Thank you for using StudentGradeCalculator by DecodeLabs!");
    }
}