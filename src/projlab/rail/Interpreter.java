package projlab.rail;

import projlab.rail.logic.Color;
import projlab.rail.logic.CrashException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Interpreter {

    @FunctionalInterface
    private interface Command {
        String call(String[] params) throws CrashException, IllegalArgumentException, NumberFormatException;
    }

    private final Proto proto = new Proto();

    private final Map<String, Command> commandLookup = new HashMap<>();
    {
        commandLookup.put("step",               this::step);
        commandLookup.put("activateTunnel",     this::activateTunnel);
        commandLookup.put("deactivateTunnel",   this::deactivateTunnel);
        commandLookup.put("toggle",             this::toggle);
        commandLookup.put("launch",             this::launch);
        commandLookup.put("createRail",         this::createRail);
        commandLookup.put("createStation",      this::createStation);
        commandLookup.put("createCar",          this::createCar);
        commandLookup.put("createLocomotive",   this::createLocomotive);
        commandLookup.put("connectToTrain",     this::connectToTrain);
        commandLookup.put("connect",            this::connect);
        commandLookup.put("addPerson",          this::addPerson);
    }

    private static void assertParams(String[] params, int num) throws IllegalArgumentException {
        if (params == null) {
            if (num > 0) {
                throw new IllegalArgumentException("Illegal parameters for command!");
            }
        } else if (params.length != num) {
            throw new IllegalArgumentException("Illegal parameters for command!");
        }
    }

    private String callCommand(String in) throws CrashException {
        String[] ss = in.split(" ");
        String[] params = new String[ss.length - 1];
        for (int i = 0; i < params.length; i++) {
            params[i] = ss[i + 1].trim();
        }
        Command command = commandLookup.get(ss[0].trim());
        if (command != null) {
            return command.call(params);
        }

        throw new IllegalArgumentException("Unknown command!");
    }

    private void run() throws CrashException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            String line;
            System.out.print(">");
            while ((line = br.readLine()) != null && (line = line.trim()).length() > 0) {
                System.out.println(callCommand(line));
                System.out.print(">");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String step(String[] params) throws CrashException {
        assertParams(params, 0);
        proto.step();
        return "";
    }

    private String activateTunnel(String[] params) throws CrashException {
        assertParams(params, 1);
        proto.activateTunnel(Integer.parseInt(params[0]));

        return "Tunnel activated";
    }

    private String deactivateTunnel(String[] params) throws CrashException {
        assertParams(params, 1);
        proto.deactivateTunnel(Integer.parseInt(params[0]));

        return "Tunnel deactivated";
    }

    private String toggle(String[] params) throws CrashException {
        if (params == null || params.length != 1){
            throw new IllegalArgumentException("Illegal parameters for command!");
        }

        proto.toggle(Integer.parseInt(params[0]));
        return "The switch has been toggled";
    }

    private String launch(String[] params) throws CrashException, NumberFormatException {
        assertParams(params, 1);
        proto.launch(Integer.parseInt(params[0]));

        return "Train has been started";
    }

    private String createRail(String[] params) throws CrashException {
        if (params == null || !(params.length == 1 || params.length == 0)) {
            throw new IllegalArgumentException("Illegal parameters for command!");
        }

        Proto.RailType type = Proto.RailType.PLAIN;
        if (params.length > 0) {
            type = Proto.RailType.lookup(params[0], Proto.RailType.PLAIN);
        }
        return "Created with id: " + proto.createRail(type);
    }

    private String createStation(String[] params) throws CrashException {
        assertParams(params, 1);
        if(Color.lookup(params[0]) != null){
            proto.createStation(Color.lookup(params[0]));
        }

        return "Station created";
    }

    private String createCar(String[] params) throws CrashException {
        return "";
    }

    private String createLocomotive(String[] params) throws CrashException {
        return "";
    }

    private String connectToTrain(String[] params) throws CrashException {
        return "";
    }

    private String connect(String[] params) throws CrashException {
        return "";
    }

    private String addPerson(String[] params) throws CrashException {
        return "";
    }

    public static void main(String[] args) {
        try {
            new Interpreter().run();
        } catch (CrashException ce) {
            System.out.print("Crash exception! - " + ce.getMessage());
        }
    }

}