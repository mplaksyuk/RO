public class MatrixMultiplication {
    public static void main(String[] args) {
        int[] sizes = {100, 1000, 5000};

        for (int matrixSize : sizes) {
            StringMatrix.calculate(args, matrixSize);
            FoxMatrix.calculate(args, matrixSize);
            CannonMatrix.calculate(args, matrixSize);
            SimpleMatrix.calculate(args, matrixSize);
        }
    }
}