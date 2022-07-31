import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class backjoon_2210 {

    static Map<String, Integer> map = new HashMap<>();
    static int[][] numPad;
    static int[] dr = {1,0,-1,0};
    static int[] dc = {0,1,0,-1};
    static int answer = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        numPad = new int[5][5];

        for(int i = 0; i<5; i++) {
            StringTokenizer st =new StringTokenizer(br.readLine());
            for(int j = 0; j<5; j++) {
                numPad[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for(int i =0; i<5; i++) {
            for(int j =0; j<5; j++) {
                dfs(new String(""),0, i, j);
            }
        }

        System.out.println(answer);
    }

    private static void dfs(String string, int cnt, int row, int col) {
        if(cnt == 6) {
            if(map.get(string) == null) {
                map.put(string, 0);
                answer++;
            }
            return;
        }

        for(int i = 0; i<4; i++) {
            int nr = row + dr[i];
            int nc = col + dc[i];
            if(nr > -1 && nc > -1 && nr < 5 && nc < 5)
                dfs(string + numPad[nr][nc], cnt+1 ,nr, nc);
        }
    }
}
