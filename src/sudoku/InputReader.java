package sudoku;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InputReader {
	private static final String EASY_FILE_PATH = "./sudoku_input/easy50.txt";
	private static final String HARD_FILE_PATH = "./sudoku_input/top95.txt";
	private static final String HARDEST_FILE_PATH = "./sudoku_input/hardest.txt";	
	
	public static List<String> getEasyInputList() throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(EASY_FILE_PATH));
		
		final String delimiter = "========";
		List<String> inputList = new ArrayList<String>();
		StringBuilder inputBuilder = new StringBuilder();
		
		String line = null;
		while((line = br.readLine()) != null) {
			if(delimiter.equals(line)) {
				inputList.add(inputBuilder.toString());
				inputBuilder = new StringBuilder();
			}else {
				inputBuilder.append(line);
			}
		}
		return inputList;
	}
	
	private static List<String> getCommonInputList(String filePath) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(filePath));
		
		List<String> inputList = new ArrayList<String>();
		String line = null;
		while((line = br.readLine()) != null) {
			inputList.add(line);
		}
		return inputList;
	}
	
	public static List<String> getHardInputList() throws IOException {
		return getCommonInputList(HARD_FILE_PATH);
	}
	
	public static List<String> getHardestInputList() throws IOException {
		return getCommonInputList(HARDEST_FILE_PATH);
	}
}
