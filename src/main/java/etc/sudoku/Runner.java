package etc.sudoku;

import java.io.IOException;
import java.util.*;

public class Runner {

	public static void main(String[] args) throws IOException {
		Sudoku sudoku = new Sudoku();
		
		// 테스트 코드 
		//testUnitListMap();
		//testPeerSet();
		
		List<String> inputList = InputReader.getEasyInputList();
//		inputList.addAll(InputReader.getHardInputList());
		inputList = new ArrayList();
		inputList.addAll(InputReader.getHardestInputList());
		
		for(String input : inputList) {
			System.out.println(input);
			
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
				throw new RuntimeException("");
			}
			return;
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
