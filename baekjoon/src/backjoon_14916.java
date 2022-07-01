import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class backjoon_14916 {

    static int IMPOSSIBLE = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int input = Integer.parseInt(br.readLine());
        int[] money = new int[100001];

        Arrays.fill(money, IMPOSSIBLE);

        money[0] = 0;
        money[2] = 1;
        money[4] = 2;
        money[5] = 1;

        for(int i = 6; i <= input; i++) {
            if(money[i-2] != IMPOSSIBLE && money[i-5] == IMPOSSIBLE) {
                money[i] = money[i-2] +1;
            } else if(money[i-5] != IMPOSSIBLE && money[i-2] == IMPOSSIBLE) {
                money[i] = money[i-5] +1;
            } else if(money[i-2] != IMPOSSIBLE && money[i-5] != IMPOSSIBLE) {
                money[i] = Math.min(money[i-2], money[i-5]) + 1;
            }
        }
        if(money[input] == IMPOSSIBLE) System.out.println(-1);
        else System.out.println(money[input]);
    }
}
