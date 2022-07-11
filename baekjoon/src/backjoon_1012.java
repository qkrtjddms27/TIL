import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class backjoon_1012 {

    static int[] dr = {1,0,-1,0};
    static int[] dc = {0,1,0,-1};
    static int N,M;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());

        for(int t = 0; t<T; t++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());
            int R = Integer.parseInt(st.nextToken());
            int answer = 0;
            boolean[][] map = new boolean[N][M];

            for(int i =0; i<R; i++) {
                st = new StringTokenizer(br.readLine());
                map[Integer.parseInt(st.nextToken())][Integer.parseInt(st.nextToken())] = true;
            }

            for(int i = 0; i<N; i++) {
                for(int j = 0; j<M; j++) {
                    if(map[i][j]){
                        bfs(map, i, j);
                        answer++;
                    }
                }
            }

            System.out.println(answer);
        }
    }

    private static void bfs(boolean[][] map, int row, int col) {
        Queue<Node> queue = new LinkedList<>();

        queue.offer(new Node(row, col));
        map[row][col] = false;

        while(!queue.isEmpty()){
            Node cur = queue.poll();

            for(int i = 0; i<4; i++){
                int nr = cur.row + dr[i];
                int nc = cur.col + dc[i];

                if(nr > -1 && nc > -1 && nr < N && nc < M && map[nr][nc]) {
                    queue.offer(new Node(nr, nc));
                    map[nr][nc] = false;
                }
            }
        }
    }

    static class Node{
        int row, col;

        Node(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }
}
