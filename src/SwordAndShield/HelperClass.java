package SwordAndShield;

/**
 * A helper class about peices thats being created
 *
 * @author Mohsen Javaher
 * @SutdentID 300 39 40 70
 * @StudentCode javahemohs
 *
 */
public class HelperClass implements GameAction {

	//The piece that is being created
    private Piece created;

    //rotation value about how much it was rotated
    private int rotation;

    /**
     * Constructor --> creating a creation action
     */
    HelperClass(Piece piece, int r){
        this.created = piece;
        this.rotation = r;
    }

    /**
     * Undo the creation
     */
    public void undo(Game game) {
        game.undoCreation(created, rotation);
    }
}
