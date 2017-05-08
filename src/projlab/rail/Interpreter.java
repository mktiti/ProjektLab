package projlab.rail;

public class Interpreter {

    /*
    @FunctionalInterface
    private interface Command {
        String call(String[] params) throws TrainException, IllegalArgumentException, NumberFormatException;
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
*/
    /** Checks if the right number of parameters are given */
   /* private static void assertParams(String[] params, int num) throws IllegalArgumentException {
        if (params == null) {
            if (num > 0) {
                throw new IllegalArgumentException("Illegal parameters for command!");
            }
        } else if (params.length != num) {
            throw new IllegalArgumentException("Illegal parameters for command!");
        }
    }
*/
    /** runs specified command */
   /* private String callCommand(String in) throws TrainException {
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
*/
    /** runs the interpreter */
   /* private void run() throws TrainException {
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
*/
    /*
    private String step(String[] params) throws TrainException {
        assertParams(params, 0);
        proto.step();
        return "";
    }

    private String activateTunnel(String[] params) throws TrainException {
        assertParams(params, 1);
        proto.activateTunnel(Integer.parseInt(params[0]));

        return "Tunnel activated";
    }

    private String deactivateTunnel(String[] params) throws TrainException {
        assertParams(params, 1);
        proto.deactivateTunnel(Integer.parseInt(params[0]));

        return "Tunnel deactivated";
    }

    private String toggle(String[] params) throws TrainException {
        if (params == null || params.length != 1){
            throw new IllegalArgumentException("Illegal parameters for command!");
        }

        proto.toggle(Integer.parseInt(params[0]));
        return "The switch has been toggled";
    }

    private String launch(String[] params) throws TrainException, NumberFormatException {
        assertParams(params, 1);
        proto.launch(Integer.parseInt(params[0]));

        return "Train has been started";
    }

    private String createRail(String[] params) throws TrainException {
        if (params == null || !(params.length == 1 || params.length == 0)) {
            throw new IllegalArgumentException("Illegal parameters for command!");
        }

        Proto.RailType type = Proto.RailType.PLAIN;
        if (params.length > 0) {
            type = Proto.RailType.lookup(params[0], Proto.RailType.PLAIN);
        }
        return "Created with id: " + proto.createRail(type);
    }

    private String createStation(String[] params) throws TrainException {
        assertParams(params, 1);
        Color color = Color.lookup(params[0]);
        if (color == null) {
            return "Invalid color!";
        } else {
            return "Station created with id: " + proto.createStation(color);
        }
    }

    private String createCar(String[] params) throws TrainException {
        if (params == null || !(params.length == 1 || params.length == 0)) {
            throw new IllegalArgumentException("Illegal parameters for command!");
        }

        if (params.length == 0 || params[0].trim().length() == 0) {
            return "Coal Car created, id: " + proto.createCar(null);
        }

        Color color = Color.lookup(params[0]);
        if (color == null) {
            return "Invalid color!";
        } else {
            return "Car created with id: " + proto.createCar(color);
        }

    }

    private String createLocomotive(String[] params) throws TrainException {
        assertParams(params, 0);
        return "Locomotive created, id: " + proto.createLocomotive();
    }

    private String connectToTrain(String[] params) throws TrainException, NumberFormatException {
        assertParams(params, 2);
        proto.connectToTrain(Integer.parseInt(params[0]), Integer.parseInt(params[1]));
        return "Connected";
    }

    private String connect(String[] params) throws TrainException {
        assertParams(params, 4);
        try{
            proto.connect(Integer.parseInt(params[0]),
                          StaticEntity.ConnectionType.valueOf(params[1]),
                          Integer.parseInt(params[2]),
                          StaticEntity.ConnectionType.valueOf(params[3]));

        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid connection type!");
        }
        return params[0] + " and " + params[2] + " connected";
    }

    private String addPerson(String[] params) throws TrainException, IllegalArgumentException {
        if (params == null || params.length > 2){
            throw new IllegalArgumentException("Illegal parameters for command!");
        }

        if(params.length == 1){
            proto.addPerson(Integer.parseInt(params[0]));
            return "Passanger added to car " + params[0];
        }
        else{
            if(Color.lookup(params[1]) == null) {
                throw new IllegalArgumentException("Invalid color!");
            }

            proto.addPerson(Integer.parseInt(params[0]), Color.valueOf(params[1]));
            return "Passanger added to station " + params[0];
        }
    }

    public static void main(String[] args) {
        try {
            new Interpreter().run();
        } catch (TrainException te) {
            te.printStackTrace();
        }
    }
*/
}