import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class backjoon_1926 {

    static int[] dr = {0,1,0,-1};
    static int[] dc = {1,0,-1,0};
    static boolean[][] visited;
    static int[][] picture;
    static int N, M;
    static int max = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int cnt = 0;
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        picture = new int[N][M];
        visited = new boolean[N][M];

        for(int i = 0; i<N; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j<M; j++) {
                picture[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for(int i = 0; i<N; i++) {
            for(int j =0; j<M; j++) {
                if(!visited[i][j] && picture[i][j] == 1) {
                    max = Math.max(bfs(i, j), max);
                    cnt++;
                }
            }
        }

        System.out.println(cnt);
        System.out.println(max);
    }

    private static int bfs(int row, int col) {
        Queue<Node> queue = new LinkedList<>();

        queue.offer(new Node(row, col));
        visited[row][col] = true;
        int cnt = 1;
        while(!queue.isEmpty()) {
            Node cur = queue.poll();
            for(int i = 0; i<4; i++) {
                int nr = cur.row + dr[i];
                int nc = cur.col + dc[i];

                if(nr > -1 && nc > -1 && nr < N && nc < M && !visited[nr][nc] && picture[nr][nc] == 1) {
                    queue.offer(new Node(nr,nc));
                    visited[nr][nc] = true;
                    cnt++;
                }
            }
        }

        return cnt;
    }

    static class Node {
        int row, col;

        Node(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }

}
