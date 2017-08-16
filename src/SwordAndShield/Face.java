package SwordAndShield;

/**
 * Represents the face of one of the players
 *
 * @author David Hack
 * @version 1.0
 */
public class Face implements BoardItem{
    private int playerNo;
    private Player player;

    /**
     * Constructs a face using the supplied int to identify it
     *
     * @param player the actual player that owns this face
     * @param playerNo int to identify the player
     */
    Face(Player player, int playerNo){
        this.player = player;
        this.playerNo = playerNo;
    }

    /**
     * Simple getter for the letter
     *
     * @return the letter of this piece
     */
    public char defaultValue(){
        return Integer.toString(playerNo).charAt(0);
    }

    /**
     * Draws the TOP row of face tile
     * MUST BE OF 7 CHARACTERS WIDE
     */
    public void drawTop() {
        System.out.print("       ");
    }

    /**
     * Draws the MID row of face tile including the int
     * MUST BE OF 7 CHARACTERS WIDE
     */
    public void drawMid() {
        System.out.print("   " + playerNo + "   ");
    }

    /**
     * Draws the BOT row of face tile
     * MUST BE OF 7 CHARACTERS WIDE
     */
    public void drawBot() {
        System.out.print("       ");
    }

    /**
     * Getter for the player that owns this face.
     * @return the player that owns this face
     */
    Player getPlayer() {
        return player;
    }
}
