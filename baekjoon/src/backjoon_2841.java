import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

public class backjoon_2841 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int answer = 0;

        Stack<Integer>[] stacks = new Stack[N];
        for(int i = 0; i<N; i++){
            stacks[i] = new Stack<Integer>();
        }
        same : for(int i = 0; i<N; i++) {
            st = new StringTokenizer(br.readLine());
            int m = Integer.parseInt(st.nextToken());
            int f = Integer.parseInt(st.nextToken());

            while(!stacks[m].isEmpty() && stacks[m].peek() >= f) {
                if(stacks[m].peek() == f) continue same;
                stacks[m].pop();
                answer++;
            }

            stacks[m].push(f);
            answer++;
        }
        System.out.println(answer);
    }
}
