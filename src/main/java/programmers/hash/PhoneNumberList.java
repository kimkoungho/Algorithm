package programmers.hash;

import org.junit.Assert;

import java.util.HashMap;

// 전화번호 목록(level2)
// https://programmers.co.kr/learn/courses/30/lessons/42577
public class PhoneNumberList {

    static class Solution {
        // 전화번호 부에서 한 번호가 다른 번호의 접두어 인지 체크
        // ㄴ 접두어가 있으면 false
        // 접두어 이기 때문에 시작 글자가 같아야 한다 (중간은 안되는 듯)
        // 각 길이 1 ~ 20
        // 1 ~ 1,000,000
        public boolean solution(String[] phone_book) {
            for(int i=0; i<phone_book.length; i++) {
                for(int j=i+1; j<phone_book.length; j++) {
                    String shortPhone, longPhone;

                    if (phone_book[i].length() < phone_book[j].length()) {
                        shortPhone = phone_book[i];
                        longPhone = phone_book[j];
                    } else {
                        shortPhone = phone_book[j];
                        longPhone = phone_book[i];
                    }

                    if (shortPhone.hashCode() == longPhone.substring(0, shortPhone.length()).hashCode()) {
                        return false;
                    }
                }
            }

            return true;
        }
    }

    public static void main(String[] args) {
        Solution solution = new Solution();


        Assert.assertFalse(solution.solution(new String[] {"119", "97674223", "1195524421"}));

        Assert.assertTrue(solution.solution(new String[] {"123","456","789"}));

        Assert.assertFalse(solution.solution(new String[] {"12","123","1235","567","88"}));

        Assert.assertFalse(solution.solution(new String[] {"12","45","91","134","158","1158","111961","1349951"}));

        Assert.assertTrue(solution.solution(new String[] {"12","45","91","134","158","1158","111961","51349951"}));

    }
}
