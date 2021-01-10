package programmers.hash;

import org.junit.Assert;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

// 완주하지 못한 선수 (level1)
// https://programmers.co.kr/learn/courses/30/lessons/42576
public class NotCompletionPlayer {

    static class Solution {
        // 최대 길이 1 ~ 10만
        // 원소 : 1~20 , 동명 이인 가능
        // participant : 참여선수
        // completion : 완료 선수 (participant.len -1)
        public String solution(String[] participant, String[] completion) {
            Map<String, Long> nameCountMap = Arrays.stream(completion)
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

            for (String name : participant) {
                Long count = nameCountMap.getOrDefault(name, 0L);
                if (count == 0) {
                    return name;
                }

                nameCountMap.put(name, count - 1);
            }

            return null;
        }
    }

    public static void main(String[] args) {
        Solution solution = new Solution();

        // input1
        String[] part1 = new String[]{"leo", "kiki", "eden"};
        String[] comp1 = new String[]{"eden", "kiki"};

        Assert.assertEquals("leo", solution.solution(part1, comp1));

        String[] part2 = new String[]{"marina", "josipa", "nikola", "vinko", "filipa"};
        String[] comp2 = new String[]{"josipa", "filipa", "marina", "nikola"};
        Assert.assertEquals("vinko", solution.solution(part2, comp2));

        String[] part3 = new String[]{"mislav", "stanko", "mislav", "ana"};
        String[] comp3 = new String[]{"stanko", "ana", "mislav"};
        Assert.assertEquals("mislav", solution.solution(part3, comp3));


        String[] part4 = new String[]{"mislav", "stanko", "mislav", "ana"};
        String[] comp4 = new String[]{"stanko", "mislav", "mislav"};
        Assert.assertEquals("ana", solution.solution(part4, comp4));
    }
}
