import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class backjoon_2579 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int size = Integer.parseInt(br.readLine());
        int answer = 0;
        int[] stairs = new int[size];
        int[] dp = new int[size];

        for (int i = 0; i < size; i++)
            stairs[i] = Integer.parseInt(br.readLine());

        if (size > 3) {
            dp[0] = stairs[0];
            dp[1] = Math.max(stairs[0] + stairs[1], stairs[1]);
            dp[2] = Math.max(stairs[0] + stairs[2], stairs[1] + stairs[2]);

            for (int i = 3; i < size; i++)
                dp[i] = Math.max(dp[i - 2] + stairs[i], stairs[i - 1] + stairs[i] + dp[i - 3]);

            answer = dp[size - 1];

        } else if(size == 3)
            answer = Math.max(stairs[0] + stairs[1], Math.max(stairs[1] + stairs[2], stairs[0] + stairs[2]));
        else {
            for (int i : stairs)
                answer += i;
        }


        System.out.println(answer);
    }

}
