import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class backjoon_1240 {

    static int[][] tree;
    static int N, M;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        tree = new int[N][N];

        for(int i = 0; i<N; i++) {
            Arrays.fill(tree[i], Integer.MAX_VALUE);
        }

        for(int i = 0; i<N-1; i++) {
            st = new StringTokenizer(br.readLine());
            int start = Integer.parseInt(st.nextToken())-1;
            int end = Integer.parseInt(st.nextToken())-1;
            int weight = Integer.parseInt(st.nextToken());
            tree[start][end] = weight;
            tree[end][start] = weight;
        }

        for(int i = 0; i<M; i++) {
            st = new StringTokenizer(br.readLine());
            int start = Integer.parseInt(st.nextToken())-1;
            int end = Integer.parseInt(st.nextToken())-1;
            System.out.println(bfs(start, end));
        }

    }

    private static int bfs(int start, int end) {
        boolean[] visited = new boolean[N];

        Queue<Node> queue = new LinkedList<>();
        queue.offer(new Node(start, 0));
        visited[start] = true;

        while(!queue.isEmpty()) {
            Node cur = queue.poll();

            for(int i = 0; i<N; i++) {
                if(!visited[i] && tree[cur.start][i] != Integer.MAX_VALUE) {

                    if(end == i)
                        return cur.weight + tree[cur.start][i];

                    queue.offer(new Node(i, cur.weight+tree[cur.start][i]));
                    visited[cur.start] = true;
                }
            }
        }

        return -1;
    }

    static class Node {
        int start, weight;

        Node(int start, int weight) {
            this.start = start;
            this.weight = weight;
        }
    }

}
