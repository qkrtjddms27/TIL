import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 저번 학기 영상처리 수업을 듣고 배웠던 지식을 최대한 응용 해보고 싶은 혁진이는 이 현수막에서 글자가 몇 개인지 알아보는 프로그램을 만들려 한다.
 *
 * 혁진이는 우선 현수막에서 글자인 부분은 1, 글자가 아닌 부분은 0으로 바꾸는 필터를 적용하여 값을 만드는데 성공했다.
 *
 * 그런데 혁진이는 이 값을 바탕으로 글자인 부분 1이 상, 하, 좌, 우, 대각선으로 인접하여 서로 연결되어 있다면 한 개의 글자라고 생각만 하였다.
 *
 * 혁진이가 필터를 적용하여 만든 값이 입력으로 주어졌을 때, 혁진이의 생각대로 프로그램을 구현하면 글자의 개수가 몇 개인지 출력하여라.
 *
 * 입력
 * 첫 번째 줄에는 현수막의 크기인 M와 N가 주어진다. (1 ≤ M, N ≤ 250)
 *
 * 두 번째 줄부터 M+1 번째 줄까지 현수막의 정보가 1과 0으로 주어지며, 1과 0을 제외한 입력은 주어지지 않는다.
 *
 * 출력
 * 혁진이의 생각대로 프로그램을 구현했을 때, 현수막에서 글자의 개수가 몇 개인지 출력하여라.
 */
public class backjoon_14716{

    static int[] dr = {0,1,0,-1,1,-1,1,-1};
    static int[] dc = {1,0,-1,0,1,1,-1,-1};
    static int n,m;
    public static void main(String[] args) throws IOException  {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int answer = 0;

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        int[][] map = new int[n][m];
        boolean[][] visited = new boolean[n][m];

        for(int i=0; i<n; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j=0; j<m; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for(int i = 0; i<n; i++) {
            for(int j = 0; j<m; j++) {
                if(map[i][j] == 1 && !visited[i][j]) {
                    bfs(i, j, visited, map);
                    answer++;
                }
            }
        }

        System.out.println(answer);
    }

    private static void bfs(int row, int col, boolean[][] visited, int[][] map) {
        Queue<Node> queue = new LinkedList<>();

        queue.offer(new Node(row, col));
        visited[row][col] = true;

        while(!queue.isEmpty()) {
            Node cur = queue.poll();

            for(int i = 0 ; i<8; i++) {
                int nr = cur.row+dr[i];
                int nc = cur.col+dc[i];

                if(nr > -1 && nc > -1 && nc < m && nr < n && !visited[nr][nc] && map[nr][nc] == 1) {
                    queue.offer(new Node(nr, nc));
                    visited[nr][nc] = true;
                }
            }
        }
    }

    static class Node {
        int row,col;

        Node (int row, int col) {
            this.row = row;
            this.col = col;
        }
    }
}
