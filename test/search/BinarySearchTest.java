package search;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;



public class BinarySearchTest {
	
	@Test
	public void binarySearch_값이_없는경우() {
		int[] arr = new int[] {
			1, 7, 13, 17, 23, 40, 56, 70, 81, 211, 300	
		};
		
		int key = 5;
		
		int resultIndex = BinarySearch.binarySearch(arr, key);
		
		System.out.println("binarySearch_값이_없는경우");
		System.out.println(Arrays.toString(arr));
		System.out.println("key : " + key + "-> result: " + resultIndex);
		
		Assert.assertEquals(-1, resultIndex);
	}
	
	@Test
	public void binarySearch_동일한_값이_존재() {
		int key = 7;
		
		int[] arr1 = new int[] {
			1, 7, 7, 17, 23, 40, 56, 70, 81, 211, 300	
		};
		int[] arr2 = new int[] {
			1, 7, 7, 17, 40, 56, 23, 70, 81, 211	
		};
		
		int resultIndex1 = BinarySearch.binarySearch(arr1, key);
		int resultIndex2 = BinarySearch.binarySearch(arr2, key);
		
		// 배열의 길이에 따라 무엇이 나올지 알 수 없음 
		System.out.println("binarySearch_동일한_값이_존재");
		System.out.println(Arrays.toString(arr1));
		System.out.println(Arrays.toString(arr2));
		System.out.println("key: " + key + "-> arr1 result: " + resultIndex1 + ", arr2 result: " + resultIndex2);
	}
	
	
	@Test
	public void upperBound_중앙값_Test() {
		int[] arr = new int[] {
				// 0   1   2    3   4    5    6
					1, 7, 13, 13, 13, 33, 40	
		};
		
		int key = 13;
		
		int resultIndex = BinarySearch.upperBound(arr, key);
		
		System.out.println("upperBound_중앙값_Test");
		System.out.println(Arrays.toString(arr));
		System.out.println("key : " + key + "-> result: " + resultIndex);
		
		Assert.assertEquals(5, resultIndex);
	}
	
	
	@Test
	public void upperBound_동일결과_Test() {
		int[] arr = new int[] {
				1, 7, 13, 17, 23, 56, 56, 70, 81, 211, 300, 400	
		};
		
		int key1 = 56;
		// 56 보다 큰 최초 인덱스 
		int resultIndex1 = BinarySearch.upperBound(arr, key1);
		
		// 57 보다 큰 최초 인덱스
		int key2 = 57;
		int resultIndex2 = BinarySearch.upperBound(arr, key2);
		
		System.out.println("upperBound_동일결과_Test");
		System.out.println(Arrays.toString(arr));
		System.out.println("key1 : " + key1 + "-> resul1t: " + resultIndex1);
		System.out.println("key1 : " + key2 + "-> result2: " + resultIndex2);
		
		Assert.assertEquals(7, resultIndex1);
		Assert.assertEquals(7, resultIndex2);
	}
	
	@Test
	public void lowerBound_중앙값_Test() {
		int[] arr = new int[] {
				// 0   1   2    3   4    5    6
					1, 7, 13, 13, 13, 33, 40	
		};
		
		int key = 13;
		
		int resultIndex = BinarySearch.lowerBound(arr, key);
		
		System.out.println("lowerBound_중앙값_Test");
		System.out.println(Arrays.toString(arr));
		System.out.println("key : " + key + "-> result: " + resultIndex);
		
		Assert.assertEquals(2, resultIndex);
	}
	
	@Test
	public void lowerBound_동일결과_Test() {
		int[] arr = new int[] {
			// 0   1   2    3   4    5    6    7    8   9     10    11   12    13   14   15
				1, 7, 13, 17, 23, 33, 40, 45,  56, 56, 81, 211, 300, 400, 500, 600	
		};
		
		int key1 = 56;
		// 56 보다 크거나 같은 최초 인덱스 = 8
		int resultIndex1 = BinarySearch.lowerBound(arr, key1);
		
		// 50 보다 크거나 같은 최초 인덱스 = 8
		int key2 = 50;
		int resultIndex2 = BinarySearch.lowerBound(arr, key2);
		
		System.out.println("lowerBound_동일결과_Test");
		System.out.println(Arrays.toString(arr));
		System.out.println("key1 : " + key1 + "-> result1: " + resultIndex1);
		System.out.println("key1 : " + key2 + "-> result2: " + resultIndex2);
		
		Assert.assertEquals(8, resultIndex1);
		Assert.assertEquals(8, resultIndex2);
	}
	
	@Test
	public void compare_값이_모든_요소보다_작음_Test() {
		int[] arr = new int[] {
			// 0  1  2  3  4  5  6  7  8  
			   2, 3, 4, 5, 5, 5, 6, 7, 9
		};
		
		int key = 1;
		
		int lower = BinarySearch.lowerBound(arr, key);
		int upper = BinarySearch.upperBound(arr, key);
		
		System.out.println("compare_값이_모든_요소보다_작음_Test");
		System.out.println("key: " + key + "-> lower: " + lower + ", upper: " + upper);
		
		Assert.assertEquals(0, lower);
		Assert.assertEquals(0, upper);
	}
	
	@Test
	public void compare_값이_모든_요소보다_큼_Test() {
		int[] arr = new int[] {
			// 0  1  2  3  4  5  6  7  8  
			   2, 3, 4, 5, 5, 5, 6, 7, 9
		};
		
		int key = 11;
		
		int lower = BinarySearch.lowerBound(arr, key);
		int upper = BinarySearch.upperBound(arr, key);
		
		System.out.println("compare_값이_모든_요소보다_큼_Test");
		System.out.println("key: " + key + "-> lower: " + lower + ", upper: " + upper);
		
		Assert.assertEquals(9, lower);
		Assert.assertEquals(9, upper);
	}
	
	@Test
	public void compare_값이_존재하지_않음_Test() {
		int[] arr = new int[] {
			// 0  1  2  3  4  5  6  7  8  9
			   2, 3, 4, 5, 5, 5, 6, 7, 9, 10
		};
		
		int key = 8;
		
		int lower = BinarySearch.lowerBound(arr, key);
		int upper = BinarySearch.upperBound(arr, key);
		
		System.out.println("compare_값이_모든_요소보다_큼_Test");
		System.out.println("key: " + key + "-> lower: " + lower + ", upper: " + upper);
		
		Assert.assertEquals(8, lower);
		Assert.assertEquals(8, upper);
	}
}
