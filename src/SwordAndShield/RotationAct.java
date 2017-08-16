package SwordAndShield;

/**
 * A rotation turn that stores what was rotated and how to undo it.
 *
 * @author David Hack
 * @version 1.0
 */
public class RotationAct implements GameAction {

    private Piece rotated;
    private int rotation;

    /**
     * Creates a rotation action, with the piece that was rotated and the amount
     * @param piece rotated piece
     * @param r rotation amount
     */
    RotationAct(Piece piece, int r){
        rotated = piece;
        rotation = r;
    }

    /**
     * Undos the rotation using the game.
     * @param game game to perform changes on.
     */
    public void undo(Game game) {
        game.undoRotation(rotated, rotation);
    }
}
