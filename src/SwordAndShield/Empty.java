package SwordAndShield;

/**
 * Represents a blank spot on the board that can be moved to
 *
 * @author David Hack
 * @version 1.0
 */
public class Empty implements BoardItem{

    /**
     * Draws the TOP row of empty tile
     * MUST BE OF 7 CHARACTERS WIDE
     */
    public void drawTop() {
        System.out.print("       ");
    }

    /**
     * Draws the MID row of empty tile
     * MUST BE OF 7 CHARACTERS WIDE
     */
    public void drawMid() {
        System.out.print("       ");
    }

    /**
     * Draws the BOT row of empty tile
     * MUST BE OF 7 CHARACTERS WIDE
     */
    public void drawBot() {
        System.out.print("       ");
    }
}
