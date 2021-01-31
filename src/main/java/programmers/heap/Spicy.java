package programmers.heap;

import org.junit.Assert;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.function.BiFunction;

// 더 맵게 ?
// https://programmers.co.kr/learn/courses/30/lessons/42626
public class Spicy {

    static class Solution {
        /**
         * @param scoville : 스코빌 지수 배열
         * ㄴ 원소 : 0 ~ 1,000,000
         * ㄴ 길이 : 2 ~ 1,000,000,000
         * @param K : 원하는 스코빌 지수 (K 이상) (0 ~ 1,000,000,000)
         * @return 각 스코빌 지수를 섞어서 K 이상이 되려면 몇 회 섞어야 하는지 (최소 횟수)
         * ㄴ K 이상으로 만들 수 없으면 -1
         * 섞은 음식 = 가장 작은 스코빌 + (두 번째 작은 스코빌 * 2)
         */
        public int solution(int[] scoville, int K) {
            PriorityQueue<Long> foodQueue = new PriorityQueue<>();
            Arrays.stream(scoville).forEach(food -> foodQueue.add((long) food));

            // 섞은 음식 계산 함수
            BiFunction<Long, Long, Long> calculate = (min1, min2) -> min1 + (min2 * 2);

            int count = 0;
            while(!foodQueue.isEmpty()) {
                long minFood = foodQueue.poll();
                if (minFood >= K) {
                    return count;
                }

                // 2 개 이상인 경우만 계산
                if (!foodQueue.isEmpty()) {
                    count++;
                    foodQueue.add(calculate.apply(minFood, foodQueue.poll()));
                }
            }

            return -1;
        }
    }

    public static void main(String[] args) {
        Solution solution = new Solution();


        Assert.assertEquals(2, solution.solution(new int[]{1, 2, 3, 9, 10, 12}, 7));

        // 만들 수 없는 경우
        Assert.assertEquals(-1, solution.solution(new int[]{1, 2, 3, 9, 10, 12}, 1000000000));
        // 모든 원소를 사용하는 경우
        Assert.assertEquals(5, solution.solution(new int[]{1, 2, 3, 9, 10, 12}, 104));

        // 최대 값 테스트
        Assert.assertEquals(1, solution.solution(new int[]{999999999, 999999998}, 1000000000));

        // 처음부터 완성 된 경우
        Assert.assertEquals(0, solution.solution(new int[]{9, 10, 12}, 5));
    }
}
