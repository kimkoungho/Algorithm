package programmers.hash;

import org.junit.Assert;

import java.util.*;

import static java.util.stream.Collectors.*;

// 위장 (level2)
// https://programmers.co.kr/learn/courses/30/lessons/42578
public class Camouflage {
    static class Solution {

        // 서로 다른 옷의 조합의 수를 return
        // row =  의상 이름, 의상 종류
        // max row len < 30
        // 최소 1개 이상을 입어야 함
        public int solution(String[][] clothes) {
            Map<String, Integer> typeCountMap = new HashMap<String, Integer>();

            for(String[] clotheInfo : clothes) {
                typeCountMap.put(clotheInfo[1], typeCountMap.getOrDefault(clotheInfo[1], 0)+1);
            }

            int answer = 1;
            for(int count : typeCountMap.values()) {
                // 각 의상별로 입을 수 있는 것 : count
                // 벗는 경우 +1
                answer *= (count + 1);
            }

            // 모두 벗는 경우 제외
            return answer - 1;
        }

        // 스트림
        public int solutionStream(String[][] clothes) {
            return Arrays.stream(clothes)
                    .collect(groupingBy(p -> p[1], mapping(p -> p[0], counting())))
                    .values()
                    .stream()
                    .collect(reducing(1L, (x, y) -> x * (y + 1))).intValue() - 1;
        }
    }



    public static void main(String[] args) {
        Solution solution = new Solution();

        // 뒤에꺼 기준으로 그룹핑 + 조합

        String[][] input1 = new String[][]{{"yellow_hat", "headgear"}, {"blue_sunglasses", "eyewear"}, {"green_turban", "headgear"}};
        int result1 = solution.solution(input1);
        Assert.assertEquals(5, result1);
        //


        String[][] input2 = new String[][]{{"crow_mask", "face"}, {"blue_sunglasses", "face"}, {"smoky_makeup", "face"}};
        int result2 = solution.solution(input2);
        Assert.assertEquals(3, result2);

        String[][] input3 = new String[][]{
                {"yellow_hat", "headgear"}, {"blue_sunglasses", "eyewear"}, {"green_turban", "headgear"}, {"yellow", "eyewear"},
                {"blue", "shoes"}, {"red", "headgear"}
        };
        int result3 = solution.solution(input3);
        Assert.assertEquals(23, result3);
        Assert.assertEquals(23, solution.solutionStream(input3));


    }
}
