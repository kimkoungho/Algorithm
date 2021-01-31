package programmers.heap;


import org.junit.Assert;

import java.util.*;
import java.util.stream.Collectors;

// https://programmers.co.kr/learn/courses/30/lessons/42627
public class DiskController {
    static class Job {
        private int requestTime;
        private int workingTime;

        public Job(int requestTime, int workingTime) {
            this.requestTime = requestTime;
            this.workingTime = workingTime;
        }

        public int getRequestTime() {
            return requestTime;
        }

        public int getWorkingTime() {
            return workingTime;
        }
    }

    static class Solution {

        /**
         * @param jobs : 처리할 작업
         * ㄴ row 길이 : 1 ~ 500
         * ㄴ row[0] : 요청 시간 (0 ~ 1,000)
         * ㄴ row[1] : 소요 시간 (1 ~ 1,000)
         * @return 모든 작업의 요청부터 종료까지 걸린 평균 시간
         * ㄴ 작업을 하지 않을 때에는 먼저 들어온 것 부터 처리
         */
        public int solution(int[][] jobs) {
            // 입력된 jobs 를 요청시간으로 정렬
            LinkedList<Job> sortedJobList = Arrays.stream(jobs)
                    .map(jobArr -> new Job(jobArr[0], jobArr[1]))
                    .sorted(Comparator.comparing(Job::getRequestTime))
                    .collect(Collectors.toCollection(LinkedList::new));

            // 실행할 job 이 담긴 queue
            PriorityQueue<Job> jobReadyQueue = new PriorityQueue<Job>(Comparator.comparing(Job::getWorkingTime));

            int currentTime = 0;
            int answer = 0;
            do {
                // job[i] 요청 시간 <= 현재시간
                // ㄴ 모두 준비 큐에 넣는다
                while (!sortedJobList.isEmpty() &&
                        sortedJobList.peek().getRequestTime() <= currentTime) {
                    jobReadyQueue.add(sortedJobList.poll());
                }

                // current time < next job request time
                // 현재 시간을 next job request time 으로 세팅
                if (jobReadyQueue.isEmpty()) {
                    currentTime = sortedJobList.isEmpty() ? currentTime + 1 : sortedJobList.peek().getRequestTime();
                    continue;
                }

                // 준비 큐에서 우선 순위가 가정 높은것 추출 = min(실행 시간)
                Job workJob = jobReadyQueue.poll();
                currentTime += workJob.getWorkingTime();
                // 요청 - 종료 까지 걸린 시간
                int responseTime = currentTime - workJob.getRequestTime();
                answer += responseTime;

            } while (!jobReadyQueue.isEmpty() || !sortedJobList.isEmpty());


            return answer / jobs.length;
        }
    }

    public static void main(String[] args) {
        Solution solution = new Solution();

        // [[0, 3], [1, 9], [2, 6]]
        // 9
        Assert.assertEquals(9, solution.solution(new int[][]{
                {0, 3}, {1, 9}, {2, 6}
        }));

        // [[0, 3], [1, 9], [2, 6], [3, 5]]
        // 4 개짜리
        Assert.assertEquals(10, solution.solution(new int[][]{
                {0, 3}, {1, 9}, {2, 6}, {3, 5}
        }));


        // 해당 시간에 요청이 1개면 -> 그냥 실행 (A, B 우선)
        // [[0, 9], [1, 5], [10, 7], [12, 5]]
        Assert.assertEquals(11, solution.solution(new int[][]{
                {0, 9}, {1, 5}, {10, 7}, {12, 5}
        }));

        // 작업의 요청 시간이 같은 경우 -> 우선 순위가 높은 B 실행
        // [[0, 9], [0, 5], [10, 7], [12, 5]]
        Assert.assertEquals(10, solution.solution(new int[][]{
                {0, 9}, {0, 5}, {10, 7}, {12, 5}
        }));

        // 다음 작업을 기다리는 경우
        // [[1, 3], [1, 5], [13, 7]]
        Assert.assertEquals(6, solution.solution(new int[][]{
                {3, 3}, {3, 5}, {100, 7}
        }));
    }
}
