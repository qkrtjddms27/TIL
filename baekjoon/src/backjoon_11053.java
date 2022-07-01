import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class backjoon_11053 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int length = Integer.parseInt(br.readLine());
        int[] arr = new int[length];
        int[] dp = new int[length];

        StringTokenizer st = new StringTokenizer(br.readLine());

        for(int i = 0; i < length; i++)
            arr[i] = Integer.parseInt(st.nextToken());

        for(int i = 0; i<length; i++) {
            int tmp = -1;
            for(int j = 0; j<i; j++) {
                if(arr[i] > arr[j]) {
                    tmp = Math.max(tmp, dp[j]);
                }
            }
            dp[i] = Math.max(dp[i], tmp) + 1;
        }

        Arrays.sort(dp);

        System.out.println(dp[length-1]);

    }
}
