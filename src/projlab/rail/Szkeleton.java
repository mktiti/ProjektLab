package projlab.rail;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

public class Szkeleton {

    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

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
}