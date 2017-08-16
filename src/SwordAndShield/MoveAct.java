package SwordAndShield;

/**
 * A move turn that stores what was moved and how to undo it.
 *
 * @author David Hack
 * @version 1.0
 */
public class MoveAct implements GameAction {

    private Piece moved;
    private int numMoved;
    private GameDirection gameDirection;

    /**
     * Creates a move action.
     * @param piece that was moved
     * @param n number of pieces moved
     * @param d direction of move
     */
    MoveAct(Piece piece, int n, GameDirection d) {
        moved = piece;
        numMoved = n;
        gameDirection = d;
    }

    /**
     * Undos the movement.
     * @param game game to perform changes on.
     */
    public void undo(Game game) {
        game.undoMove(moved, numMoved, gameDirection);
    }
}
