import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class backjoon_18223 {

    static final int INF = 987654321;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int V = Integer.parseInt(st.nextToken());
        int E = Integer.parseInt(st.nextToken());
        int P = Integer.parseInt(st.nextToken());
        int cnt = 0;
        int start = 0;
        int[] min = new int[V];
        boolean[] visited = new boolean[V];

        Arrays.fill(min, INF);
        min[0] = 0;
        visited[0] = true;

        int[][] map = new int[V][V];
        for(int i = 0; i<V; i++){
            Arrays.fill(map[i], INF);
        }

        for(int i = 0; i<E; i++) {
            st = new StringTokenizer(br.readLine());
            int V1 = Integer.parseInt(st.nextToken())-1;
            int V2 = Integer.parseInt(st.nextToken())-1;
            int w = Integer.parseInt(st.nextToken());

            map[V1][V2] = w;
            map[V2][V1] = w;
        }

        while(cnt < V) {
            for(int i = 0; i<V; i++){
                if(i == start) continue;
//
//                map[start][i] = Math.min(map[start][i], );
//                map[i][start] = ;
            }
            cnt++;
            start = find(min, visited);
            visited[start] = true;
        }
    }

    private static int find(int[] minArr, boolean[] visited) {
        int min = Integer.MAX_VALUE;
        int idx = -1;

        for(int i = 1, end = minArr.length; i<end; i++) {
            if(!visited[i] && minArr[i] < min) {
                idx = i;
            }
        }

        return idx;
    }

}
