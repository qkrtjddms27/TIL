import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class backjoon_4108 {

    static int[] dr = {0,1,0,-1,1,-1,1,-1};
    static int[] dc = {1,0,-1,0,1,-1,-1,1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while(true) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            int row = Integer.parseInt(st.nextToken());
            int col = Integer.parseInt(st.nextToken());
            if(row == 0) return;

            char[][] map = new char[row][col];

            for(int i =0; i<row; i++)
                map[i] = br.readLine().toCharArray();


            for(int i = 0; i<row; i++) {
                for(int j = 0; j<col; j++) {
                    if(map[i][j] == '.') {
                        int cnt = 0;

                        for(int k =0; k<8; k++){
                            int nr = i + dr[k];
                            int nc = j + dc[k];
                            if(nr > -1 && nc > -1 && nr < row && nc < col && map[nr][nc] == '*'){
                                cnt++;
                            }
                        }
                        map[i][j] = (char)(cnt + '0');
                    }
                }
            }

            for(int i = 0; i<row; i++) {
                for(int j = 0; j<col; j++) {
                    System.out.print(map[i][j]);
                }
                System.out.println();
            }

        }
    }
}
