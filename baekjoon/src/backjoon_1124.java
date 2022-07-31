import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class backjoon_1124 {

    static boolean[] prime;

    private static void setPrime(int maxSize){
        prime = new boolean[maxSize+1];

        Arrays.fill(prime, true);
        prime[1] = false;
        prime[0] = false;
        for(int i = 2; i <= Math.sqrt(maxSize); i++) {
            for(int j = 2; i*j <= maxSize; j++) {
                prime[i*j] = false;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int answer = 0;

        setPrime(M);

        for(int i = N; i<=M; i++) {
            if(!prime[i]) { // 소수는 계산 할 필요가 없다.
                if(primeFact(i)){
                    answer++;
                }
            }
        }

        System.out.println(answer);
    }

    private static boolean primeFact(int num) {
        int cnt = 0;
        for(int i = 2; i<=num; i++){
            if(num%i == 0) {
                num /= i;
                if(prime[i])
                    cnt++;
                i--;
            }
        }
        return prime[cnt];
    }

}
