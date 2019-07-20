package lcs;

/** LCS : 최장 공통 부분 문자열 
 * - https://twinw.tistory.com/126
 *  
 */
public class LongestCommonSubSequence {
	
	// recursive
	public static int getLcsRecursive(String base, String other) {
		
		return 1;
	}
	
	// DP 
	public static int getLcsDp(String base, String other) {
		int[][] dp = new int[base.length()+1][other.length()+1];
		
		int lcs = 0;
		for(int i=1; i<=base.length(); i++) {
			for(int j=1; j<=other.length(); j++) {
				// base[i] = other[j] -> dp[i-1][j-1] + 1
				if(base.charAt(i-1) == other.charAt(j-1)) {
					dp[i][j] = dp[i-1][j-1] + 1;
				} // max(dp[i-1][j], dp[i][j-1])
				else {
					dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);
				}
				
				lcs = Math.max(lcs, dp[i][j]);
			}
		}
		
		return lcs;
	}

	public static void main(String[] args) {
		String base = "ABCD";
		String other = "AEBD";
		
		int lcs = getLcsDp(base, other);
		System.out.println(lcs);

	}

}
