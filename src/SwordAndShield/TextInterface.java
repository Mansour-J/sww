package SwordAndShield;

import java.util.List;
import java.util.Scanner;
import java.util.Stack;
import java.util.concurrent.TimeUnit;

/**
 * A text interface for the sword and shield game
 *
 * @author David Hack
 * @version 1.0
 */
public class TextInterface {
    private Player green;
    private Player yellow;

    private Game game;
    private boolean gameWon = false;

    private Stack<GameAction> curTurn = new Stack<>();

    private TextInterface(){
        green = new Player("Green", true, 2);
        yellow = new Player("Yellow", false, 7);
        game = new Game(green, yellow);

        run();
    }

    private void run(){
        while(!gameWon) { //this loop WILL break if the game is quit or won, dw about it
            executeTurn(yellow);
            if(gameWon) break; //check in between turns
            executeTurn(green);
        }
    }

    /**
     * draws all the main components of the game.
     */
    private void draw(){
        green.draw();
        yellow.draw();
        game.draw();
    }

    /**
     * Handles a turn for a player
     * @param player who's turn it is
     */
    private void executeTurn(Player player){
        curTurn = new Stack<>();
        draw();
        creation(player);
        draw();
        moves(player);
        player.resetPieces();
    }

    /**
     * Conducts the creation phase of the players turn
     *
     * @param player the player who's turn it is.
     */
    private void creation(Player player){
        boolean validInput = false;
        String prompt = "Would " + player.getName() + " like to 'pass' CREATION, OR 'create <letter> <0/90/180/270>'";

        while(!validInput) { //while we are not happy with input
            System.out.println(prompt); //formatting plus prompt
            System.out.println("---------------------------");
            String input = getInput();

            if (input.equals("pass")) { //user may pass if they wish
                validInput = true;

            } else if(input.equals("undo")){
                prompt = "You are at the start of your turn, nothing to undo!";

            }else if(input.matches("create [a-zA-z] (0|90|180|270)")){
                //try to to create
                try{
                    HelperClass action = game.tryCreate(player, input.charAt(7), Integer.parseInt(input.split(" ")[2]));
                    curTurn.push(action);
                    validInput = true;
                }catch(GameContinuityException e){
                    prompt = e.getMessage();
                }
            }else{
                prompt = "Please enter a valid command"; //formatting and change prompt
            }
            System.out.println("---------------------------");
        }
    }

    /**
     * Conducts the movement phase of the game, this will also involve reactions.
     * While they player has pieces to move or hasn't typed pass this method continues
     * to ask for moves.
     *
     * @param player the player who's turn it is.
     */
    private void moves(Player player){
        //Creation could have caused a reaction.
        if(game.hasReactions()){ //Check reactions possible to win.
            if(gameWon = reactions(player)) return;
        }

        String prompt = player.getName() + " may now MOVE or ROTATE each piece they control once\n" +
                "'rotate <letter> <1-4>' OR\n" +
                "'move <letter> <up/right/down/left>' OR 'pass'";

        boolean passed = false;

        //Loop while the the player has pieces to move
        while(!passed){
            System.out.println(prompt + "\n---------------------------"); //print some formatting
            String input = getInput();

            if(input.equals("pass")){ //if they wan't to pass
                passed = true;
            }else if(input.equals("undo")){
                undo(player);
            }else if(input.matches("rotate [a-zA-Z] (0|90|180|270)")){
                //if they have a valid rotate request in syntax
                try{
                    RotationAct action = game.rotationMove(player, input.charAt(7), Integer.parseInt(input.split(" ")[2]));
                    curTurn.push(action);
                }catch(GameContinuityException e){
                    prompt = e.getMessage();
                    continue; //skip drawing
                }
            }else if(input.matches("move [a-zA-Z] (up|down|left|right)")){
                //If they have a valid move request in syntax
                try{
                    MoveAct action = game.moveMove(player, input.charAt(5), input.split(" ")[2]);
                    curTurn.push(action);
                }catch(GameContinuityException e){
                    prompt = e.getMessage();
                    continue; //skip drawing
                }
            }else{ //If there input wasn't valid
                prompt = "Please enter a valid command";
                continue; //skip drawing
            }

            if(game.hasReactions()){ //Check reactions possible to win.
                if(gameWon = reactions(player)) return;
            }

            //print formatting, and default message after first
            System.out.println("---------------------------");
            String remaining = player.getRemaining();
            prompt = "Pieces that you can MOVE/ROTATE\n" + ((remaining.length() > 0)? remaining : "NONE, Pass?");
            draw();
        }

        //Turn is over
        System.out.println(">>>>" + player.getName() + "'s turn is now over!<<<<");
        //Sleep for a bit so users can see
        sleep(2);
    }

    /**
     * Undos a turn with a little bit of logic to figure out if the player needs to be asked to create again.
     * @param player who is try to undo
     */
    private void undo(Player player){
        if(curTurn.isEmpty()){ //could be like this if they pass creation
            creation(player);
        }else{ //otherwise no matter the action, undo it
            GameAction gameAction = curTurn.pop();
            gameAction.undo(game);
            if(curTurn.isEmpty() && gameAction instanceof HelperClass){
                draw();
                creation(player); //re ask if they want creation
            }
        }
    }

    /**
     * Handles reaction segments of a players turn.
     * @param player who's turn it is
     * @return true if the game was won
     */
    private boolean reactions(Player player){
        String prompt = "";
        do{
            draw();
            List<Reaction> possibleReactions = game.getReactions();
            System.out.println("Reactions! Very exciting. choose one by entering '<letter> <letter>'");
            System.out.print(prompt);
            for(Reaction r : possibleReactions){
                System.out.print(r);
            }
            System.out.println("\n---------------------------");

            String input = getInput();

            if(input.equals("undo")){
                undo(player);
            }else if(input.matches("[a-zA-Z] ([a-zA-Z]|[0-1])")){
                Reaction chosen = find(input, possibleReactions);
                if(chosen != null){
                    if(chosen.isFaceReaction()){
                        printVictory(chosen);
                        return true;
                    }else {
                        GameAction gameAction = game.react(chosen);
                        curTurn.push(gameAction);
                        prompt = "";
                    }
                }else{
                    prompt = "Your chosen reaction was not found, please try again.\n";
                }
            }else{
                prompt = "Please enter a valid input.\n";
            }

        }while(game.hasReactions());
        draw();
        return false;
    }

    /**
     * Prints out victory stuff
     * @param winningReaction the winning reaction
     */
    private void printVictory(Reaction winningReaction){
        if(!(winningReaction.getDefenderPiece() instanceof Face)) return;

        Piece attacker = winningReaction.getSwordAttackPiece();
        Face face = (Face) winningReaction.getDefenderPiece();
        Player loosing = face.getPlayer();

        System.out.println("\n" + loosing.getName() + " Lost!\n" +
                "Winning attack was from piece " + attacker.defaultValue());
    }

    /**
     * Finds the reaction the user queried
     * @param input input string
     * @param toSearch reactions to search
     * @return a reaction if one as found
     */
    private Reaction find(String input, List<Reaction> toSearch){
        char a = input.charAt(0);
        char b = input.charAt(2);

        for(Reaction r : toSearch){
            if(r.matches(a, b)){
                return r;
            }
        }
        return null;
    }

    /**
     * Sleeps system
     * @param s time in seconds
     */
    private void sleep(int s){
        try {
            TimeUnit.SECONDS.sleep(s);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets input from System.in.
     * @return input
     */
    private static String getInput(){
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }

    /**
     * Starts the game.
     * @param args, arguments
     */
    public static void main(String[] args) {
        new TextInterface();
    }
}
