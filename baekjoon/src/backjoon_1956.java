import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class backjoon_1956 {

    static final int INF = 98765432;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        int[][] map = new int[N][N];

        for(int i = 0; i<N; i++) {
            for(int j =0; j<N;j++) {
                if(i == j) map[i][j] = 0;
                else map[i][j] = INF;
            }
        }

        for(int i = 0; i<M; i++) {
            st = new StringTokenizer(br.readLine());
            int V1 = Integer.parseInt(st.nextToken())-1;
            int V2 = Integer.parseInt(st.nextToken())-1;
            int W =Integer.parseInt(st.nextToken());

            map[V1][V2] = W;
        }

        for(int k =0; k<N; k++) {
            for(int i =0; i<N; i++){
                for(int j =0; j<N; j++){
                    map[i][j] = Math.min(map[i][j],map[i][k] + map[k][j]);
                }
            }
        }

        int answer = INF;

        for(int i = 0; i<N; i++){
            for(int j =0; j<N; j++) {
                if(i == j) continue;

                if(map[i][j] != INF && map[j][i] != INF) {
                    answer = Math.min(answer, map[i][j] + map[j][i]);
                }
            }
        }

        if(answer > INF) answer = -1;

        System.out.println(answer);
    }

}
