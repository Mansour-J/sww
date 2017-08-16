package SwordAndShield;

/**
 * As this game uses matrix, this class shows how actions are implemented
 * in a matrix based game board
 *
 * @author Mohsen Javaher
 * @SutdentID 300 39 40 70
 * @StudentCode javahemohs
 *
 */
public class GameDirection extends Location{

    /**
     * A direction representation
     */
    public GameDirection(int rowChange, int colChange){
        super(rowChange, colChange);

        if(notValid(rowChange, colChange)) throw new IllegalArgumentException("(0, 1), (0, -1), (1, 0), (-1, 0)");
    }

    /**
     * Takes the input and checks if it is invalid.
     */
    private boolean notValid(int rowChange, int columnChange){


        //check the range
        if(rowChange > 1 || rowChange < -1 || columnChange > 1 || columnChange < -1) {
        	return true;
        }

        //checking if they both are 0
        if(rowChange == 0 && columnChange == 0) {
        	return true;
        }


        if((rowChange == 1 || rowChange == -1) && columnChange != 0) {
        	return true;
        }

        if((columnChange == 1 || columnChange == -1) && rowChange != 0) {
        	return true;
        }

        // returns true if the it is invalid
        return false;
    }
}
