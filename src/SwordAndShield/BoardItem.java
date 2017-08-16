package SwordAndShield;

/**
 * This class is responsible for managing item and action that exist on the board.
 *
 * @author Mohsen Javaher
 * @SutdentID 300 39 40 70
 * @StudentCode javahemohs
 *
 * Created at  10/08/2017
 */
public interface BoardItem {

    /**
     * draws the TOP row of a item
     */
    void drawTop();

    /**
     * Drawing item in the middle
     */
    void drawMid();

    /**
     * draws the BOT row of a item,
     */
    void drawBot();

    /**
     * Default for all BoardItems returns null
     *
     * @return char null
     */
    default char defaultValue(){
        return 0;
    }
}
