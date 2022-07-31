import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class backjoon_2529 {

    static char[] sign;
    static int N;
    static boolean[] visited;
    static int[] numbers;
    static String maxArr;
    static String minArr;
    static long max = -1;
    static long min = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());

        sign = new char[N];
        for(int i = 0; i<N; i++) {
            sign[i] = st.nextToken().toCharArray()[0];
        }
        visited = new boolean[10];
        numbers = new int[N+1];

        dfs(0);

        System.out.println(maxArr);
        System.out.print(minArr);
    }

    private static void dfs(int pos) {
        if(pos == N+1) {
            StringBuilder sb = new StringBuilder("");
            for(int i = 0; i<=N; i++)
                sb.append(numbers[i]);

            long sum = Long.parseLong(sb.toString());
            if(max < sum) {
                max = sum;
                maxArr = sb.toString();
            }

            if(min > sum) {
                min = sum;
                minArr = sb.toString();
            }

            return;
        }

        for(int i = 0; i<=9; i++) {
            if(visited[i] || !valid(pos, i)) continue;

            visited[i] = true;
            numbers[pos] = i;
            dfs(pos+1);
            visited[i] = false;
        }
    }

    private static boolean valid(int pos, int num) {
        if(pos == 0) return true;
        if(sign[pos-1] == '<')
            return numbers[pos-1] < num;
        else
            return numbers[pos-1] > num;
    }

}
