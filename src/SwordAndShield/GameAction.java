package SwordAndShield;

/**
 * An interface in order to make an undo action
 * in the game by passing a game reference
 *
 * @author Mohsen Javaher
 * @SutdentID 300 39 40 70
 * @StudentCode javahemohs
 *
 *
 * Created at  10/08/2017
 */
public interface GameAction {

    /**
     * @param game a game reference to turns in a specific way
     */
    void undo(Game game);
}
