package src;

import java.util.Arrays;
import java.util.Scanner;

//Método de Laplace

public class CalculateDet {

    public static void run()  throws RuntimeException{
        try {
            Scanner scan = new Scanner(System.in);

            System.out.println("Usar espaço de quantas dimensões?");
            byte dim = scan.nextByte(); //dim is byte [-128, 127], bc more than that is insane

            double[][] matrix = new double[dim][dim];

            for (int vet = 0; vet < dim; vet++) {
                for (int i = 0; i < dim; i++) {
                    System.out.println("Insira a coordenada (" + (i + 1) + ") do seu vetor (" + (vet + 1) + ") :");
                    matrix[i][vet] = scan.nextDouble();
                }
            }

            byte N = (byte) matrix[0].length; //downcasting (int -> byte)

            double det = calculateDet(matrix, N); //calculate determinant
            String formatdet = String.format("%.5f", det); //if you want a specific format


            for (byte i = 0; i < N; i++) {
                System.out.println(Arrays.toString(matrix[i])); //print each row
            }

            System.out.println("Determinante: " + formatdet);//print det or formatdet
            if (det == 0) {
                System.out.println("Vetores L.D.");
            } else {
                System.out.println("Vetores L.I.");
            }

            System.out.println("Deseja continuar: Não (qualquer tecla) ou Sim (1)?");

            try {
                byte resp = scan.nextByte();
                if (resp == 1) {
                    new CalculateDet();
                }
                scan.close();
                return;
            } catch (RuntimeException e){ //if not a byte
                scan.close();
                return;
            }

        } catch(RuntimeException e){
            System.out.println("Erro inesperado! Tente novamente");
            new CalculateDet();
        }
    }

    private static double calculateDet (double[][] matrix, byte N) {
        N = (byte) matrix[0].length; //matrix dimension
        double det = 0;

        if (N == 1){ //matrix 1x1
            return matrix[0][0];
        }
        if (N == 2){ //matrix 2x2
            det = matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
            return det;
        }
        if (N == 3) { //matrix 3x3
            det = (matrix[0][0]) * (matrix[1][1]) * (matrix[2][2]) + (matrix[0][1]) * (matrix[1][2]) * (matrix[2][0])
                    + (matrix[1][0]) * (matrix[2][1]) * (matrix[0][2]) - (matrix[0][2]) * (matrix[1][1]) * (matrix[2][0])
                    - (matrix[0][1]) * (matrix[1][0]) * (matrix[2][2]) - (matrix[0][0]) * (matrix[2][1]) * (matrix[1][2]);
            return det;
        }

        //matrix NxN
        int sign = 1;
        int col = 0;

        while(col < N){ //criando submatrix para calcular cofator de matrix[0][col]
            double [][] sub_matrix = createSubMatrix(matrix,N);
            det += sign * matrix[0][col] * calculateDet(sub_matrix, N);
            sign = ~sign + 1; //inverte o sign
            col++;
        }


        return det;
    }

    private static double[][] createSubMatrix(double[][] matrix, byte N){

        double[][] sub_matrix = new double[N-1][N-1];

        for (int i=0; i < N-1; i++){ //copy elements to create the right submatrix

            for(int j=0; j < N-1; j++){

                sub_matrix[i][j] = matrix[i+1][j+1];

            }

        }

        return sub_matrix;
    }



}
