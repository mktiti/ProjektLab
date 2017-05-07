package projlab.rail.exception;

/** Base class for logic-related exceptions */
public class TrainException extends Exception {
    public TrainException(String message) {
        super(message);
    }
}