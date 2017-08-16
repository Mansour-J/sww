package SwordAndShield;

/**
 * Allows for code reuse for all the objects that draw grids
 *
 * @author David Hack
 * @version 1.0
 */
public class DrawableGrid {
    BoardItem[][] grid;
    String title;

    private static final int NUMCOL = 10;


    DrawableGrid(String t, int numRows){
        grid = new BoardItem[numRows][NUMCOL];
        title = t.toUpperCase();
    }

    /**
     * Draws the grid using the board items draw methods line by line
     */
    public void draw(){
        System.out.print(">>- " + title + " -<<\n");
        drawDivider();
        for(int r = 0; r<grid.length; r++){
            System.out.print("|");

            for(int c = 0; c<NUMCOL; c++){
                if(grid[r][c] != null) grid[r][c].drawTop();
                System.out.print("|");
            }
            System.out.print("\n|");

            for(int c = 0; c<grid[0].length; c++){
                if(grid[r][c] != null) grid[r][c].drawMid();
                System.out.print("|");
            }
            System.out.print("\n|");

            for(int c = 0; c<grid[0].length; c++){
                if(grid[r][c] != null) grid[r][c].drawBot();
                System.out.print("|");
            }
            System.out.print("\n");
            drawDivider();
        }
    }


    public BoardItem[][] getGrid(){
        return grid;
    }

    private void drawDivider(){
        System.out.print("+");
        for(int i = 0; i<NUMCOL; i++) System.out.print("-------+");

        System.out.print("\n");
    }

}
