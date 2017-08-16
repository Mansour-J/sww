package SwordAndShield;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Basic game class that operates the other classes
 *
 * @author David Hack
 * @version 1.0
 */
public class Game {

    private final static Map<String, GameDirection> gameDirections = new HashMap<>();
    static{
        gameDirections.put("left", new GameDirection(0, -1));
        gameDirections.put("right", new GameDirection(0, 1));
        gameDirections.put("up", new GameDirection(-1, 0));
        gameDirections.put("down", new GameDirection(1, 0));
    }

    private GameBoard gameBoard;

    /**
     * Constructs a new game object.
     * @param topLeft player
     * @param topRight player
     */
    public Game(Player topLeft, Player topRight){
        gameBoard = new GameBoard(topLeft, topRight);
    }

    /**
     * draws all the main components of the game.
     */
    void draw(){
        gameBoard.draw();
    }

    /**
     * Checks if the rotation is valid in terms of the player having the piece
     * and that it hasn't been moved this turn.
     *
     * @param player making the rotation
     * @param l letter of the piece to rotate
     * @param r amount to rotate
     * @return true or false depending on success
     */
    public RotationAct rotationMove(Player player, char l, int r){
        if(!player.pieceAvailable(l)){
            throw new GameContinuityException("This piece is not available to the player, either moved already or not owned.");
        }

        Piece toRotate= player.getInPlayPiece(l);
        toRotate.rotate(r);
        player.pieceMoved(l); //tells the player that this piece has been moved for this turn

        return new RotationAct(toRotate, r);
    }

    /**
     * Checks if the move is valid in terms of the player having the piece
     * and that it hasn't been moved this turn.
     *
     * @param player making the move
     * @param l letter of the piece to move
     * @param direction direction of the movement as a string
     * @return true or false depending on success
     */
    public MoveAct moveMove(Player player, char l, String direction){
        if(!player.pieceAvailable(l) ){
            throw new GameContinuityException("This piece is not available to the player, either moved already or not owned.");
        }

        Piece toMove= player.getInPlayPiece(l);
        //get the location object that pertains to this direction (relative)
        GameDirection dir = gameDirections.get(direction);
        int numMoved = gameBoard.move(toMove, dir, 0);
        player.pieceMoved(l); //tells the player that this piece has been moved for this turn

        return new MoveAct(toMove, numMoved, dir);
    }

    /**
     * Trys to create a piece as the player dictated.
     *
     * @param player how is making the piece
     * @param l letter of the piece
     * @param r rotation desired
     * @return true or false depending on success
     */
    public HelperClass tryCreate(Player player, char l, int r){
        int creation = player.getCreationLoc();
        if(!(gameBoard.get(creation) instanceof Empty && player.hasPieceHand(l))){
            throw new GameContinuityException("Player doesn't have piece in hand or creation spot is full.");
        }

        Piece toAdd = player.removePieceHand(l);
        toAdd.rotate(r);
        gameBoard.set(creation, creation, toAdd);

        return new HelperClass(toAdd, r);
    }

    /**
     * A method that undos a creation, provided the piece and the rotation.
     * Should be called sensibly else will throw an exception.
     * @param created piece that was created
     * @param rotation how much it was rotated
     */
    public void undoCreation(Piece created, int rotation){
        //get some information
        Player player = created.getPlayer();
        int creationPos = player.getCreationLoc();

        //if the piece on the board in creation is not this one we have a problem
        if(gameBoard.get(creationPos).defaultValue() != created.defaultValue()){
            throw new GameContinuityException("Could be piece with letter: " + created.defaultValue()
                    + " At: " + creationPos + "," + creationPos);
        }

        //this will rotate the piece back to its original orientation
        if(rotation != 0){
            created.rotate(360 - rotation);
        }

        //remove from board, and put back in players hand
        gameBoard.erase(creationPos, creationPos);
        player.putBackInHand(created);
    }

    /**
     * A method that undos a rotation move, provided the piece and the rotation.
     * @param rotated piece that was rotated
     * @param rotation how much it was rotated
     */
    public void undoRotation(Piece rotated, int rotation){
        //this will rotate the piece back to its original orientation
        if(rotation != 0){
            rotated.rotate(360 - rotation);
        }

        //make piece available again
        rotated.setMoved(false);
    }

    /**
     * A method for undoing moves, relies on the board.
     * @param moved piece that was moved
     * @param numMoved number of pieces moved (shifted)
     * @param gameDirection direction of the movement
     */
    public void undoMove(Piece moved, int numMoved, GameDirection gameDirection) {
        gameBoard.undoMove(moved, gameDirection, numMoved);
        moved.setMoved(false);
    }

    /**
     * Asks the board to resurrect a pieace
     */
    void undoKill(){
        gameBoard.resurrect();
    }

    /**
     * Takes a reaction and handles it appropriately, and returns a action to undo it.
     * @param reaction Information on the reaction
     * @return A action to undo the reaction
     */
    public GameAction react(Reaction reaction){
        if(!(reaction.getDefenderPiece() instanceof Piece)){
            throw new GameContinuityException("This should be a piece");
        }

        Piece defender = (Piece) reaction.getDefenderPiece();

        Weapon defending = reaction.getDefendersWeapon();
        Location ofDefender = defender.getLocation();
        Location ofAttacker = reaction.getSwordAttackPiece().getLocation();

        if(defending == Weapon.NOTHING){
            gameBoard.remove(ofDefender);
            return new KillAct();
        }else if(defending == Weapon.SWORD){
            gameBoard.remove(ofDefender);
            gameBoard.remove(ofAttacker);
            return new DKA();
        }else if(defending == Weapon.SHIELD){
            GameDirection dir = reaction.getRelDirection();
            int numMoved = gameBoard.move(reaction.getSwordAttackPiece(), new GameDirection(-dir.getRow(), -dir.getCol()), 0);
            return new MoveAct(reaction.getSwordAttackPiece(), numMoved, new GameDirection(-dir.getRow(), -dir.getCol()));
        }else{
            throw new GameContinuityException("Should have sword, shield or empty");
        }
    }

    /**
     * Checks if the game has reactions.
     * @return true if there are reactions
     */
    public boolean hasReactions(){
        return gameBoard.hasReactions();
    }

    /**
     * Returns a list of the reactions on the board.
     * @return List of reactions
     */
    public List<Reaction> getReactions(){
        return gameBoard.getReactions();
    }

    /**
     * Getter for the board.
     * @return a board
     */
    public GameBoard getBoard(){
        return gameBoard;
    }
}
