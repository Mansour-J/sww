package SwordAndShield;

/**
 *
 * This class is responsible for double kill attack, sword sword
 *
 * @author Mohsen Javaher
 * @SutdentID 300 39 40 70
 * @StudentCode javahemohs
 *
 */
public class DKA implements GameAction{
    public void undo(Game game){
        game.undoKill();
        game.undoKill();
    }
}
