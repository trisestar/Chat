import java.util.Arrays;

public class Test {
    public static void main(String[] args) {
        int a[] = {3,4,5};
        int b[] = {3,4,5};
        if (Arrays.equals(a, b)){
            System.out.println("true");

        } else System.out.println("false");

        int id, lol;
        lol=100;
        id=lol++;
        System.out.println(id);
        System.out.println(lol);
    }
}
