import SwordAndShield.*;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;

/**
 * Tests for Sword and Shield
 */
public class Tests {

    /**
     * Test whether a valid creation with 0 rotation works
     */
    @Test
    public void createTest1(){
        Player tPlayer = new Player("Green", true, 2);
        Player tPlayer2 = new Player("Yellow", true, 7);
        Game tGame = new Game(tPlayer, tPlayer2);
        tGame.tryCreate(tPlayer, 'A', 0);
        creationChecks('A', tGame, tPlayer);
        Weapon[] weaponLayout = {Weapon.SWORD, Weapon.SHIELD, Weapon.SWORD, Weapon.SWORD};
        checkRotation(2, 2, tGame, weaponLayout);
        confirmNotInHand('A', tPlayer);
    }

    /**
     * Test whether a valid creation with 90 rotation works
     */
    @Test
    public void createTest2(){
        Player tPlayer = new Player("Green", true, 2);
        Player tPlayer2 = new Player("Yellow", true, 7);
        Game tGame = new Game(tPlayer, tPlayer2);
        tGame.tryCreate(tPlayer, 'A', 90);
        creationChecks('A', tGame, tPlayer);
        Weapon[] weaponLayout = {Weapon.SWORD, Weapon.SWORD, Weapon.SHIELD, Weapon.SWORD};
        checkRotation(2, 2, tGame, weaponLayout);
        confirmNotInHand('A', tPlayer);
    }

    /**
     * Test whether a valid creation with 180 rotation works
     */
    @Test
    public void createTest3(){
        Player tPlayer = new Player("Green", true, 2);
        Player tPlayer2 = new Player("Yellow", true, 7);
        Game tGame = new Game(tPlayer, tPlayer2);
        tGame.tryCreate(tPlayer, 'A', 180);
        creationChecks('A', tGame, tPlayer);
        Weapon[] weaponLayout = {Weapon.SWORD, Weapon.SWORD, Weapon.SWORD, Weapon.SHIELD};
        checkRotation(2, 2, tGame, weaponLayout);
        confirmNotInHand('A', tPlayer);
    }

    /**
     * Test whether a valid creation with 270 rotation works
     */
    @Test
    public void createTest4(){
        Player tPlayer = new Player("Green", true, 2);
        Player tPlayer2 = new Player("Yellow", true, 7);
        Game tGame = new Game(tPlayer, tPlayer2);
        tGame.tryCreate(tPlayer, 'A', 270);
        creationChecks('A', tGame, tPlayer);
        Weapon[] weaponLayout = {Weapon.SHIELD, Weapon.SWORD, Weapon.SWORD, Weapon.SWORD};
        checkRotation(2, 2, tGame, weaponLayout);
        confirmNotInHand('A', tPlayer);
    }

    /**
     * Test that creating when the creation square is full fails
     */
    @Test
    public void invalidCreateTest1(){
        Player tPlayer = new Player("Green", true, 2);
        Player tPlayer2 = new Player("Yellow", true, 7);
        Game tGame = new Game(tPlayer, tPlayer2);
        tGame.tryCreate(tPlayer, 'A', 0);
        try {
            tGame.tryCreate(tPlayer, 'B', 0);
            fail("This create should not happen");
        }catch(GameContinuityException e){}
        creationChecks('A', tGame, tPlayer);
        confirmInHand('B', tPlayer);
    }

    /**
     * Tests that creating with a piece the player has used fails
     */
    @Test
    public void invalidCreateTest2(){
        Player tPlayer = new Player("Green", true, 2);
        Player tPlayer2 = new Player("Yellow", true, 7);
        Game tGame = new Game(tPlayer, tPlayer2);
        tGame.tryCreate(tPlayer, 'A', 0);
        tGame.moveMove(tPlayer, 'A', "up");
        try {
            tGame.tryCreate(tPlayer, 'A', 0);
            fail("This create should not happen");
        }catch(GameContinuityException e){}
        assertTrue(tGame.getBoard().get(2, 2) instanceof Empty);
    }

    /**
     * Tests that creating with a piece the player does not have fails, ie opponents piece
     */
    @Test
    public void invalidCreateTest3(){
        Player tPlayer = new Player("Green", true, 2);
        Player tPlayer2 = new Player("Yellow", true, 7);
        Game tGame = new Game(tPlayer, tPlayer2);
        try{
            tGame.tryCreate(tPlayer, 'a', 0);
            fail("This create should not happen");
        }catch(GameContinuityException e){}
        assertTrue(tGame.getBoard().get(2, 2) instanceof Empty);
    }

    /**
     * Tests that rotating 0 works correctly
     */
    @Test
    public void rotateTest1(){
        Player tPlayer = new Player("Green", true, 2);
        Player tPlayer2 = new Player("Yellow", true, 7);
        Game tGame = new Game(tPlayer, tPlayer2);
        tGame.tryCreate(tPlayer, 'A', 0);
        tGame.rotationMove(tPlayer, 'A', 0);
        Weapon[] weaponLayout = {Weapon.SWORD, Weapon.SHIELD, Weapon.SWORD, Weapon.SWORD};
        checkRotation(2, 2, tGame, weaponLayout);
    }

    /**
     * Tests that rotating 90 works correctly
     */
    @Test
    public void rotateTest2(){
        Player tPlayer = new Player("Green", true, 2);
        Player tPlayer2 = new Player("Yellow", true, 7);
        Game tGame = new Game(tPlayer, tPlayer2);
        tGame.tryCreate(tPlayer, 'A', 0);
        tGame.rotationMove(tPlayer, 'A', 90);
        Weapon[] weaponLayout = {Weapon.SWORD, Weapon.SWORD, Weapon.SHIELD, Weapon.SWORD};
        checkRotation(2, 2, tGame, weaponLayout);
    }

    /**
     * Tests that rotating 180 works correctly
     */
    @Test
    public void rotateTest3(){
        Player tPlayer = new Player("Green", true, 2);
        Player tPlayer2 = new Player("Yellow", true, 7);
        Game tGame = new Game(tPlayer, tPlayer2);
        tGame.tryCreate(tPlayer, 'A', 0);
        tGame.rotationMove(tPlayer, 'A', 180);
        Weapon[] weaponLayout = {Weapon.SWORD, Weapon.SWORD, Weapon.SWORD, Weapon.SHIELD};
        checkRotation(2, 2, tGame, weaponLayout);
    }

    /**
     * Tests that rotating 270 works correctly
     */
    @Test
    public void rotateTest4(){
        Player tPlayer = new Player("Green", true, 2);
        Player tPlayer2 = new Player("Yellow", true, 7);
        Game tGame = new Game(tPlayer, tPlayer2);
        tGame.tryCreate(tPlayer, 'A', 0);
        tGame.rotationMove(tPlayer, 'A', 270);
        Weapon[] weaponLayout = {Weapon.SHIELD, Weapon.SWORD, Weapon.SWORD, Weapon.SWORD};
        checkRotation(2, 2, tGame, weaponLayout);
    }

    /**
     * Tests that rotating a piece that is not on the board fails
     */
    @Test
    public void invalidRotateTest1(){
        Player tPlayer = new Player("Green", true, 2);
        Player tPlayer2 = new Player("Yellow", true, 7);
        Game tGame = new Game(tPlayer, tPlayer2);
        try {
            tGame.rotationMove(tPlayer, 'A', 0);
            fail("This rotation should throw");
        }catch(GameContinuityException e){}
    }

    /**
     * Tests that rotating a piece twice before resting fails
     */
    @Test
    public void invalidRotateTest2(){
        Player tPlayer = new Player("Green", true, 2);
        Player tPlayer2 = new Player("Yellow", true, 7);
        Game tGame = new Game(tPlayer, tPlayer2);
        tGame.tryCreate(tPlayer, 'A', 0);
        tGame.rotationMove(tPlayer, 'A', 270);
        try{
            tGame.rotationMove(tPlayer, 'A', 90);
            fail("This rotation should throw");
        }catch(GameContinuityException e){}
        Weapon[] weaponLayout = {Weapon.SHIELD, Weapon.SWORD, Weapon.SWORD, Weapon.SWORD};
        checkRotation(2, 2, tGame, weaponLayout);
    }

    /**
     * Tests that rotating a piece that player does not own fails
     */
    @Test
    public void invalidRotateTest3(){
        Player tPlayer = new Player("Green", true, 2);
        Player tPlayer2 = new Player("Yellow", true, 7);
        Game tGame = new Game(tPlayer, tPlayer2);
        tGame.tryCreate(tPlayer, 'A', 0);
        try{
            tGame.rotationMove(tPlayer2, 'A', 90);
            fail("This rotation should throw");
        }catch(GameContinuityException e){}
        Weapon[] weaponLayout = {Weapon.SWORD, Weapon.SHIELD, Weapon.SWORD, Weapon.SWORD};
        checkRotation(2, 2, tGame, weaponLayout);
    }

    /**
     * Tests that moving up works correctly
     */
    @Test
    public void moveTest1(){
        Player tPlayer = new Player("Green", true, 2);
        Player tPlayer2 = new Player("Yellow", true, 7);
        Game tGame = new Game(tPlayer, tPlayer2);
        tGame.tryCreate(tPlayer, 'A', 0);
        tGame.moveMove(tPlayer, 'A', "up");
        assertEquals('A', tGame.getBoard().get(1, 2).letter());
        assertTrue(tGame.getBoard().get(2, 2) instanceof Empty);
    }

    /**
     * Tests that moving down works correctly
     */
    @Test
    public void moveTest2(){
        Player tPlayer = new Player("Green", true, 2);
        Player tPlayer2 = new Player("Yellow", true, 7);
        Game tGame = new Game(tPlayer, tPlayer2);
        tGame.tryCreate(tPlayer, 'A', 0);
        tGame.moveMove(tPlayer, 'A', "down");
        assertEquals('A', tGame.getBoard().get(3, 2).letter());
        assertTrue(tGame.getBoard().get(2, 2) instanceof Empty);
    }

    /**
     * Tests that moving left works correctly
     */
    @Test
    public void moveTest3(){
        Player tPlayer = new Player("Green", true, 2);
        Player tPlayer2 = new Player("Yellow", true, 7);
        Game tGame = new Game(tPlayer, tPlayer2);
        tGame.tryCreate(tPlayer, 'A', 0);
        tGame.moveMove(tPlayer, 'A', "left");
        assertEquals('A', tGame.getBoard().get(2, 1).letter());
        assertTrue(tGame.getBoard().get(2, 2) instanceof Empty);
    }

    /**
     * Tests that moving right works correctly & moving after resetting works
     */
    @Test
    public void moveTest4(){
        Player tPlayer = new Player("Green", true, 2);
        Player tPlayer2 = new Player("Yellow", true, 7);
        Game tGame = new Game(tPlayer, tPlayer2);
        tGame.tryCreate(tPlayer, 'A', 0);
        tGame.moveMove(tPlayer, 'A', "right");
        assertEquals('A', tGame.getBoard().get(2, 3).letter());
        assertTrue(tGame.getBoard().get(2, 2) instanceof Empty);
        //Second move, after reset
        tPlayer.resetPieces();
        tGame.moveMove(tPlayer, 'A', "right");
        assertEquals('A', tGame.getBoard().get(2, 4).letter());
        assertTrue(tGame.getBoard().get(2, 3) instanceof Empty);
    }

    /**
     * Tests that trying to move a piece that is not on the board fails
     */
    @Test
    public void invalidMoveTest1(){
        Player tPlayer = new Player("Green", true, 2);
        Player tPlayer2 = new Player("Yellow", true, 7);
        Game tGame = new Game(tPlayer, tPlayer2);
        try {
            tGame.moveMove(tPlayer, 'A', "right");
            fail("Move is invalid");
        }catch(GameContinuityException e){}
        assertTrue(tGame.getBoard().get(2, 2) instanceof Empty);
        assertTrue(tGame.getBoard().get(2, 3) instanceof Empty);
    }

    /**
     * Tests that trying to move a piece twice before resetting fails
     */
    @Test
    public void invalidMoveTest2(){
        Player tPlayer = new Player("Green", true, 2);
        Player tPlayer2 = new Player("Yellow", true, 7);
        Game tGame = new Game(tPlayer, tPlayer2);
        tGame.tryCreate(tPlayer, 'A', 0);
        tGame.moveMove(tPlayer, 'A', "right");
        assertEquals('A', tGame.getBoard().get(2, 3).letter());
        assertTrue(tGame.getBoard().get(2, 2) instanceof Empty);
        //Second move
        try {
            tGame.moveMove(tPlayer, 'A', "right");
            fail("Move is invalid");
        }catch(GameContinuityException e){}
        assertEquals('A', tGame.getBoard().get(2, 3).letter());
        assertTrue(tGame.getBoard().get(2, 4) instanceof Empty);
    }

    /**
     * Tests that trying to move a piece that is not owned by the player fails
     */
    @Test
    public void invalidMoveTest3(){
        Player tPlayer = new Player("Green", true, 2);
        Player tPlayer2 = new Player("Yellow", true, 7);
        Game tGame = new Game(tPlayer, tPlayer2);
        tGame.tryCreate(tPlayer, 'A', 0);
        try {
            tGame.moveMove(tPlayer2, 'A', "right");
            fail("Move is invalid");
        }catch(GameContinuityException e){}
        assertTrue(tGame.getBoard().get(2, 3) instanceof Empty);
        assertEquals('A', tGame.getBoard().get(2, 2).letter());
    }

    /**
     * Tests that moving pieces against each other shifts them
     */
    @Test
    public void multiMoveTest1(){
        Player tPlayer = new Player("Green", true, 2);
        Player tPlayer2 = new Player("Yellow", true, 7);
        Game tGame = new Game(tPlayer, tPlayer2);
        tGame.tryCreate(tPlayer, 'A', 0);
        tGame.moveMove(tPlayer, 'A', "right");
        tGame.tryCreate(tPlayer, 'B', 0);
        tGame.moveMove(tPlayer, 'B', "right");
        assertEquals('A', tGame.getBoard().get(2, 4).letter());
        assertEquals('B', tGame.getBoard().get(2, 3).letter());
        assertTrue(tGame.getBoard().get(2, 2) instanceof Empty);
    }

    /**
     * Tests that moving pieces against each other shifts them
     */
    @Test
    public void multiMoveTest2(){
        Player tPlayer = new Player("Green", true, 2);
        Player tPlayer2 = new Player("Yellow", true, 7);
        Game tGame = new Game(tPlayer, tPlayer2);
        tGame.tryCreate(tPlayer, 'A', 0);
        tGame.moveMove(tPlayer, 'A', "down");
        tGame.tryCreate(tPlayer, 'B', 0);
        tGame.moveMove(tPlayer, 'B', "down");
        tGame.tryCreate(tPlayer, 'C', 0);
        tGame.moveMove(tPlayer, 'C', "down");
        assertEquals('A', tGame.getBoard().get(5, 2).letter());
        assertEquals('B', tGame.getBoard().get(4, 2).letter());
        assertEquals('C', tGame.getBoard().get(3, 2).letter());
        assertTrue(tGame.getBoard().get(2, 2) instanceof Empty);
    }

    /**
     * Tests that moving a piece into a face kills it
     */
    @Test
    public void faceDestructionMove(){
        Player tPlayer = new Player("Green", true, 2);
        Player tPlayer2 = new Player("Yellow", true, 7);
        Game tGame = new Game(tPlayer, tPlayer2);
        //C is a piece with all shields, so can move into face without win
        tGame.tryCreate(tPlayer, 'C', 0);
        tGame.moveMove(tPlayer, 'C', "up");
        tPlayer.resetPieces();
        assertEquals('C', tGame.getBoard().get(1, 2).letter());
        //This should kill the piece
        tGame.moveMove(tPlayer, 'C', "left");
        assertTrue(tGame.getBoard().get(1, 2) instanceof Empty);
        assertEquals('C', tGame.getBoard().getCemetery().getGrid()[0][0].letter());
        assertNull(tPlayer.getInPlayPiece('C'));
    }

    /**
     * Tests that moving a piece into a out of bounds spot kills it
     */
    @Test
    public void boundsDestructionMove(){
        Player tPlayer = new Player("Green", true, 2);
        Player tPlayer2 = new Player("Yellow", true, 7);
        Game tGame = new Game(tPlayer, tPlayer2);
        tGame.tryCreate(tPlayer, 'C', 0);
        tGame.moveMove(tPlayer, 'C', "up");
        tPlayer.resetPieces();
        tGame.moveMove(tPlayer, 'C', "up");
        tPlayer.resetPieces();
        assertEquals('C', tGame.getBoard().get(0, 2).letter());
        //This should kill the piece
        tGame.moveMove(tPlayer, 'C', "left");
        assertTrue(tGame.getBoard().get(0, 2) instanceof Empty);
        assertEquals('C', tGame.getBoard().getCemetery().getGrid()[0][0].letter());
        assertNull(tPlayer.getInPlayPiece('C'));
    }

    /**
     * Tests that moving a piece off the board kills it
     */
    @Test
    public void boardDestructionMove(){
        Player tPlayer = new Player("Green", true, 2);
        Player tPlayer2 = new Player("Yellow", true, 7);
        Game tGame = new Game(tPlayer, tPlayer2);
        tGame.tryCreate(tPlayer, 'C', 0);
        tGame.moveMove(tPlayer, 'C', "up");
        tPlayer.resetPieces();
        tGame.moveMove(tPlayer, 'C', "up");
        tPlayer.resetPieces();
        assertEquals('C', tGame.getBoard().get(0, 2).letter());
        //This should kill the piece
        tGame.moveMove(tPlayer, 'C', "up");
        assertTrue(tGame.getBoard().get(0, 2) instanceof Empty);
        assertEquals('C', tGame.getBoard().getCemetery().getGrid()[0][0].letter());
        assertNull(tPlayer.getInPlayPiece('C'));
    }

    /**
     * Tests that moving a piece off the board with another kills it
     */
    @Test
    public void shiftDestructionMove(){
        Player tPlayer = new Player("Green", true, 2);
        Player tPlayer2 = new Player("Yellow", true, 7);
        Game tGame = new Game(tPlayer, tPlayer2);
        //C is a piece with all shields, so can move into face without win
        tGame.tryCreate(tPlayer, 'A', 0);
        tGame.moveMove(tPlayer, 'A', "up");
        tGame.tryCreate(tPlayer, 'B', 0);
        tGame.moveMove(tPlayer, 'B', "up");
        assertEquals('A', tGame.getBoard().get(0, 2).letter());
        tGame.tryCreate(tPlayer, 'C', 0);
        tGame.moveMove(tPlayer, 'C', "up");
        assertEquals('C', tGame.getBoard().get(1, 2).letter());
        assertEquals('B', tGame.getBoard().get(0, 2).letter());
        assertEquals('A', tGame.getBoard().getCemetery().getGrid()[0][0].letter());
        assertNull(tPlayer.getInPlayPiece('A'));
    }

    /**
     * Tests that creating a piece then undoing it works
     */
    @Test
    public void simpleUndo1(){
        Player tPlayer = new Player("Green", true, 2);
        Player tPlayer2 = new Player("Yellow", true, 7);
        Game tGame = new Game(tPlayer, tPlayer2);
        tGame.tryCreate(tPlayer, 'A', 180);
        creationChecks('A', tGame, tPlayer);
        //Now undo
        tGame.undoCreation(tPlayer.getInPlayPiece('A'), 180);
        Weapon[] weaponLayout = {Weapon.SWORD, Weapon.SHIELD, Weapon.SWORD, Weapon.SWORD};
        checkRotationWithPlayer(tPlayer, 'A', weaponLayout);
        confirmInHand('A', tPlayer);
        assertTrue(tGame.getBoard().get(2, 2) instanceof Empty);
    }

    /**
     * Tests that creating a piece, moving then undoing it works
     */
    @Test
    public void simpleUndo2(){
        Player tPlayer = new Player("Green", true, 2);
        Player tPlayer2 = new Player("Yellow", true, 7);
        Game tGame = new Game(tPlayer, tPlayer2);
        tGame.tryCreate(tPlayer, 'A', 0);
        creationChecks('A', tGame, tPlayer);
        Piece piece = (Piece) tGame.getBoard().get(2);
        tGame.moveMove(tPlayer, 'A', "up");
        assertEquals('A', tGame.getBoard().get(1, 2).letter());
        assertTrue(piece.hasMoved());
        //Now undo
        tGame.undoMove(tPlayer.getInPlayPiece('A'), 1, new Direction(-1, 0));
        assertEquals('A', tGame.getBoard().get(2, 2).letter());
        assertFalse(piece.hasMoved());
        tGame.undoCreation(tPlayer.getInPlayPiece('A'), 0);
        confirmInHand('A', tPlayer);
        assertTrue(tGame.getBoard().get(2, 2) instanceof Empty);
    }

    /**
     * Tests that creating a piece, rotating then undoing it works
     */
    @Test
    public void simpleUndo3(){
        Player tPlayer = new Player("Green", true, 2);
        Player tPlayer2 = new Player("Yellow", true, 7);
        Game tGame = new Game(tPlayer, tPlayer2);
        tGame.tryCreate(tPlayer, 'A', 0);
        creationChecks('A', tGame, tPlayer);
        Piece piece = (Piece) tGame.getBoard().get(2);
        tGame.rotationMove(tPlayer, 'A', 2);
        assertEquals('A', tGame.getBoard().get(2, 2).letter());
        assertTrue(piece.hasMoved());
        //Now undo
        tGame.undoRotation(tPlayer.getInPlayPiece('A'), 2);
        Weapon[] weaponLayout = {Weapon.SWORD, Weapon.SHIELD, Weapon.SWORD, Weapon.SWORD};
        checkRotation(2, 2, tGame, weaponLayout);
        assertFalse(piece.hasMoved());
        tGame.undoCreation(tPlayer.getInPlayPiece('A'), 0);
        confirmInHand('A', tPlayer);
        assertTrue(tGame.getBoard().get(2, 2) instanceof Empty);
    }


    /**
     * Tests that moving a pieces that shifts others can be undone correctly
     */
    @Test
    public void shiftUndo1(){
        Player tPlayer = new Player("Green", true, 2);
        Player tPlayer2 = new Player("Yellow", true, 7);
        Game tGame = new Game(tPlayer, tPlayer2);
        tGame.tryCreate(tPlayer, 'A', 0);
        Piece pieceA = (Piece) tGame.getBoard().get(2);
        tGame.moveMove(tPlayer, 'A', "up");

        tPlayer.resetPieces(); //Simulate turn ending, A can now move again.

        tGame.tryCreate(tPlayer, 'B', 0);
        Piece pieceB = (Piece) tGame.getBoard().get(2);
        tGame.moveMove(tPlayer, 'B', "up");

        assertEquals('A', tGame.getBoard().get(0, 2).letter());
        assertEquals('B', tGame.getBoard().get(1, 2).letter());
        assertFalse(pieceA.hasMoved());
        assertTrue(pieceB.hasMoved());

        tGame.undoMove(tPlayer.getInPlayPiece('B'), 2, new Direction(-1, 0));

        assertEquals('A', tGame.getBoard().get(1, 2).letter());
        assertEquals('B', tGame.getBoard().get(2, 2).letter());
        assertFalse(pieceA.hasMoved());
        assertFalse(pieceB.hasMoved());
    }

    /**
     * Tests that moving a pieces that shifts others can be undone correctly
     */
    @Test
    public void shiftUndo2(){
        Player tPlayer = new Player("Green", true, 2);
        Player tPlayer2 = new Player("Yellow", true, 7);
        Game tGame = new Game(tPlayer, tPlayer2);
        tGame.tryCreate(tPlayer, 'A', 0);
        Piece pieceA = (Piece) tGame.getBoard().get(2);
        tGame.moveMove(tPlayer, 'A', "right");

        tPlayer.resetPieces(); //Simulate turn ending, A can now move again.

        tGame.tryCreate(tPlayer, 'B', 0);
        Piece pieceB = (Piece) tGame.getBoard().get(2);
        tGame.moveMove(tPlayer, 'B', "right");

        tPlayer.resetPieces(); //Simulate turn ending, A & B can now move again.

        tGame.tryCreate(tPlayer, 'C', 0);
        Piece pieceC = (Piece) tGame.getBoard().get(2);
        tGame.moveMove(tPlayer, 'C', "right");

        assertEquals('A', tGame.getBoard().get(2, 5).letter());
        assertEquals('B', tGame.getBoard().get(2, 4).letter());
        assertEquals('C', tGame.getBoard().get(2, 3).letter());
        assertFalse(pieceA.hasMoved());
        assertFalse(pieceB.hasMoved());
        assertTrue(pieceC.hasMoved());

        tGame.undoMove(tPlayer.getInPlayPiece('C'), 3, new Direction(0, 1));

        assertEquals('A', tGame.getBoard().get(2, 4).letter());
        assertEquals('B', tGame.getBoard().get(2, 3).letter());
        assertEquals('C', tGame.getBoard().get(2, 2).letter());
        assertFalse(pieceA.hasMoved());
        assertFalse(pieceB.hasMoved());
        assertFalse(pieceC.hasMoved());
    }

    /**
     * Tests that moving a pieces that shifts others can be undone correctly
     */
    @Test
    public void shiftKillUndo1(){
        Player tPlayer = new Player("Green", true, 2);
        Player tPlayer2 = new Player("Yellow", true, 7);
        Game tGame = new Game(tPlayer, tPlayer2);
        tGame.tryCreate(tPlayer, 'A', 0);
        Piece pieceA = (Piece) tGame.getBoard().get(2);
        tGame.moveMove(tPlayer, 'A', "left");

        tPlayer.resetPieces(); //Simulate turn ending, A can now move again.

        tGame.tryCreate(tPlayer, 'B', 0);
        Piece pieceB = (Piece) tGame.getBoard().get(2);
        tGame.moveMove(tPlayer, 'B', "left");

        tPlayer.resetPieces(); //Simulate turn ending, A & B can now move again.

        tGame.tryCreate(tPlayer, 'C', 0);
        Piece pieceC = (Piece) tGame.getBoard().get(2);
        Action action = tGame.moveMove(tPlayer, 'C', "left");

        checkDeath('A', tGame, tPlayer, 2, 0);
        assertEquals('B', tGame.getBoard().get(2, 0).letter());
        assertEquals('C', tGame.getBoard().get(2, 1).letter());
        assertFalse(pieceA.hasMoved());
        assertFalse(pieceB.hasMoved());
        assertTrue(pieceC.hasMoved());

        action.undo(tGame);

        checkAlive('A', tGame, tPlayer, 2, 0);
        assertEquals('B', tGame.getBoard().get(2, 1).letter());
        assertEquals('C', tGame.getBoard().get(2, 2).letter());
        assertFalse(pieceA.hasMoved());
        assertFalse(pieceB.hasMoved());
        assertFalse(pieceC.hasMoved());
    }

    /**
     * Tests that moving a piece into a boarder then undoing revives it
     */
    @Test
    public void deathUndo1(){
        Player tPlayer = new Player("Green", true, 2);
        Player tPlayer2 = new Player("Yellow", true, 7);
        Game tGame = new Game(tPlayer, tPlayer2);
        tGame.tryCreate(tPlayer, 'A', 0);
        Piece pieceA = (Piece) tGame.getBoard().get(2);
        tGame.moveMove(tPlayer, 'A', "left");
        //reset and move again
        tPlayer.resetPieces();
        tGame.moveMove(tPlayer, 'A', "left");
        assertEquals('A', tGame.getBoard().get(2, 0).letter());
        //This kills the piece
        tPlayer.resetPieces();
        tGame.moveMove(tPlayer, 'A', "left");
        assertTrue(tGame.getBoard().get(2, 0) instanceof Empty);
        assertEquals('A', tGame.getBoard().getCemetery().getGrid()[0][0].letter());

        tGame.undoMove(pieceA, 1, new Direction(0, -1));

        assertEquals('A', tGame.getBoard().get(2, 0).letter());
        assertFalse(pieceA.hasMoved());
        assertNotNull(tPlayer.getInPlayPiece('A'));
        assertEquals(0, tGame.getBoard().getCemetery().getGrid()[0][0].letter());
    }

    /**
     * Tests that moving a piece into a out of bounds then undoing revives it
     */
    @Test
    public void deathUndo2(){
        Player tPlayer = new Player("Green", true, 2);
        Player tPlayer2 = new Player("Yellow", true, 7);
        Game tGame = new Game(tPlayer, tPlayer2);
        tGame.tryCreate(tPlayer, 'A', 0);
        Piece pieceA = (Piece) tGame.getBoard().get(2);
        tGame.moveMove(tPlayer, 'A', "left");
        //reset and move again
        tPlayer.resetPieces();
        tGame.moveMove(tPlayer, 'A', "left");
        assertEquals('A', tGame.getBoard().get(2, 0).letter());
        //This kills the piece
        tPlayer.resetPieces();
        tGame.moveMove(tPlayer, 'A', "up");
        assertTrue(tGame.getBoard().get(2, 0) instanceof Empty);
        assertEquals('A', tGame.getBoard().getCemetery().getGrid()[0][0].letter());

        tGame.undoMove(pieceA, 1, new Direction(-1, 0));

        assertEquals('A', tGame.getBoard().get(2, 0).letter());
        assertFalse(pieceA.hasMoved());
        assertNotNull(tPlayer.getInPlayPiece('A'));
        assertEquals(0, tGame.getBoard().getCemetery().getGrid()[0][0].letter());
    }

    /**
     * Tests that moving a piece into a face then undoing revives it
     */
    @Test
    public void deathUndo3(){
        Player tPlayer = new Player("Green", true, 2);
        Player tPlayer2 = new Player("Yellow", true, 7);
        Game tGame = new Game(tPlayer, tPlayer2);
        tGame.tryCreate(tPlayer, 'C', 0);
        Piece pieceA = (Piece) tGame.getBoard().get(2);
        tGame.moveMove(tPlayer, 'C', "left");
        //reset and move again
        //This kills the piece
        tPlayer.resetPieces();
        tGame.moveMove(tPlayer, 'C', "up");
        assertTrue(tGame.getBoard().get(2, 1) instanceof Empty);
        assertEquals('C', tGame.getBoard().getCemetery().getGrid()[0][0].letter());

        tGame.undoMove(pieceA, 1, new Direction(-1, 0));

        assertEquals('C', tGame.getBoard().get(2, 1).letter());
        assertFalse(pieceA.hasMoved());
        assertNotNull(tPlayer.getInPlayPiece('C'));
        assertEquals(0, tGame.getBoard().getCemetery().getGrid()[0][0].letter());
    }

    /**
     * Tests that a simple sword sword reaction behaves as expected
     */
    @Test
    public void reactionAndUndo1(){
        Player tPlayer = new Player("Green", true, 2);
        Player tPlayer2 = new Player("Yellow", true, 7);
        Game tGame = new Game(tPlayer, tPlayer2);
        tGame.tryCreate(tPlayer, 'A', 0);
        Piece pieceA = (Piece) tGame.getBoard().get(2);
        tGame.moveMove(tPlayer, 'A', "down");
        tPlayer.resetPieces();

        tGame.tryCreate(tPlayer, 'G', 0);
        Piece pieceB = (Piece) tGame.getBoard().get(2);
        //There should now be a sword sword reaction between these two pieces
        assertTrue(tGame.hasReactions());
        List<Reaction> results = tGame.getReactions();
        Action action = tGame.react(results.get(0));

        Cemetery cemetery = tGame.getBoard().getCemetery();
        checkDeath('A', tGame, tPlayer,3, 2);
        checkDeath('G', tGame, tPlayer,2, 2);

        //UNDO NOW
        action.undo(tGame);
        checkAlive('A', tGame, tPlayer, 3, 2);
        checkAlive('G', tGame, tPlayer, 2, 2);
        assertFalse(pieceA.hasMoved());
        assertFalse(pieceB.hasMoved());
    }

    /**
     * Tests that a simple sword nothing reaction behaves as expected
     */
    @Test
    public void reactionAndUndo2(){
        Player tPlayer = new Player("Green", true, 2);
        Player tPlayer2 = new Player("Yellow", true, 7);
        Game tGame = new Game(tPlayer, tPlayer2);
        tGame.tryCreate(tPlayer, 'E', 0);
        Piece pieceA = (Piece) tGame.getBoard().get(2);
        tGame.moveMove(tPlayer, 'E', "right");
        tPlayer.resetPieces();

        tGame.tryCreate(tPlayer, 'G', 0);
        Piece pieceB = (Piece) tGame.getBoard().get(2);
        //There should now be a sword sword reaction between these two pieces
        assertTrue(tGame.hasReactions());
        List<Reaction> results = tGame.getReactions();
        Action action = tGame.react(results.get(0));

        Cemetery cemetery = tGame.getBoard().getCemetery();
        checkDeath('E', tGame, tPlayer,2, 3);

        //UNDO NOW
        action.undo(tGame);
        checkAlive('E', tGame, tPlayer, 2, 3);
        assertFalse(pieceA.hasMoved());
        assertFalse(pieceB.hasMoved());
    }

    /**
     * Tests that a simple shield sword reaction behaves as expected
     */
    @Test
    public void reactionAndUndo3(){
        Player tPlayer = new Player("Green", true, 2);
        Player tPlayer2 = new Player("Yellow", true, 7);
        Game tGame = new Game(tPlayer, tPlayer2);
        tGame.tryCreate(tPlayer, 'C', 0);
        Piece pieceA = (Piece) tGame.getBoard().get(2);
        tGame.moveMove(tPlayer, 'C', "left");
        tPlayer.resetPieces();

        tGame.tryCreate(tPlayer, 'O', 90);
        Piece pieceB = (Piece) tGame.getBoard().get(2);
        //There should now be a shield sword reaction between these two pieces
        assertTrue(tGame.hasReactions());
        List<Reaction> results = tGame.getReactions();
        assertEquals('C', tGame.getBoard().get(2, 1).letter());
        assertEquals('O', tGame.getBoard().get(2, 2).letter());
        Action action = tGame.react(results.get(0));

        assertEquals('C', tGame.getBoard().get(2, 1).letter());
        assertEquals('O', tGame.getBoard().get(2, 3).letter());

        //UNDO NOW
        action.undo(tGame);
        assertEquals('O', tGame.getBoard().get(2, 2).letter());
        assertFalse(pieceA.hasMoved());
        assertFalse(pieceB.hasMoved());
    }

    private void checkAlive(char letter, Game game, Player player, int row, int col){
        Board board = game.getBoard();
        Cemetery cemetery = board.getCemetery();
        assertEquals(letter, board.get(row, col).letter());
        assertNotNull(player.getInPlayPiece(letter));
        assertFalse(cemetery.contains(letter));
    }

    private void checkDeath(char letter, Game game, Player player, int row, int col){
        Board board = game.getBoard();
        Cemetery cemetery = board.getCemetery();
        assertTrue(cemetery.contains(letter));
        assertNull(player.getInPlayPiece(letter));
        assertNotEquals(letter, board.get(row, col).letter());
    }

    //HAND refers to the pieces the player can create!
    private void confirmNotInHand(char l, Player p){
        for(BoardItem[] row : p.getGrid()){
            for(BoardItem b : row){
                if(b.letter() == l) fail("Should not have " + l + " in hand!");
            }
        }
    }

    //HAND refers to the pieces the player can create!
    private void confirmInHand(char l, Player p){
        for(BoardItem[] row : p.getGrid()){
            for(BoardItem b : row){
                if(b.letter() == l) return;
            }
        }
        fail("Should have " + l + " in hand!");
    }

    private void creationChecks(char l, Game g,  Player p){
        assertEquals(l, g.getBoard().getGrid()[2][2].letter());
        BoardItem[][] hand = p.getGrid();
        for(BoardItem[] row : hand){
            for(BoardItem item : row){
                assertNotEquals(l, item.letter());
            }
        }
        assertNotNull(p.getInPlayPiece(l));
    }

    private void checkRotation(int row, int col, Game g, Weapon[] layout){
        //Safe cast because we just tested this in creationChecks
        Piece piece = (Piece) g.getBoard().get(row, col);
        Weapon[] pieceLayout = piece.getWeapons();
        for(int i = 0; i<layout.length; i++){
            assertEquals(layout[i], pieceLayout[i]);
        }
    }

    private void checkRotationWithPlayer(Player player, char letter, Weapon[] layout){
        //Safe cast because we just tested this in creationChecks
        Piece piece = player.removePieceHand(letter);
        Weapon[] pieceLayout = piece.getWeapons();
        for(int i = 0; i<layout.length; i++){
            assertEquals(layout[i], pieceLayout[i]);
        }
        player.putBackInHand(piece);
    }
}
