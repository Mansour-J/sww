package SwordAndShield;

/**
 * A class representing each piece of a reaction, the direction from attack to defender
 * as well as the defender weapon type. The attacker must have a sword
 *
 * @author David Hack
 * @version 1.0
 */
public class Reaction {

    private Piece swordAttackPiece;
    private BoardItem defenderPiece;
    private Weapon defendersWeapon;
    private GameDirection relDirection;

    /**
     * Creates a new reaction, with all the needed information
     * @param swordAttackPiece the piece doing the attacking
     * @param defenderPiece the piece doing the defending
     * @param defendersWeapon the weapon the defender is using
     * @param relDirection direction of the attack
     */
    Reaction(Piece swordAttackPiece, BoardItem defenderPiece, Weapon defendersWeapon, GameDirection relDirection) {
        this.swordAttackPiece = swordAttackPiece;
        this.defenderPiece = defenderPiece;
        this.defendersWeapon = defendersWeapon;
        this.relDirection = relDirection;
    }

    /**
     * Getter for the string form of this reaction.
     * @return string of the reaction
     */
    public String toString(){
        return "[" + swordAttackPiece.defaultValue() + " >-ATTACK-> " + defenderPiece.defaultValue() + "]";
    }

    /**
     * Checks if two chars match this reaction
     * @param a char A
     * @param b char B
     * @return true if they match, including order
     */
    boolean matches(char a, char b){
        return swordAttackPiece.defaultValue() == a && defenderPiece.defaultValue() == b;
    }

    /**
     * Returns the defenders weapon
     * @return defenders weapon
     */
    Weapon getDefendersWeapon() {
        return defendersWeapon;
    }

    /**
     * Returns the attacking piece.
     * @return attacking piece
     */
    Piece getSwordAttackPiece() {
        return swordAttackPiece;
    }

    /**
     * Returns the defending piece.
     * @return defending piece
     */
    BoardItem getDefenderPiece() {
        return defenderPiece;
    }

    /**
     * Returns the direction of the attack.
     * @return direction
     */
    GameDirection getRelDirection() {
        return relDirection;
    }

    /**
     * Returns true or false for checking if it is a face reaction
     * @return true if defender is face.
     */
    boolean isFaceReaction(){
        return defenderPiece instanceof Face;
    }
}
