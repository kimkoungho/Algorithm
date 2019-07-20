package sudoku;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

// 제약 조건 전파 이용 
//모든 스도쿠 풀기 : 구종만씨 글 참고 
//http://theyearlyprophet.com/solving-every-sudoku-puzzle.html 
public class Sudoku {

	// 스도쿠 3가지 규칙에 해당하는 단위들 - 메모제이션  
	private HashMap<String, EnumMap<UnitType, List<String>>> unitMap;
	// 1개의 좌표에서 제약조건 전파 대상 좌표들 
	private HashMap<String, Set<String>> peerSetMap; 
	
	public Sudoku() {
		unitMap = new HashMap<String, EnumMap<UnitType, List<String>>>();
		peerSetMap = new HashMap<String, Set<String>>();
	
		for(char row : SudokuUtil.ROWS.toCharArray()) {
			for(char col : SudokuUtil.COLS.toCharArray()) {
				String key = SudokuUtil.createKey(row, col);
				unitMap.put(key, SudokuUtil.getUnitListMap(row, col));
				peerSetMap.put(key, SudokuUtil.getPeerSet(unitMap.get(key), key));
			}
		}
	}
	
	/** 1번 전략 
	 * - 값을 지운후, 특정 좌표에 값이 1개만 들어갈 수 있다면, peer 들에서 해당 값을 제거함 
	 * @param key : 키 값 (좌표)
	 * @param valueMap : 실제 스도쿠 값이 있는 해시맵 
	 * @return 성공여부 (false : 스도쿠를 만들수 없는 경우)
	 */
	private boolean propagation(String key, HashMap<String, SudokuValue> valueMap) {
		SudokuValue deleteTarget = valueMap.get(key);
		// 실행 조건 : 좌표에 값이 1개일 때만 수행  
		if(deleteTarget.getLength() != 1) {
			return true;
		}
		
		// 값이 1개 
		char deleteValue = deleteTarget.getValues()[0];

		// 제약 조건 전파 : 나와 같은 단위를 사용하는 모든 좌표들에서 값을 제거
		Set<String> peerSet = peerSetMap.get(key);
		for(String peerKey : peerSet) {
			// A4
			if(!deleteValue(peerKey, deleteValue, valueMap)) {
				return false;
			}
		}
		return true;
	}
	
	/** 2번 전략  
	 * - 현재 지운 값(value) 는 3가지 단위에서 1개씩은 존재해야함
	 * @param key : 키 (좌표)
	 * @param value : 값 (단위 별로 1개씩은 무조건 존재해야하는 값) 
	 * @param valueMap : 실제 스도쿠 값이 있는 해시맵
	 * @return 성공여부 (false : 스도쿠를 만들수 없는 경우) 
	 */
	private boolean checkUnitList(String key, char value, HashMap<String, SudokuValue> valueMap) {
		EnumMap<UnitType, List<String>> unitListMap = unitMap.get(key);
		
		for(UnitType unitType : unitListMap.keySet()) {
			// 단위 별로 value 가 가능한 좌표 리스트 
			List<String> possibleKeyList = new ArrayList<String>();
			for(String possibleKey : unitListMap.get(unitType)) {
				SudokuValue possibleValue = valueMap.get(possibleKey); 
				if(possibleValue.contains(value)) {
					possibleKeyList.add(possibleKey);
				}
			}
			
			// value 가 가능한 좌표가 없음 -> 규칙에 어긋남 
			if(possibleKeyList.isEmpty()) {
				return false;
			} // value 가 가능한 좌표가 1개 : 값을 지정함 
			else if(possibleKeyList.size() == 1) {
				String targetKey = possibleKeyList.get(0);
				if(!setValue(targetKey, value, valueMap)) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	/** 특정 좌표에 값을 지정  
	 * - 값을 지정하는 것은 모든 후보들 중 value 를 제외한 값을 제거하는 것 
	 * @param key : 키 (좌표)
	 * @param value : 값 (지정하려는 값) 
	 * @param valueMap : 실제 스도쿠 값이 있는 해시맵
	 * @return 성공여부 (false : 스도쿠를 만들수 없는 경우) 
	 */
	public boolean setValue(String key, char value, HashMap<String, SudokuValue> valueMap) {
		SudokuValue innerValue = valueMap.get(key);
		
		// 현재 좌표에서 value 제외하고 모두 제거 
		for(char candidate : innerValue.getValues()) {
			if(candidate == value) { // 현재 값 제외 
				continue;
			}
			
			// 다른 후보 값들 제거 
			if(!deleteValue(key, candidate, valueMap)) {
				return false;
			}
		}
		
		return true;
	}
	
	
	/** 특정 좌표에 값을 제거
	 * - 1번 전략 : 값을 지운후, 특정 좌표에 값이 1개만 들어갈 수 있다면, peer 들에서 해당 값을 제거함
	 * - 2번 전략 : 현재 지운 값(value) 는 3가지 단위에서 1개씩은 존재해야함
	 * @param key : 키 (좌표)
	 * @param value : 값 (지우려고 하는 값) 
	 * @param valueMap : 실제 스도쿠 값이 있는 해시맵
	 * @return 성공여부 (false : 스도쿠를 만들수 없는 경우)   
	 */
	public boolean deleteValue(String key, char value, HashMap<String, SudokuValue> valueMap) {
		SudokuValue innerValue = valueMap.get(key);
		
		// 후보들 중에서 값이 없음 - 이미 지움 
		if(!innerValue.contains(value)) {
			return true;
		}
		
		// 후보자가 1개 -> 지울수 있는 값이 없음
		if(innerValue.getLength() == 1) {
			return false;
		}else { // 값을 후보에서 제거 
			if(!innerValue.delete(value)) {
				return false;
			}
		}
		
		// 1번 전략 : 값을 지운후, 특정 좌표에 값이 1개만 들어갈 수 있다면  
		if(!propagation(key, valueMap)) {
			return false;
		}
		
		// 2번 전략 : 현재 지운 값(value) 는 3가지 단위에서 1개씩은 존재해야함  
		if(!checkUnitList(key, value, valueMap)) {
			return false;
		}
		
		return true;
	}

	/** 검색 
	 * - 아직 답을 못 찾은 칸 중 가장 후보의 수가 적은 칸부터 DFS 로 검색을 수행  
	 * @param valueMap : 실제 스도쿠 값이 있는 해시맵
	 * @return 결과 해시맵 
	 */
	public HashMap<String, SudokuValue> search(HashMap<String, SudokuValue> valueMap) {
		// 이미 완성 
		if(SudokuUtil.validateResult(valueMap)) {
			return valueMap;
		}
		
		// 아직 답을 못 찾은 칸 중 가장 후보의 수가 적은 칸 찾기 
		String targetKey = null;
		int targetLength = 10;
		for(String key : valueMap.keySet()) {
			SudokuValue value = valueMap.get(key);
			if(value.getLength() == 1) {
				continue;
			}
			
			if(targetLength > value.getLength()) {
				targetKey = key;
				targetLength = value.getLength();
			}
		}
		

		if(valueMap.get(targetKey) == null) {
			System.err.println("not founded target : " + targetKey + "::"+valueMap);
			return null;
		}
		
		HashMap<String, SudokuValue> resultMap = null;
		char[] targetValues = valueMap.get(targetKey).getValues();
		for(char targetValue : targetValues) {
			HashMap<String, SudokuValue> cloneValueMap = SudokuUtil.deepCopyValueMap(valueMap);
			
			// 현재 값을 설정 후  
			if(! setValue(targetKey, targetValue, cloneValueMap)) {
				continue;
			}
			
			// 다른 미완성 값들 검색 
			resultMap = search(cloneValueMap); 
			if(resultMap == null) {
				continue;
			}
			
			// 이미 완성
			if(SudokuUtil.validateResult(resultMap)) {
				return resultMap;
			}
		}
		
		return resultMap;
	}
	
}
