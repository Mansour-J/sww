package SwordAndShield;

/**
 * Represents a area that will move pieces to the cemetery if touched.
 *
 * @author David Hack
 * @version 1.0
 */
public class Outside implements BoardItem{

    /**
     * Draws the TOP row of outside tile
     * MUST BE OF 7 CHARACTERS WIDE
     */
    public void drawTop() {
        System.out.print("XXXXXXX");
    }

    /**
     * Draws the MID row of outside tile
     * MUST BE OF 7 CHARACTERS WIDE
     */
    public void drawMid() {
        System.out.print("XXXXXXX");
    }

    /**
     * Draws the BOT row of outside tile
     * MUST BE OF 7 CHARACTERS WIDE
     */
    public void drawBot() {
        System.out.print("XXXXXXX");
    }
}
