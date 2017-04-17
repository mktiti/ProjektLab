package projlab.rail;

import projlab.rail.logic.Car;
import projlab.rail.logic.Locomotive;
import projlab.rail.logic.Switch;
import projlab.rail.logic.Tunnel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Szkeleton {

    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static boolean askBoolean(String message) {
        return "I".equals(ask(message, "I", "N"));
    }

    public static int askNumbered(String message, String... options) {
        if (options == null || options.length == 0) {
            System.out.println("No possible answers given!");
            return -1;
        }

        System.out.println(message);
        for (int i = 0; i < options.length; i++) {
            System.out.println("[" + i + "] " + options[i]);
        }

        String in;
        try {
            while ((in = br.readLine()) != null) {
                if ((in = in.trim()).length() > 0) {
                    try {
                        int n = Integer.parseInt(in);
                        if (n >= 0 && n < options.length) {
                            return n;
                        }
                    } catch (NumberFormatException nfe) {}
                }
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
                if ((in = in.trim()).length() > 0) {
                    for (int i = 0; i < answers.length; i++) {
                        if (in.equalsIgnoreCase(answers[i])) {
                            return answers[i];
                        }
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
        if (askBoolean("Van jármű a pályaelemen?")) {
            System.out.println("Csak akkor állítható a váltó, ha üres");
        } else {
            new Switch().toggle();
            System.out.println("A váltó átállt");
        }

    }

    private static void tunnelPlace() {
        System.out.println("Alagút lerakás");
        String[] questions = new String[] {"Létezik már alagút a pályán?", "Van-e már aktivált alagútszáj?"};
        if(askBoolean(questions[0]))
            System.out.println("Egyszerre csak egy alagút lehet a pályán!");
        else{
            if(askBoolean(questions[1])) {
                new GameEngine().buildTunnel(new Tunnel(),new Tunnel());
                System.out.println("Alagút létrehozva");
            }
            else
                System.out.println("Ez az első aktív alagútszáj");
        }
    }

    private static void trainStep() {
        System.out.println("Vonat léptetés");
        Locomotive loc = new Locomotive();
        GameEngine ge = new GameEngine();
        Car[] cars = new Car[] { new Car(), new Car(), new Car() };

        loc.getDestination();
        if (askBoolean("Ütköznek a vonatok?")) {
            System.out.println("A vonatok ütköztek");
            ge.gameOver();
        } else {
            System.out.println("Nincs vonat-vonat ütközés, a vonatok léphetnek.");
            loc.next();

            for (Car c : cars) {
                c.next();
            }

            if (askBoolean("Sérül a vonat?")) {
                System.out.println("A vonat sérült");
                ge.gameOver();

            } else {
                System.out.println("A vonat továbblépett.");
            }
        }
    }

    private static void passangerUnboard() {
        System.out.println("Utasok leszállítása, vonat eltűnés és győzelem");
        char color;

        switch (ask("Milyen színű az állomás?", "P", "K", "Z", "S", "F")) {
            case "P":
                color = 'p';
                System.out.println("Az állomás színe piros.");
                break;
            case "K":
                color = 'k';
                System.out.println("Az állomás színe kék.");
                break;
            case "Z":
                color = 'z';
                System.out.println("Az állomás színe zöld.");
                break;
            case "S":
                color = 's';
                System.out.println("Az állomás színe sárga.");
                break;
            case "F":
                color = 'f';
                System.out.println("Az állomás színe fehér.");
                break;
            default:
                color = 'd';
                break;
        }

        boolean last = false;
        boolean matched = false;

        String q = "Van utas a(z) n kocsiban?";

        char i = 0;
        while (!last && !matched) {
            ++i;

            if (askBoolean("Van következő kocsi?")) {
                System.out.println("A következő kocsit vizsgálom.");
                new Car().next();
            } else {
                System.out.println("A leszállók vizsgálata véget ért.");
                new Car().next();
                last = true;
            }

            q.toCharArray()[14] = i;
            q.toString();

            if (askBoolean(q)) {
                String match = "A kocsi színe megegyezik az állomáséval, utasok leszállítása.";
                String mismatch = "A kocsi színe nem egyezik meg az állomáséval.";
                String msg;

                switch (ask("Milyen színű ez a kocsi?", "P", "K", "Z", "S", "F")) {
                    case "P":
                        if (color == 'p') {
                            matched = true;
                            msg = match;
                        } else {
                            msg = mismatch;
                        }
                        System.out.println(msg);
                        break;
                    case "K":
                        if (color == 'k') {
                            matched = true;
                            msg = match;
                        } else {
                            msg = mismatch;
                        }
                        System.out.println(msg);
                        break;
                    case "Z":
                        if (color == 'z') {
                            matched = true;
                            msg = match;
                        } else {
                            msg = mismatch;
                        }
                        System.out.println(msg);
                        break;
                    case "S":
                        if (color == 's') {
                            matched = true;
                            msg = match;
                        } else {
                            msg = mismatch;
                        }
                        System.out.println(msg);
                        break;
                    case "F":
                        if (color == 'f') {
                            matched = true;
                            msg = match;
                        } else {
                            msg = mismatch;
                        }
                        System.out.println(msg);
                        break;
                    default:
                        break;
                }
                if(matched) {
                    if (askBoolean("Van még utas valamelyik kocsiban?")) {
                        System.out.println("A vonat folytatja az utat.");
                    } else {
                        System.out.println("A vonat eltűnik.");
                    }
                }
            }
        }
        if (askBoolean("Van még vonat a pályán?")) {
            System.out.println("A játék folytatódik.");
        } else {
            System.out.println("Megnyerted a pályát.");
            new GameEngine().gameWon();
        }

    }
}