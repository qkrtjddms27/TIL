import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class backjoon_1541 {

    public static void main(String[] args) throws IOException {
        int answer = 0;

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(),"-");

        List<String> plusExpression = new ArrayList<>();
        List<Integer> minusExpression = new ArrayList<>();

        while(st.hasMoreTokens()) {
            plusExpression.add(st.nextToken());
        }

        for (String expression : plusExpression) {
            int tmp =0;
            st = new StringTokenizer(expression, "+");

            while(st.hasMoreTokens()) {
                tmp += Integer.parseInt(st.nextToken());
            }

            minusExpression.add(tmp);
        }

        answer = minusExpression.get(0);
        for(int i = 1, end = minusExpression.size(); i<end; i++) {
            answer -= minusExpression.get(i);
        }

        System.out.println(answer);
    }
}
