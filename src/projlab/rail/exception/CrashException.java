package projlab.rail.exception;

/** Thrown when two or more trains crash -or would crash- into each other */
public class CrashException extends TrainException {

    public CrashException() {
        super("Train crash!");
    }

}