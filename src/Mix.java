import java.util.Random;

/**
 * Created by Ishakovdv on 12.03.2019.
 */
public class Mix{

    static void mix(int[] arr){
        Random random = new Random();
        for (int i = 0; i < arr.length; i++) {
            change(arr, i, random.nextInt(arr.length));
        }
    }

    static void change(int[] arr, int i, int j){
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

}
