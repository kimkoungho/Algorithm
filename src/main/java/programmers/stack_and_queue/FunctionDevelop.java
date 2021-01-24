package programmers.stack_and_queue;

import java.util.*;

// 기능 개발
// https://programmers.co.kr/learn/courses/30/lessons/42586
public class FunctionDevelop {

    static class Solution {
        /** 각 progress 가 speed 로 100 이상이 되면 작업을 배포한다
         * ㄴ n 일에는 몇개의 작업이 배포되는지 ..
         * @param progresses : 먼저 배포 되어야 할 작업들
         * ㄴ 배열의 길이 : 1 ~ 100
         * ㄴ 원소는 100 미만 자연수
         * @param speeds : 각 기능의 개발 속도
         * ㄴ 배열의 길이 : 1 ~ 100
         * ㄴ 원소는 100 이하 자연수
         * @return 각 배포마다 몇 개의 기능이 배포되는지 리턴
         * ㄴ 배포는 하루에 1번 만 가능함
         **/
        public int[] solution(int[] progresses, int[] speeds) {
            Queue<Integer> completeDateQueue = new LinkedList<Integer>();
            List<Integer> answerList = new ArrayList<>();
            for(int i = 0; i < progresses.length; i++) {
                int date = calculateCompleteDate(progresses[i], speeds[i]);

                // 이전 날짜가 현재 날짜 보다 작은 경우
                if (!completeDateQueue.isEmpty() && completeDateQueue.peek() < date) {
                    answerList.add(completeDateQueue.size());
                    completeDateQueue.clear();
                }

                completeDateQueue.add(date);
            }
            answerList.add(completeDateQueue.size());

            int[] answer = new int[answerList.size()];
            for(int i=0; i<answerList.size(); i++) {
                answer[i] = answerList.get(i);
            }
            return answer;
        }

        /** 해당 작업의 완료일자를 계산
         * @param progress
         * @param speed
         * @return
         */
        private int calculateCompleteDate(int progress, int speed) {
            // 남은 진행률 = 100 (전체) - progress (현재 진행률)
            int remain = 100 - progress;
            // 완료일자 = ceil(남은 진행률 / 작업 진행속도) : 나머지 존재하면 올림
            return (int) Math.ceil(remain / (double) speed);
        }

    }

    public static void main(String[] args) {
        Solution solution = new Solution();

        // [93, 30, 55]
        // [1, 30, 5]
        //= [2, 1]
        int[] answer1 = solution.solution(new int[]{93, 30, 55},
                new int[]{1, 30, 5});
        System.out.println(Arrays.toString(answer1));

        // [95, 90, 99, 99, 80, 99]
        // [1, 1, 1, 1, 1, 1]
        //= [1, 3, 2]
        int[] answer2 = solution.solution(new int[]{95, 90, 99, 99, 80, 99},
                new int[]{1, 1, 1, 1, 1, 1});
        System.out.println(Arrays.toString(answer2));

        int[] answer3 = solution.solution(new int[]{99, 99, 80, 99, 98},
                new int[]{1, 1, 1, 1, 1});
        // [2, 3]
        System.out.println(Arrays.toString(answer3));
    }
}
