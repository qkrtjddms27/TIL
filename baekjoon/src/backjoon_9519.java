import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class backjoon_9519 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        String string = br.readLine();
        Map<String, Integer> map = new HashMap<>();
        List<String> list = new ArrayList<>();
        list.add(string);
        boolean isCycle = false;

        int size = string.length();
        int cycle = 0;
        StringBuilder front = new StringBuilder();
        StringBuilder back = new StringBuilder();

        for(int i = 0; i<N; i++) {
            front.setLength(0);
            back.setLength(0);
            if(size % 2 == 0) { // 짝
                for(int j = size-1; j >-1; j-=2){
                    back.append(string.charAt(j));
                }
                for(int j = 0; j <size; j+=2){
                    front.append(string.charAt(j));
                }
            } else { // 홀
                for(int j = size-2; j >-1; j-=2){
                    back.append(string.charAt(j));
                }
                for(int j = 0; j <size; j+=2){
                    front.append(string.charAt(j));
                }
            }

            string = front.append(back.toString()).toString();
            list.add(string);
            if(map.get(string) == null) {
                map.put(string, cycle++);
            } else {
                isCycle = true;
                break;
            }
        }
        int find = N % cycle;

        if(isCycle){
            System.out.println(list.get(find));
        } else {
            System.out.println(string);
        }
    }
}
