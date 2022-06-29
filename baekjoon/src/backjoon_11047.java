import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class backjoon_11047 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        /**
         * coinCount = 코인의 종류 갯수
         * money = 거스름돈
         * idx = 시작은 가장 비싼 금액부터 시작하는 인덱스
         * answer = 최종 출력 값
         * coinArr = 종류를 담아 놓은 배열
         */
        int coinCount = Integer.parseInt(st.nextToken());
        int money = Integer.parseInt(st.nextToken());
        int idx = coinCount-1;
        int answer = 0;
        int[] coinArr = new int[coinCount];

        /**
         *  동전종류 배열에 채워넣기
         */
        for(int i = 0; i < coinCount; i++)
            coinArr[i] = Integer.parseInt(br.readLine());

        /**
         * 거스름돈이 0이 되면 반복문 멈춤춤
         */
        while(money > 0) {
            if(money >= coinArr[idx]){
                answer += money/coinArr[idx];
                money %= coinArr[idx];
            }
            idx--;
        }

        System.out.println(answer);
    }
}
