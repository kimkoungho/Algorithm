package programmers.stack_and_queue;

import org.junit.Assert;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Queue;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

// 다리를 지나는 트럭
// https://programmers.co.kr/learn/courses/30/lessons/42583
public class TruckPassingBridge {
    static class Solution {
        // 모든 트럭이 다리를 건너려면 몇 초가 걸리는지
        // 1초 1 이동
        // 트럭이 다리에 완전히 오르지 않은 경우의 무게는 고려되지 않음
        /**
         * @param bridge_length : 다리 길이 (1 ~ 10,000)
         * @param weight : 다리가 견딜 수 있는 무게 (1 ~ 10,000)
         * @param truck_weights : 대기중인 트럭들의 무게 (len: 1~10,000, 1~weight)
         */
        public int solution(int bridge_length, int weight, int[] truck_weights) {

            Queue<Integer> weightQueue = new LinkedList<Integer>();
            LinkedList<Integer> timeQueue = new LinkedList<Integer>();

            int index = 0;
            weightQueue.add(truck_weights[index]);
            timeQueue.add(bridge_length);

            int currentTime = 1, currentWeight = truck_weights[index];
            int incrementTime = 1;


            while (!weightQueue.isEmpty() && !timeQueue.isEmpty()) {
                currentTime += incrementTime;

                // 시간이 증가 했으니 모두 감소
                ListIterator<Integer> it = timeQueue.listIterator();
                while (it.hasNext()) {
                    it.set(it.next() - incrementTime);
                }

                // 시간이 0 인 원소는 삭제
                if (!timeQueue.isEmpty() && !weightQueue.isEmpty() && timeQueue.peek() == 0) {
                    currentWeight -= weightQueue.poll();
                    timeQueue.poll();
                }

                // 증가시간 초기화
                incrementTime = 1;
                // 다음 원소를 넣을 수 있는지 검사
                if (index + 1 < truck_weights.length && currentWeight + truck_weights[index+1] <= weight) {
                    currentWeight += truck_weights[++index]; // 현재 무게를 추가

                    weightQueue.add(truck_weights[index]);
                    // 어느 원소던 bride length 만큼 대기
                    timeQueue.add(bridge_length);
                } else if (!timeQueue.isEmpty()) { // 다음 원소를 넣을 수 없으면 현재 원소의 대기 시간 만큼을 다음에 뺸다
                    incrementTime = timeQueue.peek();
                }

            }

            return currentTime;
        }
    }

    public static void main(String[] args) {
        Solution solution = new Solution();

        int ret1 = solution.solution(2, 10, new int[]{7,4,5,6});
        Assert.assertEquals(8, ret1);

        int ret2 = solution.solution(100, 100, new int[]{10});
        Assert.assertEquals(101, ret2);

        int ret3 = solution.solution(100, 100, new int[]{10,10,10,10,10,10,10,10,10,10});
        Assert.assertEquals(110, ret3);

        Assert.assertEquals(19, solution.solution(5,5, new int[]{2, 2, 2, 2, 1, 1, 1, 1, 1}));

        long start = System.currentTimeMillis();
        int[] input4 = IntStream.range(0, 10000)
                .map(i -> 1).toArray();
        int ret4 = solution.solution(10000, 10000, input4);
        System.out.println(System.currentTimeMillis() - start);
        System.out.println(ret4);
    }
}
