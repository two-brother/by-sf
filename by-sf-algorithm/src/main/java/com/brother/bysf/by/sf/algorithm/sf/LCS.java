package com.brother.bysf.by.sf.algorithm.sf;

/**
 * @author sk-shifanwen
 * @date 2018/1/8
 */
public class LCS {
    // These are "constants" which indicate a direction in the backtracking array.
    private static final int NEITHER     = 0;
    private static final int UP          = 1;
    private static final int LEFT        = 2;
    private static final int UP_AND_LEFT = 3;

    public static String LCSAlgorithm(String a, String b) {
        int n = a.length();
        int m = b.length();
        int S[][] = new int[n+1][m+1];
        int R[][] = new int[n+1][m+1];
        int ii, jj;

        // It is important to use <=, not <.  The next two for-loops are initialization
        for(ii = 0; ii <= n; ++ii) {
            S[ii][0] = 0;
            R[ii][0] = UP;
        }
        for(jj = 0; jj <= m; ++jj) {
            S[0][jj] = 0;
            R[0][jj] = LEFT;
        }

        // This is the main dynamic programming loop that computes the score and
        // backtracking arrays.
        for(ii = 1; ii <= n; ++ii) {
            for(jj = 1; jj <= m; ++jj) {
                if( a.charAt(ii-1) == b.charAt(jj-1) ) {
                    S[ii][jj] = S[ii-1][jj-1] + 1;
                    R[ii][jj] = UP_AND_LEFT;
                } else {
                    S[ii][jj] = S[ii-1][jj-1] + 0;
                    R[ii][jj] = NEITHER;
                }

                if( S[ii-1][jj] >= S[ii][jj] ) {
                    S[ii][jj] = S[ii-1][jj];
                    R[ii][jj] = UP;
                }

                if( S[ii][jj-1] >= S[ii][jj] ) {
                    S[ii][jj] = S[ii][jj-1];
                    R[ii][jj] = LEFT;
                }
            }
        }

        // The length of the longest substring is S[n][m]
        ii = n;
        jj = m;
        int pos = S[ii][jj] - 1;
        char lcs[] = new char[ pos+1 ];

        // Trace the backtracking matrix.
        while( ii > 0 || jj > 0 ) {
            if( R[ii][jj] == UP_AND_LEFT ) {
                ii--;
                jj--;
                lcs[pos--] = a.charAt(ii);
            } else if( R[ii][jj] == UP ) {
                ii--;
            } else if( R[ii][jj] == LEFT ) {
                jj--;
            }
        }

        return new String(lcs);
    }

    public static void main(String args[]) {
        try {
            String a = "ABCDEFABD";
            String b = "AEFCDEBD";
            String s = LCSAlgorithm(a, b);
            System.out.println(s);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}
class DP{
    public static int getLongestCommonSubsequence(String a, String b){
        int m = a.length();
        int n = b.length();
        int[][] dp = new int[m+1][n+1];

        for(int i=0; i<=m; i++){
            for(int j=0; j<=n; j++){
                if(i==0 || j==0){
                    dp[i][j]=0;
                }else if(a.charAt(i-1)==b.charAt(j-1)){
                    dp[i][j] = 1 + dp[i-1][j-1];
                }else{
                    dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);
                }
            }
        }
        return dp[m][n];
    }

    public static void main(String args[]) {
        try {
            String a = "ABCDEFABD";
            String b = "AEFCDEBD";
            int score = getLongestCommonSubsequence(a, b);
            System.out.println(score);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}