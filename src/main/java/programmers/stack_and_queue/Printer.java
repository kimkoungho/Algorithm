package programmers.stack_and_queue;

import org.junit.Assert;

import java.util.*;
// 프린터
// https://programmers.co.kr/learn/courses/30/lessons/42587
public class Printer {

    static class Solution {
        static class Print {
            private int index;
            private int priority;

            public Print(int index, int priority) {
                this.index = index;
                this.priority = priority;
            }

            public int getIndex() {
                return index;
            }

            public int getPriority() {
                return priority;
            }
        }
        /**
         * @param priorities : 각 문서의 중요도 (element[i] : 1~9, 길이 1~100)
         * @param location : 인쇄를 요청한 문서의 인덱스 (0~priorities.length-1)
         * @return 요청한 문서가 몇번째로 출력되는지
         */
        public int solution(int[] priorities, int location) {
            // 가장 앞 원소 추출 -> 나머지 중 중요도가 높은 문서가 있으면 , 맨 뒤에 삽입
            // 해당 원소가 중요도가 가장 높으면 인쇄

            // 최대 값 1개를 찾고
            // 그 앞 원소는 잘라서 뒤에 붙이고
            // 최대 값은 인쇄
            // ㄴ 이때 location 과 최대 값 index 가 같으면 반환

            LinkedList<Print> printQueue = new LinkedList<Print>();
            for(int i = 0; i < priorities.length; i++) {
                printQueue.add(new Print(i, priorities[i]));
            }

            int answer = 0;
            while(!printQueue.isEmpty()) {
                Print print = printQueue.poll();
                // 현재 값 보다 큰 값이 존재
                if (printQueue.stream().anyMatch(p -> p.getPriority() > print.getPriority())) {
                    printQueue.add(print);
                } else { // 현재 값이 최대
                    answer++;
                    // 출력할 프린트가 요청한 것인 경우
                    if (print.getIndex() == location) {
                        return answer;
                    }
                }
            }

            return 0;
        }

    }


    public static void main(String[] args) {
        Solution solution = new Solution();

        Assert.assertEquals(1,
                solution.solution(new int[]{2, 1, 3, 2}, 2));

        // [1, 1, 9, 1, 1, 1], 0
        // 5
        Assert.assertEquals(5,
                solution.solution(new int[]{1, 1, 9, 1, 1, 1}, 0));

        // [2, 3, 5, 2]
        // 5, 2, 2, 3
        // 5
        // 3, 2, 2
        Assert.assertEquals(3,
                solution.solution(new int[]{2, 3, 5, 2}, 3));
    }
}
