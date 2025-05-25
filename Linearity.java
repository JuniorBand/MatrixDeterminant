package src;

import java.util.Locale;


public class Linearity {

    public static void main(String[] args){
        Locale.setDefault(Locale.US); //set US for rationals (using "."), unset to use ",";

        new CalculateDet();

    }

}
