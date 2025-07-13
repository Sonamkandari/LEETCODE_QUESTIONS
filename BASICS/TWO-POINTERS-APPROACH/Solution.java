import java.util.*;

public class Solution {
    public int maxProfitAssignment(int[] difficulty, int[] profit, int[] worker) {
        int n = difficulty.length;
        List<int[]> jobs = new ArrayList<>();

        // Pair each job's difficulty with its profit
        for (int i = 0; i < n; i++) {
            jobs.add(new int[]{difficulty[i], profit[i]});
        }

        // Sort jobs by difficulty
        jobs.sort(Comparator.comparingInt(a -> a[0]));

        // Sort worker abilities
        Arrays.sort(worker);

        int totalProfit = 0;
        int j = 0; // Pointer for jobs
        int maxProfit = 0;

        // Iterate over each worker
        for (int i = 0; i < worker.length; i++) {
            // Update maxProfit for jobs the worker can do
            while (j < jobs.size() && worker[i] >= jobs.get(j)[0]) {
                maxProfit = Math.max(maxProfit, jobs.get(j)[1]);
                j++;
            }
            totalProfit += maxProfit;
        }

        return totalProfit;
    }
}
