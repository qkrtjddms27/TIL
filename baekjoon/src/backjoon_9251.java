import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class backjoon_9251 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        char[] string1 = br.readLine().toCharArray();
        char[] string2 = br.readLine().toCharArray();

        int string1Size = string1.length+1;
        int string2Size = string2.length+1;
        int[][] dp = new int[string1Size][string2Size];

        int max = -1;
        for(int i = 1; i< string1Size; i++) {
            for(int j = 1; j< string2Size; j++) {
                dp[i][j] = string1[i-1] == string2[j-1] ? dp[i - 1][j - 1] + 1 : Math.max(dp[i - 1][j], dp[i][j - 1]);
                max = Math.max(max, dp[i][j]);
            }
        }

        System.out.println(max);
    }
}
