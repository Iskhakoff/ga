/**
 * Created by Ishakovdv on 12.03.2019.
 */

public class Main {

    public static void main(String[] args) {
        int size = 3;
        int generations = 9;
        int chrom = 3;

        Matrix matrix = new Matrix(chrom, size, 1, size, generations);

        matrix.startFind();
    }

}
