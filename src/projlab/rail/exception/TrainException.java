package projlab.rail.exception;

/** Base class for logic-related exceptions */
public abstract class TrainException extends Exception {
    public TrainException(String message) {
        super(message);
    }
}