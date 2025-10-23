import java.util.Arrays;
import java.util.Scanner;

// Método de eliminação de Gauss

public class CalculateDet2 {
    public static void run() throws RuntimeException{
        try {
            Scanner scan = new Scanner(System.in);

            System.out.println("Do you want to find the determinant or to row reduce a matrix?\n1 - Determinant\n2 - Row Echelon Form");
            byte choice = scan.nextByte(); //dim is byte [-128, 127], bc more than that is insane

            if (choice == 2) {
                System.out.println("Number of rows:");
                byte row = scan.nextByte();
                System.out.println("Number of columns:");
                byte col = scan.nextByte();
                double[][] ech = new double[row][col];
                for (int i = 0; i < row; i++) {
                    for (int j = 0; j < col; j++) {
                        System.out.println("Insert the element (" + (i + 1) + ", " + (j + 1) + ") of the matrix:");
                        ech[i][j] = scan.nextDouble();
                    }
                }
                echelon(ech);
                return;
            }

            System.out.println("Space of how many dimensions?");
            byte dim = scan.nextByte(); //dim is byte [-128, 127], bc more than that is insane

            if(dim == 0){
                System.out.println("\n0-dimensional matrix!\nL.D.");
                return;
            }

            double[][] matrix = new double[dim][dim];

            for (int vet = 0; vet < dim; vet++) {
                for (int i = 0; i < dim; i++) {
                    System.out.println("Insert the (" + (i + 1) + "º) coordinate of your vector (" + (vet + 1) + ") :");
                    matrix[i][vet] = scan.nextDouble();
                }
            }

            byte N = (byte) matrix[0].length; //downcasting (int -> byte)
            System.out.println("\nMatrix:");
            for (byte i = 0; i < N; i++) {
                System.out.println(Arrays.toString(matrix[i])); //print each row
            }

            double det = calculateDet2(matrix); //calculate determinant
            String formatdet = String.format("%.5f", det); //if you want a specific format
            if(formatdet.equals("-0.00000")){formatdet = "0.00000";}

            if (N != 1) {
                System.out.println("\nEchelon Matrix:");
                for (byte i = 0; i < N; i++) {
                    System.out.println(Arrays.toString(matrix[i])); //print each row
                }
            }

            System.out.println("\nDeterminant: " + formatdet);//print det or formatdet
            if (det == 0) {
                System.out.println("L.D. vectors.");
            } else {
                System.out.println("L.I. vectors.");
            }

        } catch(RuntimeException e){
            System.out.println("\nUnexpected Error! Try again");
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

    private static void echelon(double[][] matrix){
        byte N = (byte) matrix.length; // number of rows
        byte M = (byte) matrix[0].length; // number of columns

        // 1st line is fixed, it is not gonna be altered.
        int row = 1;
        byte counter = 0; // counts which pivot line we are on
        double last_pivot = 0;
        double element = 0;

        while(counter < N-1){ // starts with the first line and goes till the (N-1)th line

            if(row == N){ row = counter + 1; } // if we already found another pivot, we go find the pivot of the next line

            last_pivot = matrix[counter][counter];

            if (last_pivot == 0) { // If the pivot equals 0, tries to swap with other lines and invert sign
                for (int k = counter + 1; k < N; k++) {
                    if (matrix[k][counter] != 0) {
                        // Swap lines
                        double[] temp = matrix[counter];
                        matrix[counter] = matrix[k];
                        matrix[k] = temp;
                        break;
                    }
                }
                last_pivot = matrix[counter][counter]; // New pivot
            }

            for(int i = counter; i < M; i++) {
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

        System.out.println("\nEchelon Matrix:");
        for (byte i = 0; i < N; i++) {
            System.out.println(Arrays.toString(matrix[i])); //print each row
        }

    }
}