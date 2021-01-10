package etc.permutation;

import java.util.Arrays;
import java.util.Comparator;

public class LexicographicPermutation implements Permutation {
	
	private Comparator<Object> comparator;
	
	public LexicographicPermutation(Comparator<Object> comparator) {
		this.comparator = comparator;
	}

	@Override
	public void printPermutationAll(Object[] inputs) {
		do {
			// 다음 순열이 존재하는 동안 출력 
			System.out.println(Arrays.toString(inputs));
		}while(nextPermutation(inputs));
	}
	
	private void swap(Object[] inputs, int base, int other) {
		Object baseValue = inputs[base];
		inputs[base] = inputs[other];
		inputs[other] = baseValue;
	}

	private boolean nextPermutation(Object[] inputs) {
		// 접미사의 시작 i를 찾기
		// 가장 큰 i 를 찾기 위해서 뒤에서부터 검색 
		int i = inputs.length - 1;
		
		// i > 0 && [i-1] >= [i]
		while(i > 0 && comparator.compare(inputs[i - 1], inputs[i]) >= 0) {
			i--;
		}
		
		// i < 0 경우는 가장 큰 순열을 출력한 경우 
		if(i <= 0) {
			return false;
		}
		
		// pivot = [i-1] 을 만족하는 가장 큰 j 찾기 
	    int j = inputs.length - 1;
	    while (comparator.compare(inputs[j], inputs[i - 1]) <= 0) {
	    		j--;
	    }
	        
	    // swap pivot, j
	    swap(inputs, i-1, j);
	    
	    // 접미사 reverse
	    int k = inputs.length - 1;
	    while(i < k) {
	    		swap(inputs, i++, k--);
	    }
		
		return true;
	}
}
