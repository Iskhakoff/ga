import java.util.Arrays;
import java.util.Random;

/**
 * Created by Ishakovdv on 12.03.2019.
 */
public class Matrix {

    public Random random = new Random();
    private int[][] matrix; // матрица
    private int[][] setChroms; // набор хромосом
    private int chroms; // количество хромосом в поколении
    private int size; // количество узлов
    private int generations; // размер популяции
    private int startPos; // откуда
    private int finishPos; // куда
    private int numGens; // количество поколений
    private int chancePercent;


    public Matrix(int chroms, int size, int startPos, int finishPos, int numGens) {
        this.matrix = new int[size][size];
        this.size = size;
        this.generations = size + 1;
        this.startPos = startPos - 1;
        this.finishPos = finishPos - 1;
        this.numGens = numGens;
        this.chroms = chroms;
    }

    void startFind(){
        createMatrix();
        showMatrix();
        setChroms = initializeSet(generations, chroms);
        getSum(setChroms);
        sort(setChroms);
        int[][] newGeneration = selection(setChroms);

        for (int i = 2; i <= numGens; i++) {
            System.out.println(i + " GENERATION:");

            if (i != 2) selection(newGeneration);

            cross(newGeneration);
            mutation(newGeneration);
            getSum(newGeneration);
            sort(newGeneration);
        }
    }

    private void createMatrix(){
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if(i == j){
                    matrix[i][j] = 0;

                }else{
                    int randomNum;
                    randomNum = random.nextInt(20);
                    matrix[i][j] = randomNum;
                    matrix[j][i] = randomNum;
                }

            }
        }
    }

    private void showMatrix(){
        System.out.print("\t");
        for (int n = 1; n < size + 1; n++) {
            System.out.print(n + "\t");
        }
        for (int i = 1; i < size + 1; i++) {
            System.out.print("\n");
            System.out.print(i + "\t");
            for (int j = 0; j < size; j++) {
                System.out.print(matrix[i-1][j] + "\t");
            }
        }
        System.out.println();
    }

    private int[][] initializeSet(int generations, int chroms){
        setChroms = new int[chroms][generations];

        for (int i = 0; i < chroms; i++) {
            setChroms[i][0] = startPos;
            setChroms[i][generations - 2] = finishPos; // TODO: check!

        }
        for (int i = 0; i < chroms; i++) {
            for (int j = 0; j < generations - 2; j++) {
                setChroms[i][j] = random.nextInt(generations - 2); // TODO: check!
            }
        }
        System.out.println("FIRST GENERATION:");
        showGeneration(setChroms);
        return setChroms;
    }

    private void showGeneration(int[][] setChroms){
        for (int i = 0; i < setChroms.length; i++) {
            for (int j = 0; j < setChroms[i].length; j++) {
                System.out.print(setChroms[i][j] + "\t");
            }
            System.out.println();
        }
    }

    private int[][] getSum(int[][] setChroms){
        int sum;
        for (int i = 0; i < chroms; i++) {
            sum = 0;
            for (int j = 0; j < generations - 2; j++) {
                sum += matrix[setChroms[i][j]][setChroms[i][j+1]];
            }
            setChroms[i][generations - 1] = sum;
        }
        System.out.println("Sum:");
        showGeneration(setChroms);
        return setChroms;
    }

    private int[][] selection(int[][] setChroms){
        int[][] newGeneration = new int[chroms][generations];
        int[] rowGeneration = new int[chroms];
        for (int i = 0; i < chroms; i++) {
            rowGeneration[i] = i;
        }
        Mix.mix(rowGeneration);
        System.out.println("COMPARE:");
        System.out.println(Arrays.toString(rowGeneration));

        int pos = 0;

        for (int i = 0; i < rowGeneration.length - 1; i++) {
            if(setChroms[rowGeneration[i]][generations - 1] < setChroms[rowGeneration[i + 1]][generations - 1]){
                newGeneration[pos] = setChroms[rowGeneration[i]];
            }else{
                newGeneration[pos] = setChroms[rowGeneration[i+1]];
            }
            pos++;
        }
        System.out.println("NEW GENERATION:");
        showGeneration(newGeneration);
        return newGeneration;
    }

    private int[][] sort(int[][] setChroms){
        for (int i = 0; i < setChroms.length - 1; i++) {
            for (int j = 0; j < setChroms.length-1-i; j++) {
                if(setChroms[j][generations - 1] > setChroms[j+1][generations - 1]){
                    int[] temp = setChroms[j];
                    setChroms[j] = setChroms[j+1];
                    setChroms[j+1] = temp;
                }
            }
        }
        System.out.println("SORT:");
        showGeneration(setChroms);
        return setChroms;
    }

    private int[][] cross(int[][] setChroms){
        int num;
        int[] arr;
        int[] arr2;

        for (int i = 0; i < chroms/2; i+=2) {
            arr = cloneArray(setChroms, i);
            arr2= cloneArray(setChroms, i+1);

            for (int j = 0; j < (generations - 2)/2; j++) {
                num = arr[j];
                arr[j] = arr2[j];
                arr2[j] = num;
            }
            setChroms[i+chroms/2] = arr;
            setChroms[i+1+chroms/2] = arr2;
        }
        System.out.println("CROSSING:");
        showGeneration(setChroms);
        return setChroms;
    }

    private int[][] mutation(int[][] newGeneration){
        for (int i = chroms/2; i < chroms; i++) {
            if(random.nextInt(10) < chancePercent){
                int amount = random.nextInt(generations - 4) + 1;
                for (int j = 0; j < amount; j++) {
                    int randomPos = random.nextInt(generations - 4) + 1;
                    newGeneration[i][randomPos] = newGeneration[i][randomPos + 1];
                }
            }
        }
        System.out.println("MUTATION:");
        showGeneration(newGeneration);
        return newGeneration;
    }


    private int[] cloneArray(int[][] baseArray, int pos){
        int[] arr = new int[generations];
        if (baseArray[pos].length >= 0)
            System.arraycopy(baseArray[pos], 0, arr, 0, baseArray[pos].length);
        return arr;
    }
}
