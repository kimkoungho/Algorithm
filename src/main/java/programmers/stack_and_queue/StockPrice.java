package programmers.stack_and_queue;

import org.junit.Assert;

import java.util.Arrays;
import java.util.Stack;

// 주식 가격
// https://programmers.co.kr/learn/courses/30/lessons/42584
public class StockPrice {
    static class Solution {
        /** 주식 가격이 담긴 배열 prices 에서 가격이 떨어지지 않은 기간은 몇 초인지를 return
         * @param prices : 각 원소는 1 ~ 10,000 / 배열의 길이 2 ~ 100,000
         * @return 각 원소가 몇 초 뒤에 가격이 떨어지는지
         *
         * 각 숫자보다 작아지는 시점을 모두 기록
         * 각 숫자는 마지막은 0, 나머지는 최소 1초로 계산
         **/
        //
        public int[] solution(int[] prices) {
            Stack<Integer> indexStack = new Stack<Integer>();
            indexStack.add(0);

            // 초기화
            int[] answer = new int[prices.length];
            Arrays.fill(answer, 0);

            for(int i = 1; i < prices.length; i++) {
                // 이전에 넣은 값이 현재 값 보다 크면, 모두 빼면서 계산
                while(!indexStack.isEmpty() && prices[indexStack.peek()] > prices[i]) {
                    int index = indexStack.pop();
                    answer[index] = i - index; // 현재 index - 해당 값 인덱스
                }

                // 스택이 비었을 때에도 추가
                // 스택에 있는 이전 값이 현재보다 작거나 같으면 누적
                indexStack.add(i);
            }

            // stack 이 비었으면 추가 작업 없음
            if (indexStack.isEmpty()) {
                return answer;
            }

            // stack 의 마지막 값보다 큰 값은 없다
            // stack 이 빌때 까지 계산한다
            int lastIndex = indexStack.pop();
            while(!indexStack.isEmpty()) {
                int index = indexStack.pop();
                answer[index] = lastIndex - index;
            }

            return answer;
        }
    }

    public static void main(String[] args) {
        Solution solution = new Solution();

        int[] answer1 = solution.solution(new int[]{1, 2, 3, 2, 3});
        Assert.assertArrayEquals(new int[]{4, 3, 1, 1, 0}, answer1);

        int[] answer2 = solution.solution(new int[]{3, 4, 3});
        Assert.assertArrayEquals(new int[]{2, 1, 0}, answer2);

        int[] answer3 = solution.solution(new int[]{3, 4, 2});
        Assert.assertArrayEquals(new int[]{2, 1, 0}, answer3);
        // 마지막 원소의 감소는 영향을 주지 않는다 ?

        int[] answer4 = solution.solution(new int[]{3, 4, 3, 1});
        Assert.assertArrayEquals(new int[]{3, 1, 1, 0}, answer4);

        int[] answer5 = solution.solution(new int[]{3, 4, 2, 1});
        Assert.assertArrayEquals(new int[]{2, 1, 1, 0}, answer5);
    }
}
