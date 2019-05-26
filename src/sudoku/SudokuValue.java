package sudoku;

//스도쿠 특정 위치에 해당 하는 값 
class SudokuValue {
	private static final char EMPTY = '_';
	// 길이
	private int length = 9;
	// 후보 숫자들
	private String values = "123456789";
	
	public SudokuValue() {		
	}
	
	public SudokuValue(SudokuValue sudokuValue) {
		this.length = sudokuValue.getLength();
		this.values = sudokuValue.getValueString();
	}

	// 후보에서 값을 제거
	public boolean delete(char value) {
		if (contains(value)) {
			values = values.replace(value, EMPTY);
			length--;
			return true;
		}

		return false;
	}

	// 포함되었는지 ..
	public boolean contains(char value) {
		return values.indexOf(value) > -1;
	}
	
	public char[] getValues() {
		char[] retValues = new char[this.getLength()];
		int index = 0;
		for(char value : values.toCharArray()) {
			if(value == EMPTY) {
				continue;
			}
			retValues[index++] = value;
		}
		return retValues;
	}
	
	public String getValueString() {
		return values;
	}

	// 후보들의 길이 반환
	public int getLength() {
		return length;
	}

	@Override
	public String toString() {
		return values;
	}
	
}
