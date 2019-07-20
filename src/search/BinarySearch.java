package search;


public class BinarySearch {

	// binary search : key 못찾으면 -1 (key 위치를 찾는것)
	/** 정렬 되었다고 가정 + 중복 데이터가 존재하면 그 중 어느것이 출력될지 알 수 없음 */
	public static int binarySearch(int[] arr, int key) {
		int first = 0;
		int end = arr.length - 1;
		
		while(first < end) {
			int mid = (first + end) >>> 1;
			
			if(key < arr[mid]) {// left side 
				end = mid - 1;
			}else if(key > arr[mid]) {// right side
				first  = mid + 1;
			}else {
				return mid;
			}
			
		}
		return -1;
	}
	
	// upper bound : key 를 초과하는 값의 인덱스 (가장 처음)
	public static int upperBound(int[] arr, int key) {
		int first = 0;
		int end =  arr.length;
		
		while(first < end) {
			int mid = (first + end) >>> 1;
			
			// if) from + 1 = to, mid = from  
			if(key >= arr[mid]) {
				first = mid + 1;
			}else {
				end = mid;
			}
		}
		
		return first;
	}
	
	// lower bound : key 보다 작거나 같은 값의 인덱스 (가장 처음)
	public static int lowerBound(int[] arr, int key) {
		int first = 0;
		int end = arr.length;
		
		while(first < end) {
			int mid = (first + end) >>> 1;
			
			if(key <= arr[mid]) {
				end = mid;
			}else {
				first = mid + 1;
			}
		}
		
		return end;
	}
}
