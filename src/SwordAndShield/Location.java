package SwordAndShield;

/**
 * Represents a location in a matrix.
 *
 * @author David Hack
 * @version 1.0
 */
class Location {
    private int row;
    private int col;

    /**
     * Construct a new location from row col.
     *
     * @param row in the matrix
     * @param col in the matrix
     */
    Location(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * Returns the row of this location.
     * @return int, row
     */
    int getRow() {
        return row;
    }

    /**
     * Returns the row of this location.
     * @return int, col
     */
    int getCol() {
        return col;
    }
}
