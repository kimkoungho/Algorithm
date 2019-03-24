package permutation;

import java.util.Comparator;

public class Runner {

	public static void main(String[] args) {

		SelectPermutation selectPerm = new SelectPermutation();
		RemovePermutation removePerm = new RemovePermutation();
		HeapsAlgorithmPermutation heapsPerm = new HeapsAlgorithmPermutation();
		SteinhausJohnsonTrotterPermutation johnsonPerm = new SteinhausJohnsonTrotterPermutation();
		
		Comparator<Object> comparator = new Comparator<Object>() {
			@Override
			public int compare(Object o1, Object o2) {
				if( o1 instanceof Comparable && o2 instanceof Comparable){
                    Comparable c1 = (Comparable)o1;
                    Comparable c2 = (Comparable)o2;
                    return c1.compareTo(c2);
				}
				return -1;
			}

		};
		LexicographicPermutation lexicographicPerm = new LexicographicPermutation(comparator);
		
		Character[] inputs = new Character[] {'A', 'B', 'C', 'D'};
		
//		selectPerm.printPermutationAll(inputs);
//		removePerm.printPermutationAll(inputs);
//		heapsPerm.printPermutationAll(inputs);
//		johnsonPerm.printPermutationAll(inputs);	
		
		lexicographicPerm.printPermutationAll(inputs);
	}

}