package permutation;

import java.util.Arrays;

public class HeapsAlgorithmPermutation implements Permutation{

	private Object[] inputs;
	
	private int swapCount;
	
	private void swap(int base, int other) {
		if(base == other) {
			return;
		}
		
		Object baseObj = inputs[base];
		inputs[base] = inputs[other];
		inputs[other] = baseObj;
		
		swapCount ++;
	}
	
	@Override
	public void printPermutationAll(Object[] inputs) {
		swapCount = 0;
		
		this.inputs = inputs;
		heapsAlgorithm(inputs.length);
		
//		System.out.println(swapCount);
		System.out.println(cnt);
	}

	int cnt = 0;
	
	private void heapsAlgorithm(int n) {
		cnt ++;
		if(n == 1) {
	        System.out.println(Arrays.toString(inputs));
	    }else {
	        for(int i=0; i<n-1; i++) {
	        	
	        		heapsAlgorithm(n-1);
	            
	        		// 다음 순열 출력을 위한 swap 
	            if(n % 2 == 0) {
	                swap(i, n-1);
	            }else {
	                swap(0, n-1);
	            }
	        }
	        // loop 종료시 마지막 swap 한 결과 출력을 위한 재귀 
	        heapsAlgorithm(n-1);
	    }
	}

}
