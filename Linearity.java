package src;

import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;


public class Linearity {

    public static void main(String[] args){
        Locale.setDefault(Locale.US); //set US for rationals (using "."), unset to use ",";
        Scanner scan = new Scanner(System.in);
        byte resp = 1;

        while (resp == 1) {
            CalculateDet2.run(); // Executa uma vez a lógica principal

            System.out.println("Deseja continuar: Não (qualquer tecla) ou Sim (1)?");
            try {
                resp = scan.nextByte();
            } catch (Exception e) {
                resp = 0; // Qualquer coisa diferente de 1 encerra
            }
        }

    }

}
