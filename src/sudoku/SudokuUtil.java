package sudoku;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

class SudokuUtil {
	// keys
	static final String ROWS = "ABCDEFGHI";
	static final String COLS = "123456789";
	// 3 *3 사각형 keys 
	private static final String[] SQUARE_ROWS = { "ABC", "DEF", "GHI" };
	private static final String[] SQUARE_COLS = { "123", "456", "789" };
	
	/** 해시 맵의 key 만들기 : 좌표 */
	public static String createKey(char row, char col) {
		return row + "" + col;
	}
	
	/** 스도쿠 해시맵 초기화 
	 * @return A1 ~ I9 까지의 key 를 갖는 스도쿠 게임 판 
	 * ex) "A1": "123456789"
	 */
	public static HashMap<String, SudokuValue> getValueMap(){
		HashMap<String, SudokuValue> valueMap = new LinkedHashMap<String, SudokuValue>();
		for(char row : ROWS.toCharArray()) {
			for(char col : COLS.toCharArray()) {
				String key = createKey(row, col);
				valueMap.put(key, new SudokuValue());
			}
		}
		
		return valueMap;
	}
	
	/** 현재 col 과 같은 행들 추출 
	 * @param col : 컬럼 이름
	 * @return 같은 행을 같는 list (A1 ~ I1)
	 */
	private static List<String> getRowUnitList(char col){
		List<String> rowUnitList = new ArrayList<String>();
		for(char row : ROWS.toCharArray()) {
			rowUnitList.add(createKey(row, col));
		}
		return rowUnitList;
	}
	
	/** 현재 row 와 같은 열들 추출 
	 * @param row : 행 이름 
	 * @return 같은 열을 같는 list (A1 ~ A9)
	 */
	private static List<String> getColUnitList(char row){
		List<String> colUnitList = new ArrayList<String>();
		for(char col : COLS.toCharArray()) {
			colUnitList.add(createKey(row, col));
		}
		return colUnitList;
	}
	
	/** 현재 row, col 이 포함되는 사각형 추출  
	 * @param row : 행 이름
	 * @param col : 컬럼 이름 
	 * @return 현 좌표가 해당하는 3*3 영역 (A1 ~ A3, B1 ~ B3, C1 ~ C3)  
	 */
	private static List<String> getSquareUnitList(char row, char col){	
		// 현재 좌표가 해당하는 3 * 3 영역 찾기 
		String targetRows = null, targetCols = null;
		for(String squareRow : SQUARE_ROWS) {
			if(squareRow.contains(row + "")) {
				targetRows = squareRow;
				break;
			}
		}
		
		for(String squareCol : SQUARE_COLS) {
			if(squareCol.contains(col + "")) {
				targetCols = squareCol;
				break;
			}
		}
		
		List<String> squareUnitList = new ArrayList<String>();
		for(char targetRow : targetRows.toCharArray()) {
			for(char targetCol : targetCols.toCharArray()) {
				squareUnitList.add(createKey(targetRow, targetCol));
			}
		}
		return squareUnitList;
	}
	
	/** 현재 좌표에 해당하는 unit 반환 -> 스도쿠 게임 3가지 규칙에 해당 
	 * @param row : 행 이름
	 * @param col : 열 이름 
	 * @return 현 좌표에 해당하는 unit들 반환
	 * ex) C2 
	 * - ROW : A2 ~ I2
	 * - COL : C1 ~ C9
	 * - SQUARE : A1 ~ A3, B1 ~ B3, C1 ~ C3
	 */
	public static EnumMap<UnitType, List<String>> getUnitListMap(char row, char col){
		EnumMap<UnitType, List<String>> unitListMap = new EnumMap<UnitType, List<String>>(UnitType.class);
		unitListMap.put(UnitType.ROW, getRowUnitList(col));
		unitListMap.put(UnitType.COL, getColUnitList(row));
		unitListMap.put(UnitType.SQUARE, getSquareUnitList(row, col));
		return unitListMap;
	}
	
	
	/** 현재 key 에 해당하는 중복을 제거해서 추출 : 1차원 set
	 * @param unitListMap : 모든 unit 들을 담은 map
	 * @param key : 현재 좌표 
	 * @return 모든 단위 에서 중복을 제거하여 저장 
	 * - 같은 행 9 + 같은 열 9 + 같은 사각형 9 = 27 
	 * - 중복 제거 = 9 + 6 + 6 = 21  
	 */
	public static Set<String> getPeerSet(EnumMap<UnitType, List<String>> unitListMap, String key){
		Set<String> peerSet = new HashSet<String>();
		
		// 단위들에서 모두 추출 
		for(UnitType unitType : unitListMap.keySet()) {
			for(String targetKey : unitListMap.get(unitType)) {
				// 현재 좌표 제거 
				if(key.equals(targetKey)) {
					continue;
				}
				peerSet.add(targetKey);
			}
		}
		return peerSet;
	}
	

	/** 입력 데이터를 파싱하여 key, value 상태로 반환 
	 * @param input : 1줄로 된 것 (top95, hardest), 
	 * - 4.....8.5.3..........7......2.....6.....8.4......1.......6.3.7.5..2.....1.4......
	 */
	public static HashMap<String, Character> parseInput(String input){
		input = input.trim();
		// 입력 오류 
		assert input.length() == 81;
		
		HashMap<String, Character> inputMap = new HashMap<String, Character>();
		int index = 0;
		for(char value : input.toCharArray()) {
			if(COLS.contains(value + "")) {
				int rowIndex = (index) / 9;
				int colIndex = (index - rowIndex * 9) % 9;
				
				inputMap.put(createKey(ROWS.charAt(rowIndex), COLS.charAt(colIndex)), value);
			}
			index++;
		}
		
		return inputMap;
	}
	
	/** 입력 데이터 깊은 복사 */
	public static HashMap<String, SudokuValue> deepCopyValueMap(HashMap<String, SudokuValue> valueMap){
		HashMap<String, SudokuValue> cloneMap = new LinkedHashMap<String, SudokuValue>();
		for(String key : valueMap.keySet()) {
			// 값에 대한 래퍼런스 복사
			SudokuValue cloneValue = new SudokuValue(valueMap.get(key));
			cloneMap.put(key, cloneValue);
		}
		return cloneMap;
	}

	/** 현재 valueMap 출력 */
	public static void print(HashMap<String, SudokuValue> valueMap) {
		System.out.println("-----------------------------------------------------------------");
		for(char row : ROWS.toCharArray()) {
			for(char col : COLS.toCharArray()) {
				String key = row + "" + col;
				System.out.print(valueMap.get(key) +"\t");
			}
			System.out.println();
		}
		System.out.println("-----------------------------------------------------------------");
	}
	
	/** 행들 유효성 검사 */
	private static boolean validateRowUnitList(HashMap<String, SudokuValue> valueMap) {
		for(char col : COLS.toCharArray()) {
			Set<Character> valueSet = new HashSet<Character>();
			for(char row : ROWS.toCharArray()) {
				String key = createKey(row, col);
				SudokuValue value = valueMap.get(key);
				
				if(value.getLength() != 1) {
					return false;
				}
				
				char realValue = value.getValues()[0];
				valueSet.add(realValue);
			}
			
			StringBuilder valueBuilder = new StringBuilder();
			for(char value : valueSet) {
				valueBuilder.append(value);
			}
			
			if(!COLS.equals(valueBuilder.toString())) {
				return false;
			}
		}
		
		return true;
	}
	
	/** 열들 유효성 검사 */
	private static boolean validateColUnitList(HashMap<String, SudokuValue> valueMap) {
		for(char row : ROWS.toCharArray()) {
			Set<Character> valueSet = new HashSet<Character>();
			for(char col : COLS.toCharArray()) {
				String key = createKey(row, col);
				SudokuValue value = valueMap.get(key);
				
				if(value.getLength() != 1) {
					return false;
				}
				
				char realValue = value.getValues()[0];
				valueSet.add(realValue);
			}
			
			StringBuilder valueBuilder = new StringBuilder();
			for(char value : valueSet) {
				valueBuilder.append(value);
			}
			
			if(!COLS.equals(valueBuilder.toString())) {
				return false;
			}
		}
		
		return true;
	}
	
	/** 3*3 사각형들 유효성 검사 */
	private static boolean validateSqaureUnitList(HashMap<String, SudokuValue> valueMap) {
		for(String squareRow : SQUARE_ROWS) {
			for(String squareCol : SQUARE_COLS) {
				Set<Character> valueSet = new HashSet<Character>();
				for(char row : squareRow.toCharArray()) {
					for(char col : squareCol.toCharArray()) {
						String key = createKey(row, col);
						SudokuValue value = valueMap.get(key);
						
						if(value.getLength() != 1) {
							return false;
						}
						
						char realValue = value.getValues()[0];
						valueSet.add(realValue);
					}
				}
				
				StringBuilder valueBuilder = new StringBuilder();
				for(char value : valueSet) {
					valueBuilder.append(value);
				}
				
				if(!COLS.equals(valueBuilder.toString())) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	/** 현재 스도쿠 성공 여부 검사 */
	public static boolean validateResult(HashMap<String, SudokuValue> valueMap) {
		if(!validateRowUnitList(valueMap)) {
			return false;
		}
		
		if(!validateColUnitList(valueMap)) {
			return false;
		}
		
		if(!validateSqaureUnitList(valueMap)) {
			return false;
		}
		
		return true;
	}
}
