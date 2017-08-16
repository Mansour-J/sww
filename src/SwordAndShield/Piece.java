package SwordAndShield;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a actual piece that is in play on the board or
 * in other areas such as hands and cemetery
 * By index:
 * North: 0, East: 1, South: 2, West: 3
 *
 * @author David Hack
 * @version 1.0
 */
public class Piece implements BoardItem{
    private Weapon[] weapons = new Weapon[4]; //North: 0, East: 1, South: 2, West: 3
    private char[] weaponChars = new char[4]; //North: 0, East: 1, South: 2, West: 3
    private char letter;
    private boolean moved = false;
    private Player player;
    private Location location;

    /**
     * Construct a piece from the char and the provided augmentations
     * can be any combination of nothing, sword & shield for each side
     * of the piece
     *
     * @param l the letter in the center
     * @param north the north part of the piece
     * @param east the east part of the piece
     * @param south the south part of the piece
     * @param west the west part of the piece
     */
    public Piece(Player p,  char l, Weapon north, Weapon east, Weapon south, Weapon west){
        letter = l;
        weapons[0] = north; weapons[1] = east;
        weapons[2] = south; weapons[3] = west;
        player = p;

        setWeaponChars();
    }

    /**
     * Rotates by a certain amount in degrees.
     * Clockwise.
     * @param rotation, in degrees clockwise
     */
    void rotate(int rotation){
        switch (rotation){
            case 270:
                rotateBy(3);
                break;
            case 180:
                rotateBy(2);
                break;
            case 90:
                rotateBy(1);
                break;
        }
    }

    /**
     * Rotates 90 degrees clockwise a certain number of times.
     * @param times, how many times to rotate
     */
    private void rotateBy(int times){
        for(int i = 0; i<times; i++){
            Weapon end = weapons[3];
            for(int j = 3; j>0; j--){
                weapons[j] = weapons[j-1];
            }
            weapons[0] = end;
        }
        setWeaponChars();
    }

    /**
     * Returns a list of directions/locations that have swords on them.
     * @return list of directions that can react
     */
    List<GameDirection> reactableDirectionsRel(){
        List<GameDirection> locations = new ArrayList<>();
        if(weapons[0] == Weapon.SWORD){ //Up, North
            locations.add(new GameDirection(-1, 0));
        }
        if(weapons[1] == Weapon.SWORD){ //Right, East
            locations.add(new GameDirection(0, 1));
        }
        if(weapons[2] == Weapon.SWORD){ //Down, South
            locations.add(new GameDirection(1, 0));
        }
        if(weapons[3] == Weapon.SWORD){ //Left, West
            locations.add(new GameDirection(0, -1));
        }
        return locations;
    }

    /**
     * Finds what weapon this piece responds with when being attacked from a certain direction.
     * @param relative Direction getting attacked from
     * @return weapon to defend with
     */
    Weapon getDefence(GameDirection relative){
        if(relative.getRow() == -1){ //They attack up, return bot
            return weapons[2];
        }else if(relative.getRow() == 1){ //They attack down, return top
            return weapons[0];
        }else if(relative.getCol() == -1){ //They attack left, return right
            return weapons[1];
        }else if(relative.getCol() == 1){ //They attack right, return left
            return weapons[3];
        }else{
            throw new GameContinuityException("Not a valid direction");
        }
    }

    /**
     * Removes piece from players inplay pieces.
     */
    void removeFromPlay(){
        player.removePiecePlay(letter);
    }

    /**
     * resurrects this piece to its player.
     */
    void resurrect(){
        player.putBackInPlay(this);
    }

    /**
     * Draws the TOP row of the piece including the weapon
     * MUST BE OF 7 CHARACTERS WIDE
     */
    public void drawTop() {
        System.out.print("   " + weaponChars[0] + "   ");
    }

    /**
     * Draws the MID row of the piece including the weapons and the char
     * MUST BE OF 7 CHARACTERS WIDE
     */
    public void drawMid() {
        System.out.print(" " + weaponChars[3] + " " + letter +  " " + weaponChars[1] + " ");
    }

    /**
     * Draws the BOT row of the piece including the weapon
     * MUST BE OF 7 CHARACTERS WIDE
     */
    public void drawBot() {
        System.out.print("   " + weaponChars[2] + "   ");
    }

    /**
     * Returns the array of weapons on this piece
     *
     * @return Weapons[] array of weapons
     */
    public Weapon[] getWeapons(){
        return weapons.clone();
    }

    /**
     * Simple getter for the letter
     *
     * @return the letter of this piece
     */
    public char defaultValue(){
        return letter;
    }

    /**
     * Whether or not hte piece has moved or not.
     *
     * @return boolean
     */
    public boolean hasMoved() {
        return moved;
    }

    /**
     * Set moved to boolean supplied.
     *
     * @param b, set moved
     */
    void setMoved(boolean b){
        moved = b;
    }

    /**
     * Return the player owning this piece.
     *
     * @return SwordAndShield.Player
     */
    Player getPlayer() {
        return player;
    }

    /**
     * Getter for location of piece
     * @return location of piece
     */
    Location getLocation() {
        return location;
    }

    /**
     * Sets this pieces location
     * @param location location to set to
     */
    void setLocation(Location location) {
        this.location = location;
    }

    /**
     * Sets the char of weapons to the correct representations
     */
    private void setWeaponChars(){
        int i = 0;
        for(Weapon w : weapons){
            weaponChars[i] = getRepresentation(w, (i%2 == 0));
            i++;
        }
    }

    /**
     * Returns a text representation of the supplied weapon
     *
     * @param i weapon that they want represented
     * @param vert is it in the vertical orientation
     * @return char version of that weapon
     */
    private char getRepresentation(Weapon i, boolean vert){
        switch (i){
            case SWORD:
                return vert ? '|' : '-';
            case SHIELD:
                return '#';
            default:
                return ' ';
        }
    }
}
