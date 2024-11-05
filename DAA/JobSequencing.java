import java.util.Arrays;
import java.util.Scanner;

class Job {
    char id;       // Job ID
    int deadline;  // Job deadline
    int profit;    // Profit if the job is done

    public Job(char id, int deadline, int profit) {
        this.id = id;
        this.deadline = deadline;
        this.profit = profit;
    }
}

public class JobSequencing {

    public static void printJobSequence(Job[] jobs) {
        // Step 1: Sort jobs in descending order of profit
        Arrays.sort(jobs, (j1, j2) -> Integer.compare(j2.profit, j1.profit));

        // Step 2: Find the maximum deadline to define the job sequence array size
        int maxDeadline = 0;
        for (Job job : jobs) {
            maxDeadline = Math.max(maxDeadline, job.deadline);
        }

        // Initialize an array for the job sequence and a slot tracker
        char[] jobSequence = new char[maxDeadline];
        boolean[] slotFilled = new boolean[maxDeadline];
        Arrays.fill(jobSequence,'X');  // Fill with 'X' as placeholders

        int totalProfit = 0;

        // Step 3: Allocate jobs to free slots
        for (Job job : jobs) {
            // Try to find a slot from job's deadline (or the last possible slot) backward
            for (int slot = Math.min(maxDeadline - 1, job.deadline - 1); slot >= 0; slot--) {
                if (!slotFilled[slot]) {  // If the slot is free
                    jobSequence[slot] = job.id;  // Assign job ID to this slot
                    slotFilled[slot] = true;     // Mark slot as filled
                    totalProfit += job.profit;   // Add profit to total
                    break;
                }
            }
        }

        // Step 4: Print the job sequence and total profit
        System.out.print("Job Sequence: ");
        for (char jobId : jobSequence) {
            if (jobId != 'X') {
                System.out.print(jobId + " ");
            }
        }
        System.out.println("\nTotal Profit: " + totalProfit);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Get the number of jobs from the user
        System.out.print("Enter the number of jobs: ");
        int numJobs = scanner.nextInt();

        // Initialize the jobs array
        Job[] jobs = new Job[numJobs];

        // Get details for each job from the user
        for (int i = 0; i < numJobs; i++) {
            System.out.print("Enter job ID (character), deadline, and profit for job " + (i + 1) + ": ");
            char id = scanner.next().charAt(0);
            int deadline = scanner.nextInt();
            int profit = scanner.nextInt();
            jobs[i] = new Job(id, deadline, profit);
        }

        System.out.println("Job Sequencing with Deadlines:");
        printJobSequence(jobs);

        scanner.close();
    }
}
