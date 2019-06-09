package sudoku;

import java.io.IOException;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class Runner {

	public static void main(String[] args) throws IOException {
		Sudoku sudoku = new Sudoku();
		
		// 테스트 코드 
		//testUnitListMap();
		//testPeerSet();
		
		List<String> inputList = InputReader.getEasyInputList();
		inputList.addAll(InputReader.getHardInputList());
		inputList.addAll(InputReader.getHardestInputList());

		for(String input : inputList) {
			HashMap<String, SudokuValue> valueMap = SudokuUtil.getValueMap();
			
			HashMap<String, Character> inputMap = SudokuUtil.parseInput(input);
			for(String key : inputMap.keySet()) {
				sudoku.setValue(key, inputMap.get(key), valueMap);
			}
			
			HashMap<String, SudokuValue> resultMap = sudoku.search(valueMap);
			if(resultMap != null) {
				SudokuUtil.print(resultMap);
				System.out.println(SudokuUtil.validateResult(resultMap));
			}else {
				System.out.println("error ... \n" + input);	
			}
		}
		
	}
	
	private static void testUnitListMap() {
		EnumMap<UnitType, List<String>> unitListMap = SudokuUtil.getUnitListMap('A', '3');
		System.out.println("A3");
		System.out.println(unitListMap.get(UnitType.ROW));
		System.out.println(unitListMap.get(UnitType.COL));
		System.out.println(unitListMap.get(UnitType.SQUARE));
	}
	
	private static void testPeerSet() {
		EnumMap<UnitType, List<String>> unitListMap = SudokuUtil.getUnitListMap('A', '3');
		Set<String> peerSet = SudokuUtil.getPeerSet(unitListMap, "A3");
		System.out.println("A3");
		System.out.println(unitListMap.get(UnitType.ROW));
		System.out.println(unitListMap.get(UnitType.COL));
		System.out.println(unitListMap.get(UnitType.SQUARE));
		System.out.println(peerSet);
	}
}
