package permutation;

import java.util.Arrays;

public class RemovePermutation implements Permutation{

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
		removePermutation(0);
		
		System.out.println(swapCount);
	}
	
	private void removePermutation(int index) {
		if(index == inputs.length) {
			System.out.println(Arrays.toString(inputs));
		}else {
			for(int i = index; i < inputs.length; i++) {
				swap(index, i); // 기존 배열에서 다른 순열을 생성하기 위한 swap
				removePermutation(index+1);
				swap(index, i); // 기존 배열로 돌려 놓기 위한 swap
			}
		}
	}
}
