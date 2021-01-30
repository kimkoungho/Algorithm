package programmers.heap;


import java.util.PriorityQueue;

// https://programmers.co.kr/learn/courses/30/lessons/42627
public class DiskController {
    static class Solution {
        static class Job {
            private int requestTime;
            private int workingTime;

            public Job(int requestTime, int workingTime) {
                this.requestTime = requestTime;
                this.workingTime = workingTime;
            }
        }

        /**
         * @param jobs : 처리할 작업
         * ㄴ row 길이 : 1 ~ 500
         * ㄴ row[0] : 요청 시간 (0 ~ 1,000)
         * ㄴ row[1] : 소요 시간 (1 ~ 1,000)
         * @return 모든 작업의 요청부터 종료까지 걸린 평균 시간
         * ㄴ 작업을 하지 않을 때에는 먼저 들어온 것 부터 처리
         */
        public int solution(int[][] jobs) {
            // 초기화
            PriorityQueue<Job> queue = new PriorityQueue<>();

            for(int i=0; i<jobs.length; i++) {
                if (jobs[i][0] == 0) {

                }
            }



            int currentTime = 0;
            // 요청 시간이 가장 작은 것 추출

            // 2가지 job 비교
            // ㄴ 작업 요청 시간 <= 현재 시간 이면 ok
            // ㄴ 비교할 작업이 없는 경우 실행



            int answer = 0;
            return answer;
        }
    }

    public static void main(String[] args) {
        Solution solution = new Solution();

        // [[0, 3], [1, 9], [2, 6]]
        // 9
        // B, C, A = 10 - 1, 16 - 2, 19 - 3

        // [[0, 3], [1, 9], [2, 6], [3, 5]]
        // A, B, C, D = 3 + (12-1) + (18-2) + (23-3) = 50 / 4 = 12
        // A, B, D, C = 3 + (12-1) + (17-3) + (23-2) = 49 / 4

        // A, C, B, D = 3 + (9-2) + (18-1) + (23-3) = 53-6 = 47
        // A, C, D, B = 3 + (9-2) + (14-3) + (23-2) = 49-6 = 43

        // 4 개짜리
//        solution.solution(new int[][]{
//                {0, 3}, {1, 9}, {2, 6}, {3, 5}
//        });


        // 해당 시간에 요청이 1개면 -> 그냥 실행
        // [[0, 9], [1, 5], [10, 7], [12, 5]]
        // 정력 때리면 5, 5, 7, 9
        // ㄴ 안됨

        // 작업의 요청 시간이 같은 경우
        // [[0, 9], [0, 5], [10, 7], [12, 5]]
    }
}
