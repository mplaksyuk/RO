import mpi.*;

public class SimpleMatrix {
    public static void calculate(String[] args, int matrixSize) {
        MPI.Init(args);

        int procRank = MPI.COMM_WORLD.Rank();

        Matrix matrixA = new Matrix(matrixSize, "A");
        Matrix matrixB = new Matrix(matrixSize, "B");
        Matrix matrixC = new Matrix(matrixSize, "C");
        long startTime = 0L;

        if (procRank == 0) {
            matrixA.fillRandom(3);
            matrixB.fillRandom(3);
            startTime = System.currentTimeMillis();
        }

        for (int i = 0; i < matrixA.width; i++)
            for (int j = 0; j < matrixB.height; j++)
                for (int k = 0; k < matrixA.height; k++)
                    matrixC.matrix[i * matrixA.width + j] += matrixA.matrix[i * matrixA.width + k] * matrixB.matrix[k * matrixB.width + j];

        if (procRank == 0) {
            System.out.print("Simple: matrixSize = " + matrixSize + ", ");
            System.out.println(System.currentTimeMillis() - startTime + " ms");
        }
        MPI.Finalize();
    }
}