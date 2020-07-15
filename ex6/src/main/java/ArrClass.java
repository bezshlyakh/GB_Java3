import java.util.Arrays;

public class ArrClass {

    public static void main(String[] args) {
        int[] inArr = {1,1,4,4,1};
        //System.out.println(Arrays.toString(check4(inArr)));
        //System.out.println(has14(inArr));
    }

    public static int[] check4(int[] in){
        for(int i = in.length - 1; i >= 0; i--){
            if(in[i] == 4){
                return Arrays.copyOfRange(in, i + 1, in.length);
            }
        }
        throw new RuntimeException("4 was not found");
    }

    public static boolean has14(int[] in){
        boolean has1 = false, has4 = false;
        for(int i = in.length - 1; i >= 0; i--){
            if(in[i] == 4) has4 = true;
            else if(in[i] == 1) has1 = true;
            else return false;
        }
        return has1 & has4;
    }
}
