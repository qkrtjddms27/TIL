import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * https://www.acmicpc.net/problem/11437
 *
 * 문제
 * N(2 ≤ N ≤ 50,000)개의 정점으로 이루어진 트리가 주어진다. 트리의 각 정점은 1번부터 N번까지 번호가 매겨져 있으며, 루트는 1번이다.
 *
 * 두 노드의 쌍 M(1 ≤ M ≤ 10,000)개가 주어졌을 때, 두 노드의 가장 가까운 공통 조상이 몇 번인지 출력한다.
 *
 * 입력
 * 첫째 줄에 노드의 개수 N이 주어지고, 다음 N-1개 줄에는 트리 상에서 연결된 두 정점이 주어진다. 그 다음 줄에는 가장 가까운 공통 조상을 알고싶은 쌍의 개수 M이 주어지고, 다음 M개 줄에는 정점 쌍이 주어진다.
 *
 * 출력
 * M개의 줄에 차례대로 입력받은 두 정점의 가장 가까운 공통 조상을 출력한다.
 *
 */
public class backjoon_11437 {

    static int[] tree;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int size = Integer.parseInt(br.readLine());

        tree = new int[size+1];
        tree[1] = 0;

        for(int i = 0; i < size-1; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int num1 = Integer.parseInt(st.nextToken());
            int num2 = Integer.parseInt(st.nextToken());
            int parents = Math.min(num1, num2);
            int idx = Math.max(num1, num2);
            tree[idx] = parents;
        }

        System.out.println(Arrays.toString(tree));
        size = Integer.parseInt(br.readLine());
        for(int i = 0; i < size; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int num1 = Integer.parseInt(st.nextToken());
            int num2 = Integer.parseInt(st.nextToken());
            System.out.println(find(num1, num2));
        }
    }

    private static int find(int num1, int num2) {
        List<Integer> list1 = makeList(num1);
        List<Integer> list2 = makeList(num2);

        System.out.println("==============list1============" + num1);
        for(int n : list1) {
            System.out.print(n + " ");
        }
        System.out.println();

        System.out.println("==============list2=============" + num2);
        for(int n : list2) {
            System.out.print(n + " ");
        }
        System.out.println();

        for (int n: list1) {
            for(int m: list2) {
                if(n == m) return n;
            }
        }

        return -1;
    }

    private static List<Integer> makeList(int num) {
        List<Integer> list = new ArrayList<>();
        list.add(num);
        while(tree[num] > 0) {
            list.add(tree[num]);
            num = tree[num];
        }

        return list;
    }
}
