package permutation;

import java.util.Arrays;

/** Steinhaus–Johnson–Trotter algorithm
 *  https://commons.apache.org/proper/commons-collections/jacoco/org.apache.commons.collections4.iterators/PermutationIterator.java.html
 * */
public class SteinhausJohnsonTrotterPermutation implements Permutation{

	// index array
	private int[] keys;
	// direction array : false (left), true (right)
	private boolean[] direction;
	
	// 다음 출력 순열
	private Object[] nextOutputs;
	
	@Override
	public void printPermutationAll(Object[] inputs) {
		keys = new int[inputs.length];
		// 1 ~ n 까지 값 설정 
		for(int i = 0; i < keys.length; i++) {
			keys[i] = i + 1;
		}
		
		direction = new boolean[inputs.length];
		Arrays.fill(direction, false); // left 로 모두 설정 
		
		// inputs 으로 outputs 초기화 (최초 순열은 입력 값)
		nextOutputs = new Object[inputs.length];
		System.arraycopy(inputs, 0, nextOutputs, 0, inputs.length);
		
		while(hasNext()) {
			Object[] out = next();
			System.out.println(Arrays.toString(out));
		}
	}
	
	// next permutation 존재하는지 
	private boolean hasNext() {
		return nextOutputs != null;
	}
	
	// mobile integer 의 index return
	private int findMobileIndex() {
		int mobileIndex = -1;
		int mobile = -1;
		for(int i = 0; i < keys.length; i++) {
			if(direction[i]) { // right
				if(i < keys.length - 1 && keys[i] > keys[i+1] && mobile < keys[i]) {
					mobileIndex = i;
					mobile = keys[i];
				}
			}else { // left
				if(i > 0 && keys[i] > keys[i-1] && mobile < keys[i]) {
					mobileIndex = i;
					mobile = keys[i];
				}
			}
		}
		
		return mobileIndex;
	}
	
	private void swap(int mobileIndex) {
		// swap 위치를 지정할 offset
		int offset = direction[mobileIndex] ? 1 : -1;
		
		// index swap 
		int mobile = keys[mobileIndex];
		keys[mobileIndex] = keys[mobileIndex + offset];
		keys[mobileIndex + offset] = mobile;
		
		// real value swap
		Object value = nextOutputs[keys[mobileIndex] - 1];
		nextOutputs[keys[mobileIndex] -1] = nextOutputs[keys[mobileIndex + offset] - 1];
		nextOutputs[keys[mobileIndex + offset] - 1] = value;
		
		// 방향 swap
		boolean mobileDirection = direction[mobileIndex];
		direction[mobileIndex] = direction[mobileIndex + offset];
		direction[mobileIndex + offset] = mobileDirection;
	}
	
	private void reverseDirection(int mobile) {
		for(int i = 0; i < keys.length; i++) {
			if(keys[i] > mobile) {
				direction[i] = !direction[i];
			}
		}
	}
	
	private Object[] next() {
//		System.out.println(toStringKeyAndDirection());
		
		// 현재 permutation 
		Object[] outputs = new Object[nextOutputs.length];
		System.arraycopy(nextOutputs, 0, outputs, 0, nextOutputs.length);
		
		// find the largest mobile integer index
		int mobileIndex = findMobileIndex();
		
		// mobile integer not found exit
		if(mobileIndex == -1) {
			nextOutputs = null;
			return outputs;
		}
		
		// swap 전에 mobile 값 저장 
		int mobile = keys[mobileIndex];
		// swap 
		swap(mobileIndex);
		
		// not move integer direction toggle
		reverseDirection(mobile);
		
		
		return outputs;
	}	
	
	// index들 과 방향 출력 
	private String toStringKeyAndDirection() {
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < keys.length; i++) {
			if(direction[i]) {
				sb.append("<"+keys[i]+" ");
			}else {
				sb.append(keys[i]+"> ");
			}
		}
		return sb.toString();
	}

}
