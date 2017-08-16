package SwordAndShield;

import java.util.*;

/**
 * SwordAndShield.Player class stores information on the player and performs methods on it.
 *
 * @author David Hack
 * @version 1.0
 */
public class Player extends DrawableGrid{
    private int creationLocation; //x and y
    private Map<Character, Piece> inPlay = new HashMap<>();

    /**
     * Creates a player class from certain information.
     *
     * @param n name of the player
     * @param upper whether the players pieces are upper or lowercase
     * @param cl creation location, diagonal so one value
     */
    public Player(String n, boolean upper, int cl){
        super(n, 3); //3 * 10 = 30 enough to hold 24 pieces
        creationLocation = cl;
        createPieces(upper);
    }

    /**
     * Sets the moved piece to store that information.
     * @param l letter fo the piece
     */
    void pieceMoved(char l){
        Piece query = inPlay.get(l);
        if(query != null){
            query.setMoved(true);
        }
    }

    /**
     * Checks whether or not the player owns the piece and that the piece
     * has not been moved yet in this turn.
     *
     * @param l letter fo the piece
     * @return true if the piece can be played by player
     */
    boolean pieceAvailable(char l){
        Piece query = inPlay.get(l);
        if(query != null){
            return !query.hasMoved();
        }
        return false;
    }

    /**
     * Returns the remaining pieces in string form.
     *
     * @return string of pieces left
     */
    String getRemaining(){
        String remaining = "";
        for(Map.Entry<Character, Piece> e : inPlay.entrySet()){
            if(!e.getValue().hasMoved()){
                remaining += e.getKey() + ",";
            }
        }
        return remaining;
    }

    /**
     * Resets all the pieces so the may be moved next turn.
     */
    public void resetPieces(){
        for(Piece piece : inPlay.values()){
            piece.setMoved(false);
        }
    }

    /**
     * Finds the desired piece and moves it from the hand (grid)
     * to in play. Assumes that the piece is in the hand else
     * throws an exception.
     *
     * @param l letter of the piece
     * @return the piece that was removed from the hand
     */
    public Piece removePieceHand(char l){
        for(int row = 0; row<grid.length; row++){
            for(int col = 0; col<grid[0].length; col++){
                if(grid[row][col].defaultValue() == l){
                    Piece found = (Piece) grid[row][col];
                    grid[row][col] = new Empty(); //remove and add to in play
                    inPlay.put(found.defaultValue(), found);
                    return found;
                }
            }
        }
        throw new IllegalArgumentException("SwordAndShield.Piece should be in hand");
    }

    /**
     * Puts a piece back in the players hand (grid) and removes it
     * from their in play map, its the reverse of removePieceHand
     *
     * @param piece to place in hand
     */
    public void putBackInHand(Piece piece) {
        if(!inPlay.remove(piece.defaultValue(), piece)){
            throw new GameContinuityException("This piece should be in play: " + piece.defaultValue());
        }

        for(int row = 0; row<grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                if(grid[row][col] instanceof Empty){
                    grid[row][col] = piece;
                    return;
                }
            }
        }
    }

    /**
     * Removes a piece from a players in play pieces.
     * @param l letter of the piece
     */
    void removePiecePlay(char l){
        inPlay.remove(l);
    }

    /**
     * Puts a piece back into play.
     *
     * @param piece to put back in play
     */
    void putBackInPlay(Piece piece){
        inPlay.put(piece.defaultValue(), piece);
    }

    /**
     * Checks the players hand for the queried piece.
     * @param l letter fo the piece
     * @return true if the player has the piece in hand
     */
    boolean hasPieceHand(char l){
        for(BoardItem[] row : grid){
            for(BoardItem bi: row){
                if(bi.defaultValue() == l) return true;
            }
        }
        return false;
    }

    /**
     * Getter for the creation grid of the player.
     * @return creation location
     */
    int getCreationLoc(){
        return creationLocation;
    }

    /**
     * Getter for the name of the player.
     * @return name of the player
     */
    String getName(){
        return title;
    }

    /**
     * Getter for the Pieces in inPlay.
     * Will return null if not available.
     * @return a piece from in play pieces
     */
    public Piece getInPlayPiece(char l){
        return inPlay.get(l);
    }

    private void createPieces(boolean upper){
        char letter = (upper)? 'A' : 'a';

        grid[0][0] = new Piece(this, letter++, Weapon.SWORD, Weapon.SHIELD, Weapon.SWORD, Weapon.SWORD);
        grid[0][1] = new Piece(this, letter++, Weapon.SWORD, Weapon.NOTHING, Weapon.SWORD, Weapon.SWORD);
        grid[0][2] = new Piece(this, letter++, Weapon.SHIELD, Weapon.SHIELD, Weapon.SHIELD, Weapon.SHIELD);
        grid[0][3] = new Piece(this, letter++, Weapon.SWORD, Weapon.NOTHING, Weapon.SHIELD, Weapon.NOTHING);
        grid[0][4] = new Piece(this, letter++, Weapon.NOTHING, Weapon.NOTHING, Weapon.NOTHING, Weapon.NOTHING);
        grid[0][5] = new Piece(this, letter++, Weapon.SWORD, Weapon.SHIELD, Weapon.SHIELD, Weapon.SWORD);
        grid[0][6] = new Piece(this, letter++, Weapon.SWORD, Weapon.SWORD, Weapon.SWORD, Weapon.SWORD);
        grid[0][7] = new Piece(this, letter++, Weapon.SWORD, Weapon.NOTHING, Weapon.SHIELD, Weapon.SHIELD);
        grid[0][8] = new Piece(this, letter++, Weapon.NOTHING, Weapon.SHIELD, Weapon.NOTHING, Weapon.NOTHING);
        grid[0][9] = new Piece(this, letter++, Weapon.SWORD, Weapon.SHIELD, Weapon.SWORD, Weapon.SHIELD);

        grid[1][0] = new Piece(this, letter++, Weapon.SWORD, Weapon.SHIELD, Weapon.NOTHING, Weapon.SWORD);
        grid[1][1] = new Piece(this, letter++, Weapon.SWORD, Weapon.NOTHING, Weapon.NOTHING, Weapon.NOTHING);
        grid[1][2] = new Piece(this, letter++, Weapon.SWORD, Weapon.SHIELD, Weapon.SHIELD, Weapon.NOTHING);
        grid[1][3] = new Piece(this, letter++, Weapon.NOTHING, Weapon.SHIELD, Weapon.SHIELD, Weapon.NOTHING);
        grid[1][4] = new Piece(this, letter++, Weapon.SWORD, Weapon.NOTHING, Weapon.SWORD, Weapon.SHIELD);
        grid[1][5] = new Piece(this, letter++, Weapon.SWORD, Weapon.NOTHING, Weapon.SHIELD, Weapon.SWORD);
        grid[1][6] = new Piece(this, letter++, Weapon.SWORD, Weapon.NOTHING, Weapon.NOTHING, Weapon.SHIELD);
        grid[1][7] = new Piece(this, letter++, Weapon.SWORD, Weapon.SHIELD, Weapon.NOTHING, Weapon.SHIELD);
        grid[1][8] = new Piece(this, letter++, Weapon.NOTHING, Weapon.SHIELD, Weapon.NOTHING, Weapon.SHIELD);
        grid[1][9] = new Piece(this, letter++, Weapon.SWORD, Weapon.NOTHING, Weapon.SWORD, Weapon.NOTHING);

        grid[2][0] = new Piece(this, letter++, Weapon.SWORD, Weapon.NOTHING, Weapon.NOTHING, Weapon.SWORD);
        grid[2][1] = new Piece(this, letter++, Weapon.SWORD, Weapon.SHIELD, Weapon.NOTHING, Weapon.NOTHING);
        grid[2][2] = new Piece(this, letter++, Weapon.SWORD, Weapon.SHIELD, Weapon.SHIELD, Weapon.SHIELD);
        grid[2][3] = new Piece(this, letter, Weapon.NOTHING, Weapon.SHIELD, Weapon.SHIELD, Weapon.SHIELD);

        for(int r = 2; r<grid.length; r++){
            for(int c = 4; c<grid[0].length; c++){
                grid[r][c] = new Empty();
            }
        }
    }
}
