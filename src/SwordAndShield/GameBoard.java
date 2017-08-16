package SwordAndShield;

import java.util.*;

/**
 * Representation of the board and its pieces and state
 *
 * @author David Hack
 * @version 1.0
 */
public class GameBoard extends DrawableGrid{

    private Graveyard graveyard;

    /**
     * Creates a new board
     * @param topLeft player
     * @param topRight player
     */
    GameBoard(Player topLeft, Player topRight){
        super("SwordAndShield.Board", 10);
        graveyard = new Graveyard();

        for(BoardItem[] cur: grid){
            Arrays.fill(cur, new Empty());
        }

        //Add the no go zones
        grid[0][0] = new Outside();
        grid[0][1] = new Outside();
        grid[1][0] = new Outside();
        grid[9][9] = new Outside();
        grid[9][8] = new Outside();
        grid[8][9] = new Outside();

        //Add faces
        grid[1][1] = new Face(topLeft, 1);
        grid[8][8] = new Face(topRight, 0);
    }

    /**
     * Draws the board and its pieces.
     */
    public void draw(){
        graveyard.draw();
        super.draw();
    }

    /**
     * Moves pieces, recursively if needed.
     * Assumes the piece is on the board.
     *
     * @param toMove piece to move
     * @param gameDirection to move in
     * @param numMoved 0, used for recursion
     * @return number of pieces affected.
     */
    int move(Piece toMove, GameDirection gameDirection, int numMoved) {
        int rowChange = gameDirection.getRow();
        int colChange = gameDirection.getCol();
        Location location = toMove.getLocation();

        int desiredRow = location.getRow() + rowChange;
        int desiredCol = location.getCol() + colChange;

        if(!in(desiredRow, desiredCol) || grid[desiredRow][desiredCol] instanceof Outside
                || grid[desiredRow][desiredCol] instanceof Face){
            //If this piece wants to move off the board or into out of bounds
            BoardItem item = grid[location.getRow()][location.getCol()];
            //Sanity check, should be piece
            if(item instanceof Piece){
                Piece toRemove = (Piece) item;
                toRemove.setLocation(new Location(desiredRow, desiredCol));
                toRemove.removeFromPlay(); //remove piece
            }
            graveyard.addItem(item);
            //SwordAndShield.Empty grid location
            grid[location.getRow()][location.getCol()] = new Empty();
            return ++numMoved; //We are done
        }else if(grid[desiredRow][desiredCol] instanceof Piece){
            //If there is a piece in our way shift it be calling move on it
            Piece next = (Piece) grid[desiredRow][desiredCol];
            numMoved = move(next, gameDirection, numMoved);
        }
        //Finally move our piece to the spot
        grid[desiredRow][desiredCol] = grid[location.getRow()][location.getCol()];
        //SwordAndShield.Empty spot we came from
        grid[location.getRow()][location.getCol()] = new Empty();
        //Update the boards record of the pieces location
        toMove.setLocation(new Location(desiredRow, desiredCol));

        return ++numMoved;
    }

    /**
     * Undos a move, including moves that kill pieces or kill them by shifting.
     * @param moved the piece that was moved
     * @param gameDirection that was moved
     * @param numMoved the number of pieces that were moved
     */
    void undoMove(Piece moved, GameDirection gameDirection,  int numMoved){
        int rowChange = gameDirection.getRow();
        int colChange = gameDirection.getCol();
        Location location = moved.getLocation();
        //moved is the piece that was moved but it says nothing of the other pieces it MAY have moved.
        if(numMoved == 1){ //means the piece was moved and didn't shift anything.
            //This also means we can assume that the position it left is empty, and must be within the grid.
            int startRow = location.getRow();
            int startCol = location.getCol();
            int prevRow = location.getRow() - rowChange;
            int prevCol = location.getCol() - colChange;
            if(!in(prevRow, prevCol)){
                throw new GameContinuityException("This location should be within the board");
            }
            if(!(grid[prevRow][prevCol] instanceof Empty)){
                throw new GameContinuityException("This location should be empty");
            }

            set(prevRow, prevCol, moved);
            //Resurrect piece if required
            if(graveyard.contains(moved.defaultValue())){
                graveyard.resurrect(); //If this piece died in this move it should be at the top of the cemetery
                moved.resurrect();
            }else{ //else was on board need to erase prev spot
                erase(startRow, startCol);
            }
        }else{ //else, more pieces were shifted
            int curRow = location.getRow();
            int curCol = location.getCol();
            //Advance to end of pieces shifted
            for(int i = 1; i<numMoved; i++){
                curRow += rowChange;
                curCol += colChange;
            }
            //We now have the location of the last piece in the push
            boolean pieceWasPushedOut = false;
            //Check if we went of the board, and therefore a piece did as well
            if(!in(curRow, curCol)){
                pieceWasPushedOut = true;
                curRow = normalize(curRow, grid.length);
                curCol = normalize(curCol, grid[0].length);
            }else if(grid[curRow][curCol] instanceof Face || grid[curRow][curCol] instanceof Outside){
                pieceWasPushedOut = true;
                curRow -= rowChange;
                curCol -= colChange;
            }
            //This spot should be a piece
            if(!(grid[curRow][curCol] instanceof Piece)){
                throw new GameContinuityException("Should be piece type here");
            }
            //Safe cast bc of ^
            Piece toPushBack = (Piece) grid[curRow][curCol];
            //We'll just move everything back
            move(toPushBack, new GameDirection(-rowChange, -colChange), 0);
            //And if a piece was pushed off, resurrect it and place it.
            if(pieceWasPushedOut){
                Piece fromDeath = graveyard.resurrect();
                fromDeath.resurrect();
                set(curRow, curCol, fromDeath);
            }
        }
    }

    /**
     * Resurrects the piece at the top of the cemetery
     */
    void resurrect(){
        Piece fromDeath = graveyard.resurrect();
        fromDeath.resurrect();
        Location toPut = fromDeath.getLocation();
        set(toPut.getRow(), toPut.getCol(), fromDeath);
    }

    /**
     * Removes a item from the board and performs the appropriate actions on it
     * @param toRemove Location to be cleared
     */
    void remove(Location toRemove){
        BoardItem item = grid[toRemove.getRow()][toRemove.getCol()];
        //Sanity check, should be piece
        if(item instanceof Piece){
            Piece remove = (Piece) item;
            remove.removeFromPlay(); //remove piece
        }
        graveyard.addItem(item);
        //SwordAndShield.Empty grid location
        grid[toRemove.getRow()][toRemove.getCol()] = new Empty();
    }

    /**
     * Checks if the game has any reactions that can take place, (Sword v piece).
     * @return true if there are reactions
     */
    boolean hasReactions() {
        for(BoardItem[] row : grid){
            for(BoardItem bi : row){
                if(bi instanceof Piece){ //for each board item that is a piece
                    Piece piece = (Piece) bi;
                    Location location = piece.getLocation();
                    for(GameDirection relative : piece.reactableDirectionsRel()){ //for each relative sword location
                        Location attackLoc = new Location(location.getRow() + relative.getRow(),
                                                        location.getCol() + relative.getCol());
                        if(in(attackLoc) && (grid[attackLoc.getRow()][attackLoc.getCol()] instanceof Piece
                                                ||  grid[attackLoc.getRow()][attackLoc.getCol()] instanceof Face)){
                            //can react with pieces and faces
                            return true; //If inside grid and another piece there will be a reaction
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Gets all the reactions that are on the board currently between pieces.
     * @return A list of the Reaction object
     */
    List<Reaction> getReactions(){
        ArrayList<Reaction> reactions = new ArrayList<>();

        for(BoardItem[] row : grid){
            for(BoardItem bi : row){
                if(bi instanceof Piece){ //for each board item that is a piece
                    Piece piece = (Piece) bi;
                    reactions.addAll(getNonDuplicates(piece));
                }
            }
        }
        return reactions;
    }

    /**
     * Helper method for getReactions
     * @param attacker Attacking piece
     * @return List of the object Reactions
     */
    private List<Reaction> getNonDuplicates(Piece attacker){
        List<Reaction> attacks = new ArrayList<>();

        Location location = attacker.getLocation();
        for(GameDirection relative : attacker.reactableDirectionsRel()){ //for each relative sword location
            Location attackLoc = new Location(location.getRow() + relative.getRow(),
                    location.getCol() + relative.getCol());
            if(in(attackLoc)) {
                BoardItem item = grid[attackLoc.getRow()][attackLoc.getCol()];
                if (item instanceof Piece) {
                    Piece defender = (Piece) grid[attackLoc.getRow()][attackLoc.getCol()];
                    Weapon defenderWep = defender.getDefence(relative);

                    //to avoid duplicates
                    if (!(defenderWep == Weapon.SWORD && (relative.getRow() == -1 || relative.getCol() == -1))) {
                        attacks.add(new Reaction(attacker, defender, defenderWep, relative));
                    }
                } else if(item instanceof Face){
                    attacks.add(new Reaction(attacker, item, Weapon.NOTHING, relative));
                }
            }
        }
        return attacks;
    }

    /**
     * Adds a piece to the board at the desired location.
     * @param row location
     * @param col location
     * @param piece piece to add
     */
    void set(int row, int col, Piece piece){
        piece.setLocation(new Location(row, col));
        grid[row][col] = piece;
    }

    /**
     * Erases a piece from the board.
     * @param row location
     * @param col location
     */
    void erase(int row, int col){
        if(!(grid[row][col] instanceof Piece)){
            throw new GameContinuityException("Trying to erase empty position, this should not happen");
        }

        grid[row][col] = new Empty();
    }

    /**
     * Gets piece from the specified diagonal.
     * @param i diagonal to get from
     * @return SwordAndShield.BoardItem that is there
     */
    public BoardItem get(int i){
        return grid[i][i];
    }

    /**
     * Gets from a specific spot.
     * @param row location
     * @param col location
     * @return SwordAndShield.BoardItem that was there
     */
    public BoardItem get(int row, int col){
        return grid[row][col];
    }

    /**
     * Getter for the boards cemetery.
     * @return the boards cemetery
     */
    public Graveyard getCemetery() {
        return graveyard;
    }

    /**
     * Normalize to within board sizes.
     * @param x to be normalized
     * @param max max size
     * @return normal int
     */
    private int normalize(int x, int max){
        if(x < 0) return 0;
        if(x >= max) return max-1;
        return x;
    }

    /**
     * Checks if a row and col is in the board.
     * @param row row
     * @param col col
     * @return true if in
     */
    private boolean in(int row, int col){
        return !(row < 0 || row >= grid.length || col < 0 || col >= grid[0].length);
    }

    /**
     * Checks if the location is in the board.
     * @param location location to check
     * @return true if in
     */
    private boolean in(Location location){
        return in(location.getRow(), location.getCol());
    }
}
