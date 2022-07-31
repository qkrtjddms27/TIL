import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class backjoon_5212 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int[] dr = {0,1,0,-1};
        int[] dc = {1,0,-1,0};
        char[][] map = new char[N][M];
        char[][] newMap = new char[N][M];
        for(int i = 0; i<N; i++) {
            map[i] = br.readLine().toCharArray();
            newMap[i] = map[i].clone();
        }

        for(int i = 0; i<N; i++) {
            for(int j = 0; j<M; j++) {
                int cnt = 0;

                for(int k = 0; k < 4; k++) {
                    int nr = i + dr[k];
                    int nc = j + dc[k];

                    if(nr < 0 || nc < 0 || nr > N-1 || nc > M-1 || map[nr][nc] == '.')
                        cnt++;

                }

                if(cnt > 2) newMap[i][j] = '.';
            }
        }

        int left = 11;
        int right = -1;
        int top = 11;
        int bottom = -1;

        for(int i =0; i <N; i++) {
            for(int j  =0; j <M; j++) {
                if(newMap[i][j] == 'X') {
                    left = Math.min(left, j);
                    right = Math.max(right, j);
                    top = Math.min(top, i);
                    bottom = Math.max(bottom, i);
                }
            }
        }
        for(int i = top; i <= bottom; i++) {
            for(int j = left; j <= right; j++) {
                System.out.print(newMap[i][j]);
            }
            System.out.println();
        }
    }

}
