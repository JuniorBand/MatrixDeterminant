import java.util.Arrays;
import java.util.Scanner;

// Método de eliminação de Gauss

public class CalculateDet2 {
    public static void run() throws RuntimeException{
        Scanner scan = new Scanner(System.in);
        try {

            System.out.println("Do you want to find the determinant or to row reduce a matrix?" +
                    "\n1 - Determinant\n2 - Row Echelon Form\n3 - Inverse of a Matrix");
            byte choice = scan.nextByte(); //dim is byte [-128, 127], bc more than that is insane

            if (choice != 1 && choice != 2 && choice != 3) {
                System.out.println("Invalid choice. Please select 1, 2, or 3.");
                return;
            }

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

                System.out.println("\nMatrix:");
                for (byte i = 0; i < row; i++) {
                    System.out.println(Arrays.toString(ech[i])); //print each row
                }

                ech = echelon(ech);

                System.out.println("\nEchelon Matrix:");
                for (byte i = 0; i < row; i++) {
                    System.out.println(Arrays.toString(ech[i])); //print each row
                }

                return;
            }

            if (choice == 3) {
                System.out.println("Matrix dimension (Ex: if the matrix is 2x2, type 2):");
                byte dim = scan.nextByte();

                double[][] inv = new double[dim][dim*2];

                for (int i = 0; i < dim; i++) {
                    for (int j = 0; j < dim*2; j++) {
                        if(j == dim + i){
                            inv[i][j] = 1;
                            continue; 
                        }
                        if(j > (dim-1)){
                            inv[i][j] = 0;
                            continue;
                        }

                        System.out.println("Insert the element (" + (i + 1) + ", " + (j + 1) + ") of the matrix:");
                        inv[i][j] = scan.nextDouble();
                    }
                }

                System.out.println("\nGauss-Jordan Method for the Inverse Matrix:");
                System.out.println("\n[ A | I ]:");
                for (byte i = 0; i < dim; i++) {
                    System.out.println(Arrays.toString(inv[i])); //print each row
                }

                invert(inv);

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

    private static byte sign = 1; // to keep track of line swaps

    private static double calculateDet2 (double[][] matrix) {
        byte N = (byte) matrix[0].length; //matrix dimension
        double det = 0;

        // 1st line is fixed, it is not gonna be altered.
        int row = 1;
        byte counter = 0; // counts which pivot line we are on
        double last_pivot = 0;
        double element = 0;
        sign = 1;

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

        det = calcDetEch(matrix, sign);

        return det;
    }

    private static double[][] echelon(double[][] ech){
        byte N = (byte) ech.length; // number of rows
        byte M = (byte) ech[0].length; // number of columns

        // 1st line is fixed, it is not gonna be altered.
        int row = 1;
        byte counter = 0; // counts which pivot line we are on
        double last_pivot = 0;
        double element = 0;
        sign = 1;

        while(counter < N-1){ // starts with the first line and goes till the (N-1)th line

            if(row == N){ row = counter + 1; } // if we already found another pivot, we go find the pivot of the next line

            last_pivot = ech[counter][counter];

            if (last_pivot == 0) { // If the pivot equals 0, tries to swap with other lines and invert sign
                for (int k = counter + 1; k < N; k++) {
                    if (ech[k][counter] != 0) {
                        // Swap lines
                        double[] temp = ech[counter];
                        ech[counter] = ech[k];
                        ech[k] = temp;
                        break;
                    }
                }
                last_pivot = ech[counter][counter]; // New pivot
                sign = -1;
            }

            for(int i = counter; i < M; i++) {
                if(i == counter){element = ech[row][counter]; ech[row][counter] = 0; continue;} // sets, as we go along, the elements before each pivot as 0
                ech[row][i] -= (ech[counter][i]*element)/(last_pivot);

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

        return ech;
    }

    private static double[][] invert(double[][] inv){
        inv = echelon(inv);
        byte N = (byte) (inv.length - 1); // number of rows
        byte M = (byte) (inv.length*2 - 1); // number of columns

        int row = N;
        byte counter = (byte) N; // counts which pivot line (column) we are on
        double element = 0;

        double det = calcDetEch(inv, sign);

        if (inv[row][counter] == 0) {
            // Determinant = 0
            System.out.println("\nThe matrix is not invertible.");
            System.out.println("Determinant = 0");
            System.out.println("\nEchelon Matrix:");
            for (byte i = 0; i <= N; i++) {
                System.out.println(Arrays.toString(inv[i])); //print each row
            }
            return inv;
        }

        // OK, here, the matrix is in echelon form, now we have to make the pivots equal to 1 and the elements after each pivot (same line) divided by it.
        while(counter >= 0){ 

            for(int j = row; j <= M; j++) {

                if(j == row && inv[row][j] == 1){break;} // pivot = 1 and line doesn't change
                if(j == row){element = inv[row][j]; inv[row][j] = 1; continue;} // sets, as we go along, the elements before each pivot as 0
                
                inv[row][j] *= 1/(element); //assim dividimos todas os elementos da linha pelo pivot, tornando o pivot 1 e ajustando os outros elementos

            }

            // Fine, the pivot of the row in question is 1 and the rest of the line is adjusted, now we have to eliminate the above elements of each pivot, going upwards.
            int col = row;
            if(row != 0){
                for(int i = row - 1; i >= 0; i--) {

                    double eliminator = inv[i][col]; // element above the pivot we want to eliminate
                    inv[i][col] = 0; // set it to 0

                    for(int j = col + 1; j <= M; j++) {
                        inv[i][j] -= (inv[row][j]*eliminator); // subtract the pivot line multiplied by the element we want to eliminate
                    }
                }
            }

            row--;
            counter--;
        }

        System.out.println("\n[ I | A⁻¹ ]:");
        for (byte i = 0; i <= N; i++) {
            System.out.println(Arrays.toString(inv[i])); //print each row
        }

        double[][] inverted = new double[N + 1][N + 1];

        for (byte i = 0; i <= N; i++){
            for (byte j = 0; j <= N; j++){
                inverted[i][j] = inv[i][j + N + 1];
            }
        }

        System.out.println("\nInverse Matrix (A⁻¹):");
        for (byte i = 0; i <= N; i++) {
            System.out.println(Arrays.toString(inverted[i])); //print each row
        }
        
        System.out.println("\nMatrix Determinant = " + det);
        System.out.println("Inverse Matrix Determinant = " + (1/det));

        return inv;
    }

    private static double calcDetEch(double[][] matrix, byte trustsign){
        double det = 0;
        byte N = (byte) matrix.length; //matrix dimension

        for(int i = 0; i < N; i++){
            if (i == 0){det = trustsign*matrix[0][0]; continue;}
            det *= matrix[i][i]; // Multiply each element of the principal/primary diagonal of the now upper triangle matrix.
        }
        return det;
    }

}

