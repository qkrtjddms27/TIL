import java.io.BufferedReader;
import java.io.InputStreamReader;

public class backjoon_1309 {

    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int[][] dp = new int[3][N+1];

        dp[0][1] = 1;
        dp[1][1] = 1;
        dp[2][1] = 1;
        for(int i = 2; i<=N; i++) {
            dp[0][i] = dp[0][i-1] + dp[1][i-1] + dp[2][i-1] % 9901;
            dp[1][i] = (dp[0][i-1] + dp[2][i-1]) % 9901;
            dp[2][i] = (dp[0][i-1] + dp[1][i-1]) % 9901;
        }

        System.out.println(dp[0][N]+dp[1][N]+dp[2][N] % 9901);

    }
}
