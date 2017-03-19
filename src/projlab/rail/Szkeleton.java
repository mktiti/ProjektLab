package projlab.rail;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

public class Szkeleton {

    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static int askNumbered(String message, String... options) {
        if (options == null || options.length == 0) {
            System.out.println("No possible answers given!");
            return -1;
        }

        System.out.println(message);
        for (int i = 0; i < options.length; i++) {
            System.out.println(options[i] + " [" + i + "]");
        }

        String in;
        try {
            while ((in = br.readLine()) != null) {

                try {
                    int n = Integer.parseInt(in);
                    if (n >= 0 && n < options.length) {
                        return n;
                    }
                } catch (NumberFormatException nfe) {}

                System.out.println("Invalid input!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return -1;
    }

    public static String ask(String message, String... answers) {
        if (answers == null || answers.length == 0) {
            System.out.println("No possible answers given!");
            return null;
        }

        System.out.print(message + " (" + answers[0]);
        for (int i = 1; i < answers.length; i++) {
            System.out.print(", " + answers[i]);
        }
        System.out.println(")");

        String in;
        try {
            while ((in = br.readLine()) != null) {
                for (int i = 0; i < answers.length; i++) {
                    if (Objects.equals(in, answers[i])) {
                        return answers[i];
                    }
                }
                System.out.println("Invalid input!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void print(String s) {
        System.out.println(s);
    }

    public static void main(String[] args) {
        String[] options = new String[] { "Váltó állítás", "Alagút lerakás", "Vonat léptetés", "Utasok leszállítása, vonat eltűnés és győzelem" };
        switch (askNumbered("Melyik eset fusson?", options)) {
            case 0:
                switchToggle();
                break;
            case 1:
                tunnelPlace();
                break;
            case 2:
                trainStep();
                break;
            case 3:
                passangerUnboard();
                break;
            default:
                break;
        }
    }

    private static void switchToggle() {
        System.out.println("Váltó állítás");
    }

    private static void tunnelPlace() {
        System.out.println("Alagút lerakás");
    }

    private static void trainStep() {
        System.out.println("Vonat léptetés");
    }

    private static void passangerUnboard() {
        System.out.println("Utasok leszállítása, vonat eltűnés és győzelem");
    }

}