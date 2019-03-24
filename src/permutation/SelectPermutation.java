package permutation;

import java.util.Arrays;

public class SelectPermutation implements Permutation{

	// input 배열 caching
	private Object[]inputs;
	// 방문 배열 
	private boolean[] visited;
	// 출력 배열(순열)
	private Object[] outputs;

	@Override
	public void printPermutationAll(Object[] inputs) {
		this.inputs = inputs;
		visited = new boolean[inputs.length];
		Arrays.fill(visited, false);
		outputs = new Object[inputs.length];
		
		selectElement(0);
	}
	
	private void selectElement(int selectIndex) {
		// all element select 
		if(inputs.length == selectIndex) {
			System.out.println(Arrays.toString(outputs));
			return;
		}
		
		for(int i=0; i<inputs.length; i++) {
			// 이미 선택한 요소 제외 
			if(visited[i]) {
				continue;
			}
			
			//현재 요소 선택됨 
			visited[i] = true; 
			outputs[selectIndex] = inputs[i];
			// 다음 칸 선택 
			selectElement(selectIndex + 1);
			//현재 요소 해제
			visited[i] = false;
		}
	}
	
	// TODO: 비트 마스크 이용 
	private void selectElementBitMask(int selectIndex) {
		
		
	}

}
