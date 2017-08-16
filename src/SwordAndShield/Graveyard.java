package SwordAndShield;

import java.util.Arrays;

/**
 * The cemetery for the board
 *
 * @author David Hack
 * @version 1.0
 */
public class Graveyard extends DrawableGrid{

    private int rowCount = 0;
    private int columnCount = 0;

    /**
     * Constructor of Graveyard
     */
    Graveyard(){
        super("Cemetery", 1);

        for(BoardItem[] cur: grid) Arrays.fill(cur, new Empty());
    }


    public boolean contains(char l){

    	// Looping through the Board to check
    	// if the letter exist
    	for(BoardItem[] row : grid){
            for(BoardItem b : row){
                if(b.defaultValue() == l) return true;
            }
        }

        // return false if letter is not in cemetery
        // return true if it is in cemetery
        return false;
    }

    /**
     * Adding Item to the board by checking the constraint that if the row is full
     *
     * @param b The item that needs to be added into the grave yard
     */
    void addItem(BoardItem b){


        if(columnCount >= grid[0].length){
        	// When the row is not full we will be able to add the item

        	BoardItem[][] temp = new BoardItem[grid.length+1][grid[0].length];

            for(BoardItem[] cur: temp) Arrays.fill(cur, new Empty());

            for(int r = 0; r<grid.length; r++) for(int c = 0; c<grid[0].length; c++) temp[r][c] = grid[r][c];

            grid = temp;
            rowCount++;
            columnCount = 0;
        }
        grid[rowCount][columnCount++] = b;
    }


    Piece resurrect(){

    	if(columnCount == 0 && rowCount == 0) throw new GameContinuityException("This should not happen, can't resurrect when no pieces to");

        Piece toReturn = (Piece) grid[rowCount][--columnCount];
        grid[rowCount][columnCount] = new Empty();

        if(columnCount == 0 && rowCount != 0){
            BoardItem[][] temp = new BoardItem[grid.length-1][grid[0].length];

            for(BoardItem[] cur: temp) Arrays.fill(cur, new Empty());

            for(int r = 0; r<temp.length; r++) for(int c = 0; c<temp[0].length; c++) temp[r][c] = grid[r][c];

            grid = temp;
            rowCount--;
            columnCount = grid[0].length;
        }
        //the piece at the top of the pile
        return toReturn;
    }


}
