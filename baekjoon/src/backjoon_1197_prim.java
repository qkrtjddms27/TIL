import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class backjoon_1197_prim {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int[] shorts = new int[N];
        int start = 0;
        int answer = 0;
        boolean[] visited = new boolean[N];

        int[][] map = new int[N][N];

        for(int i = 0; i<N; i++)
            Arrays.fill(map[i], Integer.MAX_VALUE);

        for(int i = 0; i<N; i++)
            Arrays.fill(shorts, Integer.MAX_VALUE);

        for(int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int idx1 = Integer.parseInt(st.nextToken())-1;
            int idx2 = Integer.parseInt(st.nextToken())-1;
            int weight = Integer.parseInt(st.nextToken());

            map[idx1][idx2] = weight;
            map[idx2][idx1] = weight;
        }


        /**
         * 0으로 시작.
         * 0에서 가장 가까운 노드 탐색 visited[], shortest[]를
         */

        while(allVisited(visited)) {
            visited[start] = true;

            // 경로 값 변경
            for(int i = 0; i<N; i++)
                shorts[i] = Math.min(map[start][i], shorts[i]);

            start = findShortest(visited, shorts);
        }

        for (int num : shorts)
            answer += num;

        answer -= shorts[0];

        System.out.println(answer);
    }

    private static boolean allVisited(boolean[] visited) {
        for(boolean check : visited) {
            if(!check) return true;
        }
        return false;
    }

    private static int findShortest(boolean[] visited, int[] shorts) {
        int value = Integer.MAX_VALUE;

        for(int i = 0, end = shorts.length; i<end; i++) {
            if(!visited[i] && value > shorts[i]) {
                value = shorts[i];
            }
        }

        return value;
    }
}
