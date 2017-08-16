package SwordAndShield;

/**
 * Exception that warns tha something wrong with the game has occurred
 *
 * @author David Hack
 * @version 1.0
 */
public class GameContinuityException extends RuntimeException {
    private String message;

    /**
     * Creates a new exception
     * @param s message
     */
    GameContinuityException(String s) {
        message = s;
    }

    /**
     * The message detailing the cause
     * @return message
     */
    public String getMessage() {
        return message;
    }
}
