package SwordAndShield;

/**
 * A kill action that stores what was killed and how to undo it.
 *
 * @author David Hack
 * @version 1.0
 */
public class KillAct implements GameAction{

    /**
     * Simply calls undoKill on the game.
     * @param game game to perform changes on.
     */
    public void undo(Game game){
        game.undoKill();
    }
}
