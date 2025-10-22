import java.util.Arrays;
import java.util.Scanner;

// Método de eliminação de Gauss

public class CalculateDet2 {
    public static void run()  throws RuntimeException{
        try {
            Scanner scan = new Scanner(System.in);

            System.out.println("Usar espaço de quantas dimensões?");
            byte dim = scan.nextByte(); //dim is byte [-128, 127], bc more than that is insane

            if(dim == 0){
                System.out.println("\nDimensão nula!\nL.D.");
                return;
            }

            double[][] matrix = new double[dim][dim];

            for (int vet = 0; vet < dim; vet++) {
                for (int i = 0; i < dim; i++) {
                    System.out.println("Insira a (" + (i + 1) + "º) coordenada do seu vetor (" + (vet + 1) + ") :");
                    matrix[i][vet] = scan.nextDouble();
                }
            }

            byte N = (byte) matrix[0].length; //downcasting (int -> byte)
            System.out.println("\nMatriz:");
            for (byte i = 0; i < N; i++) {
                System.out.println(Arrays.toString(matrix[i])); //print each row
            }

            double det = calculateDet2(matrix); //calculate determinant
            String formatdet = String.format("%.5f", det); //if you want a specific format
            if(formatdet.equals("-0.00000")){formatdet = "0.00000";}

            if (N != 1) {
                System.out.println("\nMatriz Escalonada:");
                for (byte i = 0; i < N; i++) {
                    System.out.println(Arrays.toString(matrix[i])); //print each row
                }
            }

            System.out.println("\nDeterminante: " + formatdet);//print det or formatdet
            if (det == 0) {
                System.out.println("Vetores L.D.");
            } else {
                System.out.println("Vetores L.I.");
            }

        } catch(RuntimeException e){
            System.out.println("\nErro inesperado! Tente novamente");
            CalculateDet2.run();
        }
    }

    private static double calculateDet2 (double[][] matrix) {
        byte N = (byte) matrix[0].length; //matrix dimension
        double det = 0;

        // 1st line is fixed, it is not gonna be altered.
        int row = 1;
        byte counter = 0; // counts which pivot line we are on
        double last_pivot = 0;
        double element = 0;
        byte sign = 1;

        while(counter < N-1){ // starts with the first line and goes till the (N-1)th line

            if(row == N){ row = counter + 1;} // if we already found another pivot, we go find the pivot of the next

            last_pivot = matrix[counter][counter];

            if (last_pivot == 0) { // If the pivot equals 0, tries to swap with other lines and invert sign
                boolean found = false;
                for (int k = counter + 1; k < N; k++) {
                    if (matrix[k][counter] != 0) {
                        // Swap lines
                        double[] temp = matrix[counter];
                        matrix[counter] = matrix[k];
                        matrix[k] = temp;
                        found = true;
                        break;
                    }
                }
                if (!found) return 0; // Singular matrix
                last_pivot = matrix[counter][counter]; // New pivot
                sign = -1;
            }

            for(int i = counter; i < N; i++) {
                if(i == counter){element = matrix[row][counter]; matrix[row][counter] = 0; continue;} // sets, as we go along, the elements before each pivot as 0
                matrix[row][i] -= (matrix[counter][i]*element)/(last_pivot);

                // Example: if we are in counter = 0 and row = 1
                // I'm gonna use the notation aij with i,j ∊ R⁺, even though keep in mind that the matrix in the code begins with i,j = 0.
                // 1st pivot -> L2 - L1 (a21/a11) = L2', we get a new 2nd line with its first element as 0 and its respective pivot.
                // Notice that it's true to all other lines: L3 -> L3', L4 -> L4'... -> Ln -> Ln'
                // And as we are finally finished with setting the first elements of the rest of the lines as 0 and setting the first pivot in L2'.
                // We go define the pivot of the third line (L3, that is now L3'), so we now take reference in L2 as it is now the line that
                // we are gonna use to subtract with the other lines to set 0 as the second element of the rest of the other lines and with that,
                // find the pivot of the third line.
                // 2nd pivot -> L3' - L2' (a'31/a'21) = L3''.
                // And so we keep going till we get the desired upper triangle.
            }

            if(row == N-1){ counter++;}
            row++;
        }

        for(int i = 0; i < N; i++){
            if (i == 0){det = sign*matrix[0][0]; continue;}
            det *= matrix[i][i]; // Multiply each element of the principal/primary diagonal of the now upper triangle matrix.
        }

        return det;
    }

}
